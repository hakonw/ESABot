package me.aslettemark.esabot.command;

import me.aslettemark.esabot.ESABot;

public abstract class CommandExecutor {
	
	protected ESABot bot;
	
	public CommandExecutor(ESABot bot) {
		this.bot = bot;
	}
	
	public abstract void execute(String channel, String sender, String login, String hostname, String command, boolean pm);
	
}
