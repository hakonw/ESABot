package me.aslettemark.esabot.command;

import java.util.HashMap;

import me.aslettemark.esabot.ESABot;

public class NoteCommand extends CommandExecutor{

	public NoteCommand(ESABot bot) {
		super(bot);
	}

	@Override
	public void execute(String channel, String sender, String login, String hostname, String command, boolean pm) {
		HashMap<String, String> note = new HashMap<String, String>();
		note.put(sender, command.replaceFirst("note ", ""));
		this.bot.notes.put(command.split(" ")[1], note);
		if(pm) {
			this.bot.sendMessage(sender, "Note noted.");
		} else {
			this.bot.sendMessage(channel, sender + ": Note noted.");
		}
	}

}
