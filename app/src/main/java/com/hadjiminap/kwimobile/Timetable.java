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

import java.util.Calendar;
import java.util.Iterator;


public class Timetable extends Fragment
{

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        String[] iteratorhandling = new String[11];
        String[] dates = new String [11];
        JSONObject[] days = new JSONObject[11];
        int mo=0;

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

            parameterw.width = 200;
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



        //Receiving JSON Data
        MainActivity activity = (MainActivity) getActivity();
        String jdata = activity.getData();

        try
        {
            JSONObject json = new JSONObject(jdata);

            Iterator<String> iter = json.keys();

            int i = 0;
            while (iter.hasNext())
            {

                iteratorhandling[i] = iter.next();
                i++;
            }

            mo = mondaycheck(iteratorhandling);
            Log.w("potato",String.valueOf(mo));

            //WEIRD HANDLING FROM ITERATOR => WORKAROUND
            dates[6] = iteratorhandling[0];
            dates[7] = iteratorhandling[1];
            dates[8] = iteratorhandling[2];
            dates[9] = iteratorhandling[3];
            dates[0] = iteratorhandling[4];
            dates[10] = iteratorhandling[5];
            dates[1] = iteratorhandling[6];
            dates[4] = iteratorhandling[7];
            dates[5] = iteratorhandling[8];
            dates[2] = iteratorhandling[9];
            dates[3] = iteratorhandling[10];

            /*for (int c = 0; c < 10 ; c++)
            {
                days[c] = json.getJSONObject(dates[c]);
            }*/
            days[0] = json.getJSONObject(dates[0]);
            days[1] = json.getJSONObject(dates[1]);
            days[2] = json.getJSONObject(dates[2]);
            days[3] = json.getJSONObject(dates[3]);
            days[4] = json.getJSONObject(dates[4]);
            days[5] = json.getJSONObject(dates[5]);
//            days[6] = json.getJSONObject(dates[6]);
            days[7] = json.getJSONObject(dates[7]);
            days[8] = json.getJSONObject(dates[8]);
            days[9] = json.getJSONObject(dates[9]);
            days[10] = json.getJSONObject(dates[10]);






        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }




        return ret;
    }

    public int mondaycheck(String items[])
    {
        int[] checker = new int[10];

        int monday = 0;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        int date = cal.get(Calendar.DATE);
        Log.w("date", String.valueOf(date));
        for (int q = 0; q < 10;q ++)
        {
            Log.w("items", String.valueOf(items[q]));
            items[q]= items[q].substring(8);

            char c = items[q].charAt(0);

            if (c == 0)
            {
                items[q]= items[q].substring(1);
            }
            Log.w("items", String.valueOf(items[q]));
            try
            {
             checker[q] = Integer.parseInt(items[q]);
            }
            catch(NumberFormatException nfe)
            {
            Log.w("mytag", "error in string to int");
            }

            if (checker[q]== date)
            {
                monday = checker[q];

            }

            Log.w("monday", String.valueOf(monday));
        }
        return monday;
    }


    public void onBackPressed()
    {
        //Don't do anything when back button is pressed
    }


}