package com.hadjiminap.kwimobile;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;

//All the sources I used are makred in the matura paper in Chpater 2 in the corresponding section
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
        super.onDestroy();
        am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarm.SetAlarm(Mute.this);

        return START_STICKY;

    }

    public void onStart(Context context,Intent intent, int startId)
    {

        alarm.SetAlarm(context);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }



}