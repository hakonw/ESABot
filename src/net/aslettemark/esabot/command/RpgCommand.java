package net.aslettemark.esabot.command;

import net.aslettemark.esabot.ESABot;
import net.aslettemark.esabot.util.StringUtils;
import java.util.*;
import java.util.Map.Entry;

public class RpgCommand extends CommandExecutor {

    public RpgCommand(ESABot bot) {
        super(bot);
    }

    // note
    // 1 fix den
    // 2 verdier her
    // 3 flere classes
    // 4 do it better

    // rpg verdier

    int[][] RpgID = new int[10][5];
    HashMap<String, Integer> hm = new HashMap<String, Integer>();
    String RpgIDS[];

    public void execute(String channel, String sender, String login, String hostname, String command, boolean pm) {

        String[] argu = command.split(" ");
        String rest = StringUtils.combineSplit(1, argu, " ");

        switch (argu[1]) {
            case "first":

                // tester hashmap #aksel
                // funket!
                // put inn i en egen void for a kunne teste alene

                makeRpgValue(sender, channel);
                break;

            case "attack":

                // add masse ting

                if (RpgID[ hm.get(sender)][0] == 1) {
                    switch (argu[3]) {

                        case "fist":
                            makeRpgAttack(sender, argu[2], "fist", 1, 3, channel);
                            break;

                        default:
                            bot.sendMessage(channel, "Error 404, unknown attack");
                    }

                } else {
                    bot.sendMessage(channel, "It looks like you're dead.");
                }
                break;

            case "walk":

            case "q":

                bot.sendMessage(channel, sender + " is hacking " + argu[2] + ".");

                break;

            case "cheat": // start cheat case

                System.out.println("arg[1]=cheat");
                System.out.println(rest);

                switch (argu[2]) {
                    case "first":
                        makeRpgValue(argu[3], channel);
                        bot.sendMessage(channel, "your cheat was successful");
                        break;

                    case "data":

                        Set set = hm.entrySet();
                        Iterator i = set.iterator();

                        while (i.hasNext()) {
                            Map.Entry me = (Map.Entry) i.next();
                            String tempMsg = me.getKey() + " = " + me.getValue();
                            bot.sendMessage(channel, tempMsg);
                        }

                        break;

                    case "dataid":

                        int tempMax = Integer.parseInt(argu[3]);

                        for (int i2 = 0; i2 <= 4; i2++) {
                            System.out.println(RpgID[tempMax][i2]);
                        }
                        break;

                    case "attack":
                        int tempargu6 = Integer.parseInt(argu[6]);
                        int tempargu7 = Integer.parseInt(argu[7]);

                        makeRpgAttack(argu[3], argu[4], argu[5], tempargu6, tempargu7, channel);
                        // attacker, attaacket, spell, - stamina - health
                        bot.sendMessage(channel, "I'm doing Science and I'm still alive.");

                        bot.sendMessage(channel, "I feel FANTASTIC and I'm still alive.");

                        bot.sendMessage(channel, "While you're dying I'll be still alive.");

                        bot.sendMessage(channel, "And when you're dead I will be still alive.");

                        bot.sendMessage(channel, "STILL ALIVE");

                        bot.sendMessage(channel, "STILL ALIVE");
                        break;

                    case "cheat_val":
                        makeRpgValChange(argu[3], argu[4], argu[5]);
                        break;

                    case "reset":
                        int tempargu3 = Integer.parseInt(argu[3]);
                        makeRpgClassValue(tempargu3);
                        break;

                    case "update":

                        makeRpgUpdate(channel);

                        break;

                    default:
                        bot.sendMessage(channel, "that aint a cheat");

                }// here stops cheats switch

                break;

            default:
                bot.sendMessage(channel, "error 404  unknown command");
        }

    }

    public void makeRpgClass(String rpgSender, String rpgChannel) {
        int tempMax = hm.get(rpgSender);
        System.out.println(tempMax);
        RpgIDS[tempMax] = rpgSender;
        makeRpgClassValue(tempMax);
        bot.sendMessage(rpgChannel, "Done with id " + tempMax);
        System.out.println(tempMax);
    }

    public void makeRpgClassValue(int tempMax) {
        
        RpgID[tempMax][0] = 1; // skal bli x pos er na om levene
        RpgID[tempMax][1] = 0; // skal bli y pos
        RpgID[tempMax][2] = 1; // skal bli class
        RpgID[tempMax][3] = 20; // hp
        RpgID[tempMax][4] = 7; // stamina
    }

    public void makeRpgValue(String rpgSender, String rpgChannel) {

        if (hm.get(rpgSender) == null) {

            hm.put(rpgSender, new Integer(hm.size()));
            System.out.println(hm.get(rpgSender));

            Set<Entry<String, Integer>> set = hm.entrySet();
            Iterator<Entry<String, Integer>> i = set.iterator();

            while (i.hasNext()) {
                Map.Entry me = (Map.Entry) i.next();
                String tempMsg = me.getKey() + " = " + me.getValue();
                System.out.println(tempMsg);
            } // hm.put(sender, new Boolean("false");

            makeRpgClass(rpgSender, rpgChannel);

        } else {
            bot.sendMessage(rpgChannel, "It looks like you are registerd with ID " + hm.get(rpgSender) + ".");
        }
    }

    public void makeRpgAttack(String attacker, String attacket, String spell, int stamina, int damage, String rpgChannel) {

        if (!(hm.get(attacker) == null)) {

            if (!(hm.get(attacket) == null)) {

                if (RpgID[ hm.get(attacker)][4] >= stamina) {

                    // sw

                    int tempStam = RpgID[ hm.get(attacker)][4]; // stamina
                    RpgID[ hm.get(attacker)][4] = tempStam - stamina;

                    int tempHp = RpgID[ hm.get(attacket)][3]; // liv
                    RpgID[hm.get(attacket)][3] = tempHp - damage;

                    bot.sendMessage(rpgChannel, attacker + " took " + damage + " hp to " + attacket + " with his " + spell + " and lost " + stamina + " stamina.");

                    makeRpgUpdate(rpgChannel);

                } else {
                    bot.sendMessage(rpgChannel, attacker + " you're to low on stamina!");
                }

            } else {
                bot.sendMessage(rpgChannel, "That aint a guy, pls choose a real guy ");
            }

        } else {
            bot.sendMessage(rpgChannel, "You're not registerd, pls do .rpg first ");
        }

    }

    public void makeRpgValChange(String argu, String argu2, String argu3) {

        int tempArg1 = Integer.parseInt(argu);
        int tempArg2 = Integer.parseInt(argu2);
        int tempArg3 = Integer.parseInt(argu3);

        RpgID[tempArg1][tempArg2] = tempArg3;
    }

    public void makeRpgUpdate(String rpgChannel) {

        for (int i = 0; i <= hm.size(); i++) {
            int tempLiv = RpgID[i][3];
            if (tempLiv <= 0) {
                RpgID[i][1] = 0;
                bot.sendMessage(rpgChannel, RpgIDS[i] + " is dead. RIP IN PEACE!");
            }
        }
    }
}
