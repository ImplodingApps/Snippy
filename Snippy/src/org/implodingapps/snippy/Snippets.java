package org.implodingapps.snippy;

import java.util.Date;
import android.content.ClipData;

public class Snippets 
{	
	public long timeOfCopy;
	public ClipData snippet;
	
	Snippets(long timeOfCopy, ClipData snippet)
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
	
	/* TODO: Make parsed ClipData methods:
	 * One that gets the MIME type
	 * One to return text
	 * One to return a URI
	 * And one to return an Intent
	 */
}
