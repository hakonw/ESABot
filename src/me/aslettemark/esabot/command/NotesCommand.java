package me.aslettemark.esabot.command;

import me.aslettemark.esabot.ESABot;

public class NotesCommand extends CommandExecutor {

	public NotesCommand(ESABot bot) {
		super(bot);
	}

	@Override
	public void execute(String channel, String sender, String login, String hostname, String command, boolean pm) {
		if(pm && bot.handler.isHerder(sender)) {
			for(String s : this.bot.notes.keySet()) {
				for(String note : this.bot.notes.get(s)) {
					bot.sendMessage(sender, "To " + s + ": " + note);
				}
			}
		}
	}
}
