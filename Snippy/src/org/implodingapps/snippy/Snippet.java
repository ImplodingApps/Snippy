package org.implodingapps.snippy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

public class Snippet 
{	
	public long timeOfCopy;
	public ClipData snippet;
	public String parsedText;
	MimeTypeMap type;
	
	Snippet(long timeOfCopy, ClipData snippet)
	{
		this.timeOfCopy = timeOfCopy;
		this.snippet = snippet;
		
		parsedText = snippet.getItemAt(0).coerceToText();
	}
	
	public Date getTimeOfCopy()
	{
		Date time = new Date(timeOfCopy);
		return time;
	}
	
	public ClipData getClip()
	{
		return snippet;
	}
	
	public String getParsedText()
	{
		return parsedText;
	}
	
	public MimeTypeMap returnMimeType() //TODO
	{
		return null;
	}

}