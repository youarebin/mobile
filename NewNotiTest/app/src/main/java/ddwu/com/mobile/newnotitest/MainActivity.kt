package ddwu.com.mobile.newnotitest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ddwu.com.mobile.newnotitest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        mainBinding.button.setOnClickListener{
            showNotification()
        }

    }

    val channelID by lazy {
        resources.getString(R.string.channel_id)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Notification Channel 의 생성

            val name = "Test Channel"
            val descriptionText = "Test Channel Message"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(channelID, name, importance)
            mChannel.description = descriptionText

            // Channel 을 시스템에 등록, 등록 후에는 중요도 변경 불가
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
            Toast.makeText(applicationContext, "${notificationManager.areNotificationsEnabled()}", Toast.LENGTH_SHORT).show()

            notificationManager.deleteNotificationChannel(channelID)    // 채널 삭제
        }
    }


    private fun showNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelID = resources.getString(R.string.channel_id)

        var newNoti = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("알림 제목")
            .setContentText("짧은 알림 내용")
            .setStyle(
                NotificationCompat.BigTextStyle()
                .bigText("확장시 확인할 수 있는 긴 알림 내용"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notiManager = NotificationManagerCompat.from(this)
        notiManager.notify(100, newNoti.build())

    }


    private fun showNotificationWithAction() {

    }

}