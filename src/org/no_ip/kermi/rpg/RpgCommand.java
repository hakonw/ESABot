package org.no_ip.kermi.rpg;

import java.util.HashMap;

import org.no_ip.kermi.rpg.RpgUtil;

import net.aslettemark.esabot.ESABot;
import net.aslettemark.esabot.command.CommandExecutor;

public class RpgCommand extends CommandExecutor {

    public RpgUtil util;
    public int[][] rpgID;
    public HashMap<String, Integer> hmID;
    public String[] rpgIDS;

    public RpgCommand(ESABot bot) {
        super(bot);
        this.util = new RpgUtil(bot, this);
        this.rpgID = new int[10][5];
        this.hmID = new HashMap<String, Integer>();
        this.rpgIDS = new String[10];
    }

    @Override
    public void execute(String channel, String sender, String login, String hostname, String command, boolean pm) {

        String[] commandArgu = command.split(" ");
        switch (commandArgu[1]) {
            case "help":
                util.makeRpgHelp(channel);
                break;
            case "attack":
                if (rpgID[hmID.get(sender)][0] == 1) {
                    switch (commandArgu[3]) {
                        case "fist":
                            util.makeRpgAttack(sender, commandArgu[2], commandArgu[3], 2, 1, channel, false);
                            break;
                        case "fireball":
                            util.makeRpgAttack(sender, commandArgu[2], commandArgu[3], 6, 3, channel, false);
                            break;
                        case "dagger":
                            util.makeRpgAttack(sender, commandArgu[2], commandArgu[3], 3, 1, channel, false);
                            break;
                        case "heal":
                            util.makeRpgAttack(sender, commandArgu[2], commandArgu[3], -6, 2, channel, true);
                            break;
                        case "death": //will be removed
                            util.makeRpgAttack(sender, commandArgu[2], commandArgu[3], 30, 0, channel, false);
                            break;
                        default:
                            bot.sendMessage(channel, "That aint an attack.");
                    }
                } else {
                    bot.sendMessage(channel, "It looks like you're dead");
                }
                break;
            case "init":
                util.makeRpgReg(sender, channel);
                break;
            case "walk":
                util.makeRpgWalk();
                bot.sendMessage(channel, "Walk is not implemented yet");
                break;
            case "info":
                if (commandArgu[2].isEmpty()) {
                    util.makeRpgCheatValues(sender, channel);
                } else {
                    util.makeRpgCheatValues(commandArgu[2], channel);
                }
                break;
            case "cheat":
                switch (commandArgu[2]) {
<<<<<<< HEAD
=======
                    case "value":
                        if (commandArgu[3].isEmpty()) {
                            bot.sendMessage(channel, "Syntax error use <.rpg cheat value 'name'>");
                        } else {
                            util.makeRpgCheatValues(commandArgu[3], channel);
                        }
                        break;

>>>>>>> b0e8d0e283d1898148416807bab043b334fd87db
                    case "reset":
                        if (commandArgu[3].isEmpty()) {
                            bot.sendMessage(channel, "Syntax error use <.rpg cheat reset 'name'>");
                        } else {
                            util.makeRpgRegVal(commandArgu[3], channel);
                            bot.sendMessage(channel, "Done with reset to " + commandArgu[3]);
                        }
                        break;
                    case "portal":
                        bot.sendMessage(channel, "I'm doing Science and I'm still alive.");
                        bot.sendMessage(channel, "I feel FANTASTIC and I'm still alive.");
                        bot.sendMessage(channel, "While you're dying I'll be still alive.");
                        bot.sendMessage(channel, "And when you're dead I will be still alive.");
                        bot.sendMessage(channel, "STILL ALIVE");
                        bot.sendMessage(channel, "STILL ALIVE");
                        break;
                    case "update_all":
                        util.makeRpgUpdateAll(channel);
                        break;
                    case "update":
                        util.makeRpgUpdate(commandArgu[3], channel);
                        break;
                    default:
                        bot.sendMessage(channel, "That is not a cheat.");
                }
                break;

            default:
                bot.sendMessage(channel, "Unknown command, you could try '.rpg help'.");
        }
    }
}