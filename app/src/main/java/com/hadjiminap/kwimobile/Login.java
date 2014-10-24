package com.hadjiminap.kwimobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class Login extends Activity
{
    //Setup for remember me checkbox
    private CheckBox rememberme;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private static final int LONG_DELAY = 1000;


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
        rememberme = (CheckBox) findViewById(R.id.rememberme);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        //Set ProgressBar INVISIBLE
        progressBar.setVisibility(View.INVISIBLE);



        //Set Font
        Typeface tf = Typeface.createFromAsset(getAssets(), "font.ttf");
        tx.setTypeface(tf);
        usr.setTypeface(tf);
        login.setTypeface(tf);
        rememberme.setTypeface(tf);

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

        //Check if radiobox checked
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true)
        {
            usr.setText(loginPreferences.getString("username", ""));
            pwd.setText(loginPreferences.getString("password", ""));
            rememberme.setChecked(true);
        }
    }

    //OnClick
    public void OnLoginClick(View v)
    {
        EditText username = (EditText) findViewById(R.id.editTextname);
        EditText password = (EditText) findViewById(R.id.editTextpwd);
        String uname = username.getText().toString();
        String passwd = password.getText().toString();
        Button login = (Button) findViewById(R.id.loginbtn);

        if (uname.equals("Benutzername")|| uname.equals(null))
        {
            Toast.makeText(Login.this, "Es wird ein Benutzername und ein Passwort benötigt", Toast.LENGTH_SHORT).show();
            login.setBackgroundColor(getResources().getColor(R.color.btnnormal));
            Log.w("name",username.getText().toString());
        }
        else if (passwd.equals("Passwort")||passwd.equals(null))
        {
            Toast.makeText(Login.this, "Es wird ein Benutzername und ein Passwort benötigt", Toast.LENGTH_SHORT).show();
            login.setBackgroundColor(getResources().getColor(R.color.btnnormal));
            Log.w("pwd", password.getText().toString());
        }
        else
        {
            Log.w("else","ELSE");
            login.setClickable(false);
            try
            {
                new Async().execute();
                login.setClickable(true);
            }

            catch (Exception e)
            {
                Toast.makeText(Login.this, "Der Benutzername oder das Passwort sind falsch", Toast.LENGTH_LONG).show();
                Log.w("mytag","usr or pwd");
            }

        }
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(username.getWindowToken(), 0);

        if (rememberme.isChecked())
        {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", uname);
            loginPrefsEditor.putString("password", passwd);
            loginPrefsEditor.commit();
        }
        else
        {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
        }
    }

    public String postrequest () throws IOException
    {
        try {
            EditText username = (EditText) findViewById(R.id.editTextname);
            EditText password = (EditText) findViewById(R.id.editTextpwd);

            URL url = new URL("https://info.kwi.ch/s/timetable/api");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username.getText().toString()));
            params.add(new BasicNameValuePair("password", password.getText().toString()));

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            InputStream response = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(response));

            String line = "";
            String serverResponseMessage = "";
            while ((line = reader.readLine()) != null) {

                serverResponseMessage += line;
            }

            response.close();
            //FOR TESTING ONLY
           /* final String TAG = "Something";
            if (serverResponseMessage.length() > 4000) {
                Log.w(TAG, "sb.length = " + serverResponseMessage.length());
                int chunkCount = serverResponseMessage.length() / 4000;     // integer division
                for (int i = 0; i <= chunkCount; i++) {
                    int max = 4000 * (i + 1);
                    if (max >= serverResponseMessage.length()) {
                        Log.w(TAG, "chunk " + i + " of " + chunkCount + ":" + serverResponseMessage.substring(4000 * i));
                    } else {
                        Log.w(TAG, "chunk " + i + " of " + chunkCount + ":" + serverResponseMessage.substring(4000 * i, max));
                    }
                }
            } else {
                Log.w(TAG, serverResponseMessage.toString());
            }*/
            return serverResponseMessage;
        }
        catch (UnknownHostException e)
        {
            return "unknown host";
        }
        catch (SocketTimeoutException e)
        {
            return "timeout";
        }


    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    class Async extends AsyncTask<Void, Integer, String>
    {
        //Pair and setup variable with items
        Button login = (Button) findViewById(R.id.loginbtn);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        int progcount;

        @Override
        public void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
            login.setBackgroundColor(getResources().getColor(R.color.btnnormal));

        }

        @Override
        protected String doInBackground(Void... params)
        {
            String JSON = null;
            try
            {
                JSON = new String(postrequest());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


            return JSON;
        }

        @Override
        protected void onPostExecute(String data)
        {
            if (data.equals("unknown host"))
            {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Login.this, "Der Server ist unerreichbar.\nVersuchen Sie es Später nochmals.", Toast.LENGTH_SHORT).show();
                Log.w("unknown",data);
            }
            else if (data.equals("timeout"))
            {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Login.this, "Der Server ist unerreichbar.\nVersuchen Sie es Später nochmals.", Toast.LENGTH_SHORT).show();
                Log.w("timeout",data);
            }
            else
            {
                Log.w("mytasdfasdfag",data);
                Intent switcher = new Intent(Login.this, MainActivity.class);
                switcher.putExtra("sender",data);
                Login.this.startActivity(switcher);
            }
        }
    }
}