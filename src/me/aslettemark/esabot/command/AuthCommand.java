package me.aslettemark.esabot.command;

import me.aslettemark.esabot.ESABot;

public class AuthCommand extends CommandExecutor {

	public AuthCommand(ESABot bot) {
		super(bot);
	}

	@Override
	public void execute(String channel, String sender, String login, String hostname, String command, boolean pm) {
		if(pm) {
			if(this.bot.herdpass.contains(command.split(" ")[1])) {
				this.bot.herders.add(sender);
				this.bot.sendMessage(sender, "Added to herders");
			}
		} else {
			bot.sendMessage(channel, "Auth is not a public command.");
		}
	}

}
