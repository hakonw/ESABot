package org.no_ip.kermi.rpg;

import net.aslettemark.esabot.ESABot;

public class RpgUtil {

    protected ESABot bot;
    public RpgCommand cmd;

    public RpgUtil(ESABot bot, RpgCommand c) {
        this.bot = bot;
        this.cmd = c;
    }

    public void makeRpgUpdateAll(String channel) {
        for (int i = 0; i <= cmd.hmID.size(); i++) {
            int tempLiv = cmd.rpgID[i][1];
            if (tempLiv <= 0) {
                cmd.rpgID[i][0] = 0;
                bot.sendMessage(channel, cmd.rpgIDS[i] + " is dead. RIP IN PEACE!");
            }
        }
    }

    public void makeRpgUpdate(String sender, String channel) {
        if (cmd.rpgID[cmd.hmID.get(sender)][1] <= 0) {
                cmd.rpgID[cmd.hmID.get(sender)][0] = 0;
                bot.sendMessage(channel, sender + " is dead. RIP IN PEACE!");
        }
    }

    public void makeRpgAttack(String sender, String receiver, String spell, int damage, int stamina, String channel, Boolean heal) {
        int tempRpgId = cmd.hmID.get(sender);
        if (!(cmd.hmID.get(sender) == null)) {
            if (!(cmd.hmID.get(receiver) == null)) {
                if (cmd.rpgID[tempRpgId][2] >= stamina) {
                    if (cmd.rpgID[cmd.hmID.get(receiver)][1] >= 0) {
                        final int tempRpgStamina = cmd.rpgID[tempRpgId][2];
                        final int tempRpgHp = cmd.rpgID[tempRpgId][1];

                        cmd.rpgID[tempRpgId][2] = tempRpgStamina - stamina;
                        cmd.rpgID[tempRpgId][1] = tempRpgHp - damage;

                        if (heal) {
                            bot.sendMessage(channel, sender + " healed " + receiver + " with " + -damage + " hp with his " + spell + "ing spell and used up " + stamina + " stamina.");
                        } else {
                            bot.sendMessage(channel, sender + " took " + damage + " hp to " + receiver + " with his " + spell + " and used up " + stamina + " stamina.");
                        }
                        makeRpgUpdate(receiver, channel);
                    } else {
                        bot.sendMessage(channel, "That dude is dead, try attacking another dude.");
                    }
                } else {
                    bot.sendMessage(channel, "It looks like you're out of stamina " + cmd.rpgID[tempRpgId][2] + "/" + stamina);
                }
            } else {
                bot.sendMessage(channel, receiver + " ain't a guy.");
            }
        } else {
            bot.sendMessage(channel, "You're not registered, use '.rpg init'.");
        }
    }

    public void makeRpgReg(String sender, String channel) {
        if (cmd.hmID.get(sender) == null) {
            cmd.hmID.put(sender, new Integer(cmd.hmID.size()));
            makeRpgRegVal(sender, channel);
            bot.sendMessage(channel, "You are now signed up with ID " + cmd.hmID.get(sender));
        } else {
            bot.sendMessage(channel, "You're registered with ID " + cmd.hmID.get(sender));
        }
    }

    public void makeRpgRegVal(String sender, String channel) {
        int tempIdVal = cmd.hmID.get(sender);
        cmd.rpgIDS[tempIdVal] = sender;
        cmd.rpgID[tempIdVal][0] = 1; // liv
        cmd.rpgID[tempIdVal][1] = 20; // hp
        cmd.rpgID[tempIdVal][2] = 12; // stamina
        cmd.rpgPOS[tempIdVal] = "2_2";
    }

    public void makeRpgCheatValues(String sender, String channel) {
        int tempValue[] = new int[3];
        for (int i = 0; i <= 2; i++) {
            tempValue[i] = cmd.rpgID[cmd.hmID.get(sender)][i];
        }
        bot.sendMessage(channel, "Alive = " + tempValue[0] + ", hp = " + tempValue[1] + ", stamina = " + tempValue[2] + ".");
    }

    public void makeRpgHelp(String channel) {
        bot.sendMessage(channel, "Help_Commands: help, init, attack <player> <method>, info <player>, cheat <cheat>");
    }

    public void makeRpgWalk(String sender, String direction, String channel) {
        
        String[] tempXaY = cmd.rpgPOS[cmd.hmID.get(sender)].split("_");
        int tempX = Integer.parseInt(tempXaY[0]);
        int tempY = Integer.parseInt(tempXaY[1]);
        if((direction.equalsIgnoreCase("north") && tempX == cmd.rpgXaY[0]) ||
            (direction.equalsIgnoreCase("vest") && tempY == cmd.rpgXaY[0]) ||
            (direction.equalsIgnoreCase("south") && tempX == cmd.rpgXaY[1]) ||
            (direction.equalsIgnoreCase("east") && tempY == cmd.rpgXaY[1])
            ){
            bot.sendMessage(channel, "You cannot move this way, you're on the edge.");
        }else{
            if(direction.equalsIgnoreCase("north")){
                cmd.rpgPOS[cmd.hmID.get(sender)] = tempX-1 + "_" + tempY;
            }else if(direction.equalsIgnoreCase("vest")){
                tempY--;
                cmd.rpgPOS[cmd.hmID.get(sender)] = tempX + "_" + tempY;
            }else if(direction.equalsIgnoreCase("south")){
                cmd.rpgPOS[cmd.hmID.get(sender)] = tempX+1 + "_" + tempY;
            }else if(direction.equalsIgnoreCase("east")){
                cmd.rpgPOS[cmd.hmID.get(sender)] = tempX + "_" + tempY+1;
            }
            bot.sendMessage(channel, "You moved " + direction + ".");
        }
    }
    
    public void makeRpgWalkWorld(int tempWorldX, int tempWorldY, String town, String spawn){
        cmd.rpgXaY[0]=tempWorldX;
        cmd.rpgXaY[0]=tempWorldY;
        for(int i = 1; i <= tempWorldX; i++){
            for(int j = 1; j <= tempWorldY; j++){
                String tempWorldXY = tempWorldX + "_" + tempWorldY;
                cmd.hmPOStown.put(tempWorldXY, "forest");
            }
            String tempWorldTown = "town_" + town;
            cmd.hmPOStown.put(spawn, tempWorldTown);
        }
    }

}