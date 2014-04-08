package me.aslettemark.esabot;

import java.io.IOException;
import java.util.ArrayList;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

public class ESABot extends PircBot {
	
	public String nick;
	private String nickpass;
	public String network;
	public String[] channels;
	
	public ArrayList<String> herders = new ArrayList<String>();
	public ArrayList<String> herdpass = new ArrayList<String>();
	
	public IRCHandler handler;
	
	public ESABot(String[] args) {
		this.network = args[0];
		this.nick = args[1];
		this.nickpass = args[2];
		this.handler = new IRCHandler(this);
		
		this.setName(nick);
		this.setVersion("ESABot v13.3 BUILD 7");
		this.doConnect();
		
		this.channels = args[3].split(",");
		for(String c: this.channels) {
			this.joinChannel(c);
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
		System.out.printf("%s %s %s %s %s %s \n", channel, sender, login, hostname, message, (handler.isOp(nick, channel))?"true":"false" );
	}
	
	@Override
	public void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
		if(herders.contains(recipientNick) && !(kickerNick.equalsIgnoreCase(this.nick))) {
			this.deOp(channel, kickerNick);
		}
	}
	
	public void onPrivateMessage(String sender, String login, String hostname, String message) {
		if(message.startsWith("auth ")) {
			if(this.herdpass.contains(message.split(" ")[1])) {
				this.herders.add(sender);
				this.sendMessage(sender, "Added to herders");
			}
		}
		if(message.equalsIgnoreCase("herders") && this.herders.contains(sender)) {
			for(String s : herders) {
				sendMessage(sender, s);
			}
		}
		if(message.equalsIgnoreCase("deauth") && this.herders.contains(sender)) {
			this.herders.remove(sender);
			this.sendMessage(sender, "De-Authed");
		}
	}
	
	@Override
	public void onDisconnect() {
		this.doConnect();
	}
	
	private void doConnect() {
		try {
			this.connectWithNoB(this.network, 6667, null);
		} catch (NickAlreadyInUseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}
	}
	
}
