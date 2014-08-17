package com.hadjiminap.kwimobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Login extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Remove top border
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Set content view
        setContentView(R.layout.loginscreen);

        //Pair and setup variable with items
        TextView tx = (TextView) findViewById(R.id.textView);
        final  EditText usr = (EditText) findViewById(R.id.editTextname);
        final  EditText pwd = (EditText) findViewById(R.id.editTextpwd);
        final Button login = (Button) findViewById(R.id.loginbtn);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Set ProgressBar INVISIBLE
        progressBar.setVisibility(View.INVISIBLE);

        //Set Font
        Typeface tf = Typeface.createFromAsset(getAssets(), "font.ttf");
        tx.setTypeface(tf);
        usr.setTypeface(tf);
        login.setTypeface(tf);

        //Change background when focuschange and set text to empty edittext
       usr.setOnFocusChangeListener(new View.OnFocusChangeListener()
       {
           @Override
           public void onFocusChange(View v, boolean hasFocususr)
           {
              String input = usr.getText().toString();

               if (hasFocususr == true)
               {
                    usr.setBackgroundDrawable(getResources().getDrawable(R.drawable.apptheme_textfield_activated_holo_light));
                    usr.setText("");
               }
               else if (hasFocususr == false)
               {
                   usr.setBackgroundDrawable(getResources().getDrawable(R.drawable.apptheme_textfield_focused_holo_light));

                   if(TextUtils.isEmpty(input))
                   {
                   usr.setText("Benutzername");
                   return;
                   }
               }

           }
       });

       pwd.setOnFocusChangeListener(new View.OnFocusChangeListener()
       {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                String input = pwd.getText().toString();

                if (hasFocus == true)
                {
                    pwd.setBackgroundDrawable(getResources().getDrawable(R.drawable.apptheme_textfield_activated_holo_light));
                    pwd.setText("");
                }
                else if (hasFocus == false)
                {
                    pwd.setBackgroundDrawable(getResources().getDrawable(R.drawable.apptheme_textfield_focused_holo_light));

                    if(TextUtils.isEmpty(input))
                    {
                        pwd.setText("Passwort");
                        return;
                    }
                }

            }
        });

        login.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                 login.setBackgroundColor(getResources().getColor(R.color.btnpressed));
                return false;
            }
        });

    }

    public  String postrequest()
    {
         EditText usr = (EditText) findViewById(R.id.editTextname);
         EditText pwd = (EditText) findViewById(R.id.editTextpwd);
         String returntoken = null;

        //Create client
        HttpClient httpClient = new DefaultHttpClient();
        // Set URL
        HttpPost httpPost = new HttpPost("www.example.com");

        //Key value pairs (Login credentials)
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("id", usr.getText().toString()));
        nameValuePair.add(new BasicNameValuePair("password", pwd.getText().toString()));


        //Encoding POST data
        try
        {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e)
        {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            //get response token
            returntoken = response.toString();
        }
        catch (ClientProtocolException e)
        {
            // Log exception
            e.printStackTrace();
        }
        catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }

        return returntoken;

    }//hello

    //OnClick
    public void OnLoginClick(View v)
    {
        Button login = (Button) findViewById(R.id.loginbtn);
        login.setClickable(false);

        new Async().execute();
    }


    class Async extends AsyncTask<Void, Integer, Void>
    {
        //Pair and setup variable with items
        TextView tx = (TextView) findViewById(R.id.textView);
        EditText usr = (EditText) findViewById(R.id.editTextname);
        Button login = (Button) findViewById(R.id.loginbtn);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        int progcount;

        @Override
        public void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params)
        {
           login.setBackgroundColor(getResources().getColor(R.color.btnnormal));
           //Placeholder for wait time
           progcount = 0;
           while (progcount < 1)
           {
                progcount++;

                SystemClock.sleep(1000);
            }

            String token = new String(postrequest());

            getJson(token);

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Intent switcher = new Intent(Login.this, MainActivity.class);
            Login.this.startActivity(switcher);

        }
    }
}