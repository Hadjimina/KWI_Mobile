package com.hadjiminap.kwimobile;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener
{

    private DrawerLayout drawerLayout;
    private ListView listView;
    private ActionBarDrawerToggle drawerListener;
    private CustomAdapter myAdapter;
    private String[] menus;
    private TextView[] lesson;
    public String sender = "youshouldnotseethis";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Linking and setup of variables
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout1);
        menus = getResources().getStringArray(R.array.menu);
        listView = (ListView) findViewById(R.id.drawerlist1);

        myAdapter = new CustomAdapter(this, "font.ttf");
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);

        //Setup for ic_navigaion_drawer animation
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Action bar styling
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00b796")));
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\">" + getString(R.string.app_name) + "</font>"));

        //Set font of actionBar
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        Typeface tfbar = Typeface.createFromAsset(getAssets(), "font.ttf");
        if (actionBarTitleView != null) {
            actionBarTitleView.setTypeface(tfbar);
        }

        //Set TimeTable fragment as default fragment
        Fragment newFragment1;
        FragmentManager fm = getFragmentManager();
        newFragment1 = new Timetable();
        fm.beginTransaction().replace(R.id.mainContent, newFragment1).commit();
        setTitle(menus[0]);



        //Drawer listener open close setup
        drawerListener= new ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_navigation_drawer, R.string.drawer_open, R.string.drawer_close)
        {
          @Override
          public void onDrawerClosed(View drawerView)
          {
              //Toast.makeText(MyActivity.this," closed", Toast.LENGTH_SHORT).show();
          }

          @Override
          public void onDrawerOpened(View drawerView)
          {
              //Toast.makeText(MainActivity.this," opened", Toast.LENGTH_SHORT).show();
          }
        };

        //Set Drawerlistener
        drawerLayout.setDrawerListener(drawerListener);

    }

    //SEND DATA TO FRAGMENT
    public String getData()
    {
        //SENDING DATA WITH SENDER
        //GET JSON FROM ASYNC FROM LOGIN.CLASS
        Bundle extras = getIntent().getExtras();
        String sender = extras.getString("sender");

        return sender;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        selectItem(position);
    }

    public void selectItem(int position)
    {
        Fragment newFragment = new Fragment();
        FragmentManager fm = getFragmentManager();
        switch(position){
            case 0:
                newFragment = new Timetable();
                break;
            case 1:
                newFragment = new settings();
                break;
            case 2:
                newFragment = new improvements();
                break;
            case 3:
                newFragment = new testfrag();
                break;
            case 4:
                newFragment = new testfrag();
                break;
        }
        fm.beginTransaction().replace(R.id.mainContent, newFragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        listView.setItemChecked(position, true);
        setTitle(menus[position]);
        drawerLayout.closeDrawer(listView);
    }

    public void setTitle(String title)
    {
        getActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\">" + title + "</font>"));
    }

    //Sync ic_navigaion_drawer animation with navigation drawer open or close
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    //Click opens Navigation Drawer
   @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (drawerListener.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
       drawerListener.onConfigurationChanged(newConfig);
    }


    @Override
    public void onBackPressed()
    {
        //Don't do anything when back button is pressed
    }

}


    //Custom Adapter
    class CustomAdapter extends BaseAdapter
    {
        private Context context;
        String[] menus;
        int[] images = {R.drawable.icon1, R.drawable.icon2, R.drawable.ic_action_2, R.drawable.ic_action_3, R.drawable.ic_action_4};
        Typeface tf;


        public CustomAdapter(Context context, String font)
        {
            tf = Typeface.createFromAsset(context.getAssets(), font);
            this.context = context;
            menus = context.getResources().getStringArray(R.array.menu);
        }

        @Override
        public int getCount()
        {
            return menus.length;
        }

        @Override
        public Object getItem(int position)
        {
            return menus[position];
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View row = null;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.custom_row, parent, false);
            } else {
                row = convertView;
            }

            TextView titleTextView = (TextView) row.findViewById(R.id.textView1);
            ImageView titleImageView = (ImageView) row.findViewById(R.id.imageView1);
            titleTextView.setTypeface(tf);
            titleTextView.setText(menus[position]);
            titleImageView.setImageResource(images[position]);


            return row;
        }
    }