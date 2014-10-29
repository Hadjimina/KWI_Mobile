package com.hadjiminap.kwimobile;

import android.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


public class Timetable extends Fragment
{
    public String[] iteratorhandling = new String[20];
    public String[] dates = new String [11];
    public JSONObject[] days = new JSONObject[5];
    public JSONArray[] daysarray = new JSONArray[5];
    public JSONArray[] hours = new JSONArray[11];
    public JSONObject[][] lektion = new JSONObject[11][3];
    public String[] subject = new String [11];
    public String[] roomnr = new String [11];
    public JSONArray[] rooms = new JSONArray[11];
    public int num,col,everyday,everydaytext,v ;
    TableRow [] rows = new TableRow[11];
    public TextView[][] lessons = new TextView[11][6];


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View ret =  inflater.inflate(R.layout.timetable, null);

        //SETUP ARRAY LIST AND POPULATE IT
        ArrayList<Integer> vals = new ArrayList<Integer>();
        for (int w = 0;w<11;w++)
        {
            vals.add(w);
        }


        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");



        // get text view
        TableLayout table = (TableLayout) ret.findViewById(R.id.tablelayout);
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

        lessons[1][0].setText(Html.fromHtml("<p>07:30</p><p>08:15</p>"));
        lessons[2][0].setText(Html.fromHtml("<p>08:25</p><p>09:10</p>"));
        lessons[3][0].setText(Html.fromHtml("<p>09:20</p><p>10:05</p>"));
        lessons[4][0].setText(Html.fromHtml("<p>10:20</p><p>11:05</p>"));
        lessons[5][0].setText(Html.fromHtml("<p>11:15</p><p>12:00</p>"));
        lessons[6][0].setText(Html.fromHtml("<p>12:25</p><p>13:10</p>"));
        lessons[7][0].setText(Html.fromHtml("<p>13:20</p><p>14:05</p>"));
        lessons[8][0].setText(Html.fromHtml("<p>14:15</p><p>15:00</p>"));
        lessons[9][0].setText(Html.fromHtml("<p>15:10</p><p>15:55</p>"));
        lessons[10][0].setText(Html.fromHtml("<p>16:00</p><p>16:45</p>"));

        //Set times to bold font
        for (int i = 0; i < 11; i++)
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

        //Receiving JSON Data
        MainActivity activity = (MainActivity) getActivity();
        String jdata = activity.getData();


