package io.github.lix3nn53.guardiansofadelia.Items.stats;

import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class StatPassive implements Stat {

    HashMap<AttributeType, Integer> attributeTypeToValue = new HashMap<>();

    public StatPassive(HashMap<AttributeType, Integer> attributeTypeToValue) {
        this.attributeTypeToValue = attributeTypeToValue;
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

    public int getAttributeValue(AttributeType attributeType) {
        if (attributeTypeToValue.containsKey(attributeType)) {
            return attributeTypeToValue.get(attributeType);
        }

        return 0;
    }

    public boolean isEmpty() {
        int total = 0;
        for (int i : attributeTypeToValue.values()) {
            total += i;
        }

        return total < 1;
    }

    private void satisfyOneRandomly(int minStatValue, int maxStatValue) {
        List<AttributeType> unUsedStats = new ArrayList<>();
        for (AttributeType attributeType : AttributeType.values()) {
            if (attributeTypeToValue.containsKey(attributeType)) {
                int value = attributeTypeToValue.get(attributeType);
                if (value == 0) {
                    unUsedStats.add(attributeType);
                }
            } else {
                unUsedStats.add(attributeType);
            }
        }

        int random = new Random().nextInt(unUsedStats.size());
        AttributeType statToSatisfy = unUsedStats.get(random);

        int randomValue = getRandomValue(minStatValue, maxStatValue);

        attributeTypeToValue.put(statToSatisfy, randomValue);
    }

    private int getRandomValue(int minStatValue, int maxStatValue) {
        if (minStatValue >= maxStatValue) {
            return maxStatValue;
        } else {
            return new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
        }
    }
}
