package kr.ac.hallym.backgroundnotice.JobScheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import kr.ac.hallym.backgroundnotice.R

class Notification( val context : Context) {


    /**
     *
     * @param title 알람 제목
     * @param text 알람 내용
     */
    fun notification(title : String, text : String){
//        applicationContext.context

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder : NotificationCompat.Builder

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelId = "one-channel"
            val channelName = "My channel One"
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "My Channel One Description"
                setShowBadge(true)
                val uri : Uri =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)

            }

            manager.createNotificationChannel(channel)

            builder = NotificationCompat.Builder(context, channelId)
        }else {
            builder = NotificationCompat.Builder(context)
        }
        builder.run{
            setSmallIcon(R.drawable.icon)
            setWhen(System.currentTimeMillis())
            setContentTitle(title)
            setContentText(text)
//            setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.icon_small))
        }


        manager.notify(11, builder.build())
    }
}