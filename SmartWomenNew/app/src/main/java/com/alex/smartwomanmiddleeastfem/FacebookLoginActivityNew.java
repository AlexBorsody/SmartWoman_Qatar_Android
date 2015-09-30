package com.alex.smartwomanmiddleeastfem;

/**
 * Created by rvtech on 8/21/2015.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.hipmob.gifanimationdrawable.GifAnimationDrawable;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class FacebookLoginActivityNew extends Activity {
    CallbackManager callbackManager;
    public static String token = null, facebook_user_id, facebook_user_name, uriString;
    // static int count = 1;
    String email, PROFILE_PIC_URL, username;
    private ProgressDialog progressDialog;
    String from,fb_id,fb_username;
    boolean where;
    private GifAnimationDrawable little, big;
    ImageView ia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(FacebookLoginActivityNew.this);
        setContentView(R.layout.activity_facebook_login);

        ia = (ImageView) findViewById(R.id.imageView2);

        try {

            big = new GifAnimationDrawable(getResources().openRawResource(
                    R.raw.anim2));
            // big.setOneShot(true);
            Log.v("GifAnimationDrawable", "===>Four");
        } catch (IOException ioe) {

        }

        ia.setImageDrawable(big);
        big.setVisible(true, true);
        ia.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        from = intent.getStringExtra("from");

        where = intent.getBooleanExtra("login", false);
/*        Local.save(FacebookLoginActivity.this, "user_name", facebook_user_name.replace(" ", ""));
        Local.save(FacebookLoginActivity.this, "user_id", facebook_user_id);*/

     fb_id=   Local.fetch(FacebookLoginActivityNew.this, "user_id");
     fb_username=   Local.fetch(FacebookLoginActivityNew.this,"user_name");



        if(fb_id.length()>0 && fb_username.length()>0)
        {
            facebook_user_name =fb_username;
            facebook_user_id =fb_id;

            new Thread(null, SignupServicee, "").start();
        }
