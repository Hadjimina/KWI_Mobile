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

import java.util.ArrayList;
import java.util.Iterator;

//All the sources I used are makred in the matura paper in Chpater 2 in the corresponding section
public class Timetable extends Fragment
{
    String [] changes = new String[4];
    TableRow [] rows = new TableRow[11];
    public TextView[][] lessons = new TextView[11][6];
    private ArrayList<Lesson> less = new ArrayList<Lesson>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View ret =  inflater.inflate(R.layout.timetable, null);

        //SETUP ARRAY LIST AND POPULATE IT
        ArrayList<Integer> vals = new ArrayList<Integer>();
        for (int w = 0;w<11;w++)
        {
            vals.add(w);
        }

        //Setup font
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

        //Receiving Lesson
        MainActivity activity = (MainActivity) getActivity();
        less = activity.getLessons();

        //cycle through all the lessons objects and add them to the timetable
        Iterator <Lesson> it_throughdays = less.iterator();

        while (it_throughdays.hasNext())
        {
            Lesson less_on = it_throughdays.next();

            if (less_on.getDay()== 6)
            {
                continue;
            }

            int time = less_on.getTimeIndex()+1;
            int day = less_on.getDay();
            changes = move(less_on);

            if (less_on.getLength() == 1)
            {

                lessons[time][day].setText(Html.fromHtml(
                        "<b>"
                        +changes[0]
                        +less_on.getSubject().get(0)
                        +"</b><br/><small>"
                        +less_on.getRooms().get(0)
                        +"</small></font>"
                ));

            }
            else if (less_on.getLength() == 2)
            {

                lessons[time][day].setText(Html.fromHtml(
                        "<b> "
                       + changes[0]
                       + less_on.getSubject().get(0)
                       + " </b><small>"
                       + less_on.getRooms().get(0)
                       + "</small></font>"
                       + "<br /><b> "
                       + changes[1]
                       + less_on.getSubject().get(1)
                       + " </b><small>"
                       + less_on.getRooms().get(1)
                       + "</small></font>"
                ));
            }
            else if (less_on.getLength() == 3)
            {

                lessons[time][day].setText(Html.fromHtml(
                         "<b> "
                        +changes[0]
                        + less_on.getSubject().get(0)
                        + " </b><small>"
                        + less_on.getRooms().get(0)
                        + "</small></font><br />"
                        + "<b>"
                        + changes[1]
                        + less_on.getSubject().get(1)
                        + " </b><small>"
                        + less_on.getRooms().get(1)
                        + "</small></font><br />"
                        + "<b> "
                        + changes[2]
                        + less_on.getSubject().get(2)
                        + " </b><small>"
                        + less_on.getRooms().get(2)
                        + "</small></font>"
                ));

            }
            else if (less_on.getLength()==4)
            {
                lessons[time][day].setText(Html.fromHtml(
                        "<b> "
                        + less_on.getSubject().get(0)
                        + "</b><br /><small>"
                        + changes[0]
                        + less_on.getRooms().get(0)
                        +"</font>"
                        + " "
                        + changes[1]
                        + less_on.getRooms().get(1)
                        +"</font>"
                        + " <br />"
                        + changes[2]
                        + less_on.getRooms().get(2)
                        + " "
                        +"</font>"
                        + changes[3]
                        + less_on.getRooms().get(3)
                        + "</small></font></p>"
                ));

            }

        }


        return ret;
    }

    //Setup colour
    public String[] move(Lesson les)
    {
        String [] moveing = new String[7];

        for (int runner = 0; runner < les.getLength(); runner++)
        {
            if (les.isChanged().get(runner)==2)
            {
                moveing[runner] = "<font color='#ff0000'>";
            }
            else if (les.isChanged().get(runner)==1)
            {
                moveing[runner] = "<font color='#5fbf00'>";
            }
            else
            {
                moveing[runner] = "<font color='#000000'>";
            }
        }

        return moveing;
    }

    public void onBackPressed()
    {
        //Don't do anything when back button is pressed
    }


    }


