package org.implodingapps.snippy;

//Shamelessly stolen from Stack Overflow: https://gist.github.com/Akayh/5566992
import java.util.ArrayList;

public class Singleton 
{
	private static Singleton mInstance = null;
	
	public static ArrayList<Snippet> snippets;
	
	private Singleton()
	{
		snippets = new ArrayList<Snippet>();
	}
	
	static Singleton getInstance()
	{
		if(mInstance == null)
		{
			mInstance = new Singleton();
		}
		return mInstance;
	}
	
	public void addSnippet(Snippet s)
	{
		snippets.add(s);
	}
	
	public Snippet getSnippet(int index)
	{
		return snippets.get(index);
	}
	
	public void deleteSnippet(int index)
	{
		snippets.remove(index);
	}
}
