package io.github.lix3nn53.guardiansofadelia.pets;

import io.github.lix3nn53.guardiansofadelia.creatures.PetList;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.NBTTagUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PetManager {

    private static HashMap<Entity, Player> petToPlayer = new HashMap<Entity, Player>();
    private static long respawnDelay = 20 * 60L;

    public static void addPet(Player owner, Entity pet) {
        petToPlayer.put(pet, owner);
    }

    public static Player getOwner(Entity entity) {
        return petToPlayer.get(entity);
    }

    public static boolean isPet(Entity entity) {
        return petToPlayer.containsKey(entity);
    }

    public static long getRespawnDelay() {
        return respawnDelay;
    }

    public static void spawnPet(Player player) {
        ItemStack egg = GuardianDataManager.getGuardianData(player.getUniqueId()).getActiveCharacter().getRpgInventory().getPetSlot().getItemOnSlot();
        if (NBTTagUtils.hasTag(egg, "petCode")) {
            if (NBTTagUtils.hasTag(egg, "petLevel")) {
                //companion
                String petCode = NBTTagUtils.getString(egg, "petCode");
                int petLevel = NBTTagUtils.getInteger(egg, "petLevel");

                Wolf companion = PetList.getCompanion(player, petCode, 0, petLevel);
                petToPlayer.put(companion, player);
            } else {
                String petCode = NBTTagUtils.getString(egg, "petCode");
                Vehicle mount = PetList.getMount(player, petCode, 0);
                petToPlayer.put(mount, player);
            }
        }
    }

    public static boolean isPetOwner(Player player) {
        for (Entity pet : petToPlayer.keySet()) {
            Player owner = petToPlayer.get(pet);
            if (owner == player) {
                return true;
            }
        }
        return false;
    }
}
