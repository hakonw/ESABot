package me.aslettemark.esabot;

public class MainClass {
	
	public static ESABot bot;
	
	public static void main(String[] args) {
		bot = new ESABot(args);
	}
	
	public static String combineSplit(int startIndex, String[] string, String separator) {
        final StringBuilder builder = new StringBuilder();
        for (int i = startIndex; i < string.length; i++) {
            builder.append(string[i]);
            builder.append(separator);
        }
        builder.setLength(builder.length() - separator.length());
        return builder.toString();
    }
	
}
