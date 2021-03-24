import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseQueries;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {

    private static final HashMap<Character, Double> playerDamages = new HashMap<>();

    private static final double MULTIPLIER = 1.05;

    public static void main(String[] args) throws InterruptedException, SQLException {

        int maxStatValue = GearLevel.NINE.getMaxStatValue(true, true);
        int maxStatValuee = GearLevel.NINE.getMaxStatValue(true, false);
        System.out.println("max element: " + maxStatValue);
        System.out.println("max attr: " + maxStatValuee);
        int minStatValue = GearLevel.NINE.getMinStatValue(true, true);
        int minStatValue1 = GearLevel.NINE.getMinStatValue(true, false);
        System.out.println("min element: " + minStatValue);
        System.out.println("min attr: " + minStatValue1);

        /*for (int requiredLevel = 0; requiredLevel <= 99; requiredLevel += 10) {
            GearLevel gearLevel = GearLevel.getGearLevel(requiredLevel);
            System.out.println("requiredLevel: " + requiredLevel + "GearLevel: " + gearLevel);
        }

        for (int requiredLevel = 0; requiredLevel <= 90; requiredLevel += 10) {
            System.out.println("REQUIRED LEVEL " + requiredLevel);
            WeaponSet weaponSet = new WeaponSet("requiredLevel" + requiredLevel, requiredLevel, 0);
            System.out.println("weaponSet damage: " + weaponSet.getElementDamage(WeaponGearType.WAND) * 1 * 0.4);
            System.out.println("weaponSet damage: " + weaponSet.getElementDamage(WeaponGearType.WAND) * 1 * 0.6);
            System.out.println("weaponSet damage: " + weaponSet.getElementDamage(WeaponGearType.WAND) * 1 * 0.8);
            System.out.println("weaponSet damage: " + weaponSet.getElementDamage(WeaponGearType.WAND) * 1 * 1);
            System.out.println("weaponSet damage: " + weaponSet.getElementDamage(WeaponGearType.WAND) * 1.4 * 0.4);
            System.out.println("weaponSet damage: " + weaponSet.getElementDamage(WeaponGearType.WAND) * 1.4 * 0.6);
            System.out.println("weaponSet damage: " + weaponSet.getElementDamage(WeaponGearType.WAND) * 1.4 * 0.8);
            System.out.println("weaponSet damage: " + weaponSet.getElementDamage(WeaponGearType.WAND) * 1.4 * 1);
            ArmorSet armorSet = new ArmorSet("requiredLevel" + requiredLevel, requiredLevel);
            int totalHp = 0;
            int totalArmor = 0;
            for (ArmorSlot armorSlot : ArmorSlot.values()) {
                totalHp += armorSet.getHealth(armorSlot, ArmorGearType.FEATHER_ARMOR);
                totalArmor += armorSet.getDefense(armorSlot, ArmorGearType.FEATHER_ARMOR);
            }
            System.out.println("armorSet health: " + totalHp);
            double defenseReduction = StatUtils.getDefenseReduction(totalArmor);
            System.out.println("armorSet defense: " + totalArmor + " - " + "(" + new DecimalFormat("##.##").format((1.0 - defenseReduction) * 100) + "% reduction)");
            *//*ShieldSet shieldSet = new ShieldSet("requiredLevel" + requiredLevel, requiredLevel, 0);
            System.out.println("shieldSet health: " + shieldSet.getHealth(ShieldGearType.SHIELD));*//*
        }*/

        /*double angle = 30;

        double[][] rotationY = MatrixHelper.rotationY(angle);

        System.out.println("rotationY: " + Arrays.deepToString(rotationY));

        double[][] location = new double[][]{new double[]{5}, new double[]{5}, new double[]{5}};

        System.out.println("location: " + Arrays.deepToString(location));

        double[][] result = MatrixHelper.multiplyMatrices(rotationY, location);

        System.out.println("result: " + Arrays.deepToString(result));
        System.out.println("result x: " + result[0][0]);
        System.out.println("result y: " + result[1][0]);
        System.out.println("result z: " + result[2][0]);

        double[][] translation = MatrixHelper.translate(result[0][0], result[1][0], result[2][0], new Vector(0, 10, 0));


        System.out.println("translation: " + Arrays.deepToString(translation));*/




        /*GearSet gearSet = new GearSet("Tutorial", 2);
        GearSet gearSet1 = new GearSet("Tutorial", 2);
        GearSet gearSet2 = new GearSet("Tutorial", 2);

        System.out.println("equals: " + gearSet.equals(gearSet1));
        System.out.println("hash1: " + gearSet1.hashCode());
        System.out.println("hash2: " + gearSet2.hashCode());*/

        /*int stepCount = 5;
        for (int ticksRun = 0; ticksRun <= 100; ticksRun++) {
            boolean doesDivide = ticksRun % 4 == 0;
            if (doesDivide) {
                System.out.println("Tick: " + ticksRun);

                int currentStep = ticksRun / 4;

                System.out.println("Step: " + currentStep);

                if (currentStep < stepCount) {
                    System.out.println("NextStep");
                } else {
                    System.out.println("Finish");
                    break;
                }
            } else {
                System.out.println("Skip tick: " + ticksRun);
            }
        }*/
        /*for (int mobLevel = 1; mobLevel <= 10; mobLevel++) {
            for (int playerLevel = 1; playerLevel <= 20; playerLevel++) {
                System.out.println("mobLevel: " + mobLevel);
                System.out.println("playerLevel: " + playerLevel);
                if (mobLevel == 0) mobLevel = 1;

                int exp = (int) (2 + Math.round(10 * Math.pow(mobLevel, 2) / 16) + 0.5);
                System.out.println(exp);

                if (playerLevel > 9) {
                    if (playerLevel > mobLevel) {
                        exp = (int) (exp * (Math.pow(mobLevel, 1.2) / Math.pow(playerLevel, 1.2)) + 0.5);
                    } else {
                        exp = (int) (exp * (Math.pow(playerLevel, 1.2) / Math.pow(mobLevel, 1.2)) + 0.5);
                    }
                }
                //Share
                int shareCount = 1;
                if (shareCount > 1) {
                    double expMultiplier = 1 - (0.1 * shareCount);

                    exp = (int) (exp * expMultiplier + 0.5);
                }

                if (exp == 0) exp = 1;
                System.out.println(exp);
            }
        }*/
        /*LocalDate localDate = LocalDate.now().with(ChronoField.DAY_OF_MONTH, 7);
        boolean dateInCurrentWeek = DateUtils.isDateInCurrentWeek(localDate);

        LocalDate firstDayOfTheWeek = DateUtils.getFirstDayOfTheWeek();
        System.out.println("firstDayOfTheWeek: " + firstDayOfTheWeek);
        int dayOfTheWeek = DateUtils.getDayOfTheWeek();
        System.out.println("dayOfTheWeek: " + dayOfTheWeek);
        System.out.println("dateInCurrentWeek: " + dateInCurrentWeek);
        boolean sameDay = DateUtils.isSameDay(LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1), LocalDate.now());
        System.out.println("sameDay: " + sameDay);*/


        /*LocalDate now = LocalDate.now();
        String currentDateString = now.toString();
        System.out.println(currentDateString);

        for (int i = 0; i < 15; i++) {
            Random rand = new Random();

            int rpgClassRandom = rand.nextInt(7);
            RPGClass rpgClass = RPGClass.values()[rpgClassRandom + 1]; //+1 to ignore NO_CLASS

            // Obtain a number between [0 - 2].
            int dropType = rand.nextInt(3);

            System.out.println(rpgClass.toString());
            System.out.println("dropType: " + dropType);
        }*/
        /*MySocketServer server = new MySocketServer("localhost", 9092);

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();*/
        /*int value = 1000;
        int z = (int) ((value * MULTIPLIER) + 0.5);
        System.out.println(z);
        System.out.println((int) ((z / MULTIPLIER) + 0.5));
        String currentName = ChatColor.GOLD + "Test";
        System.out.println(currentName);
        String stripColor = ChatColor.stripColor(currentName);
        System.out.println(stripColor);*/
        /*List<Double> downRatesForLevel = new ArrayList<>();
        downRatesForLevel.add(0.75);
        downRatesForLevel.add(0.6);
        downRatesForLevel.add(0.45);
        downRatesForLevel.add(0.325);
        downRatesForLevel.add(0.2);
        downRatesForLevel.add(0.1);
        downRatesForLevel.add(0.05);
        downRatesForLevel.add(0.02);
        downRatesForLevel.add(0.01);

        int value = 640;
        for (double rate : downRatesForLevel) {
            System.out.println(value * rate);
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
