package com.alex.smartwomanmiddleeastfem;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebServiceHandler {

    static HashMap<String, String> imagemap;
    static ArrayList<HashMap<String, String>> imagelist;
    static Globals global;
    static ArrayList<String> splashText;
    Context c;

    public static String signupservice(Context c, String email, String password) {
        SharedPreferences sp = c.getSharedPreferences("your_prefs",
                Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        global = (Globals) c.getApplicationContext();
        String result = "";
        Log.v("password id is", "" + password);

        //	String url = "http://dev-smartwoman-oman.gotpantheon.com/is_valid";
        String url = "http://www.qa-msmartwoman.com/is_valid";
        String respage = "";

        try {
            DefaultHttpClient dhc = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();

            HttpPost postMethod = new HttpPost(url);

            List<NameValuePair> namepairs = new ArrayList<NameValuePair>();

           /* namepairs.add(new BasicNameValuePair("username", "AlexBorsody" ));
            namepairs.add(new BasicNameValuePair("pass", "892248739750"));*/

            namepairs.add(new BasicNameValuePair("username", email ));
            namepairs.add(new BasicNameValuePair("pass", password));

            Log.v("email is", "" + email);
            Log.v("password", "" + password);

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));
            splashText = new ArrayList<String>();
            try {
                respage = dhc.execute(postMethod, res);

                imagemap = new HashMap<String, String>();

				/*if (respage.equals("0")) {
                    return result = "false";
				}*/

                if (respage.length() > 3) {
                    result = "true";

                    // splashText.add(respage);
                    // global.setSplashText(respage);

                    editor.putString("your_int_key", respage);
                    editor.commit();
                } else {

                    result = "false";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String callLoginService(Context c, String url)
            throws JSONException {

        String result = "error";

        String response = "";

        DefaultHttpClient dhc = new DefaultHttpClient();

        ResponseHandler<String> res = new BasicResponseHandler();

        HttpGet get = new HttpGet(url);

        try {

            response = dhc.execute(get, res);

            Log.v("response of login page", "login" + response);

            if (response.contains("Mail sent to your email"))

            {

                result = "true";
            } else {

                result = "false";
            }

        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String register_facebook(Context c, String id, String emaill) {
        SharedPreferences sp = c.getSharedPreferences("your_prefs",
                Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        global = (Globals) c.getApplicationContext();
        String result = "";

        String email = emaill.replace(" ", "");

        //	String url = "http://dev-smartwoman-oman.gotpantheon.com/is_valid";
        String url = "http://www.qa-msmartwoman.com/user_registration?username=" + email + "&pass=" + id + "&email=" + email + "@gmail.com&age=23to24";
        String respage = "";

        try {
            DefaultHttpClient dhc = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();

            //    http://dev-qatar2.pantheon.io/user_registration?username=fianl&pass=password&email=final@kgmail.com&age=23to24

            HttpGet postMethod = new HttpGet(url);


            try {
                respage = dhc.execute(postMethod, res);
                // imagelist = new ArrayList<HashMap<String, String>>();

                // splashText = new ArrayList<String>();
                imagemap = new HashMap<String, String>();

				/*if (respage.equals("0")) {
                    return result = "false";
				}*/

                if (respage.length() > 3) {
                    result = "true";

                } else {

                    result = "false";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
