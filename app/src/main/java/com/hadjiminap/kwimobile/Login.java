package com.hadjiminap.kwimobile;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;


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

               if (hasFocususr)
               {
                    usr.setBackgroundDrawable(getResources().getDrawable(R.drawable.apptheme_textfield_activated_holo_light));
                    usr.setText("");
               }
               else
               {
                   usr.setBackgroundDrawable(getResources().getDrawable(R.drawable.apptheme_textfield_focused_holo_light));

                   if(TextUtils.isEmpty(input))
                   {
                   usr.setText("Benutzername");
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

                if (hasFocus)
                {
                    pwd.setBackgroundDrawable(getResources().getDrawable(R.drawable.apptheme_textfield_activated_holo_light));
                    pwd.setText("");
                }
                else
                {
                    pwd.setBackgroundDrawable(getResources().getDrawable(R.drawable.apptheme_textfield_focused_holo_light));

                    if(TextUtils.isEmpty(input))
                    {
                        pwd.setText("Passwort");
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
        //Setup TAG for error logging
        final String TAG = "LoginActivity";

         EditText usr = (EditText) findViewById(R.id.editTextname);
         EditText pwd = (EditText) findViewById(R.id.editTextpwd);
         String tokenfrompost = null;

        //setup HttpURLConnection
        URL url;
        HttpURLConnection conn;
        String response= null;

        try
        {
            url=new URL("https://somesite/somefile.php");

            //Encode input
            String param="user"+ URLEncoder.encode(usr.getText().toString(),"UTF - 8")+
            "password"+URLEncoder.encode(pwd.getText().toString(),"UTF-8");
            //Opens connection . url.openConnection will return with https if called on https url
            conn=(HttpURLConnection)url.openConnection();
            //Setup Output to "true" => POST
            conn.setDoOutput(true);
            //actually unnecessary
            conn.setRequestMethod("POST");

            //Define length -> android user documentation recomendation
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content- Type", "application/x-www-form-urlencoded");

            //Send POST
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();

         //build the string to store the response text from the server
         //   String response= "";

         //Listen to stream
            Scanner inStream = new Scanner(conn.getInputStream());

            while(inStream.hasNextLine())
                tokenfrompost+=(inStream.nextLine());

        }
        catch(MalformedURLException ex)
        {
            Toast.makeText(Login.this,ex.toString(), Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex)
        {

            Toast.makeText(Login.this,ex.toString(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Log.e(TAG,"Error in postrequest");
        }
        //Return token
        return tokenfrompost;

    }

    public String getrequest (String token)
    {
        //Setup TAG for error logging
        final String TAG = "LoginActivity";

        //Create client
        HttpClient httpClientget = new DefaultHttpClient();
        // Set URL
        HttpGet httpGet = new HttpGet("www.example.com");

        //Add token to header
        httpGet.addHeader("token", token);
        //Setup Variables
        InputStream inputStream = null;
        String jsondata = null;

        try {

            HttpResponse response = httpClientget.execute(httpGet);
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();

            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder builder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line + "\n");
            }
            jsondata = builder.toString();
        }
        catch (Exception e)
        {
            Log.e(TAG,"Error in postrequest");
        }
        finally
        {
            try
            {
                if(inputStream != null)inputStream.close();
            }
            catch(Exception squish){ Log.e(TAG,"Error in postrequest");}
        }

        return jsondata;


    }

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

          //  String token = new String(postrequest());
            //String JSON = new String (getrequest(token));

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