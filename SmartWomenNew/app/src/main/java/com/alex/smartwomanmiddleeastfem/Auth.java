package com.alex.smartwomanmiddleeastfem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hipmob.gifanimationdrawable.GifAnimationDrawable;

import java.io.IOException;

public class Auth extends Activity implements OnClickListener {

    String username, pwd;
    EditText email, password;
    TextView t;
    Button submit, reg, facebook;
    SharedPreferences loginPreferences, spe;
    CheckBox saveLoginCheckBox;
    Editor loginPrefsEditor;
    ProgressDialog pd;
    private GifAnimationDrawable little, big;
    ImageView ia;
    boolean connected = false;
    SharedPreferences.Editor e;
    Dialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.firstt);

        reg = (Button) findViewById(R.id.button12);
        reg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showpopup();

            }
        });

        facebook = (Button) findViewById(R.id.facebook);
        facebook.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Auth.this, FacebookLoginActivityNew.class);
                i.putExtra("login", true);
                startActivity(i);
            }
        });

        if(Local.fetch(Auth.this, "user_id").length()>0)
        {
            facebook.setVisibility(View.VISIBLE);
        }else
        {
            facebook.setVisibility(View.GONE);
        }

        ia = (ImageView) findViewById(R.id.imageView2);

        try {

            big = new GifAnimationDrawable(getResources().openRawResource(
                    R.raw.anim2));
            // big.setOneShot(true);
            android.util.Log.v("GifAnimationDrawable", "===>Four");
        } catch (IOException ioe) {

        }

        ia.setImageDrawable(big);
        big.setVisible(true, true);

        spe = getSharedPreferences("t", Activity.MODE_PRIVATE);
        e = spe.edit();

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        email = (EditText) findViewById(R.id.editText11);
        password = (EditText) findViewById(R.id.editText22);
        submit = (Button) findViewById(R.id.button11);
        t = (TextView) findViewById(R.id.forgotpassword);
        t.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Auth.this, Forgot.class);
                startActivity(i);
            }
        });

        email.setOnTouchListener(new OnTouchListener() {
            public void onFocus() {

            }


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                email.setHint("");
                password.setHint("Password");
                return false;
            }
        });

        password.setOnTouchListener(new OnTouchListener() {
            public void onFocus() {

            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                password.setHint("");
                email.setHint("Email");
                return false;
            }
        });

        submit.setOnClickListener(this);
        //spe = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.checkBox1);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        boolean saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            email.setText(loginPreferences.getString("email", ""));
            password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

        // INTERNET CONNECTIVITY

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connected = false;
        if (
                (null != connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                        ||
                        (null != connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
                ) {
            // we are connected to a network
            connected = true;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button11:


                username = email.getText().toString();
                pwd = password.getText().toString();

                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("email", username);
                    loginPrefsEditor.putString("password", pwd);
                    loginPrefsEditor.commit();

                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                if (email.getText().length() == 0) {

                    email.setError("username can't be empty.");

                } else if (password.getText().length() == 0) {
                    password.setError("password is empty.");

                } else {
                    if (connected == false) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                Auth.this);

                        //	alertDialogBuilder
                        //.setTitle("In order to provide the freshest recipes and juicing information this app must be connected to the internet, please check your internet settings");
                        alertDialogBuilder.setMessage("Through our app offers some offline features, in order to stay actively connected to the SmartWoman community in realtime, you need an internet connection, tap here to check your settings or wait until you have connection.");
                        // set positive button: Yes message

                        alertDialogBuilder.setNegativeButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // cancel the alert box and put a Toast to
                                        // the user

                                        startActivityForResult(
                                                new Intent(
                                                        android.provider.Settings.ACTION_SETTINGS),
                                                0);
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show alert
                        alertDialog.show();

                    }

				/*
				 * pd = ProgressDialog.show(Auth.this, null, "Loading...", true,
				 * false);
				 */
                    ia.setVisibility(View.VISIBLE);
                    new Thread(null, SignupService, "").start();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
		/*
		 * Intent setIntent = new Intent(Intent.ACTION_MAIN);
		 * setIntent.addCategory(Intent.CATEGORY_HOME);
		 * setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 * startActivity(setIntent);
		 */
        //Intent i = new Intent(Auth.this,Main.class);
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startMain);
        System.exit(0);
        finish();
    }

    Runnable SignupService = new Runnable() {

        @Override
        public void run() {
            String result = "";
            try {
                result = WebServiceHandler.signupservice(Auth.this, username,
                        pwd);

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
            // pd.dismiss();
            ia.setVisibility(View.INVISIBLE);
            String res = msg.obj.toString();

            if (res.equalsIgnoreCase("true")) {


                e.putString("u", "y");
                // e.putString("p", pwd);

                e.commit();
                Intent i = new Intent(Auth.this, WebViewDemoActivity.class);

                startActivity(i);
                Auth.this.finish();

				/*
				 * Editor editor = sp.edit(); editor.putBoolean("orglogin",
				 * true); editor.putString("email", username);
				 * editor.putString("pwd", pwd); editor.commit();
				 */


            } else {

                Toast.makeText(Auth.this,
                        "Please enter correct username and password",
                        Toast.LENGTH_SHORT).show();
            }

        }
    };

    public void showpopup() {

        dialog = new Dialog(Auth.this);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialoglayout);


        Button facebookk = (Button) dialog.findViewById(R.id.facebookk);
        Button app = (Button) dialog.findViewById(R.id.app);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);

        facebookk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Auth.this, FacebookLoginActivityNew.class);
                i.putExtra("login", true);
                startActivity(i);
            }
        });

        app.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Auth.this, WebViewDemoActivity.class);
                i.putExtra("reg", true);
                startActivity(i);
            }
        });

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
