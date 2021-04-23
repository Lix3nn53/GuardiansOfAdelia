package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import java.util.HashMap;

public class PetSkillManager {

    private static final HashMap<String, PetData> petToData = new HashMap<>();

    public static void put(String key, PetData petData) {
        petToData.put(key, petData);
    }

    public static PetData getPetData(String key) {
        return petToData.get(key);
    }


}
