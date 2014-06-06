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
                            bot.sendMessage(channel, sender + " healed " + receiver + " with " + -damage + " hp with his " + spell + "ing spell and lost " + stamina + " stamina.");
                        } else {
                            bot.sendMessage(channel, sender + " took " + damage + " hp to " + receiver + " with his " + spell + " and lost " + stamina + " stamina.");
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
    }

    public void makeRpgCheatValues(String sender, String channel) {
        int tempValue[] = new int[3];
        for (int i = 0; i <= 2; i++) {
            tempValue[i] = cmd.rpgID[cmd.hmID.get(sender)][i];
        }
        bot.sendMessage(channel, "Alive = " + tempValue[0] + ", hp = " + tempValue[1] + ", stamina = " + tempValue[2] + ".");
    }

    public void makeRpgHelp(String channel) {
        bot.sendMessage(channel, "I should implement help.");
    }

    public void makeRpgWalk() {

    }

}