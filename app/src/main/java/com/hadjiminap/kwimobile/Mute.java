package com.hadjiminap.kwimobile;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;

//All the sources I used are makred in the matura paper in Chapter 2 in the corresponding section
public class Mute extends Service
{
    Alarm alarm = new Alarm();
    private AudioManager am;

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public void onDestroy()
    {
        //Stop alarm and set phone to normal
        super.onDestroy();
        am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        alarm.CancelAlarm(Mute.this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //get the lessons bundle and send it further to setalarm
        Bundle b = intent.getBundleExtra("lessons");

        alarm.SetAlarm(Mute.this, b);

        return START_STICKY;

    }


    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }



}