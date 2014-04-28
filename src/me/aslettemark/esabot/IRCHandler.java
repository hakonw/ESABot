package me.aslettemark.esabot;

import java.io.IOException;

import me.aslettemark.esabot.command.CommandExecutor;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class IRCHandler {
	
	public ESABot bot;
	
	public IRCHandler(ESABot bot) {
		this.bot = bot;
	}

	/*
	public boolean isOp(String nick, String channel) {
		if(bot.ops.contains(channel + ":" + nick)) {
			return true;
		}
		return false;
	}*/
	
	/**
	 * Returns wether the specified nick is a bot herder
	 * @param nick The nick to check
	 * @return
	 */
	public boolean isHerder(String nick) {
		if(bot.herders.contains(nick)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Connects to the network the bot is configured to use
	 */
	public void doConnect() {
		try {
			this.bot.connectWithNoB(this.bot.network, 6667, null);
		} catch (NickAlreadyInUseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads unread notes from the note file.
	 */
	public void loadNotes() {
		//todo: function to load in the notes from the notes file corresponding with the current network
	}
	
	/**
	 * Assigns a command to the specified CommandExecutor
	 * @param command Command to assign
	 * @param exec CommandExecutor to assign to
	 */
	public void assignCommand(String command, CommandExecutor exec) {
		this.bot.commandExecutors.put(command, exec);
	}
	
	/**
	 * Returns wether the specified nick has any unread notes
	 * @param nick The nick to check
	 * @return
	 */
	public boolean hasNotes(String nick) {
		if(this.bot.notes.containsKey(nick)) {
			return true;
		}
		return false;
	}

}
