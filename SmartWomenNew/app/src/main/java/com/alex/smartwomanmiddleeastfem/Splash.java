package com.alex.smartwomanmiddleeastfem;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity {

	private static String TAG = Splash.class.getName();
	private static long SLEEP_TIME = 3; // Sleep for some time
	SharedPreferences spe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // Removes title bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // Removes
																// notification
																// bar

		setContentView(R.layout.splashh);
		spe = getSharedPreferences("t", Activity.MODE_PRIVATE);
		// Start timer and launch main activity
		IntentLauncher launcher = new IntentLauncher();
		launcher.start();
	}

	private class IntentLauncher extends Thread {
		@Override
		/**
		 * Sleep for some time and than start new activity.
		 */
		public void run() {
			try {
				// Sleeping
				Thread.sleep(SLEEP_TIME * 2000);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}

			String myIntValue = spe.getString("u", "0");

			if (!myIntValue.contains("0")){

				if (myIntValue.contains("y")) {
					Intent intent = new Intent(Splash.this,WebViewDemoActivity.class);
					Splash.this.startActivity(intent);
					Splash.this.finish();
				}
			} else {
				Intent intent = new Intent(Splash.this, Auth.class);
				Splash.this.startActivity(intent);
				Splash.this.finish();
			}

			// Start main activity

		}
	}
}
