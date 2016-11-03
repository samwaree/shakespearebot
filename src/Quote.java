import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Quote {

	private List<String> text;
	private List<String> flagWords;
	private Random random;
	
	public Quote(List<String> text, Random random, boolean ifFlagWords) throws IOException {
		this.text = text;
		this.random = random;
		if (ifFlagWords)
			flagWords = Files.readAllLines(Paths.get("flag_words.txt"), Charset.forName("UTF-8"));
	}

	public String getPlayQuote() {
		//TODO Optimize searching and enhance quality of quotes
		String tweet = "";

		//Index of start of quote
		int index = random.nextInt(text.size() - 6) + 6;
		int maxIndex = Integer.MAX_VALUE;

		while (tweet.length() < 140 && index <= maxIndex) {

			String tweetToAdd = text.get(index);

			//Checks if the line retrieved contains any of the character cues
			//If it does, then it calls quote() to start over
			if (tweetToAdd.toUpperCase().equals(tweetToAdd)) {
				return getPlayQuote();
			}

			//Checks for "flag words" like Enter, Exuent, Aside, etc..
			for (String line : flagWords) {
				if (tweetToAdd.startsWith(line) || tweetToAdd.equals(line)) {
					if (!(tweet.length() > 100))
						return getPlayQuote();
					else 
						return tweet;
				}
			}

			//TODO Fix
			//Supposed to not allow tweets to start with lower case letters
			if (!tweet.equals("")) {
				if (tweet.toLowerCase().charAt(0) == tweet.charAt(0)){
						return getPlayQuote();
				}
			}

			//If the tweet contains exuent, start over or return if length it greater than 100
			if (tweetToAdd.contains("Exuent")) {
				if (!(tweet.length() > 100))
					return getPlayQuote();
				else 
					return tweet;
			}
			
			//If the line doesn't end with a "." or "?" check to see if the
			//line before it did, and if it did and the tweet is greater than length 80
			//just return the tweet as it is
			//Also removes commas, semi-colons, and colons on last lines
			boolean properEnding = tweetToAdd.endsWith(".") || tweetToAdd.endsWith("?") || tweetToAdd.endsWith("!");
			if (!properEnding) {
				if (tweet.length() > 80) {
					if (text.get(index - 1).endsWith(".")) {
						return tweet;
					}
				} 
				if (index < text.size() - 1 && 
						tweet.length() + tweetToAdd.length() + text.get(index + 1).length() > 140) {
					if (tweetToAdd.endsWith(",") || tweetToAdd.endsWith(";") || tweetToAdd.endsWith(":")) {
						tweetToAdd = tweetToAdd.substring(0, tweetToAdd.length() - 1);
					}
				}
			}
			//Adds the line to the tweet
			if (tweet.length() + tweetToAdd.length() <= 140 && !(tweetToAdd.equals("") || tweetToAdd.equals("\n"))) {
				tweet += tweetToAdd;
				if (!(tweetToAdd.equals("") || tweetToAdd.equals("\n"))) {
					tweet += "\n";
				}
			} else if (tweet.length() >= 140) {
				break;
			}
			if (index < text.size() - 1) {
				index++;
			} else {
				break;
			}
		}
		return tweet;
	}

	public String getSonnetQuote() {
		
		int sonnetLine = 17 * (random.nextInt(151) + 1) + 15;
		
		//Returns a random couplet from any of the sonnets
		//Missing 99 and 126 because of their weird formats
		return text.get(sonnetLine).trim() + "\n" + text.get(++sonnetLine);
	}
}
