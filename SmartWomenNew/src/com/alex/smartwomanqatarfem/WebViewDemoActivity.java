/*
 * Copyright (C) 2012 Daniel Medina <http://danielme.com>
 * 
 * This file is part of "Android WebView Demo".
 * 
 * "Android WebView Demo" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * "Android WebView Demo" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 3
 * along with this program.  If not, see <http://www.gnu.org/licenses/gpl-3.0.html/>
 */

package com.alex.smartwomanqatarfem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hipmob.gifanimationdrawable.GifAnimationDrawable;

public class WebViewDemoActivity extends Activity {
	private WebView webview;

	Globals globals;
	ProgressDialog pd;
	String uhdj;
	ImageView i;
	SharedPreferences.Editor e;
	boolean connected = false;
	SharedPreferences sp;
	private GifAnimationDrawable big;
	boolean reg;

	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainn);

		Intent intent = getIntent();
		reg = intent.getBooleanExtra("reg", false);

		i = (ImageView) findViewById(R.id.hj);

		try {

			big = new GifAnimationDrawable(getResources().openRawResource(
					R.raw.anim2));
			// big.setOneShot(true);
			android.util.Log.v("GifAnimationDrawable", "===>Four");
		} catch (IOException ioe) {

		}

		i.setImageDrawable(big);
		big.setVisible(true, true);

		sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
		// historyStack = new LinkedList<Link>();
		webview = (WebView) findViewById(R.id.webkit);

		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);

		webSettings.setDomStorageEnabled(true);
		// webview.addJavascriptInterface(new WebViewDemoActivity(), "Android");
		// webview.getSettings().setJavaScriptEnabled(true);

		webview.getSettings().setBuiltInZoomControls(true);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			// webview.getSettings().setPluginState(PluginState.ON);
			// webview.getSettings().setJavaScriptEnabled(true);
		} else {
			// IMPORTANT!! this method is no longer available since Android 4.3
			// so the code doesn't compile anymore
			// webview.getSettings().setPluginsEnabled(true);
		}

		// Internet

		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		connected = false;
		if ((null != connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) && connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
				|| (null != connectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI) && connectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
						.getState() == NetworkInfo.State.CONNECTED)) {
			// we are connected to a network
			connected = true;
		}

		if (connected == false) {
			/*
			 * Toast.makeText(Rss.this,
			 * "Connect to internet and Restart Application",
			 * Toast.LENGTH_SHORT).show();
			 */
			webview.setVisibility(View.INVISIBLE);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					WebViewDemoActivity.this);

			// alertDialogBuilder.setTitle("Please connect to Internet");
			alertDialogBuilder
					.setMessage("This app must be connected to the internet, please check your internet settings");
			// set positive button: Yes message

			alertDialogBuilder.setNegativeButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// cancel the alert box and put a Toast to the user

							startActivityForResult(new Intent(
									android.provider.Settings.ACTION_SETTINGS),
									0);
						}
					});
			AlertDialog alertDialog = alertDialogBuilder.create();
			// show alert
			alertDialog.show();

		}

		// downloads
		// webview.setDownloadListener(new CustomDownloadListener());

		webview.setWebViewClient(new CustomWebViewClient());

		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {

			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
			}

			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) {

			}

		});

		// http://stackoverflow.com/questions/2083909/android-webview-refusing-user-input
		webview.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_UP:
					if (!v.hasFocus()) {
						v.requestFocus();
					}
					break;
				}
				return false;
			}

		});

		if (reg == true) {
			if (Locale.getDefault().getLanguage().equals("es")) {

				webview.loadUrl("http://www.qa-msmartwoman.com/member/register");
				// webview.loadUrl("http://live-juice-guru.gotpantheon.com/user/login");
			} else {
				webview.loadUrl("http://www.qa-msmartwoman.com/member/register");
			}

			webview.requestFocus();
		} else {
			uhdj = sp.getString("your_int_key", "0");

			Log.e("Url is here ..............................", uhdj);
			// Welcome page loaded from assets directory
			if (Locale.getDefault().getLanguage().equals("es")) {

				webview.loadUrl(uhdj);
				// webview.loadUrl("http://live-juice-guru.gotpantheon.com/user/login");
			} else {
				webview.loadUrl(uhdj);
			}

			webview.requestFocus();
		}

	}

	class CustomWebViewClient extends WebViewClient {
		// the current WebView will handle the url
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.endsWith(".mp3") || url.endsWith(".aac")) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(url), "audio/mpeg");
				startActivity(intent);
				return true;
			}
			return false;
		}

		// history and navigation buttons
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if (checkConnectivity()) {
				if (pd != null) {
					if (pd.isShowing()) {
						pd.dismiss();
					}
				}

			}

			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			connected = false;
			if ((null != connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) && connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
					|| (null != connectivityManager
							.getNetworkInfo(ConnectivityManager.TYPE_WIFI) && connectivityManager
							.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
							.getState() == NetworkInfo.State.CONNECTED)) {
				// we are connected to a network
				connected = true;
			}

			if (connected == false) {
				/*
				 * Toast.makeText(Rss.this,
				 * "Connect to internet and Restart Application",
				 * Toast.LENGTH_SHORT).show();
				 */
				webview.setVisibility(View.INVISIBLE);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						WebViewDemoActivity.this);

				// alertDialogBuilder.setTitle("Please connect to Internet");
				alertDialogBuilder
						.setMessage("This app must be connected to the internet, please check your internet settings");
				// set positive button: Yes message

				alertDialogBuilder.setNegativeButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
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

			} else {
				// pd = ProgressDialog.show(WebViewDemoActivity.this, "",
				// "Loading...");

				Log.e("Logout URL", url);

				i.setVisibility(View.VISIBLE);

				if (url.contains("http://www.qa-msmartwoman.com/user/logout")) {
					// if
					// (url.contains("http://www.qa-msmartwoman.com/login_with_uid/1"))
					// {
					Intent i = new Intent(WebViewDemoActivity.this, Auth.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					WebViewDemoActivity.this.finish();
					// finish();
				}
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// pd.dismiss();

			Log.e("Logout URL", url);

			i.setVisibility(View.INVISIBLE);

		}

		// handles unrecoverable errors
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {

			Log.e("", "");
		}

	}

	public void go(View view) {
	}

	public void back(View view) {
		if (checkConnectivity()) {

			// progressBar.showContextMenu();
			/*
			 * Toast.makeText(WebViewDemoActivity.this, "Backkkkkkkkkkk",
			 * Toast.LENGTH_LONG).show();
			 */
			webview.goBack();
		}
	}

	public void forward(View view) {
		if (checkConnectivity()) {
			// progressBar.showContextMenu();
			webview.goForward();
		}
	}

	public void stop(View view) {
		webview.stopLoading();
		Toast.makeText(this, getString(R.string.stopping), Toast.LENGTH_LONG)
				.show();
	}

	public void history(View view) {
		showDialog(0);
	}

	private void updateButtons() {
		Button backButton = (Button) WebViewDemoActivity.this
				.findViewById(R.id.backButton);

		if (webview.canGoBack()) {
			backButton.setEnabled(true);
		} else {
			backButton.setEnabled(false);
		}

		Button forwardButton = (Button) WebViewDemoActivity.this
				.findViewById(R.id.forwardButton);

		if (webview.canGoForward()) {
			forwardButton.setEnabled(true);
		} else {
			forwardButton.setEnabled(false);
		}
	}

	private class DownloadAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			String result = "";
			String url = arg0[0];

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				InputStream inputStream = null;
				FileOutputStream fileOutputStream = null;
				try {
					HttpResponse httpResponse = httpClient.execute(httpGet);

					BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
							httpResponse.getEntity());

					inputStream = bufferedHttpEntity.getContent();

					String fileName = android.os.Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/webviewdemo";
					File directory = new File(fileName);
					File file = new File(directory, url.substring(url
							.lastIndexOf("/")));
					directory.mkdirs();

					// commons-io, I miss you :(
					fileOutputStream = new FileOutputStream(file);
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int len = 0;

					while (inputStream.available() > 0
							&& (len = inputStream.read(buffer)) != -1) {
						byteArrayOutputStream.write(buffer, 0, len);
					}

					fileOutputStream.write(byteArrayOutputStream.toByteArray());
					fileOutputStream.flush();

					result = getString(R.string.result)
							+ file.getAbsolutePath();
				} catch (Exception ex) {
					Log.e(WebViewDemoActivity.class.toString(),
							ex.getMessage(), ex);
					result = ex.getClass().getSimpleName() + " "
							+ ex.getMessage();
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException ex) {
							Log.e(WebViewDemoActivity.class.toString(),
									ex.getMessage(), ex);
							result = ex.getClass().getSimpleName() + " "
									+ ex.getMessage();
						}
					}
					if (fileOutputStream != null) {
						try {
							fileOutputStream.close();
						} catch (IOException ex) {
							Log.e(WebViewDemoActivity.class.toString(),
									ex.getMessage(), ex);
							result = ex.getClass().getSimpleName() + " "
									+ ex.getMessage();
						}
					}
				}
			} else {
				result = getString(R.string.nosd);
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					WebViewDemoActivity.this);
			builder.setMessage(result).setPositiveButton((R.string.ok), null)
					.setTitle(getString(R.string.download));
			builder.show();

		}

	}

	/**
	 * Checks networking status.
	 */
	private boolean checkConnectivity() {
		boolean enabled = true;

		ConnectivityManager connectivityManager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();

		if ((info == null || !info.isConnected() || !info.isAvailable())) {

		}
		return enabled;
	}

}
