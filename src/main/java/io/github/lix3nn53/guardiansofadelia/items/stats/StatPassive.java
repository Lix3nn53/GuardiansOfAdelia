package io.github.lix3nn53.guardiansofadelia.items.stats;

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

        int amountOfStats;

        if (minAmount < 5) {
            // int amountOfStats = new Random().nextInt(max - minAmount + 1) + minAmount;
            int bound = 50;
            int multiplier = bound / 5;
            int bonus = 5;
            amountOfStats = new Random().nextInt(bound - (minAmount * multiplier) + bonus) + (minAmount * multiplier);
            amountOfStats = (int) ((amountOfStats / (multiplier)) + 0.5);
        } else {
            amountOfStats = 5;
        }

        if (isAttribute) {
            for (int i = 0; i < amountOfStats; i++) {
                satisfyOneRandomly(minValue, maxValue, amountOfStats, false);
            }
        } else {
            for (int i = 0; i < amountOfStats; i++) {
                satisfyOneRandomly(minValue, maxValue, amountOfStats, true);
            }
        }
    }

    private void satisfyOneRandomly(int minValue, int maxValue, int amountOfStats, boolean isElement) {
        List<ElementType> unusedElements = new ArrayList<>();
        List<AttributeType> unusedAttributes = new ArrayList<>();
        if (isElement) {
            for (ElementType elementType : ElementType.values()) {
                if (elementTypeToValue.containsKey(elementType)) {
                    int value = elementTypeToValue.get(elementType);
                    if (value == 0) {
                        unusedElements.add(elementType);
                    }
                } else {
                    unusedElements.add(elementType);
                }
            }
        } else {
            for (AttributeType attributeType : AttributeType.values()) {
                if (attributeTypeToValue.containsKey(attributeType)) {
                    int value = attributeTypeToValue.get(attributeType);
                    if (value == 0) {
                        unusedAttributes.add(attributeType);
                    }
                } else {
                    unusedAttributes.add(attributeType);
                }
            }
        }

        int random;
        ElementType statToSatisfyElement = null;
        AttributeType statToSatisfyAttribute = null;
        if (isElement) {
            random = new Random().nextInt(unusedElements.size());
            statToSatisfyElement = unusedElements.get(random);
        } else {
            random = new Random().nextInt(unusedAttributes.size());
            statToSatisfyAttribute = unusedAttributes.get(random);
        }

        int gap = maxValue - minValue;
        int unusedSize = isElement ? unusedElements.size() : unusedAttributes.size();
        double currentIndex = amountOfStats - (5 - unusedSize) - 1; // index of element we are satisfying // range is 0 to amount - 1
        double percent = currentIndex / amountOfStats;

        double unit = 1.0 / amountOfStats;

        int lowerLimit = (int) (gap * percent + 0.5);
        int upperLimit = (int) ((gap * (percent + unit)) + 0.5); // next unit is upper limit
        /*System.out.println("amountOfStats: " + amountOfStats);
        System.out.println("currentIndex: " + currentIndex);
        System.out.println("min: " + minValue);
        System.out.println("max: " + maxValue);

        System.out.println("percent: " + percent);

        System.out.println("v: " + lowerLimit);
        System.out.println("v2: " + upperLimit);
        System.out.println("minValue: " + (minValue + lowerLimit));
        System.out.println("maxValue: " + (minValue + upperLimit));*/

        int randomValue = getRandomValue(minValue + lowerLimit, minValue + upperLimit);

        if (statToSatisfyElement != null) {
            elementTypeToValue.put(statToSatisfyElement, randomValue);
        } else if (statToSatisfyAttribute != null) {
            attributeTypeToValue.put(statToSatisfyAttribute, randomValue);
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
