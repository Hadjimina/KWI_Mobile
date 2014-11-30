package com.hadjiminap.kwimobile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

//Based on http://stackoverflow.com/questions/4459058/alarm-manager-example  as of 30.11.14
public class Alarm extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle b = intent.getBundleExtra("lessons");
        ArrayList<Lesson> lessons = b.getParcelableArrayList("lessons");
        int day = getDay();
        int les = getLesson();

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        Iterator<Lesson> it_throughlessons = lessons.iterator();

        while (it_throughlessons.hasNext())
        {
            Lesson lesson = it_throughlessons.next();
            Log.i("day", String.valueOf(lesson.getDay()));
            Log.i("Time",String.valueOf(lesson.getTimeIndex()));

            if (lesson.getDay()==day&&lesson.getTimeIndex()==les)
            {

            //    am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Log.i("silent","set to silent");
                break;
            }
            else
            {
              //  am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }


        }
    }

    public void SetAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 , pi); // Millisec * Second * Minute

    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public int getDay()
    {
        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_WEEK);
        int day = 5;

        if (Calendar.MONDAY == today)
        {
            day = 1;
        }
        else if (Calendar.TUESDAY == today)
        {
            day = 2;
        }
        else if (Calendar.WEDNESDAY == today)
        {
            day = 3;
        }
        else if (Calendar.THURSDAY == today)
        {
            day = 4;
        }
        else if (Calendar.FRIDAY == today)
        {
            day = 5;
        }
        else
        {
            day = 6;
        }

        return day;
    }
    public int getLesson ()
    {
        Calendar cal = Calendar.getInstance();
        int current = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        int[][] time = new int[10][2];
        int les = 42;

        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 30);
        time[0][0] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 15);
        time[0][1] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 25);
        time[1][0] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 10);
        time[1][1] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 20);
        time[2][0] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 05);
        time[2][1] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 20);
        time[3][0] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 11);
        cal.set(Calendar.MINUTE, 05);
        time[3][1] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 11);
        cal.set(Calendar.MINUTE, 15);
        time[4][0] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 00);
        time[4][1]= getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 25);
        time[5][0] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 10);
        time[5][1] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 20);
        time[6][0] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 15);
        time[6][1] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 15);
        time[7][0] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 00);
        time[7][1] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 10);
        time[8][0] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 55);
        time[8][1] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 00);
        time[9][0] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 45);
        time[9][1] = getTime(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));

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