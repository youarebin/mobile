package ddwu.com.mobile.sensorlistenertest

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ddwu.com.mobile.sensorlistenertest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivityTag"

    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

        
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        mainBinding.btnStart.setOnClickListener {
//            lateinit var result : String

            /* 센싱 구현 */
            val sensorType = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            val delay = SensorManager.SENSOR_DELAY_UI

            sensorManager.registerListener(
                sensorEventListener,
                sensorType,
                delay
            )

//            Log.d(TAG, result)
        }

        mainBinding.btnStop.setOnClickListener {
            /* 센싱 종료 */
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)

    }

    val mAccmeterReading = FloatArray(3) // 가속도계 데이터를 저장할 배열
    val mMagnetometerReading = FloatArray(3) // 자기계 데이터를 저장할 배열

    val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            Log.d( TAG, "${ event?.values?.get(0) }" )

            //Device Orientation 구현
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                System.arraycopy(event.values, 0, mAccmeterReading, 0, mAccmeterReading.size)
            } else if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
                System.arraycopy(event.values, 0, mMagnetometerReading, 0, mMagnetometerReading.size)
            }
            if (mAccmeterReading.size != 0 && mMagnetometerReading.size != 0) {
                val rotationMatrix = FloatArray(9)
                val isSuccess: Boolean = SensorManager.getRotationMatrix(
                    rotationMatrix, null, mAccmeterReading, mMagnetometerReading
                )
                if (isSuccess) {
                    var values = FloatArray(3)
                    SensorManager.getOrientation(rotationMatrix, values)
                    for (i in values.indices) {
                        val degrees: Double = Math.toDegrees(values[i].toDouble())
                        values[i] = degrees.toFloat()
                    }
                    val azimuth: Float = values[0]
                    val pitch: Float = values[1]
                    val roll: Float = values[2]
                    Log.d(TAG, "azimuth: ${azimuth}, pitch: ${pitch}, roll: ${roll} ")
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

}