package com.hadjiminap.kwimobile;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class Mute extends Service
{
    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        AudioManager am;
        am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Bundle b = intent.getBundleExtra("lessons");
        ArrayList<Lesson> lessons = b.getParcelableArrayList("lessons");

        int day = getDay();
        int les = getLesson()+1;

        if (lessons.get(day).getTimeIndex()==les)
        {
            AudioManager am;
            am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            Log.i("silent","set to silent");

        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public int getDay()
    {
        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_WEEK);
        int day = 5;

        if (Calendar.MONDAY == today)
        {
            day = 0;
        }
        if (Calendar.TUESDAY == today)
        {
            day = 1;
        }
        if (Calendar.WEDNESDAY == today)
        {
            day = 2;
        }
        if (Calendar.THURSDAY == today)
        {
            day = 3;
        }
        if (Calendar.FRIDAY == today)
        {
            day = 4;
        }

        return day;
    }
    public int getLesson ()
    {
        Calendar cal = Calendar.getInstance();
        int current = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        int[][] time = new int[10][2];
        int les = 10;

        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 30);
        time[0][0] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 15);
        time[0][1] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 25);
        time[1][0] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 10);
        time[1][1] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 20);
        time[2][0] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 05);
        time[2][1] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 20);
        time[3][0] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 11);
        cal.set(Calendar.MINUTE, 05);
        time[3][1] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 11);
        cal.set(Calendar.MINUTE, 15);
        time[4][0] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 00);
        time[4][1]= getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 25);
        time[5][0] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 10);
        time[5][1] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 20);
        time[6][0] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 15);
        time[6][1] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 15);
        time[7][0] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 00);
        time[7][1] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 10);
        time[8][0] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 55);
        time[8][1] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 00);
        time[9][0] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 45);
        time[9][1] = getTime(cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE));

        for (int c = 0; c < 10; c++)
        {
            if (time[c][0]<current&&current<time[c][1])
            {
                les = c;
            }
        }

        return les;
    }
    public int getTime (int hour, int min)
    {
        int time = hour * 100 + min;
        return time;
    }

}