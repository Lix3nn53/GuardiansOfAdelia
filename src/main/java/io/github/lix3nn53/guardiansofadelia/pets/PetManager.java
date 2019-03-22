package io.github.lix3nn53.guardiansofadelia.pets;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.creatures.PetList;
import io.github.lix3nn53.guardiansofadelia.utilities.NBTTagUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PetManager {

    private HashMap<Entity, Player> petToPlayer = new HashMap<Entity, Player>();
    private long respawnDelay = 20 * 60L;

    public void addPet(Player owner, Entity pet) {
        petToPlayer.put(pet, owner);
    }

    public Player getOwner(Entity entity) {
        return petToPlayer.get(entity);
    }

    public boolean isPet(Entity entity) {
        return petToPlayer.containsKey(entity);
    }

    public long getRespawnDelay() {
        return respawnDelay;
    }

    public void spawnPet(Player player) {
        ItemStack egg = GuardiansOfAdelia.getGuardianDataManager().getGuardianData(player.getUniqueId()).getActiveCharacter().getRpgInventory().getPetSlot().getItemOnSlot();
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

    public boolean isPetOwner(Player player) {
        for (Entity pet : petToPlayer.keySet()) {
            Player owner = petToPlayer.get(pet);
            if (owner == player) {
                return true;
            }
        }
        return false;
    }
}
