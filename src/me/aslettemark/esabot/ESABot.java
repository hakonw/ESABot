package me.aslettemark.esabot;

import java.util.ArrayList;
import java.util.HashMap;

import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.PircUser;

public class ESABot extends PircBot {
	
	public String nick;
	private String nickpass;
	public String network;
	public String[] channels;
	
	public ArrayList<String> herders = new ArrayList<String>();
	public ArrayList<String> herdpass = new ArrayList<String>();
	//public ArrayList<String> ops = new ArrayList<String>();
	
	public HashMap<String, String> topicmask = new HashMap<String, String>();
	
	public IRCHandler handler;
	
	public ESABot(String[] args) {
		this.network = args[0];
		this.nick = args[1];
		this.nickpass = args[2];
		this.handler = new IRCHandler(this);
		this.setAutoNickChange(true);
		
		this.setName(nick);
		this.setVersion("ESABot v13.3 BUILD 7");
		this.handler.doConnect();
		
		this.channels = args[3].split(",");
		for(String c: this.channels) {
			this.joinChannel(c);
			topicmask.put(c, "Welcome to " + c + " | %topic");
		}
		for(String s : args[4].split(",")) {
			herdpass.add(s);
		}
	}
	
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(message.startsWith(nick + ": ")) {
			handler.command(channel, sender, login, hostname, message.replaceFirst(this.nick + ": ", ""), true);
		} else if(message.startsWith(".")) {
			handler.command(channel, sender, login, hostname, message.replaceFirst(".", ""), false);
		}
		
		
	}
	
	public void onPrivateMessage(String sender, String login, String hostname, String message) {
		this.handler.message(sender, hostname, login, message);
		
		//construction zone
		String herd = "false";
		if(handler.isHerder(sender)) {
			herd = "true";
		}
		System.out.printf("%s %s %s %s %s\n", sender, login, hostname, message, herd);
	}
	
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
