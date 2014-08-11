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
	MimeTypeMap type;
	
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
	
	public ClipData getClip()
	{
		return snippet;
	}
	
	public MimeTypeMap returnMimeType()
	{
		
		return null;
	}
	
	public CharSequence coerceToText(Context context) 
	{
	    // If this Item has an explicit textual value, simply return that.
//	    CharSequence text = (CharSequence) snippet;
//	    if (text!= null) 
//	    {
//	        return text;
//	    }

	    // If this Item has a URI value, try using that.
//	    ClipData.Item item = snippet.getItemAt(0);// Gets the first item from the clipboard data
//	    Uri uri = item.getUri();
//	    
//	    
//	    if (uri != null) 
//	    {
//
//	        // First see if the URI can be opened as a plain text stream
//	        // (of any sub-type).  If so, this is the best textual
//	        // representation for it.
//	        FileInputStream stream = null;
//	        try {
//	            // Ask for a stream of the desired type.
//	            AssetFileDescriptor descr = context.getContentResolver()
//	                    .openTypedAssetFileDescriptor(uri, "text/*", null);
//	            stream = descr.createInputStream();
//	            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
//
//	            // Got it...  copy the stream into a local string and return it.
//	            StringBuilder builder = new StringBuilder(128);
//	            char[] buffer = new char[8192];
//	            int len;
//	            while ((len=reader.read(buffer)) > 0) {
//	                builder.append(buffer, 0, len);
//	            }
//	            return builder.toString();
//
//	        } catch (FileNotFoundException e) {
//	            // Unable to open content URI as text...  not really an
//	            // error, just something to ignore.
//
//	        } catch (IOException e) {
//	            // Something bad has happened.
//	            Log.w("ClippedData", "Failure loading text", e);
//	            return e.toString();
//
//	        } finally {
//	            if (stream != null) {
//	                try {
//	                    stream.close();
//	                } catch (IOException e) {
//	                }
//	            }
//	        }
//
//	        // If we couldn't open the URI as a stream, then the URI itself
//	        // probably serves fairly well as a textual representation.
//	        return uri.toString();
//	    }
//
//	    // Finally, if all we have is an Intent, then we can just turn that
//	    // into text.  Not the most user-friendly thing, but it's something.
//	    ClipData.Item intent_item = snippet.getItemAt(0);
//	    Intent intent = intent_item.getIntent();
//	    if (intent != null) {
//	        return intent.toUri(Intent.URI_INTENT_SCHEME);
//	    }

	    // Shouldn't get here, but just in case...
	    return "";
	}

	/* TODO: Make parsed ClipData methods:
	 * One that gets the MIME type
	 * One to return text
	 * One to return a URI
	 * And one to return an Intent
	 */
//	private final void performPaste() {
//
////	    // Gets a handle to the Clipboard Manager
////	    ClipboardManager clipboard = (ClipboardManager)
////	            getSystemService(Context.CLIPBOARD_SERVICE);
////
////	    // Gets a content resolver instance
//	    ContentResolver cr = getContentResolver();
////
////	    // Gets the clipboard data from the clipboard
//	    ClipData clip = clipboard.getPrimaryClip();
//	    if (clip != null) {
//
//	        String text=null;
//	        String title=null;
//
//	        // Gets the first item from the clipboard data
//	        ClipData.Item item = clip.getItemAt(0);
//
//	        // Tries to get the item's contents as a URI pointing to a note
//	        Uri uri = item.getUri();
//
//	        // Tests to see that the item actually is an URI, and that the URI
//	        // is a content URI pointing to a provider whose MIME type is the same
//	        // as the MIME type supported by the Note pad provider.
//	        if (uri != null && NotePad.Notes.CONTENT_ITEM_TYPE.equals(cr.getType(uri))) {
//
//	            // The clipboard holds a reference to data with a note MIME type. This copies it.
//	            Cursor orig = cr.query(
//	                    uri,            // URI for the content provider
//	                    PROJECTION,     // Get the columns referred to in the projection
//	                    null,           // No selection variables
//	                    null,           // No selection variables, so no criteria are needed
//	                    null            // Use the default sort order
//	            );
//
//	            // If the Cursor is not null, and it contains at least one record
//	            // (moveToFirst() returns true), then this gets the note data from it.
//	            if (orig != null) {
//	                if (orig.moveToFirst()) {
//	                    int colNoteIndex = mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_NOTE);
//	                    int colTitleIndex = mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_TITLE);
//	                    text = orig.getString(colNoteIndex);
//	                    title = orig.getString(colTitleIndex);
//	                }
//
//	                // Closes the cursor.
//	                orig.close();
//	            }
//	        }
//
//	        // If the contents of the clipboard wasn't a reference to a note, then
//	        // this converts whatever it is to text.
//	        if (text == null) {
//	            text = item.coerceToText(this).toString();
//	        }
//
//	        // Updates the current note with the retrieved title and text.
//	        updateNote(text, title);
//	    }
}
