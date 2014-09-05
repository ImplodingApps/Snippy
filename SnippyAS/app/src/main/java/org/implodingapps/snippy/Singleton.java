package org.implodingapps.snippy;

//Shamelessly stolen from Stack Overflow: https://gist.github.com/Akayh/5566992//... ;(
import java.util.ArrayList;

public class Singleton 
{
	public static Singleton mInstance = new Singleton();
	
	public static ArrayList<Snippet> snippets;
	
	private Singleton()
	{
		snippets = new ArrayList<Snippet>();
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