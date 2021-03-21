package io.github.lix3nn53.guardiansofadelia.Items.stats;

import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatPassive implements Stat {

    private int bonusDamage = 0;
    private int bonusDefense = 0;
    private int bonusMaxMana = 0;
    private int bonusMaxHealth = 0;
    private int bonusCriticalChance = 0;

    public StatPassive(int bonusDamage, int bonusDefense, int bonusMaxHealth, int bonusMaxMana, int bonusCriticalChance) {
        this.bonusDamage = bonusDamage;
        this.bonusMaxMana = bonusMaxMana;
        this.bonusMaxHealth = bonusMaxHealth;
        this.bonusDefense = bonusDefense;
        this.bonusCriticalChance = bonusCriticalChance;
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

    public int getBonusDamage() {
        return bonusDamage;
    }

    public int getBonusMaxMana() {
        return bonusMaxMana;
    }

    public int getBonusMaxHealth() {
        return bonusMaxHealth;
    }

    public int getBonusDefense() {
        return bonusDefense;
    }

    public int getBonusCriticalChance() {
        return bonusCriticalChance;
    }

    public boolean isEmpty() {
        return (bonusDamage + bonusMaxMana + bonusMaxHealth + bonusDefense + bonusCriticalChance) < 1;
    }

    private void satisfyOneRandomly(int minStatValue, int maxStatValue) {
        List<String> unUsedStats = new ArrayList<>();
        if (this.bonusDamage == 0) {
            unUsedStats.add(AttributeType.BONUS_ELEMENT_DAMAGE.name());
        }
        if (this.bonusDefense == 0) {
            unUsedStats.add(AttributeType.BONUS_ELEMENT_DEFENSE.name());
        }
        if (this.bonusMaxHealth == 0) {
            unUsedStats.add(AttributeType.BONUS_MAX_HEALTH.name());
        }
        if (this.bonusMaxMana == 0) {
            unUsedStats.add(AttributeType.BONUS_MAX_MANA.name());
        }
        if (this.bonusCriticalChance == 0) {
            unUsedStats.add(AttributeType.BONUS_CRITICAL_CHANCE.name());
        }
        int random = new Random().nextInt(unUsedStats.size());
        String statString = unUsedStats.get(random);

        if (statString.equals(AttributeType.BONUS_ELEMENT_DAMAGE.name())) {
            this.bonusDamage = getRandomValue(minStatValue, maxStatValue);
        } else if (statString.equals(AttributeType.BONUS_ELEMENT_DEFENSE.name())) {
            this.bonusDefense = getRandomValue(minStatValue, maxStatValue);
        } else if (statString.equals(AttributeType.BONUS_MAX_HEALTH.name())) {
            this.bonusMaxHealth = getRandomValue(minStatValue, maxStatValue);
        } else if (statString.equals(AttributeType.BONUS_MAX_MANA.name())) {
            this.bonusMaxMana = getRandomValue(minStatValue, maxStatValue);
        } else if (statString.equals(AttributeType.BONUS_CRITICAL_CHANCE.name())) {
            this.bonusCriticalChance = getRandomValue(minStatValue, maxStatValue);
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
