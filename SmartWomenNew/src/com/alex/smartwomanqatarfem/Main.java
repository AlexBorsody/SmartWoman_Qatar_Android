package com.alex.smartwomanqatarfem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class Main extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	Button t, ta;
	SharedPreferences spe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // Removes title bar

		setContentView(R.layout.first);

		spe = getSharedPreferences("t", Activity.MODE_PRIVATE);

		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		ta = (Button) findViewById(R.id.button1);
		ta.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String myIntValue = spe.getString("u", "0");

				if (!myIntValue.contains("0")) {

					if (myIntValue.contains("y")) {
						Intent intent = new Intent(Main.this,
								WebViewDemoActivity.class);
						Main.this.startActivity(intent);
						Main.this.finish();
					}

				} else {
					Intent i = new Intent(Main.this, Auth.class);
					startActivity(i);
				}

			}
		});

		t = (Button) findViewById(R.id.button2);
		t.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Main.this, Forgot.class);
				startActivity(i);
			}
		});

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				/*
				 * Toast.makeText(getApplicationContext(),
				 * listDataHeader.get(groupPosition) + " Expanded",
				 * Toast.LENGTH_SHORT).show();
				 */
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				/*
				 * Toast.makeText(getApplicationContext(),
				 * listDataHeader.get(groupPosition) + " Collapsed",
				 * Toast.LENGTH_SHORT).show();
				 */

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				/*
				 * Toast.makeText( getApplicationContext(),
				 * listDataHeader.get(groupPosition) + " : " +
				 * listDataChild.get( listDataHeader.get(groupPosition)).get(
				 * childPosition), Toast.LENGTH_SHORT) .show();
				 */
				return false;
			}
		});
	}

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("What is The SmartWoman Project?");
		listDataHeader.add("How Does It Work?");
		listDataHeader.add("How Does It Help?");

		// Adding child data
		List<String> top250 = new ArrayList<String>();
		top250.add("SmartWoman is a mobile service supporting and connecting women in Oman for empowerment and growth. It is a mobile platform that provides content from topic expert writers to help women navigate their lives better which includes social networking, asking questions and sharing experiences. SmartWoman is an initiative of Qualcomm Wireless Reach™.");

		List<String> nowShowing = new ArrayList<String>();
		nowShowing
				.add("Access SmartWoman and pay your membership fee. When you complete this process, you will immediately have access to a dynamic platform where you can access content that will help you manage your life better, ask questions, comment on articles, and view the work of organizations working towards the advancement of women. Local content provided by Knowledge Oman. Contact at info@knowledgeoman.com");

		List<String> comingSoon = new ArrayList<String>();
		comingSoon
				.add("SmartWoman is a platform for knowledge sharing and creation. Women will have the opportunity to have a safe environment to discuss issues that need to be addressed with other women. SmartWoman has a significant social impact as it is a tool for skill-building and capacity building, while promoting greater exchange of opinions and deeper conversations for women's development and success.");

		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		listDataChild.put(listDataHeader.get(1), nowShowing);
		listDataChild.put(listDataHeader.get(2), comingSoon);
	}
}
