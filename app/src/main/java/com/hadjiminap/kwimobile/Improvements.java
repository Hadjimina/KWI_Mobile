package com.hadjiminap.kwimobile;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//All the sources I used are makred in the matura paper in Chapter 2 in the corresponding section
public class Improvements extends Fragment
{


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View ret =  inflater.inflate(R.layout.improvements, null);

        Button sendbug = (Button) ret.findViewById(R.id.sendbug);
        Button sendfeature = (Button)ret.findViewById(R.id.sendfeature);

        TextView bug = (TextView)ret.findViewById(R.id.bug);
        TextView feature = (TextView)ret.findViewById(R.id.feature);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        bug.setTypeface(tf);
        feature.setTypeface(tf);
        sendbug.setTypeface(tf);
        sendfeature.setTypeface(tf);


        sendbug.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"philipp.hadjimina@gmx.ch"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Fehler Meldung in KWI Mobile");

                try
                {
                    startActivity(Intent.createChooser(i, "Email senden"));
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Es wurde kein Email client gefunden", Toast.LENGTH_LONG).show();
                }
            }
        });

        sendfeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"philipp.hadjimina@gmx.ch"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Wunsch für KWI Mobile");
                try
                {
                    startActivity(Intent.createChooser(i, "Email senden"));
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Es wurde kein Email client gefunden", Toast.LENGTH_LONG).show();
                }
            }
        });



        return ret;


    }

    public void onBackPressed()
    {
        //Don't do anything when back button is pressed
    }

}





