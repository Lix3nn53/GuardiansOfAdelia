package io.github.lix3nn53.guardiansofadelia.Items.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatPassive implements Stat {

    private int fire = 0;
    private int water = 0;
    private int earth = 0;
    private int lightning = 0;
    private int wind = 0;

    public StatPassive(int fire, int water, int earth, int lightning, int wind) {
        this.fire = fire;
        this.water = water;
        this.earth = earth;
        this.lightning = lightning;
        this.wind = wind;
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

    public int getFire() {
        return fire;
    }

    public int getWater() {
        return water;
    }

    public int getEarth() {
        return earth;
    }

    public int getLightning() {
        return lightning;
    }

    public int getWind() {
        return wind;
    }

    public boolean isEmpty() {
        return (fire + water + earth + lightning + wind) < 1;
    }

    private void satisfyOneRandomly(int minStatValue, int maxStatValue) {
        List<String> unUsedElements = new ArrayList<>();
        if (this.fire == 0) {
            unUsedElements.add("fire");
        }
        if (this.water == 0) {
            unUsedElements.add("water");
        }
        if (this.earth == 0) {
            unUsedElements.add("earth");
        }
        if (this.lightning == 0) {
            unUsedElements.add("lightning");
        }
        if (this.wind == 0) {
            unUsedElements.add("wind");
        }
        int random = new Random().nextInt(unUsedElements.size());
        String elementString = unUsedElements.get(random);

        if (elementString.equals("fire")) {
            this.fire = getRandomValue(minStatValue, maxStatValue);
        } else if (elementString.equals("water")) {
            this.water = getRandomValue(minStatValue, maxStatValue);
        } else if (elementString.equals("earth")) {
            this.earth = getRandomValue(minStatValue, maxStatValue);
        } else if (elementString.equals("lightning")) {
            this.lightning = getRandomValue(minStatValue, maxStatValue);
        } else if (elementString.equals("wind")) {
            this.wind = getRandomValue(minStatValue, maxStatValue);
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
