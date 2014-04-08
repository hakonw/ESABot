package me.aslettemark.esabot;

import org.jibble.pircbot.PircUser;

public class IRCHandler {
	
	public ESABot bot;
	
	public IRCHandler(ESABot bot) {
		this.bot = bot;
	}
	
	public void command(String channel, String sender, String login, String hostname, String command, boolean verbose) {
		boolean op = this.isOp(sender, channel);
		
		if(command.equalsIgnoreCase("kill")) {
			if(op && isHerder(sender)) {
				bot.sendAction(channel, "is kill");
				bot.disconnect();
				bot.dispose();
			} else {
				bot.sendMessage(channel, "Not allowed");
			}
		}
		
		if(command.equalsIgnoreCase("portal") && (sender.equalsIgnoreCase("Kermi") || this.isHerder(sender))) {
			bot.sendMessage(channel, "I'm doing Science and I'm still alive.");
			bot.sendMessage(channel, "I feel FANTASTIC and I'm still alive.");
			bot.sendMessage(channel, "While you're dying I'll be still alive.");
			bot.sendMessage(channel, "And when you're dead I will be still alive.");
			bot.sendMessage(channel, "STILL ALIVE");
			bot.sendMessage(channel, "STILL ALIVE");
		}
	}
	
	public boolean isOp(String nick, String channel) {
		PircUser[] u = bot.getUsers(channel);
		for(PircUser us: u) {
			if(us.getPrefix().equalsIgnoreCase("@")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isHerder(String nick) {
		for(String n: bot.herders) {
			if(n.equalsIgnoreCase(nick)) {
				return true;
			}
		}
		return false;
	}

}
