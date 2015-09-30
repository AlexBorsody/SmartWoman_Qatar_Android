package com.alex.smartwomanmiddleeastfem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class NewLog extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // Removes title bar

		setContentView(R.layout.firstt);
	}
}
