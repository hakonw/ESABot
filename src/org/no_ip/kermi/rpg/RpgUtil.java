package org.no_ip.kermi.rpg;

import net.aslettemark.esabot.ESABot;

public class RpgUtil {

    ESABot bot;
    RpgCommand cmd;

    public RpgUtil(ESABot bot, RpgCommand c) {
        this.bot = bot;
        this.cmd = c;
    }

    public void makeRpgUpdate(){
        
    }
    
    
    public void makeRpgAttack(String sender, String receiver, String spell, int damage, int stamina, String channel) {
        int tempRpgId = cmd.hmID.get(sender);
        if(cmd.hmID.get(sender) != null){ //idk int tempRpgId funka ikke her
            if(cmd.hmID.get(sender) != null){ // eller her
                if(cmd.RpgID[tempRpgId][2] >= stamina){
                    int tempRpgStamina = cmd.RpgID[tempRpgId][2];
                    int tempRpgHp = cmd.RpgID[tempRpgId][1];
                    
                    cmd.RpgID[tempRpgId][2] = tempRpgStamina - stamina;
                    cmd.RpgID[tempRpgId][1] = tempRpgHp - damage;
                    
                    bot.sendMessage(channel, sender + " took " + damage + " hp to " + receiver + " with his " + spell + " and lost " + stamina + " stamina.");
                }else{
                    bot.sendMessage(channel, "It looks like you're out of stamina " + cmd.RpgID[tempRpgId][2] + "/" + stamina);
                }
            }else{
                bot.sendMessage(channel, receiver + " aint a guy.");
            }
        }else{
            bot.sendMessage(channel, "You're not registerd, use '.rpg reg'.");
        }
    }

    public void makeRpgReg(String sender, String channel) {
        if (cmd.hmID.get(sender) == null) {
            cmd.hmID.put(sender, new Integer(cmd.hmID.size()));
            MakeRpgRegVal(sender, channel);
            bot.sendMessage(channel, "You are now signed up with ID " + cmd.hmID.get(sender));
        }else{
            bot.sendMessage(channel, "You're registerd with ID " + cmd.hmID.get(sender));
        }
    }

    public void MakeRpgRegVal(String sender, String channel) {
        int tempIdVal = cmd.hmID.get(sender);
        
        cmd.RpgID[tempIdVal][0]=1; // liv
        cmd.RpgID[tempIdVal][1]=20; // hp
        cmd.RpgID[tempIdVal][2]=12; // stamina
    }
    public void MakeRpgCheatValues(String sender, String channel){
        int tempValue[] = new int[3];
        for (int i = 0; i <= 2; i++) {
            tempValue[i] = cmd.RpgID[cmd.hmID.get(sender)][i]; 
        }
        bot.sendMessage(channel, "Alive = " + tempValue[0] + ", hp = " + tempValue[1] + ", stamina = " + tempValue[2] + ".");
    }

    public void makeRpgHelp(String channel) {
        bot.sendMessage(channel, "I should implement help.");
    }

    public void makeRpgWalk() {

    }

}
