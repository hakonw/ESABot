package org.no_ip.kermi.rpg;

import net.aslettemark.esabot.ESABot;

public class RpgUtil {
    
    ESABot bot;
    RpgCommand cmd;
    
    public RpgUtil(ESABot bot, RpgCommand c) {
        this.bot = bot;
        this.cmd = c;
    }
    
    
    public void makeRpgAttack(){
        
    }
    
    public void makeRpgReg(String sender, String command, String channel){
        bot.sendMessage(sender, command);
    }
    public void makeRpgHelp(String channel){
        bot.sendMessage(channel, "I should implement help.");
    }
    public void makeRpgWalk(){
        
    }

}
