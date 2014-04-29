package me.aslettemark.esabot.command;

import java.util.ArrayList;

import me.aslettemark.esabot.ESABot;

public class NoteCommand extends CommandExecutor{

	public NoteCommand(ESABot bot) {
		super(bot);
	}

	@Override
	public void execute(String channel, String sender, String login, String hostname, String command, boolean pm) {
		String receiver = command.split(" ")[1];
		String note = command.replaceFirst("note " + receiver + " ", "") ;
		ArrayList<String> notes = new ArrayList<String>();
		if(this.bot.handler.hasNotes(receiver)) {
			notes = this.bot.notes.get(receiver);
			notes.add("<" + sender + "> " + note);
			this.bot.notes.put(receiver, notes);
		} else {
			notes.add("<" + sender + "> " + note);
			this.bot.notes.put(receiver, notes);
		}
		if(pm) {
			this.bot.sendMessage(sender, "Note noted.");
		} else {
			this.bot.sendMessage(channel, sender + ": Note noted.");
		}
	}

}
