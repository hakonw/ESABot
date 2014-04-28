package me.aslettemark.esabot.command;

import me.aslettemark.esabot.ESABot;

public class KillCommand extends CommandExecutor {

	public KillCommand(ESABot bot) {
		super(bot);
	}

	@Override
	public void execute(String channel, String sender, String login, String hostname, String command, boolean pm) {
		if(this.bot.handler.isHerder(sender)) {
			if(!pm) {
				bot.sendMessage(channel, "Goodbye");
				try {
					Thread.sleep(1100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				bot.disconnect();
				bot.dispose();
			} else {
				bot.disconnect();
				bot.dispose();
			}
			
		} else {
			bot.sendMessage(channel, "Not allowed");
		}
	}
	
	
	
}
