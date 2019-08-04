import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseQueries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {

    private static final HashMap<Character, Double> playerDamages = new HashMap<>();

    private static final double MULTIPLIER = 0.1;

    public static void main(String[] args) {

        /*int bonusValue = getBonusValue(5000);
        System.out.println(bonusValue);
        int newValue = 5000 + bonusValue;
        int decreaseValue = getDecreaseValue(newValue);
        System.out.println(decreaseValue);

        for (int level = 0; level <= 90; level++) {
            System.out.println((int) (Math.pow(level, 1.75) - (level) + 0.5));

        }*/

        int totalDefense = 1197;

        double damage = 2465;

        double reduction = StatUtils.getDefenseReduction(totalDefense);

        damage = damage * reduction;

        System.out.println(damage);

        /*HashMap<String, HashSet<String>> castKeyToSkillFlags = new HashMap<>();

        String castKey = "key";

        String flag = "flag";


        if (castKeyToSkillFlags.containsKey(castKey)) {
            HashSet<String> flags = castKeyToSkillFlags.get(castKey);
            flags.add(flag);
            castKeyToSkillFlags.put(castKey, flags);
        } else {
            HashSet<String> flags = new HashSet<>();
            flags.add(flag);
            castKeyToSkillFlags.put(castKey, flags);
        }


        flag = "flag2";

        if (castKeyToSkillFlags.containsKey(castKey)) {
            castKeyToSkillFlags.get(castKey).add(flag);
        } else {
            HashSet<String> flags = new HashSet<>();
            flags.add(flag);
            castKeyToSkillFlags.put(castKey, flags);
        }

        System.out.println(castKeyToSkillFlags.toString());*/

       /* playerDamages.put('A', 1D);
        playerDamages.put('S', 1D);
        playerDamages.put('D', 1D);
        playerDamages.put('F', 1D);
        playerDamages.put('g', 1D);
        playerDamages.put('h', 1D);
        playerDamages.put('j', 1D);
        playerDamages.put('k', 2D);

        double max = Collections.max(playerDamages.values());
        List<Character> collect = playerDamages.entrySet().stream()
                .filter(entry -> entry.getValue() == max)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        for (char ch : collect) {
            System.out.println(ch);
        }

        for (int lvl = 0; lvl <=90; lvl++) {
            //double exp = x * lvl * lvl + y * lvl + z;
            double exp = expFormula(lvl);
            System.out.println("Level " + lvl + " TotalExp: " + exp);
        }

        int experience = 0;
        for (int level = 1; level <= 0; level++) {
            System.out.println("Level 1123213123123123123123");
        }*/
    }

    private static int getBonusValue(int value) {
        int bonus = (int) ((value * MULTIPLIER) + 0.5);
        if (bonus <= 1) {
            return 1;
        }
        return bonus;
    }

    private static int getDecreaseValue(int value) {
        double v1 = 1D - MULTIPLIER;
        double v2 = v1 * (MULTIPLIER * MULTIPLIER);
        double totalV = v1 + v2;

        int bonus = (int) ((value * totalV) + 0.5);
        if (bonus <= 1) {
            return 1;
        }
        return bonus;
    }

    private static double expFormula(int level) {
        return 10 + Math.round(5 * Math.pow(level, 3) / 4);
    }

    private static void asd() {
        DatabaseQueries.createTables();

        List<Integer> playersInGame = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            playersInGame.add(i);
        }

        for (int i = 4; i < playersInGame.size(); i+=4) {
            List<Integer> players = playersInGame.subList(i - 4, i);
            System.out.println(players);
        }

        int armor = 4000;

        //dmg reduction formula
        double reduction = ( 1 - ( armor / (armor + 3000.0) ) );

        System.out.println(reduction);

        int damage = 100;

        System.out.println(damage * reduction);

        int count = 1;
        for (int i = 1; i <= 210; i+=20) {
            double pow = Math.pow(i, 1.0/2.0) / 2.5 * Math.pow(i, 2);
            int result = (int) (pow + 0.5);
            System.out.println(count + ": " + result);
            count++;
        }

        //exp formula
        List<Integer> monstersToKill = new ArrayList<>();
        monstersToKill.add(30);
        monstersToKill.add(80);
        monstersToKill.add(110);
        monstersToKill.add(150);
        monstersToKill.add(180);
        monstersToKill.add(230);
        monstersToKill.add(260);
        monstersToKill.add(290);
        monstersToKill.add(320);
        monstersToKill.add(360);
        monstersToKill.add(400);
        monstersToKill.add(450);
        monstersToKill.add(500);
        monstersToKill.add(550);
        monstersToKill.add(600);
        monstersToKill.add(680);
        monstersToKill.add(750);
        monstersToKill.add(820);
        monstersToKill.add(900);

        for (int lvl = 0; lvl <=90; lvl++) {
            //double exp = x * lvl * lvl + y * lvl + z;
            double exp = expFormula(lvl);
            System.out.println("Level " + lvl + " TotalExp: " + exp);
        }

        for (int lvl = 0; lvl <=90; lvl+=5) {
            //double exp = x * lvl * lvl + y * lvl + z;
            double exp = expFormula(lvl);
            int i = (int) (lvl / 5 + 0.5);
            System.out.println("Level " + lvl + " MobKill: " + monstersToKill.get(i));
        }

        for (int lvl = 0; lvl <=90; lvl++) {
            //double exp = x * lvl * lvl + y * lvl + z;
            double exp = expFormula(lvl);
            int i = (int) (lvl / 5 + 0.5);
            System.out.println("Level " + lvl + " ExpPerMob: " + exp / monstersToKill.get(i));
        }

        for (int lvl = 0; lvl <=90; lvl+=5) {
            //double exp = x * lvl * lvl + y * lvl + z;
            double exp = expFormula(lvl);
            int i = (int) (lvl / 5 + 0.5);
            System.out.println("Level " + lvl + " ExpPerMob: " + exp / monstersToKill.get(i));
        }
    }

    private static void printClassStats() {
        HashMap<String, Integer> classToMaxHP = new HashMap<>();
        HashMap<String, Integer> classToMaxMP = new HashMap<>();
        HashMap<String, Integer> classToMaxDMG = new HashMap<>();
        HashMap<String, Integer> classToMaxMDMG = new HashMap<>();
        HashMap<String, Integer> classToMaxDEF = new HashMap<>();
        HashMap<String, Integer> classToMaxMDEF = new HashMap<>();

        List<String> classes = new ArrayList<>();
        classes.add("knight");
        classes.add("paladin");
        classes.add("warrior");
        classes.add("rogue");
        classes.add("archer");
        classes.add("mage");
        classes.add("monk");
        classes.add("hunter");

        for (String clas : classes) {
            double maxhp = 20000;
            double maxmp = 20000;
            double maxdmg = 2000;
            double maxmdmg = 2000;
            double maxdef = 2000;
            double maxmdef = 2000;

            double hp = 5;
            double mp = 5;
            double dmg = 5;
            double mdmg = 5;
            double def = 5;
            double mdef = 5;
            if (clas.equals("knight")) {
                hp = 5;
                mp = 2;
                dmg = 3;
                mdmg = 1;
                def = 5;
                mdef = 2;
            } else if (clas.equals("paladin")) {
                hp = 4;
                mp = 4;
                dmg = 2;
                mdmg = 1;
                def = 3;
                mdef = 4;
            } else if (clas.equals("warrior")) {
                hp = 4;
                mp = 2;
                dmg = 6;
                mdmg = 1;
                def = 3;
                mdef = 2;
            } else if (clas.equals("rogue")) {
                hp = 3;
                mp = 3;
                dmg = 5;
                mdmg = 2;
                def = 3;
                mdef = 2;
            } else if (clas.equals("archer")) {
                hp = 2;
                mp = 4;
                dmg = 4;
                mdmg = 3;
                def = 3;
                mdef = 2;
            } else if (clas.equals("mage")) {
                hp = 2;
                mp = 5;
                dmg = 1;
                mdmg = 5;
                def = 2;
                mdef = 2;
            } else if (clas.equals("monk")) {
                hp = 4;
                mp = 3;
                dmg = 4;
                mdmg = 2;
                def = 3;
                mdef = 2;
            } else if (clas.equals("hunter")) {
                hp = 3;
                mp = 4;
                dmg = 4;
                mdmg = 2;
                def = 3;
                mdef = 2;
            }

            classToMaxHP.put(clas, (int) (maxhp / 5 * hp));
            classToMaxMP.put(clas, (int) (maxmp / 5 * mp));
            classToMaxDMG.put(clas, (int) (maxdmg / 5 * dmg));
            classToMaxMDMG.put(clas, (int) (maxmdmg / 5 * mdmg));
            classToMaxDEF.put(clas, (int) (maxdef / 5 * def));
            classToMaxMDEF.put(clas, (int) (maxmdef / 5 * mdef));
        }

        List<Double> downRatesForLevel = new ArrayList<>();
        downRatesForLevel.add(0.75);
        downRatesForLevel.add(0.6);
        downRatesForLevel.add(0.45);
        downRatesForLevel.add(0.325);
        downRatesForLevel.add(0.2);
        downRatesForLevel.add(0.125);
        downRatesForLevel.add(0.05);
        downRatesForLevel.add(0.02);
        downRatesForLevel.add(0.005);

        for (String key : classes) {
            int levelCount = 90;
            Integer hp = classToMaxHP.get(key);
            System.out.println(key + " level " + levelCount + " hp: " + hp);
            Integer mp = classToMaxMP.get(key);
            System.out.println(key + " level " + levelCount + " mp: " + mp);
            Integer dmg = classToMaxDMG.get(key);
            System.out.println(key + " level " + levelCount + " dmg: " + dmg);
            Integer mdmg = classToMaxMDMG.get(key);
            System.out.println(key + " level " + levelCount + " mdmg: " + mdmg);
            Integer def = classToMaxDEF.get(key);
            System.out.println(key + " level " + levelCount + " def: " + def);
            Integer mdef = classToMaxMDEF.get(key);
            System.out.println(key + " level " + levelCount + " mdef: " + mdef);
            for (double rate : downRatesForLevel) {
                levelCount -= 10;
                System.out.println(key + " level " + levelCount + " hp: " + hp * rate);
                System.out.println(key + " level " + levelCount + " mp: " + mp * rate);
                System.out.println(key + " level " + levelCount + " dmg: " + dmg * rate);
                System.out.println(key + " level " + levelCount + " mdmg: " + mdmg * rate);
                System.out.println(key + " level " + levelCount + " def: " + def * rate);
                System.out.println(key + " level " + levelCount + " mdef: " + mdef * rate);
            }
        }
    }

    private void printArmorStats() {
        HashMap<String, Integer> classToMaxHP = new HashMap<>();
        HashMap<String, Integer> classToMaxMP = new HashMap<>();
        HashMap<String, Integer> classToMaxDMG = new HashMap<>();
        HashMap<String, Integer> classToMaxMDMG = new HashMap<>();
        HashMap<String, Integer> classToMaxDEF = new HashMap<>();
        HashMap<String, Integer> classToMaxMDEF = new HashMap<>();

        List<String> classes = new ArrayList<>();
        classes.add("knight");
        classes.add("paladin");
        classes.add("warrior");
        classes.add("rogue");
        classes.add("archer");
        classes.add("mage");
        classes.add("monk");
        classes.add("hunter");

        for (String clas : classes) {
            double maxhp = 20000;
            double maxmp = 20000;
            double maxdmg = 2000;
            double maxmdmg = 2000;
            double maxdef = 2000;
            double maxmdef = 2000;

            double hp = 5;
            double mp = 5;
            double dmg = 5;
            double mdmg = 5;
            double def = 5;
            double mdef = 5;
            if (clas.equals("knight")) {
                hp = 5;
                mp = 2;
                dmg = 3;
                mdmg = 1;
                def = 5;
                mdef = 2;
            } else if (clas.equals("paladin")) {
                hp = 4;
                mp = 4;
                dmg = 2;
                mdmg = 1;
                def = 3;
                mdef = 4;
            } else if (clas.equals("warrior")) {
                hp = 4;
                mp = 2;
                dmg = 6;
                mdmg = 1;
                def = 3;
                mdef = 2;
            } else if (clas.equals("rogue")) {
                hp = 3;
                mp = 4;
                dmg = 5;
                mdmg = 2;
                def = 2;
                mdef = 2;
            } else if (clas.equals("archer")) {
                hp = 2;
                mp = 4;
                dmg = 4;
                mdmg = 3;
                def = 3;
                mdef = 2;
            } else if (clas.equals("mage")) {
                hp = 2;
                mp = 5;
                dmg = 1;
                mdmg = 5;
                def = 2;
                mdef = 2;
            } else if (clas.equals("monk")) {
                hp = 4;
                mp = 3;
                dmg = 4;
                mdmg = 2;
                def = 3;
                mdef = 2;
            } else if (clas.equals("hunter")) {
                hp = 3;
                mp = 4;
                dmg = 4;
                mdmg = 2;
                def = 3;
                mdef = 2;
            }

            classToMaxHP.put(clas, (int) (maxhp / 5 * hp));
            classToMaxMP.put(clas, (int) (maxmp / 5 * mp));
            classToMaxDMG.put(clas, (int) (maxdmg / 5 * dmg));
            classToMaxMDMG.put(clas, (int) (maxmdmg / 5 * mdmg));
            classToMaxDEF.put(clas, (int) (maxdef / 5 * def));
            classToMaxMDEF.put(clas, (int) (maxmdef / 5 * mdef));
        }

        List<Double> downRatesForLevel = new ArrayList<>();
        downRatesForLevel.add(0.75);
        downRatesForLevel.add(0.6);
        downRatesForLevel.add(0.45);
        downRatesForLevel.add(0.325);
        downRatesForLevel.add(0.2);
        downRatesForLevel.add(0.125);
        downRatesForLevel.add(0.05);
        downRatesForLevel.add(0.02);

        for (String key : classes) {
            int levelCount = 90;
            Integer hp = classToMaxHP.get(key);
            Integer def = classToMaxDEF.get(key);
            Integer mdef = classToMaxMDEF.get(key);
            if (key.equals("knight") || key.equals("paladin")) {
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " helmet & boot hp: " + (hp) / 14 * 2);
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " helmet & boot def: " + (def) / 14 * 2);
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " helmet & boot mdef: " + (mdef) / 14 * 2);

                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " chest hp: " + (hp) / 14 * 4);
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " chest def: " + (def) / 14 * 4);
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " chest mdef: " + (mdef) / 14 * 4);

                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " leg & shield hp: " + (hp) / 14 * 3);
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " leg & shield def: " + (def) / 14 * 3);
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " leg & shield mdef: " + (mdef) / 14 * 3);
            } else {
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " helmet & boot hp: " + (hp) / 11 * 2);
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " helmet & boot def: " + (def) / 11 * 2);
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " helmet & boot mdef: " + (mdef) / 11 * 2);

                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " chest hp: " + (hp) / 11 * 4);
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " chest def: " + (def) / 11 * 4);
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " chest mdef: " + (mdef) / 11 * 4);

                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " leg hp: " + (hp) / 11 * 3);
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " leg def: " + (def) / 11 * 3);
                System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " leg mdef: " + (mdef) / 11 * 3);
            }
            for (double rate : downRatesForLevel) {
                levelCount -= 10;
                if (key.equals("knight") || key.equals("paladin")) {
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " helmet & boot hp: " + (hp * rate) / 14 * 2);
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " helmet & boot def: " + (def * rate) / 14 * 2);
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " helmet & boot mdef: " + (mdef * rate) / 14 * 2);

                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " chest hp: " + (hp * rate) / 14 * 4);
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " chest def: " + (def * rate) / 14 * 4);
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " chest mdef: " + (mdef * rate) / 14 * 4);

                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " leg & shield hp: " + (hp * rate) / 14 * 3);
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " leg & shield def: " + (def * rate) / 14 * 3);
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " leg & shield mdef: " + (mdef * rate) / 14 * 3);
                } else {
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " helmet & boot hp: " + (hp * rate) / 11 * 2);
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " helmet & boot def: " + (def * rate) / 11 * 2);
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " helmet & boot mdef: " + (mdef * rate) / 11 * 2);

                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " chest hp: " + (hp * rate) / 11 * 4);
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " chest def: " + (def * rate) / 11 * 4);
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " chest mdef: " + (mdef * rate) / 11 * 4);

                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " leg hp: " + (hp * rate) / 11 * 3);
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " leg def: " + (def * rate) / 11 * 3);
                    System.out.println(key + " level " + (levelCount - 10) + "~" + levelCount + " leg mdef: " + (mdef * rate) / 11 * 3);
                }
            }
        }
    }
}
