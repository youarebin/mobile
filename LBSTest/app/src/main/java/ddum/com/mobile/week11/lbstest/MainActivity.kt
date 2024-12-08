package ddum.com.mobile.week11.lbstest

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import ddum.com.mobile.week11.lbstest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivityTag"

    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //locationRequest(위치 정보 요청 클래스) 객체 생성
        locationRequest = LocationRequest.Builder(3000)
            .setMinUpdateIntervalMillis(5000)
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .build()
        //위치 수신 정보 클래스
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {//위치 정보들을 보관하는 클래스
                val currentLocation : Location = locationResult.locations[0]//위치 정보를 표현
                Log.d(TAG, "위도: ${currentLocation.latitude}, " +
                "경도: ${currentLocation.longitude}")
            }
        }

        //지오 코딩
        geocoder = Geocoder(this, Locale.getDefault())
        

        mainBinding.btnLocation.setOnClickListener {
            checkPermissions()
            startLocationRequest()
        }

        mainBinding.btnGeocoding.setOnClickListener {
            geocoder.getFromLocation(37.505816, 127.042383, 5){
                addresses ->
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d(TAG, addresses.get(0).getAddressLine(0).toString())
                }
            }
            geocoder.getFromLocationName("동덕여자대학교", 5){
                addresses ->
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d(TAG, "위도: ${addresses.get(0).latitude}, " +
                            "경도: ${addresses.get(0).longitude}")
                }
            }
        }

        mainBinding.btnExternal.setOnClickListener {
            callExternalMap()
        }
    }

    override fun onPause() {
        super.onPause()
        //위치 확인 종료
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun startLocationRequest() { //경고성이기 때문에 실행에는 문제 없음(permission 필요하다는말) : 버튼 누를때 permission불러오게 했음
        fusedLocationClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }

    fun callExternalMap() {
        val locLatLng   // 위도/경도 정보로 지도 요청 시
            = String.format("geo:%f,%f?z=%d", 37.606320, 127.041808, 17)
        val locName     // 위치명으로 지도 요청 시
                = "https://www.google.co.kr/maps/place/" + "Hawolgok-dong"
        val uri = Uri.parse(locLatLng)
        // val uri = Uri.parse(locName)

        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }




    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions() ) { permissions ->
        when {
            permissions.getOrDefault(ACCESS_FINE_LOCATION, false) ->
                Log.d(TAG, "정확한 위치 사용")
            permissions.getOrDefault(ACCESS_COARSE_LOCATION, false) ->
                Log.d(TAG, "근사 위치 사용")
            else ->
                Log.d(TAG, "권한 미승인")
        }
    }


    private fun checkPermissions() {
        if ( checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "필요 권한 있음")
//            startLocationRequest()
        } else {
            locationPermissionRequest.launch(
                arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            )
        }
    }
}