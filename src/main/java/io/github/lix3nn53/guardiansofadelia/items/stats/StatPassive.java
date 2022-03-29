package io.github.lix3nn53.guardiansofadelia.items.stats;

import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;

import java.util.*;

public class StatPassive implements Stat {

    private final HashMap<AttributeType, Integer> attributeTypeToValue;
    private final HashMap<ElementType, Integer> elementTypeToValue;

    private final List<Float> unsatisfiedRanksAttributes = new LinkedList<>(Arrays.asList(0.2f, 0.4f, 0.6f, 0.8f, 1.0f));
    private final List<Float> unsatisfiedRanksElements = new LinkedList<>(Arrays.asList(0.2f, 0.4f, 0.6f, 0.8f, 1.0f));

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

    private static final Random randForSatisfy = new Random();
    private static final Random randForSatisfyOne = new Random();
    private static final Random randForValue = new Random();

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
            amountOfStats = randForSatisfy.nextInt(bound - (minAmount * multiplier) + bonus) + (minAmount * multiplier);
            amountOfStats = (int) ((amountOfStats / (multiplier)) + 0.5);
        } else {
            amountOfStats = 5;
        }

        if (isAttribute) {
            for (int i = 0; i < amountOfStats; i++) {
                satisfyOneRandomly(minValue, maxValue, false);
            }
        } else {
            for (int i = 0; i < amountOfStats; i++) {
                satisfyOneRandomly(minValue, maxValue, true);
            }
        }
    }

    private void satisfyOneRandomly(int minValue, int maxValue, boolean isElement) {
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
        float percent;
        ElementType statToSatisfyElement = null;
        AttributeType statToSatisfyAttribute = null;
        if (isElement) {
            random = randForSatisfyOne.nextInt(unusedElements.size());
            statToSatisfyElement = unusedElements.get(random);

            int i = randForSatisfyOne.nextInt(unsatisfiedRanksElements.size());
            percent = unsatisfiedRanksElements.get(i);
            unsatisfiedRanksElements.remove(i);
        } else {
            random = randForSatisfyOne.nextInt(unusedAttributes.size());
            statToSatisfyAttribute = unusedAttributes.get(random);

            int i = randForSatisfyOne.nextInt(unsatisfiedRanksAttributes.size());
            percent = unsatisfiedRanksAttributes.get(i);
            unsatisfiedRanksAttributes.remove(i);
        }

        int gap = maxValue - minValue;
        // if (isElement) System.out.println("percent: " + percent);

        float unitPercent = 0.2f;

        int lowerLimit = (int) (gap * (percent - unitPercent) + 0.5);
        int upperLimit = (int) ((gap * (percent)) + 0.5);
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
            return randForValue.nextInt((maxStatValue - minStatValue) + 1) + minStatValue;
        }
    }

    public float getDecreaseOfElement(ElementType elementType) {
        List<Float> ranks = new LinkedList<>(Arrays.asList(0.2f, 0.4f, 0.6f, 0.8f, 1.0f));

        HashMap<ElementType, Integer> mapSorted = sortElementMap(); // low to high

        int i = 0;
        for (Map.Entry<ElementType, Integer> en : mapSorted.entrySet()) {
            ElementType key = en.getKey();
            if (key.equals(elementType)) {
                return ranks.get(i);
            }
            i++;
        }

        return 0f;
    }

    public float getDecreaseOfAttribute(AttributeType attributeType) {
        List<Float> ranks = new LinkedList<>(Arrays.asList(0.2f, 0.4f, 0.6f, 0.8f, 1.0f));

        HashMap<AttributeType, Integer> mapSorted = sortAttributeMap(); // low to high

        int i = 0;
        for (Map.Entry<AttributeType, Integer> en : mapSorted.entrySet()) {
            AttributeType key = en.getKey();
            if (key.equals(attributeType)) {
                return ranks.get(i);
            }
            i++;
        }

        return 0f;
    }

    private HashMap<ElementType, Integer> sortElementMap() {
        // Create a list from elements of HashMap
        List<Map.Entry<ElementType, Integer>> list = new LinkedList<>(elementTypeToValue.entrySet());

        // Sort the list
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        // put data from sorted list to hashmap
        HashMap<ElementType, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<ElementType, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }

        return temp;
    }

    private HashMap<AttributeType, Integer> sortAttributeMap() {
        // Create a list from elements of HashMap
        List<Map.Entry<AttributeType, Integer>> list = new LinkedList<>(attributeTypeToValue.entrySet());

        // Sort the list
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        // put data from sorted list to hashmap
        HashMap<AttributeType, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<AttributeType, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }

        return temp;
    }
}
