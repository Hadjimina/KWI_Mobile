package com.hadjiminap.kwimobile;

import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Settings extends Fragment
{
    private TextView t1,t2;
    private Typeface tf;
    private SharedPreferences togglePreferences;
    private SharedPreferences.Editor togglePrefsEditor;
    private boolean saveToggle;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View ret =  inflater.inflate(R.layout.settings, null);

        final ToggleButton automute = (ToggleButton) ret.findViewById(R.id.toggleButton2);
         t1 = (TextView) ret.findViewById(R.id.textView);
         t2 = (TextView) ret.findViewById(R.id.textView2);
        togglePreferences = this.getActivity().getSharedPreferences("togglePrefs",Context.MODE_PRIVATE);
        togglePrefsEditor = togglePreferences.edit();


        //Set Font
        tf = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        automute.setTypeface(tf);
        t1.setTypeface(tf);
        t2.setTypeface(tf);

        saveToggle = togglePreferences.getBoolean("togglePrefs", false);
        if (saveToggle == true)
        {
            automute.setChecked(true);

            MainActivity activity = (MainActivity)getActivity();
            Intent i = new Intent(activity, Mute.class);
            Bundle b = new Bundle();
            b.putParcelableArrayList("lessons", activity.getLessons());
            i.putExtra("lessons", b);
            getActivity().startService(i);
        }

        automute.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                boolean on = ((ToggleButton) v).isChecked();
                boolean check = isMyServiceRunning(getActivity().getApplicationContext());

                if (on)
                {
                    togglePrefsEditor.putBoolean("togglePrefs", true);
                    togglePrefsEditor.commit();

                    MainActivity activity = (MainActivity)getActivity();
                    Intent i = new Intent(activity, Mute.class);
                    Bundle b = new Bundle();
                    b.putParcelableArrayList("lessons", activity.getLessons());
                    i.putExtra("lessons", b);
                    getActivity().startService(i);
                }
                else
                {
                     togglePrefsEditor.clear();
                    togglePrefsEditor.commit();

                    if (check== true)
                    {
                        getActivity().stopService(new Intent(getActivity(), Mute.class));
                    }
                    else
                    {
                        //NOTHING IF NOT RUNNING
                    }

                }
            }
        });

        return ret;
    }

    private boolean isMyServiceRunning(Context mContext)
    {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (Mute.class.getName().equals(service.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }

    public void onBackPressed()
    {
        //Don't do anything when back button is pressed
    }


}





