package com.alex.smartwomanqatarfem;



import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;

public class Globals extends Application {

	ArrayList<HashMap<String, String>> galleryimagelist = new ArrayList<HashMap<String, String>>();

	ArrayList<String> splashText = new ArrayList<String>();

	public ArrayList<HashMap<String, String>> getGalleryimagelist() {
		return galleryimagelist;
	}

	public void setGalleryimagelist(
			ArrayList<HashMap<String, String>> galleryimagelist) {
		this.galleryimagelist = galleryimagelist;
	}
	public ArrayList<String> getsplashText() {
		return splashText;
	}
	
	
	

	public void setSplashText(ArrayList<String> splashText) {
		this.splashText = splashText;
	}

	public void setSplashText(String respage) {
		// TODO Auto-generated method stub
		this.splashText = splashText;
	}

}
