import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Quote {

	private List<String> play;
	private List<String> flagWords;
	private Random random;

	public Quote(List<String> play, Random random) throws IOException {
		this.play = play;
		this.random = random;
		flagWords = Files.readAllLines(Paths.get("flag_words.txt"), Charset.forName("UTF-8"));
	}

	public String getQuote() {
		//TODO Optimize searching and enhance quality of quotes
		String tweet = "";

		//Index of start of quote
		int index = random.nextInt(play.size() - 6) + 6;

		while (tweet.length() < 140) {

			String tweetToAdd = play.get(index);

			//Checks if the line retrieved contains any of the character cues
			//If it does, then it calls quote() to start over
			if (tweetToAdd.toUpperCase().equals(tweetToAdd)) {
				return getQuote();
			}

			//Checks for "flag words" like Enter, Exuent, Aside, etc..
			for (String line : flagWords) {
				if (tweetToAdd.startsWith(line) || tweetToAdd.equals(line)) {
					if (!(tweet.length() > 100))
						return getQuote();
					else 
						return tweet;
				}
			}

			//TODO Fix
			//Supposed to not allow tweets to start with lower case letters
			if (!tweetToAdd.equals("")) {
				if (tweetToAdd.toLowerCase().charAt(0) == tweetToAdd.charAt(0)){
					if (!(tweet.length() > 100))
						return getQuote();
					else 
						return tweet;
				}
			}

			//If the tweet contains exuent, start over or return if length it greater than 100
			if (tweetToAdd.contains("Exuent")) {
				if (!(tweet.length() > 100))
					return getQuote();
				else 
					return tweet;
			}
			
			//If the line doesn't end with a "." or "?" check to see if the
			//line before it did, and if it did and the tweet is greater than length 80
			//just return the tweet as it is
			//Also removes commas, semi-colons, and colons on last lines
			if (!(tweetToAdd.endsWith(".") || tweetToAdd.endsWith("?"))) {
				if (tweet.length() > 80) {
					if (play.get(index - 1).endsWith(".")) {
						return tweet;
					}
					if (tweet.length() + tweetToAdd.length() + play.get(index + 1).length() > 141) {
						if (tweetToAdd.endsWith(",") || tweetToAdd.endsWith(";") || tweetToAdd.endsWith(":")) {
							tweetToAdd = tweetToAdd.substring(0, tweetToAdd.length() - 1);
						}
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
			if (index < play.size() - 1) {
				index++;
			} else {
				break;
			}
		}
		return tweet;
	}
}
