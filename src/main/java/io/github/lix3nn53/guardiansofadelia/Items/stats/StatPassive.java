package io.github.lix3nn53.guardiansofadelia.Items.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatPassive implements Stat {

    private int strength = 0;
    private int spirit = 0;
    private int endurance = 0;
    private int intelligence = 0;
    private int dexterity = 0;

    public StatPassive(int strength, int spirit, int endurance, int intelligence, int dexterity) {
        this.strength = strength;
        this.spirit = spirit;
        this.endurance = endurance;
        this.intelligence = intelligence;
        this.dexterity = dexterity;
    }

    public StatPassive(int minStatValue, int maxStatValue, int minNumberOfStats) {
        if (maxStatValue <= 0) {
            return;
        }
        if (minStatValue <= 0) {
            minStatValue = 1;
        }
        if (maxStatValue - minStatValue < 1) {
            maxStatValue = minStatValue;
        }

        int amountOfStats = new Random().nextInt(50);
        amountOfStats = (int) (amountOfStats / 10 + 0.5);

        if (minNumberOfStats <= 5) {
            if (amountOfStats < minNumberOfStats) {
                amountOfStats = minNumberOfStats;
            }
        } else {
            amountOfStats = 5;
        }

        for (int i = 0; i < amountOfStats; i++) {
            satisfyOneRandomly(minStatValue, maxStatValue);
        }
    }

    public int getStrength() {
        return strength;
    }

    public int getSpirit() {
        return spirit;
    }

    public int getEndurance() {
        return endurance;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getDexterity() {
        return dexterity;
    }

    public boolean isEmpty() {
        return (strength + spirit + endurance + intelligence + dexterity) < 1;
    }

    private void satisfyOneRandomly(int minStatValue, int maxStatValue) {
        List<String> unUsedStats = new ArrayList<>();
        if (this.strength == 0) {
            unUsedStats.add("strength");
        }
        if (this.spirit == 0) {
            unUsedStats.add("spirit");
        }
        if (this.endurance == 0) {
            unUsedStats.add("endurance");
        }
        if (this.intelligence == 0) {
            unUsedStats.add("intelligence");
        }
        if (this.dexterity == 0) {
            unUsedStats.add("dexterity");
        }
        int random = new Random().nextInt(unUsedStats.size());
        String statString = unUsedStats.get(random);

        if (statString.equals("strength")) {
            this.strength = getRandomValue(minStatValue, maxStatValue);
        } else if (statString.equals("spirit")) {
            this.spirit = getRandomValue(minStatValue, maxStatValue);
        } else if (statString.equals("endurance")) {
            this.endurance = getRandomValue(minStatValue, maxStatValue);
        } else if (statString.equals("intelligence")) {
            this.intelligence = getRandomValue(minStatValue, maxStatValue);
        } else if (statString.equals("dexterity")) {
            this.dexterity = getRandomValue(minStatValue, maxStatValue);
        }
    }

    private int getRandomValue(int minStatValue, int maxStatValue) {
        if (minStatValue >= maxStatValue) {
            return maxStatValue;
        } else {
            return new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
        }
    }
}
