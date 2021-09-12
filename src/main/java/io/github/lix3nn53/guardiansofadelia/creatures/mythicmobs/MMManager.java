package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;

import java.util.HashMap;

public class MMManager {

    private static final HashMap<String, ElementType> internalNameToElement = new HashMap<>();
    private static final HashMap<String, HashMap<ElementType, Double>> internalNameToElementResistances = new HashMap<>();

    public static void addElement(String internalName, ElementType elementType) {
        internalNameToElement.put(internalName, elementType);
        GuardiansOfAdelia.getInstance().getLogger().info(internalName + ": " + elementType.toString());
    }

    public static boolean hasElementType(String internalName) {
        return internalNameToElement.containsKey(internalName);
    }

    public static ElementType getElementType(String internalName) {
        return internalNameToElement.get(internalName);
    }

    public static void addElementResistance(String internalName, ElementType elementType, double resistance) {
        HashMap<ElementType, Double> resistances = new HashMap<>();

        if (internalNameToElementResistances.containsKey(internalName)) {
            resistances = internalNameToElementResistances.get(internalName);
        }

        resistances.put(elementType, resistance);

        internalNameToElementResistances.put(internalName, resistances);
    }

    public static boolean hasElementResistance(String internalName, ElementType elementType) {
        if (internalNameToElementResistances.containsKey(internalName)) {
            HashMap<ElementType, Double> resistances = internalNameToElementResistances.get(internalName);

            return resistances.containsKey(elementType);
        }

        return false;
    }

    public static double getElementResistance(String internalName, ElementType elementType) {
        HashMap<ElementType, Double> resistances = internalNameToElementResistances.get(internalName);

        return resistances.get(elementType);
    }
}
