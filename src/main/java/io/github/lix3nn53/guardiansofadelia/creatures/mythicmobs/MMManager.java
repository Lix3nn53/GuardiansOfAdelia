package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;

import java.util.HashMap;

public class MMManager {

    private static final HashMap<String, ElementType> internalNameToElement = new HashMap<>();

    public static void addMobElement(String internalName, ElementType elementType) {
        internalNameToElement.put(internalName, elementType);
        GuardiansOfAdelia.getInstance().getLogger().info(internalName + ": " + elementType.toString());
    }

    public static boolean hasElementType(String internalName) {
        return internalNameToElement.containsKey(internalName);
    }

    public static ElementType getElementType(String internalName) {
        return internalNameToElement.get(internalName);
    }

}
