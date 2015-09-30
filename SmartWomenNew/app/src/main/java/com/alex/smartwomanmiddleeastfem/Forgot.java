package com.alex.smartwomanmiddleeastfem;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forgot extends Activity {
	EditText e;
	String s;
	Button b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forgot);

		e = (EditText) findViewById(R.id.editText1);
		b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(null, Loginservice, "").start();
			}
		});

	}

	Runnable Loginservice = new Runnable() {

		@Override
		public void run() {
			String result = "";
			s = e.getText().toString();

			String url = "http://www.qa-msmartwoman.com/forget_password/" + s;

			try {

				result = WebServiceHandler.callLoginService(Forgot.this, url);

			} catch (Exception e) {

				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = result;
			LoginHandler.sendMessage(msg);
		}
	};

	Handler LoginHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			String res = msg.obj.toString();

			if (res.equalsIgnoreCase("true")) {
				Toast.makeText(Forgot.this, "Mail sent to your email",
						Toast.LENGTH_SHORT).show();

			} else {

				Toast.makeText(Forgot.this, "Please enter correct Email ",
						Toast.LENGTH_SHORT).show();
			}
		}

	};
}