package com.hadjiminap.kwimobile;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class Timetable extends Fragment
{

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View ret =  inflater.inflate(R.layout.timetable, null);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");

        TableLayout table = (TableLayout) ret.findViewById(R.id.tablelayout);
        TableRow [] rows = new TableRow[12];
        TextView[][] lessons = new TextView[12][6];

        // get text view
        for (int i = 0; i < table.getChildCount(); i++)
        {
            if (table.getChildAt(i).getClass()==TableRow.class)
            {

                rows[i]= (TableRow) table.getChildAt(i);

               for (int c = 0; c < 6; c++)
                {
                    lessons[i][c] = (TextView)rows[i].getChildAt(c);
                    lessons[i][c].setTypeface(tf);

                }
            }

            else
            {
                Log.e("MYTAG","ERROR");
            }
        }


        //Set times to bold font
        for (int i = 0; i < 12; i++)
        {
            String txt = lessons[i][0].getText().toString();
            lessons[i][0].setText(Html.fromHtml("<b>" + txt + "</b>"));
        }

        //Set days to bold font
        for (int i = 0; i < 6; i++)
        {
            String txt = lessons[0][i].getText().toString();
            lessons[0][i].setText(Html.fromHtml("<b>" + txt + "</b>"));
        }


        //Orientation Handling
        int nowOrientation = getResources().getConfiguration().orientation;
        if (nowOrientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            TableRow.LayoutParams parameter = (TableRow.LayoutParams) lessons[1][1].getLayoutParams();
            TableRow.LayoutParams parameterw = new TableRow.LayoutParams();
            TableRow.LayoutParams parameterh = new TableRow.LayoutParams();
            TableRow.LayoutParams parameterborder = new TableRow.LayoutParams();

            parameterw.width = 130;
            parameterh.height = 104;

            parameterborder.width = parameterw.width;
            parameterborder.height = 88;

            parameter.height = parameterh.height;
            parameter.width = parameterw.width;

            for (int i = 1; i < 12; i++)
            {
                lessons[i][0].setLayoutParams(parameterborder);
            }

            for (int i = 0; i < 6; i++)
            {
                lessons[0][i].setLayoutParams(parameterh);
            }

            for (int i = 1; i < 12; i++)
            {
                    for (int c = 1; c < 6; c++)
                    {
                        lessons[i][c].setLayoutParams(parameter);

                    }
                }
            }

        else
        {
            // Portrait
        }

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null)
        {
            String value = extras.getString("new_variable_name");
            Log.w("mytag", value);

            try
            {
                JSONObject jobj = new JSONObject(value);

                Iterator<String> iter = jobj.keys();
                while (iter.hasNext())
                {
                    String key = iter.next();
                    try
                    {
                        Object num = jobj.get(key);
                        Log.w("mytag", String.valueOf(num));
                    }
                    catch (JSONException e)
                    {
                        // Something went wrong!
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }



        return ret;



    }

    public void onBackPressed()
    {
        //Don't do anything when back button is pressed
    }


}