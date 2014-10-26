package com.hadjiminap.kwimobile;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class settings extends Fragment
{
    TextView t1,t2;
    Typeface tf;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View ret =  inflater.inflate(R.layout.settings, null);

        ToggleButton automute = (ToggleButton) ret.findViewById(R.id.toggleButton2);
         t1 = (TextView) ret.findViewById(R.id.textView);
         t2 = (TextView) ret.findViewById(R.id.textView2);


        //Set Font
        tf = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        automute.setTypeface(tf);
        t1.setTypeface(tf);
        t2.setTypeface(tf);

        return ret;
    }

    public void onToggleClicked(View view)
    {
        boolean on = ((ToggleButton) view).isChecked();

        if (on)
        {
            getActivity().startService(new Intent(getActivity(), Timetable.service.class));
        }
        else
        {
            // Disable vibrate
        }

    }

    public void onBackPressed()
    {
        //Don't do anything when back button is pressed
    }


}





