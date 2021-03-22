package io.github.lix3nn53.guardiansofadelia.Items.stats;

import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class StatPassive implements Stat {

    private final HashMap<AttributeType, Integer> attributeTypeToValue;
    private final HashMap<ElementType, Integer> elementTypeToValue;

    public StatPassive(HashMap<AttributeType, Integer> attributeTypeToValue, HashMap<ElementType, Integer> elementTypeToValue) {
        this.attributeTypeToValue = attributeTypeToValue;
        this.elementTypeToValue = elementTypeToValue;
    }

    public StatPassive(int minAttrValue, int maxAttrValue, int minNumberOfAttr, int minElemValue, int maxElemValue, int minNumberOfElem) {
        this.attributeTypeToValue = new HashMap<>();
        this.elementTypeToValue = new HashMap<>();

        satisfy(minAttrValue, maxAttrValue, minNumberOfAttr, true);
        satisfy(minElemValue, maxElemValue, minNumberOfElem, false);
    }

    public int getAttributeValue(AttributeType attributeType) {
        if (attributeTypeToValue.containsKey(attributeType)) {
            return attributeTypeToValue.get(attributeType);
        }

        return 0;
    }

    public int getElementValue(ElementType elementType) {
        if (elementTypeToValue.containsKey(elementType)) {
            return elementTypeToValue.get(elementType);
        }

        return 0;
    }

    public boolean isEmpty(boolean attr, boolean element) {
        int total = 0;
        if (attr) {
            for (int i : attributeTypeToValue.values()) {
                total += i;
            }
        }
        if (element) {
            for (int i : elementTypeToValue.values()) {
                total += i;
            }
        }

        return total < 1;
    }

    /**
     * @param minValue    min possible value of a field
     * @param maxValue    max possible value of a field
     * @param minAmount   min amount of fields to satisfy
     * @param isAttribute satisfies attribute if true, satisfies element otherwise
     */
    private void satisfy(int minValue, int maxValue, int minAmount, boolean isAttribute) {
        if (maxValue <= 0) {
            return;
        }
        if (minValue <= 0) {
            minValue = 1;
        }
        if (maxValue - minValue < 1) {
            maxValue = minValue;
        }

        int amountOfStats = new Random().nextInt(50);
        amountOfStats = (int) (amountOfStats / 10 + 0.5);

        if (minAmount <= 5) {
            if (amountOfStats < minAmount) {
                amountOfStats = minAmount;
            }
        } else {
            amountOfStats = 5;
        }

        if (isAttribute) {
            for (int i = 0; i < amountOfStats; i++) {
                satisfyOneAttributeRandomly(minValue, maxValue);
            }
        } else {
            for (int i = 0; i < amountOfStats; i++) {
                satisfyOneElementRandomly(minValue, maxValue);
            }
        }
    }

    private void satisfyOneAttributeRandomly(int minValue, int maxValue) {
        List<AttributeType> unused = new ArrayList<>();
        for (AttributeType attributeType : AttributeType.values()) {
            if (attributeTypeToValue.containsKey(attributeType)) {
                int value = attributeTypeToValue.get(attributeType);
                if (value == 0) {
                    unused.add(attributeType);
                }
            } else {
                unused.add(attributeType);
            }
        }

        int random = new Random().nextInt(unused.size());
        AttributeType statToSatisfy = unused.get(random);

        int randomValue = getRandomValue(minValue, maxValue);

        attributeTypeToValue.put(statToSatisfy, randomValue);
    }

    private void satisfyOneElementRandomly(int minValue, int maxValue) {
        List<ElementType> unused = new ArrayList<>();
        for (ElementType elementType : ElementType.values()) {
            if (elementTypeToValue.containsKey(elementType)) {
                int value = elementTypeToValue.get(elementType);
                if (value == 0) {
                    unused.add(elementType);
                }
            } else {
                unused.add(elementType);
            }
        }

        int random = new Random().nextInt(unused.size());
        ElementType statToSatisfy = unused.get(random);

        int randomValue = getRandomValue(minValue, maxValue);

        elementTypeToValue.put(statToSatisfy, randomValue);
    }

    private int getRandomValue(int minStatValue, int maxStatValue) {
        if (minStatValue >= maxStatValue) {
            return maxStatValue;
        } else {
            return new Random().nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
        }
    }
}