        try {

            JSONObject json = new JSONObject(jdata);

            Iterator<String> iter = json.keys();

            int i = 0;
            while (iter.hasNext())
            {
                iteratorhandling[i] = iter.next();
                i++;

            }

            //monday check gives location in array
            //method to get position in dates array
            //WEIRD HANDLING FROM ITERATOR => WORKAROUND
            dates[2] = iteratorhandling[0];
            dates[3] = iteratorhandling[1];
            dates[4] = iteratorhandling[2];
            dates[8] = iteratorhandling[3];
            dates[5] = iteratorhandling[4];
            dates[9] = iteratorhandling[5];
            dates[6] = iteratorhandling[6];
            dates[7] = iteratorhandling[7];
            dates[0] = iteratorhandling[8];
            dates[1] = iteratorhandling[9];

            num = changenum(mondaycheck(iteratorhandling));

            for (everyday = 0; everyday <= 4; everyday++ )
            {
                for ( int v : vals)
                {

                    try
                    {

                        days[everyday] = json.getJSONObject(dates[num]);


                        try
                        {
                            hours[v] = days[everyday].getJSONArray(Integer.toString(v));

                            if (hours[v].length() == 2)
                            {
                                for (int o = 0; o < 2; o++)
                                {
                                    lektion[v][o] = hours[v].getJSONObject(o);

                                    subject[o] = (String) lektion[v][o].get("subject");

                                    rooms[o] = lektion[v][o].getJSONArray("rooms");

                                    roomnr[o] = (String) rooms[o].get(0);

                                    col = v + 1;
                                    everydaytext = everyday + 1;
                                    lessons[col][everydaytext].setText(Html.fromHtml(
                                            "<b> "
                                            + subject[0]
                                            + " </b><small>"
                                            + roomnr[0]
                                            + "</small>"
                                            + "<br /><b> "
                                            + subject[1]
                                            + " </b><small>"
                                            + roomnr[1]
                                            + "</small>"));
                                }
                            }
                            else if (hours[v].length() == 3)
                            {
                                for (int o = 0; o < 3; o++)
                                {
                                    lektion[v][o] = hours[v].getJSONObject(o);

                                    subject[o] = (String) lektion[v][o].get("subject");

                                    rooms[o] = lektion[v][o].getJSONArray("rooms");

                                    roomnr[o] = (String) rooms[o].get(0);

                                    col = v + 1;
                                    everydaytext = everyday + 1;
                                    lessons[col][everydaytext].setText(Html.fromHtml("<b> "
                                            + subject[0]
                                            + " </b><small>"
                                            + roomnr[0]
                                            + "</small><br />"
                                            + "<b> "
                                            + subject[1]
                                            + " </b><small>"
                                            + roomnr[1]
                                            + "</small><br />"
                                            + "<b> "
                                            + subject[2]
                                            + " </b><small>"
                                            + roomnr[2]
                                            + "</small>"));
                                }
                            }
                            else if (hours[v].length() == 4)
                            {
                                for (int o = 0; o < 4; o++)
                                {
                                    lektion[v][o] = hours[v].getJSONObject(o);

                                    subject[o] = (String) lektion[v][o].get("subject");

                                    rooms[o] = lektion[v][o].getJSONArray("rooms");

                                    roomnr[o] = (String) rooms[o].get(0);

                                    col = v + 1;
                                    everydaytext = everyday + 1;
                                    lessons[col][everydaytext].setText(Html.fromHtml(
                                            "<b> "
                                            + subject[0]
                                            + "</b><br /><small>"
                                            + roomnr[0]
                                            + " "
                                            + roomnr[1]
                                            + " <br />"
                                            + roomnr[2]
                                            + " "
                                            + roomnr[3]
                                            + "</small></p>"));
                                }
                            }
                            else
                            {
                                lektion[v][0] = hours[v].getJSONObject(0);

                                subject[v] = (String) lektion[v][0].get("subject");

                                rooms[v] = lektion[v][0].getJSONArray("rooms");

                                roomnr[v] = (String) rooms[v].get(0);

                                col = v + 1;
                                everydaytext = everyday + 1;
                                lessons[col][everydaytext].setText(Html.fromHtml(
                                        "<b>"
                                        + subject[v]
                                        + "</b>"
                                        + "<br />"
                                        + "<small>"
                                        + roomnr[v]
                                        + "</small>"));
                            }
                        }
                        catch (Exception e)
                        {
                            Log.i("empty string", "empty string");
                        }

                    }
                    catch (JSONException e)
                    {


                        daysarray[everyday] = json.getJSONArray(dates[num]);

                        try
                        {
                            hours[v] = daysarray[everyday].getJSONArray(v);

                            if (hours[v].length() == 2)
                            {
                                for (int o = 0; o < 2; o++)
                                {
                                    lektion[v][o] = hours[v].getJSONObject(o);

                                    subject[o] = (String) lektion[v][o].get("subject");

                                    rooms[o] = lektion[v][o].getJSONArray("rooms");

                                    roomnr[o] = (String) rooms[o].get(0);

                                    col = v + 1;
                                    everydaytext = everyday + 1;
                                    lessons[col][everydaytext].setText(Html.fromHtml(
                                            "<b> "
                                                    + subject[0]
                                                    + " </b><small>"
                                                    + roomnr[0]
                                                    + "</small>"
                                                    + "<br /><b> "
                                                    + subject[1]
                                                    + " </b><small>"
                                                    + roomnr[1]
                                                    + "</small>"));
                                }
                            }
                            else if (hours[v].length() == 3)
                            {
                                for (int o = 0; o < 3; o++)
                                {
                                    lektion[v][o] = hours[v].getJSONObject(o);

                                    subject[o] = (String) lektion[v][o].get("subject");

                                    rooms[o] = lektion[v][o].getJSONArray("rooms");

                                    roomnr[o] = (String) rooms[o].get(0);

                                    col = v + 1;
                                    everydaytext = everyday + 1;
                                    lessons[col][everydaytext].setText(Html.fromHtml("<b> "
                                            + subject[0]
                                            + " </b><small>"
                                            + roomnr[0]
                                            + "</small><br />"
                                            + "<b> "
                                            + subject[1]
                                            + " </b><small>"
                                            + roomnr[1]
                                            + "</small><br />"
                                            + "<b> "
                                            + subject[2]
                                            + " </b><small>"
                                            + roomnr[2]
                                            + "</small>"));
                                }
                            }
                            else if (hours[v].length() == 4)
                            {
                                for (int o = 0; o < 4; o++)
                                {
                                    lektion[v][o] = hours[v].getJSONObject(o);

                                    subject[o] = (String) lektion[v][o].get("subject");

                                    rooms[o] = lektion[v][o].getJSONArray("rooms");

                                    roomnr[o] = (String) rooms[o].get(0);

                                    col = v + 1;
                                    everydaytext = everyday + 1;
                                    lessons[col][everydaytext].setText(Html.fromHtml(
                                            "<b> "
                                                    + subject[0]
                                                    + "</b><br /><small>"
                                                    + roomnr[0]
                                                    + " "
                                                    + roomnr[1]
                                                    + " <br />"
                                                    + roomnr[2]
                                                    + " "
                                                    + roomnr[3]
                                                    + "</small></p>"));
                                }
                            }
                            else
                            {
                                lektion[v][0] = hours[v].getJSONObject(0);

                                subject[v] = (String) lektion[v][0].get("subject");

                                rooms[v] = lektion[v][0].getJSONArray("rooms");

                                roomnr[v] = (String) rooms[v].get(0);

                                col = v + 1;
                                everydaytext = everyday + 1;
                                lessons[col][everydaytext].setText(Html.fromHtml(
                                        "<b>"
                                        + subject[v]
                                        + "</b>"
                                        + "<br />"
                                        + "<small>"
                                        + roomnr[v]
                                        + "</small>"));
                            }

                        }
                        catch (Exception o)
                        {
                            Log.i("empty string", "empty string");
                        }
                    }
                }
                num++;
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return ret;
    }


    public int mondaycheck(String items[])
    {
        int[] checker = new int[11];
        String[] newitems = new String[11];
        int fin = 0;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        int date = cal.get(Calendar.DATE);

        for (int q = 0; q < 10;q ++)
        {
            Log.w("items",String.valueOf(items[q]));
            newitems[q]= items[q].substring(8);

            char c = newitems[q].charAt(0);

            if (c == 0)
            {
                newitems[q]= newitems[q].substring(1);
            }
            try
            {
             checker[q] = Integer.parseInt(newitems[q]);
            }
            catch(NumberFormatException nfe)
            {
            Log.w("mytag", "error in string to int");
            }

            if (checker[q]== date)
            {
                fin = q;
            }
        }
        return fin;
    }

    public int changenum (int mo)
     {
        int res = 0;

        if (mo == 0)
        {
            res = 2;
        }
        else if (mo ==1)
        {
            res = 3;
        }
        else if (mo ==2)
        {
            res = 4;
        }
        else if (mo ==3)
        {
            res = 8;
        }
        else if (mo ==4)
        {
            res = 5;
        }
        else if (mo ==5)
        {
            res = 9;
        }
        else if (mo ==6)
        {
            res = 6;
        }
        else if (mo ==7)
        {
            res = 7;
        }
        else if (mo ==8)
        {
            res = 0;
        }
        else if (mo ==9)
        {
            res = 1;
        }
        else if (mo ==10)
        {
            res = 1;
        }
        return res;
    }


    public void onBackPressed()
    {
        //Don't do anything when back button is pressed
    }


    }


