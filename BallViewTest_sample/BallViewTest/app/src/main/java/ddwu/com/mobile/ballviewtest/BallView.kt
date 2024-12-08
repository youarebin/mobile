package ddwu.com.mobile.ballviewtest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.AttributeSet
import android.util.Log
import android.util.MonthDisplayHelper
import android.view.View

class BallView : View {

    val TAG = "BallView"

    constructor(context: Context) : super(context) { initialize() }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){ initialize() }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initialize()}

    fun initialize() {
        isStart = true
        ballR = 100f

        /*센서 매니저 생성 및 센서 준비*/


    }

    lateinit var sensorManager : SensorManager
    lateinit var accelerometer : Sensor
    lateinit var magentometer : Sensor

    val mAccmeterReading = FloatArray(3)
    val mMagnetometerReading = FloatArray(3)

    var pitch: Float = 0f
    var roll: Float = 0f

    /*센서 이벤트 처리 리스너*/
    val listener : SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
                    /*가속도계와 지자기계를 이용하여 pitch, roll 확인*/

                    /*Sensing 값의 결과에 따라 x, y 좌표 값 변경*/

                    /*BallView 를 새로운 x, y 좌표로 다시 그림*/
                    invalidate()
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    }


    /*센싱 시작*/
    fun startListening () {
        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(listener, magentometer, SensorManager.SENSOR_DELAY_UI)
    }

    /*센싱 종료*/
    fun stopListening() {
        sensorManager.unregisterListener(listener)
    }

    /*Ball 을 그리는 도구 지정*/
    val paint: Paint by lazy {
        Paint().apply {
            color = Color.RED
            isAntiAlias = true
        }
    }

    var ballX : Float = 0f      // Ball 의 X 좌표
    var ballY : Float = 0f      // Ball 의 Y 좌표
    var ballR : Float = 0f      // Ball 의 반지름

    var width : Int? = 0        // 화면의 너비
    var height: Int? = 0        // 화면의 높이

    var isStart : Boolean = true    // 처음 그려질 때만 초기화 진행을 위한 구분 플래그

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isStart) {
            width = canvas?.width
            height = canvas?.height
            ballX = width?.div(2)?.toFloat() ?: 100f
            ballY = height?.div(2)?.toFloat() ?: 100f
            isStart = false
        }

        canvas?.drawCircle(ballX, ballY, ballR, paint)
    }
}