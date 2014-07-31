package org.implodingapps.snippy;

import java.util.Date;

import android.content.ClipData;
import android.webkit.MimeTypeMap;

public class Snippet 
{	
	public long timeOfCopy;
	public ClipData snippet;
	
	Snippet(long timeOfCopy, ClipData snippet)
	{
		this.timeOfCopy = timeOfCopy;
		this.snippet = snippet;
	}
	
	public Date getTimeOfCopy()
	{
		Date time = new Date(timeOfCopy);
		return time;
	}
	
	public ClipData getRawClip()
	{
		return snippet;
	}
	
	public MimeTypeMap returnMimeType()
	{
		
		return null;
	
	}
	
	/* TODO: Make parsed ClipData methods:
	 * One that gets the MIME type
	 * One to return text
	 * One to return a URI
	 * And one to return an Intent
	 */
}
