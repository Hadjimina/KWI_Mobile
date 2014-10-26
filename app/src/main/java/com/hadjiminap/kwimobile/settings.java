package com.hadjiminap.kwimobile;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class settings extends Fragment
{


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View ret =  inflater.inflate(R.layout.timetable, null);

        CheckBox automute = (CheckBox) ret.findViewById(R.id.checkBox);
        TextView t1 = (TextView) ret.findViewById(R.id.textView);
        TextView t2 = (TextView) ret.findViewById(R.id.textView2);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        t1.setTypeface(tf);
        t2.setTypeface(tf);

        if (automute.isChecked())
        {
            t1.setText("potoat");
        }


        return ret;
    }

    public void onBackPressed()
    {
        //Don't do anything when back button is pressed
    }

}





