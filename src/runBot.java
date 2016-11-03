
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
		
		random = new Random();
		//Generates a random number between 0 and number of plays - 1
		//Currently 2 plays
		int play = random.nextInt(4);
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
		}
	
		//Reads in the file chosen and stores it line by line in a List, then passes it to quote()
		Quote quote = new Quote(Files.readAllLines(Paths.get(playFile), Charset.forName("UTF-8")), random);
		return quote.getQuote(); 
	}
	
	public static void main(String... args) throws TwitterException, IOException{
		
		Twitter shakespeare = TwitterFactory.getSingleton();
		
		//Used for testing purposes
		//System.out.println(getPlayQuote());
		
		//Calls getPlayQuote() to get tweet
		String tweet = getPlayQuote();
		
		//Posts tweet
		Status status = shakespeare.updateStatus(tweet);
		
	}
}
