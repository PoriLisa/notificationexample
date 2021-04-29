package com.lissa.notificationexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {
    var button: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "My Notification"
            val descriptionText = "My Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("My Notification", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
        button!!.setOnClickListener {
            addNotification();
        }
    }


    private fun addNotification() {
        val builder = NotificationCompat.Builder(this, "My Notification")
                .setSmallIcon(R.drawable.ic_launcher_background) //set icon for notification
                .setContentTitle("Notifications Example") //set title of notification
                .setContentText("This is a notification message") //this is notification message
                .setAutoCancel(true) // makes auto cancel of notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) //set priority of notification
        val notificationIntent = Intent(this, NotificationView::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", "This is a notification message")
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        // Add as notification
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())
    }
}