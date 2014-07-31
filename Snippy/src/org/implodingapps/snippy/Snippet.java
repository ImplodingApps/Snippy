package org.implodingapps.snippy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import android.content.ClipData;
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
	MimeTypeMap type = new MimeTypeMap();
	
	Snippet(long timeOfCopy, ClipData snippet)
	{
		this.timeOfCopy = timeOfCopy;
		Snippet snippet = new Snippet();
		this.snippet = snippet;
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
	
	
	public CharSequence coerceToText(Context context) 
	{
	    // If this Item has an explicit textual value, simply return that.
	    CharSequence text = getText();
	    if (text!= null) 
	    {
	        return text;
	    }

	    // If this Item has a URI value, try using that.
	    Uri uri = getUri();
	    if (uri != null) {

	        // First see if the URI can be opened as a plain text stream
	        // (of any sub-type).  If so, this is the best textual
	        // representation for it.
	        FileInputStream stream = null;
	        try {
	            // Ask for a stream of the desired type.
	            AssetFileDescriptor descr = context.getContentResolver()
	                    .openTypedAssetFileDescriptor(uri, "text/*", null);
	            stream = descr.createInputStream();
	            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");

	            // Got it...  copy the stream into a local string and return it.
	            StringBuilder builder = new StringBuilder(128);
	            char[] buffer = new char[8192];
	            int len;
	            while ((len=reader.read(buffer)) > 0) {
	                builder.append(buffer, 0, len);
	            }
	            return builder.toString();

	        } catch (FileNotFoundException e) {
	            // Unable to open content URI as text...  not really an
	            // error, just something to ignore.

	        } catch (IOException e) {
	            // Something bad has happened.
	            Log.w("ClippedData", "Failure loading text", e);
	            return e.toString();

	        } finally {
	            if (stream != null) {
	                try {
	                    stream.close();
	                } catch (IOException e) {
	                }
	            }
	        }

	        // If we couldn't open the URI as a stream, then the URI itself
	        // probably serves fairly well as a textual representation.
	        return uri.toString();
	    }

	    // Finally, if all we have is an Intent, then we can just turn that
	    // into text.  Not the most user-friendly thing, but it's something.
	    Intent intent = getIntent();
	    if (intent != null) {
	        return intent.toUri(Intent.URI_INTENT_SCHEME);
	    }

	    // Shouldn't get here, but just in case...
	    return "";
	}
	/* TODO: Make parsed ClipData methods:
	 * One that gets the MIME type
	 * One to return text
	 * One to return a URI
	 * And one to return an Intent
	 */
}
