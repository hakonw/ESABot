package me.aslettemark.esabot;

import java.util.ArrayList;
import java.util.HashMap;

import me.aslettemark.esabot.command.*;

import org.jibble.pircbot.PircBot;

public class ESABot extends PircBot {
	
	public String nick;
	private String nickpass;
	public String network;
	public String[] channels;
	
	public ArrayList<String> herders = new ArrayList<String>();
	public ArrayList<String> herdpass = new ArrayList<String>();
	//public ArrayList<String> ops = new ArrayList<String>();
	
	public HashMap<String, String> topicmask = new HashMap<String, String>();
	//Create a hashmap with receiver, note where note is a hashmap with sender, notetext
	public HashMap<String, HashMap<String, String>> notes = new HashMap<String, HashMap<String, String>>();
	public HashMap<String, CommandExecutor> commandExecutors = new HashMap<String, CommandExecutor>();
	
	public IRCHandler handler;
	
	public ESABot(String[] args) {
		//set variables
		this.network = args[0];
		this.nick = args[1];
		this.nickpass = args[2];
		this.handler = new IRCHandler(this);
		this.setAutoNickChange(true);
		this.setName(nick);
		this.setVersion("ESABot v13.3 BUILD 7");
		
		//do the connection, set up automatic variables
		this.handler.doConnect();
		this.channels = args[3].split(",");
		for(String c: this.channels) {
			this.joinChannel(c);
			topicmask.put(c, "Welcome to " + c + " | %topic");
		}
		for(String s : args[4].split(",")) {
			herdpass.add(s);
		}
		this.handler.loadNotes();
		
		//assign commands
		IRCHandler h = this.handler;
		CommandExecutor killCommand = new KillCommand(this);
		CommandExecutor authCommand = new AuthCommand(this);
		CommandExecutor topicCommand = new TopicCommand(this);
		CommandExecutor topicMaskCommand = new TopicMaskCommand(this);
		CommandExecutor herdersCommand = new HerdersCommand(this);
		CommandExecutor deAuthCommand = new DeAuthCommand(this);
		h.assignCommand("kill", killCommand);
		h.assignCommand("auth", authCommand);
		h.assignCommand("topic", topicCommand);
		h.assignCommand("topicmask", topicMaskCommand);
		h.assignCommand("herders", herdersCommand);
		h.assignCommand("deauth", deAuthCommand);
	}
	
	/**
	 * Triggers when there is a message in a channel.
	 * The function passes all the data received to the command handling system.
	 */
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(message.startsWith(nick + ": ")) {
			this.commandExecutors.get(message.split(" ")[1]).execute(channel, sender, login, hostname, message.replaceFirst(this.nick + ": ", ""), false);
		} else if(message.startsWith(".")) {
			this.commandExecutors.get(message.split(" ")[0].replaceFirst(".", "")).execute(channel, sender, login, hostname, message.replaceFirst(".", ""), false);
		}
	}
	
	/**
	 * Triggers when the bot receives a private message.
	 * The function passes all the data received to the command handling system.
	 */
	@Override
	public void onPrivateMessage(String sender, String login, String hostname, String message) {
		this.commandExecutors.get(message.split(" ")[0]).execute(null, sender, login, hostname, message, true);
	}
	
	/**
	* Triggers on kick, used for herder protection.
	*/
	@Override
	public void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
		if(herders.contains(recipientNick) && !(kickerNick.equalsIgnoreCase(this.nick))) {
			this.deOp(channel, kickerNick);
		}
	}
	
	@Override
	public void onDisconnect() {
		this.handler.doConnect();
	}
	
	/*
	@Override
	public void onUserList(String channel, PircUser[] users) {
		for(PircUser u : users) {
			if(u.isOp()) {
				this.ops.add(channel + ":" + u.getNick());
			}
		}
	}
	
	@Override
	public void onOp(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		this.ops.add(channel + ":" + recipient);
	}
	
	@Override
	public void onDeop(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		this.ops.remove(channel + ":" + recipient);
	}
	
	@Override
	public void onNickChange(String oldNick, String login, String hostname, String newNick) {
		if(this.handler.isHerder(oldNick)) {
			this.herders.remove(oldNick);
			this.herders.add(newNick);
		}
		/*for(String ch : this.channels) {
			if(this.handler.isOp(oldNick, ch)) {
				this.ops.remove(oldNick);
				this.ops.add(newNick);
			}
		}*//*
	}
	*/
}
