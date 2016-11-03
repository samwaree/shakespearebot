
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import twitter4j.TwitterException;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * Shakesconsin Idea project created to provide twitter with the wise words of
 * Shakespeare
 * 
 * @author Sam Ware
 */
public class runBot {
	
	static Random random;
	
	public static String getPlayQuote() throws IOException {
		
		//Generates a random number between 0 and number of plays - 1
		//Currently 2 plays
		int play = random.nextInt(6);
		String playFile = "";
		
		//Chooses a random text file
		switch (play) {
			case 0:
				playFile = "plays/the_tempest.txt";
				break;
			case 1:
				playFile = "plays/amnd.txt";
				break;
			case 2:
				playFile = "plays/henry_iv_1.txt";
				break;
			case 3:
				playFile = "plays/hamlet.txt";
				break;
			case 4:
				playFile = "plays/julius_caesar.txt";
				break;
			case 5:
				playFile = "plays/romeo_juliet.txt";
				break;
		}
	
		//Reads in the file chosen and stores it line by line in a List, then passes it to quote()
		Quote quote = new Quote(Files.readAllLines(Paths.get(playFile), Charset.forName("UTF-8")), random, true);
		return quote.getPlayQuote(); 
	}
	
	public static String getSonnetQuote() throws IOException {
		
		Quote quote = new Quote(Files.readAllLines(Paths.get("sonnets.txt"), Charset.forName("UTF-8")), random, false);
		
		return quote.getSonnetQuote();
	}
	
	public static void main(String... args) throws TwitterException, IOException{
		
		Twitter shakespeare = TwitterFactory.getSingleton();
		
		random = new Random();
		//Used for testing purposes
		//System.out.println(getSonnetQuote());
		
		//Calls getPlayQuote() to get tweet
		String tweet = getPlayQuote();
		
		//Calls getSonnetQuote() and gets a random couplet
		//String tweet = getSonnetQuote();
		
		//Posts tweet
		Status status = shakespeare.updateStatus(tweet);
		
	}
}
