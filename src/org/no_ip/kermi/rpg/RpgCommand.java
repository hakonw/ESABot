package org.no_ip.kermi.rpg;

import java.util.HashMap;

import org.no_ip.kermi.rpg.RpgUtil;

import net.aslettemark.esabot.ESABot;
import net.aslettemark.esabot.command.CommandExecutor;

public class RpgCommand extends CommandExecutor {
    
    RpgUtil util;
    int[][] RpgID = new int[10][5];
    HashMap<String, Integer> hsm = new HashMap<String, Integer>();
    String RpgIDS[];
    
    public RpgCommand(ESABot bot) {
        super(bot);
        this.util = new RpgUtil(bot, this);
        
    }
    

    @Override
    public void execute(String channel, String sender, String login, String hostname, String command, boolean pm) {
        
        String[] commandArgu = command.split(" ");        
            switch(commandArgu[1]){
                
                case "help":
                    util.makeRpgHelp(channel);                    
                    break;
                    
                case "attack":
                    util.makeRpgAttack();
                    break;
                    
                case "reg":
                    util.makeRpgReg(sender, command, channel);
                    bot.sendMessage(channel, "Reg should be done.");
                    break;
                case "walk":
                    util.makeRpgWalk();
                    bot.sendMessage(channel, "Walk is not implemented yet");
                    
                default:
                    bot.sendMessage(channel, "Unknown command, you could try 'help'.");
                
            }
        
    }

}