else {

      /*  PackageInfo packageInfo;
        String key = null;
        try {
//getting application package name, as defined in manifest
            String packageName = getApplicationContext().getPackageName();

//Retriving package info
            packageInfo = getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

// String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }*/


            callbackManager = CallbackManager.Factory.create();

            AccessToken token = AccessToken.getCurrentAccessToken();
            LoginManager.getInstance().logInWithReadPermissions(FacebookLoginActivityNew.this,
                    Arrays.asList("public_profile", "user_actions.books", "user_actions.fitness", "email"
                    ));




       /* if(token.toString().contains("ACCESS_TOKEN_REMOVED"))
        {
          //  finish();
            return;
        }*/

            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    getUserInformation();
                    // if (count == 1) {
                    //   count++;
                    //    Toast.makeText(FacebookLoginActivity.this, "Please click again on Facebook to" + " Login" + " ", Toast.LENGTH_SHORT).show();
                    //  FacebookLoginActivity.this.finish();
                    //  }
                }

                @Override
                public void onCancel() {
                    // App code
                    Toast.makeText(getApplicationContext(), "Oncancel error", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                    /*Toast.makeText(getApplicationContext(), "Something went wrong,please try again", Toast.LENGTH_SHORT).show();
                    finish();*/


                    Intent i = new Intent(FacebookLoginActivityNew.this,FacebookLoginActivityNew.class);
                    startActivity(i);
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserInformation() {

        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            facebook_user_id = profile.getId();
            facebook_user_name = profile.getName().trim();

            if(facebook_user_id.length()>0&& facebook_user_name.length()>0)
            {
                Local.save(FacebookLoginActivityNew.this, "user_name", facebook_user_name.replace(" ", ""));
                Local.save(FacebookLoginActivityNew.this, "user_id", facebook_user_id);
            }

            if (where == false) {
                new Thread(null, Login, "").start();
            } else {
                new Thread(null, SignupServicee, "").start();
            }


            final String firsName = profile.getFirstName();
            final String middleName = profile.getMiddleName();
            final String lastName = profile.getLastName();
            final Uri profilePic = profile.getProfilePictureUri(300, 300);
            uriString = profilePic.toString();

            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            token = accessToken.getToken();

         /*   if(from.contains("a"))
            {
                Intent i = new Intent(FacebookLoginActivity.this, Auth.class);
                i.putExtra("token",String.valueOf(token));
                startActivity(i);
                finish();
            }
            else
            {
                Intent i = new Intent(FacebookLoginActivity.this, Auth.class);
                i.putExtra("token",String.valueOf(token));
                startActivity(i);
                finish();
            }*/


            GraphRequest request = GraphRequest.newMeRequest(accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            System.out.println("object = " + object);
                     /*       try {
                               email = object.getString("email");
                                // username  = object.getString("username");



                        *//*        Intent intent = new Intent(FacebookLoginActivity.this, FacebookProfileActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.flip_horizontal_in, R.anim.fade_out);
                                finish();*//*

                                // Toast.makeText(FacebookLoginActivity.this, "Logged in as  " + facebookUserName+ email+ token, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/
                            // setDataFromFacebookJsonObject(object);
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email");
            request.setParameters(parameters);
            request.executeAsync();
        }
        else
        {
            Intent i = new Intent(FacebookLoginActivityNew.this,FacebookLoginActivityNew.class);
            startActivity(i);

            Log.e("hahahahahahah","hahahahahaha");
        }
    }



    Runnable SignupService = new Runnable() {

        @Override
        public void run() {
            String result = "";
            try {
                result = WebServiceHandler.register_facebook(FacebookLoginActivityNew.this, facebook_user_id,
                        facebook_user_id);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Message msg = new Message();
            msg.obj = result;
            SignupHandlerc.sendMessage(msg);
        }
    };
    @SuppressLint("HandlerLeak")
    Handler SignupHandlerc = new Handler() {

        @Override
        public void handleMessage(Message msg) {


            String res = msg.obj.toString();

            if (res.equalsIgnoreCase("true")) {

                Intent i = new Intent(FacebookLoginActivityNew.this, WebViewDemoActivity.class);

                startActivity(i);

                FacebookLoginActivityNew.this.finish();

				/*
                 * Editor editor = sp.edit(); editor.putBoolean("orglogin",
				 * true); editor.putString("email", username);
				 * editor.putString("pwd", pwd); editor.commit();
				 */


            } else {

                Toast.makeText(FacebookLoginActivityNew.this,
                        "You are already registered",
                        Toast.LENGTH_SHORT).show();
            }

        }
    };


    Runnable Login = new Runnable() {

        @Override
        public void run() {
            String result = "";
            try {
                result = WebServiceHandler.register_facebook(FacebookLoginActivityNew.this, facebook_user_id,
                        facebook_user_id);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Message msg = new Message();
            msg.obj = result;
            SignupHandlerr.sendMessage(msg);
        }
    };
    @SuppressLint("HandlerLeak")
    Handler SignupHandlerr = new Handler() {

        @Override
        public void handleMessage(Message msg) {


            String res = msg.obj.toString();

            if (res.equalsIgnoreCase("true")) {

                /*Intent i = new Intent(FacebookLoginActivityNew.this, WebViewDemoActivity.class);

                startActivity(i);

                FacebookLoginActivityNew.this.finish();*/

                fb_id=   Local.fetch(FacebookLoginActivityNew.this, "user_id");
                fb_username=   Local.fetch(FacebookLoginActivityNew.this,"user_name");

                if(fb_id.length()>0 && fb_username.length()>0)
                {
                    facebook_user_name =fb_username;
                    facebook_user_id =fb_id;

                    new Thread(null, SignupServicee, "").start();
                }

				/*
                 * Editor editor = sp.edit(); editor.putBoolean("orglogin",
				 * true); editor.putString("email", username);
				 * editor.putString("pwd", pwd); editor.commit();
				 */


            } else {
                fb_id=   Local.fetch(FacebookLoginActivityNew.this, "user_id");
                fb_username=   Local.fetch(FacebookLoginActivityNew.this,"user_name");

                if(fb_id.length()>0 && fb_username.length()>0)
                {
                    facebook_user_name =fb_username;
                    facebook_user_id =fb_id;

                    new Thread(null, SignupServicee, "").start();
                }


                /*Toast.makeText(FacebookLoginActivity.this,
                        "You are already registered",
                        Toast.LENGTH_SHORT).show();

                finish();*/
            }

        }
    };


    Runnable SignupServicee = new Runnable() {

        @Override
        public void run() {
            String result = "";
            try {
                result = WebServiceHandler.signupservice(FacebookLoginActivityNew.this, facebook_user_id,
                        facebook_user_id);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Message msg = new Message();
            msg.obj = result;
            SignupHandler.sendMessage(msg);
        }
    };
    @SuppressLint("HandlerLeak")
    Handler SignupHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            String res = msg.obj.toString();

            if (res.equalsIgnoreCase("true")) {

                Local.save(FacebookLoginActivityNew.this, "user_name", facebook_user_name.replace(" ", ""));
                Local.save(FacebookLoginActivityNew.this, "user_id", facebook_user_id);
                //   e.putString("u", "y");


                //    e.commit();
                Intent i = new Intent(FacebookLoginActivityNew.this, WebViewDemoActivity.class);

                startActivity(i);
                FacebookLoginActivityNew.this.finish();

				/*
				 * Editor editor = sp.edit(); editor.putBoolean("orglogin",
				 * true); editor.putString("email", username);
				 * editor.putString("pwd", pwd); editor.commit();
				 */


            } else {



                Toast.makeText(FacebookLoginActivityNew.this,
                        facebook_user_name.replace(" ", "")  + " userId " + facebook_user_id,
                        Toast.LENGTH_SHORT).show();

                finish();
            }

        }
    };
}
