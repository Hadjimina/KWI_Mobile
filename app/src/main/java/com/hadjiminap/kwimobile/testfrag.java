package com.hadjiminap.kwimobile;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by philipp on 8/10/14.
 */
public class testfrag extends Fragment
{


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.main, null);


    }

    public void onBackPressed()
    {
        //Don't do anything when back button is pressed
    }

}





