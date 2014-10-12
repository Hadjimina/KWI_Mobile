package com.hadjiminap.kwimobile;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class improvements extends Fragment
{


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"philipp.hadjimina@gmx.ch"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Verbesserungsvorschlag");
        i.putExtra(Intent.EXTRA_TEXT   , "");
        try
        {
            startActivity(Intent.createChooser(i, "Send mail..."));
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "Es wurde kein Email client gefunden", Toast.LENGTH_LONG).show();
        }

        return inflater.inflate(R.layout.main, null);
    }

    public void onBackPressed()
    {
        //Don't do anything when back button is pressed
    }

}





