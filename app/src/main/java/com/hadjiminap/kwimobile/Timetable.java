package com.hadjiminap.kwimobile;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;


public class Timetable extends Fragment
{


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View ret =  inflater.inflate(R.layout.timetable, null);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        TableRow tableRow = (TableRow) ret.findViewById(R.id.row2);

        ArrayList<TextView> cells = new ArrayList<TextView>();

        // get text view
        for (int i = 1; i < tableRow.getChildCount(); i++)
        {
            if (tableRow.getChildAt(i).getClass() == TextView.class)
            {
                cells.add((TextView)tableRow.getChildAt(i));


            }
        }

        // set titles
        final String zimmer1 = "Zimmer1";
        final String fach1 = "Fach1";
        for(Iterator<TextView> i = cells.iterator(); i.hasNext(); )
        {
            TextView item = i.next();
            item.setText(Html.fromHtml("<b>" + fach1 + "</b>" + "<br />" +
                    "<small>" + zimmer1 + "</small>" + "<br />"));
            item.setTypeface(tf);
        }

        return ret;
    }

    public void onBackPressed()
    {
        //Don't do anything when back button is pressed
    }

}
