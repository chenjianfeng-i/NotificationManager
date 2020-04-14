package com.example.notificationmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

/**
 * 关于通知的学习，主要知识点：
 * NotificationManager，Notification，NotificationCompat.Builder的使用；
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private NotificationManager notificationManager;

    private int basicNotificationID = 0;
    private int basicNotificationWithDetailID = 10;
    private int juniorNotificationID = 20;
    private int higherNotificationID = 30;

    private int priorityID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mBtnBasicNotificationWithDetail = findViewById(R.id.base_notification_button_with_detail);
        Button mBtnJuniorNotification = findViewById(R.id.junior_notification_button);
        Button mBtnHigherNotification = findViewById(R.id.higher_notification_button);
        Button mBtnMinNotificationPriority = findViewById(R.id.min_notification_priority_button);
        Button mBtnLowNotificationPriority = findViewById(R.id.low_notification_priority_button);
        Button mBtnDefaultNotificationPriority = findViewById(R.id.default_notification_priority_button);
        Button mBtnHighNotificationPriority = findViewById(R.id.high_notification_priority_button);
        Button mBtnMaxNotificationPriority = findViewById(R.id.max_notification_priority_button);
        Button mBtnClearAllNotification = findViewById(R.id.clear_all_notification);
        mBtnBasicNotificationWithDetail.setOnClickListener(this);
        mBtnJuniorNotification.setOnClickListener(this);
        mBtnHigherNotification.setOnClickListener(this);
        mBtnMinNotificationPriority.setOnClickListener(this);
        mBtnLowNotificationPriority.setOnClickListener(this);
        mBtnDefaultNotificationPriority.setOnClickListener(this);
        mBtnHighNotificationPriority.setOnClickListener(this);
        mBtnMaxNotificationPriority.setOnClickListener(this);
        mBtnClearAllNotification.setOnClickListener(this);
        // 第一步：
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("1", "name", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.base_notification_button_with_detail:
                setBasicNotificationWithDetail();
                break;
            case R.id.junior_notification_button:
                setJuniorNotification();
                break;
            case R.id.higher_notification_button:
                setHigherNotification();
                break;
            case R.id.min_notification_priority_button:
                minPriorityNotification();
                break;
            case R.id.low_notification_priority_button:
                lowPriorityNotification();
                break;
            case R.id.default_notification_priority_button:
                defaultPriorityNotification();
                break;
            case R.id.high_notification_priority_button:
                highPriorityNotification();
                break;
            case R.id.max_notification_priority_button:
                maxPriorityNotification();
                break;
            case R.id.clear_all_notification:
                clearAllNotification();
                break;
            default:
                break;
        }
    }

    /**
     * 基本通知实现，就只是简单显示通知消息 - 没有配置通知详细展示页面
     * @param view 反射
     */
    public void setBasicNotification(View view){
        // 第二步：
        Notification notification = new NotificationCompat.Builder(this, "1")
                .setContentTitle("初级通知" + basicNotificationID)
                .setContentText("你有一条初级未读消息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.big_image)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .build();
        // 第三步：
        notificationManager.notify(basicNotificationID, notification);
        basicNotificationID++;
    }

    /**
     * 基本通知实现，就只是简单显示通知消息 - 配置通知详细展示页面
     */
    private void setBasicNotificationWithDetail(){
        // 第二步：
        Intent intent = new Intent(this, basicNotificationInfoPage.class);
        intent.putExtra("basic_notification_with_detail_ID", basicNotificationWithDetailID);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 第三步：
        Notification notification = new NotificationCompat.Builder(this, "1")
                .setContentTitle("初级通知(配置详细页面)" + basicNotificationWithDetailID)
                .setContentText("你有一条初级未读消息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.big_image)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();
        // 第四步：
        notificationManager.notify(basicNotificationWithDetailID, notification);
        basicNotificationWithDetailID++;
    }

    /**
     * 在通知的时候，添加声音和震动，闪烁灯提示，这里声音直接使用读取本地文件的方式
     */
    private void setJuniorNotification(){
        // 第二步：
        Intent intent = new Intent(this, juniorNotificationInfoPage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // 第三步：
        Notification notification = new NotificationCompat.Builder(this, "1")
            .setContentTitle("中级通知" + juniorNotificationID)
            .setContentText("你有一条中级未读消息")
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.big_image)
            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            // 添加音频文件路径
            .setSound(Uri.fromFile(new File("/system/media/audio/notifications/pixiedust.ogg")))
            // 添加震动，第一:延时，第二:震动时长，第三:延时，第四:震动时长
            .setVibrate(new long[] {0, 1000, 1000, 1000})
            // 添加未读消息提示灯，亮起时长，暗去时长
            .setLights(Color.RED, 1000, 1000)
            .build();
        // 第四步：
        notificationManager.notify(juniorNotificationID, notification);
        juniorNotificationID++;
    }

    /**
     * 实现在通知状态栏显示富文本和图片，主要通过setStyle()和NotificationCompat对象
     */
    private void setHigherNotification(){
        // 第二步：
        Intent intent = new Intent(this, higherNotificationInfoPage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // 第三步：
        Notification notification = new NotificationCompat.Builder(this, "1")
                .setContentTitle("高级通知" + higherNotificationID)
                .setContentText("你有一条高级未读消息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.big_image)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                // 长文本
//                .setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle("BIG CONTENT TITLE").bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
                // 大图片
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.big_image)))
                .build();
        // 第四步：
        notificationManager.notify(higherNotificationID, notification);
        higherNotificationID++;
    }


    /**
     * 最小优先级通知测试
     */
    private void minPriorityNotification(){
        // 第二步：
        Notification notification = new NotificationCompat.Builder(this, "1")
                .setContentTitle("min优先级通知" + priorityID)
                .setContentText("你有一条min优先级未读消息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.big_image)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .build();
        // 第三步：
        notificationManager.notify(priorityID, notification);
        priorityID++;
    }


    /**
     * 低优先级通知测试
     */
    private void lowPriorityNotification(){
        // 第二步：
        Notification notification = new NotificationCompat.Builder(this, "1")
                .setContentTitle("low优先级通知" + priorityID)
                .setContentText("你有一条low优先级未读消息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.big_image)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        // 第三步：
        notificationManager.notify(priorityID, notification);
        priorityID++;
    }


    /**
     * 默认优先级通知测试
     */
    private void defaultPriorityNotification(){
        // 第二步：
        Notification notification = new NotificationCompat.Builder(this, "1")
                .setContentTitle("default优先级通知" + priorityID)
                .setContentText("你有一条default优先级未读消息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.big_image)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        // 第三步：
        notificationManager.notify(priorityID, notification);
        priorityID++;
    }


    /**
     * 高优先级通知测试
     */
    private void highPriorityNotification(){
        // 第二步：
        Notification notification = new NotificationCompat.Builder(this, "1")
                .setContentTitle("high优先级通知" + priorityID)
                .setContentText("你有一条high优先级未读消息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.big_image)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        // 第三步：
        notificationManager.notify(priorityID, notification);
        priorityID++;
    }


    /**
     * 最高优先级通知测试
     */
    private void maxPriorityNotification(){
        // 第二步：
        Notification notification = new NotificationCompat.Builder(this, "1")
                .setContentTitle("max优先级通知" + priorityID)
                .setContentText("你有一条max优先级未读消息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.big_image)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();
        // 第三步：
        notificationManager.notify(priorityID, notification);
        priorityID++;
    }

    /**
     * 实现清除已有通知
     */
    private void clearAllNotification(){
        notificationManager.cancelAll();
    }
}
