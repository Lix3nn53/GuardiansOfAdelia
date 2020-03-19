package io.github.lix3nn53.guardiansofadelia.creatures;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.entitySkills.EntitySkillSet;

import java.util.HashMap;

public class AdeliaEntityManager {

    private final static HashMap<String, AdeliaEntity> keyToEntity = new HashMap<>();
    private final static HashMap<String, EntitySkillSet> keyToSkillSet = new HashMap<>();

    public static void putEntity(String adeliaEntityKey, AdeliaEntity adeliaEntity) {
        keyToEntity.put(adeliaEntityKey, adeliaEntity);
    }

    public static AdeliaEntity getEntity(String entityKey) {
        if (!keyToEntity.containsKey(entityKey)) {
            GuardiansOfAdelia.getInstance().getLogger().info("WRONG ENTITY KEY: " + entityKey);
        }
        return keyToEntity.get(entityKey);
    }

    public static void putSkillSet(String entityKey, EntitySkillSet entitySkillSet) {
        keyToSkillSet.put(entityKey, entitySkillSet);
    }

    public static EntitySkillSet getSkillSet(String entityKey) {
        return keyToSkillSet.get(entityKey);
    }
}
