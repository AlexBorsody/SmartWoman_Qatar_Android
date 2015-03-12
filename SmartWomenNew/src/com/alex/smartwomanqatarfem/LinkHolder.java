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

import android.widget.ImageView;
import android.widget.TextView;

public class LinkHolder
{
	
	private TextView url;
	
	private ImageView imageView;

	public TextView getUrl()
	{
		return url;
	}

	public void setUrl(TextView url)
	{
		this.url = url;
	}

	public ImageView getImageView()
	{
		return imageView;
	}

	public void setImageView(ImageView imageView)
	{
		this.imageView = imageView;
	}
	
	

}
