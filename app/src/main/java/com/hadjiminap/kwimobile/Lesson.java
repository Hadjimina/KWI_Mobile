package com.hadjiminap.kwimobile;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class Lesson implements Parcelable {

    private int m_timeIndex;
    private int m_day;
    private int m_length;
    private ArrayList<String> m_rooms;
    private ArrayList<String> m_subject;
    private ArrayList<Integer> m_changes;

    public Lesson(int timeIndex, ArrayList<String> rooms, ArrayList<String> subject, Integer day, ArrayList<Integer> changes, int length) {
        m_timeIndex = timeIndex;
        m_rooms = rooms;
        m_subject = subject;
        m_day  = day;
        m_changes = changes;
        m_length = length;
    }

    public ArrayList<String> getRooms() {
        return m_rooms;
    }

    public ArrayList<String> getSubject() {
        return m_subject;
    }

    public int getTimeIndex() {
        return m_timeIndex;
    }

    public Integer getDay() {
        return m_day;
    }

    public Integer getLength() {
        return m_length;
    }

    public ArrayList<Integer> isChanged() {
        return m_changes;
    }

    public static Lesson fromJSON(int time_index, JSONArray obj,String date) throws JSONException {
        ArrayList<String> rooms = new ArrayList<String>();
        ArrayList<String> subjects = new ArrayList<String>();
        ArrayList<Integer> changes = new ArrayList<Integer>();

        String date_sub;
        int day,length = 0;


        int calmo_int,caldi_int,calmi_int,caldo_int,calfr_int,date_int;
        boolean ok = false;

        length = obj.length();

       // Log.w("lengthclass",String.valueOf(length));

        for (int j = 0; j < length; ++j)
        {

            JSONObject room_subject = obj.getJSONObject(j);
            rooms.add(room_subject.getJSONArray("rooms").getString(0));
            subjects.add(room_subject.getString("subject"));

            if (room_subject.length()==3)
            {
                String canceled_string = room_subject.getString("diff");

                if (canceled_string.equals("-"))
                {
                    changes.add(2);
                }
                else if (canceled_string.equals("+"))
                {
                    changes.add(1);
                }
            }
            else
            {
                changes.add(0);
            }

        }

        date_sub= date.substring(8);
        date_int=Integer.parseInt(date_sub);

        Calendar calmo = Calendar.getInstance();
        calmo.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calmo_int = calmo.get(Calendar.DATE);
        Calendar caldi = Calendar.getInstance();
        caldi.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        caldi_int = caldi.get(Calendar.DATE);
        Calendar calmi = Calendar.getInstance();
        calmi.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        calmi_int = calmi.get(Calendar.DATE);
        Calendar caldo = Calendar.getInstance();
        caldo.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        caldo_int = caldo.get(Calendar.DATE);
        Calendar calfr = Calendar.getInstance();
        calfr.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        calfr_int = calfr.get(Calendar.DATE);

        if (date_int == calmo_int)
        {
         day = 1;
        }
        else if (date_int == caldi_int)
        {
            day = 2;
        }
        else if (date_int == calmi_int)
        {
            day = 3;
        }
        else if (date_int == caldo_int)
        {
            day = 4;
        }
        else if (date_int == calfr_int)
        {
            day = 5;
        }
        else{
            day = 6;
        }

        return new Lesson(time_index, rooms, subjects, day, changes, length);
    }

    public static Lesson fromBundle(Bundle b) {
        ArrayList<String> subject = b.getStringArrayList("subject");
        int timeIndex = b.getInt("time-index");
        ArrayList<String> rooms = b.getStringArrayList("rooms");
        Integer day = b.getInt("day");
        ArrayList<Integer> changes = b.getIntegerArrayList("changecheck");
        Integer length = b.getInt("length");

        return new Lesson(timeIndex, rooms, subject,day,changes,length);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle b = new Bundle();
        b.putInt("time-index", m_timeIndex);
        b.putStringArrayList("rooms", m_rooms);
        b.putStringArrayList("subject", m_subject);
        b.putInt("day", m_day);
        b.putIntegerArrayList("changecheck", m_changes);
        b.putInt("length", m_length);
        parcel.writeBundle(b);
    }

    public static final Parcelable.Creator<Lesson> CREATOR
            = new Parcelable.Creator<Lesson>() {
        public Lesson createFromParcel(Parcel in) {
            return Lesson.fromBundle(in.readBundle());
        }

        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };


}
