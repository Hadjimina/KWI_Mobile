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

import org.json.JSONArray;
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
        JSONArray[] hours = new JSONArray[11];
        JSONObject[][] lektion = new JSONObject[11][3];
        String[] subject = new String [11];
        String[] roomnr = new String [11];
        JSONArray[] rooms = new JSONArray[11];
        int num = 0;

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

            //monday check gives location in array
            //method to get position in dates array
            //WEIRD HANDLING FROM ITERATOR => WORKAROUND
            dates[4] = iteratorhandling[0];
            dates[5] = iteratorhandling[1];
            dates[6] = iteratorhandling[2];
            dates[10] = iteratorhandling[3];
            dates[7] = iteratorhandling[4];
            dates[8] = iteratorhandling[5];
            dates[9] = iteratorhandling[6];
            dates[2] = iteratorhandling[7];
            dates[3] = iteratorhandling[8];
            dates[0] = iteratorhandling[9];
            dates[1] = iteratorhandling[10];

            days[0] = json.getJSONObject(dates[0]);
            days[1] = json.getJSONObject(dates[1]);
            days[2] = json.getJSONObject(dates[2]);
            days[3] = json.getJSONObject(dates[3]);
           // days[4] = json.getJSONObject(dates[4]);
            days[5] = json.getJSONObject(dates[5]);
            days[6] = json.getJSONObject(dates[6]);
            days[7] = json.getJSONObject(dates[7]);
            days[8] = json.getJSONObject(dates[8]);
            //days[9] = json.getJSONObject(dates[9]);
            days[10] = json.getJSONObject(dates[10]);

            num = changenum(mondaycheck(iteratorhandling));

            int potoato =0;

           /* while (potoato < 5)
            {
                Log.w("asdf","asdfasdf");
                try
                {*/
                    for (int les = 0;les < 5; les ++)
                    {
                        hours[les] = days[num].getJSONArray(Integer.toString(les));

                        if (hours[les].length() == 2)
                        {
                            for (int o = 0; o < 2; o++)
                            {
                                lektion[les][o] = hours[les].getJSONObject(o);

                                subject[o] = (String) lektion[les][o].get("subject");

                                rooms[o] = lektion[les][o].getJSONArray("rooms");

                                roomnr[o] = (String) rooms[o].get(0);

                                int col = les + 1;
                                lessons[col][1].setText(Html.fromHtml("<p align=center> <b> "
                                        + subject[0]
                                        + " </b><small>"
                                        + roomnr[0]
                                        + "</small></p>"
                                        + "<p align=center><b> "
                                        + subject[1]
                                        + " </b><small>"
                                        + roomnr[1]
                                        + "</small></p>"));
                            }
                        }
                        else if (hours[les].length() == 3)
                        {
                            for (int o =0; o < 3; o++)
                            {
                                lektion[les][o] = hours[les].getJSONObject(o);

                                subject[o] = (String) lektion[les][o].get("subject");

                                rooms[o] = lektion[les][o].getJSONArray("rooms");

                                roomnr[o] = (String) rooms[o].get(0);

                                int col = les + 1;
                                lessons[col][1].setText(Html.fromHtml("<b> "
                                        + subject[0]
                                        + " </b><small>"
                                        + roomnr[0]
                                        + "</small></p>"
                                        + "<b> "
                                        + subject[1]
                                        + " </b><small>"
                                        + roomnr[1]
                                        + "</small></p>"
                                        + "<b> "
                                        + subject[2]
                                        + " </b><small>"
                                        + roomnr[2]
                                        + "</small></p>"));
                            }
                        }
                        else
                        {
                            lektion[les][0] = hours[les].getJSONObject(0);
                            Log.w("mytag", String.valueOf(lektion[les][0]));

                            subject[les] = (String) lektion[les][0].get("subject");
                            Log.w("mytag", String.valueOf(subject[les]));

                            rooms[les] = lektion[les][0].getJSONArray("rooms");
                            Log.w("mytag",String.valueOf(rooms[les]));

                            roomnr[les] = (String) rooms[les].get(0);
                            Log.w("mytag",String.valueOf(roomnr[les]));

                            int col = les + 1;
                            lessons[col][1].setText(Html.fromHtml("<b>" + subject[les] + "</b>" + "<br/>" + "<small>" + roomnr[les] + "</small>"));


                        }
                    }
              /*  }
                catch (Exception e)
                {
                    Log.w("error","error");
                }
            potoato++;
            }*/





           /* for (int a = 0; a < 4; a ++)
            {
                try
                {
                    hours[a] = days[num].getJSONArray(Integer.toString(a));
                    Log.w("asdffa", String.valueOf(hours[a]));
                    lektion[a] = hours[a].getJSONArray(a + 1);
                    Log.w("lökjlöj", String.valueOf(lektion[a]));

                }
                catch (Exception e)
                {
                    Log.w("asdf", "error");
                }

            }*/

            /* int z =0;
            while ( z < 9)
            {
                for (int a = 0; a < 9; a ++)
                {
                    try
                    {
                        hours[a] = days[num].getJSONArray(Integer.toString(a));
                    }
                    catch (Exception e)
                    {
                        Log.w("asdf", "error");
                    }

                }
                z++;
            }
            hours[0] = days[num].getJSONArray("0");
            hours[1] = days[num].getJSONArray("1");
            hours[2] = days[num].getJSONArray("2");
            hours[3] = days[num].getJSONArray("3");
            hours[4] = days[num].getJSONArray("4");
            hours[5] = days[num].getJSONArray("5");
            hours[6] = days[num].getJSONArray("6");
            hours[7] = days[num].getJSONArray("7");
            hours[8] = days[num].getJSONArray("8");*/
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

        int monday = 0;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        int date = cal.get(Calendar.DATE);

        for (int q = 0; q < 11;q ++)
        {
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
            res = 4;
        }
        else if (mo ==1)
        {
            res = 5;
        }
        else if (mo ==2)
        {
            res = 6;
        }
        else if (mo ==3)
        {
            res = 10;
        }
        else if (mo ==4)
        {
            res = 7;
        }
        else if (mo ==5)
        {
            res = 8;
        }
        else if (mo ==6)
        {
            res = 9;
        }
        else if (mo ==7)
        {
            res = 2;
        }
        else if (mo ==8)
        {
            res = 3;
        }
        else if (mo ==9)
        {
            res = 0;
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