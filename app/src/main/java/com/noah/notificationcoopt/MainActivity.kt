package com.noah.notificationcoopt

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.noah.notificationcoopt.ui.theme.NotificationCoOptTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class) // Needs to be called to check permissions
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotificationCoOptTheme {
                val permission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS) // Creates a permission state to remember users choice
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Notification(
                        modifier = Modifier.padding(innerPadding),
                        context = this
                    )
                }
            }
        }
    }
}

@Composable
fun Notification(modifier: Modifier = Modifier, context: MainActivity ) {
    Box()
    {
        Column(modifier = modifier.padding(125.dp)) {
            Button(onClick = { sendNotification(context) }) {
                Text("Send notification")
            }
        }
    }
}

fun sendNotification(context: MainActivity){
    var builder = NotificationCompat.Builder(context, "Notification example") // Needs the same channel id as in the Notification Application
        .setSmallIcon(R.drawable.baseline_circle_notifications_24) // A small icon is required to make the notification
        .setContentTitle("New Notification!!!") // Title of the notification
        .setContentText("This is how you create a notification") // Body of the notification
        .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Sets how intrusive the notification is (Channel importance is used in Android 8.0 or higher instead in the NotificationApplication)

    with(NotificationManagerCompat.from(context)) { // Creates a notification manager and checks notification permissions
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            // ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            // public fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
            //                                        grantResults: IntArray)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        notify(1, builder.build()) // This calls the notification to the screen
    }
}