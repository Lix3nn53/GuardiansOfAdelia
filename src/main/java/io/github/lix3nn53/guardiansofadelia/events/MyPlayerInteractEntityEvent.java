package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.menu.GuiPlayerInterract;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.EggSlot;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MyPlayerInteractEntityEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onEvent(PlayerInteractEntityEvent event) {
        event.setCancelled(true);

        if (!event.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }

        Entity rightClicked = event.getRightClicked();
        Player player = event.getPlayer();

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.hasItemMeta()) {
            ItemMeta itemMeta = itemInMainHand.getItemMeta();
            if (itemMeta.hasDisplayName()) {
                if (itemInMainHand.getType().equals(Material.BLACK_DYE)) { //right click with premium item
                    LivingEntity pet = (LivingEntity) rightClicked;
                    if (PetManager.isCompanionAlsoPet(pet)) {
                        if (!PersistentDataContainerUtil.hasString(itemInMainHand, "petSkinCode")) return;

                        Player owner = PetManager.getOwner(pet);
                        if (!player.equals(owner)) return;

                        if (GuardianDataManager.hasGuardianData(player)) {
                            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                            if (guardianData.hasActiveCharacter()) {
                                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                                EggSlot eggSlot = activeCharacter.getRpgInventory().getEggSlot();

                                if (eggSlot.isEmpty()) return;

                                ItemStack itemOnSlot = eggSlot.getItemOnSlot();

                                String petSkinCode = PersistentDataContainerUtil.getString(itemInMainHand, "petSkinCode");
                                PersistentDataContainerUtil.putString("petCode", petSkinCode, itemOnSlot);

                                //change values of itemOnSlot
                                ItemMeta onSlotItemMeta = itemOnSlot.getItemMeta();
                                String name = PetManager.getName(petSkinCode);
                                onSlotItemMeta.setDisplayName(name);
                                if (itemMeta.hasCustomModelData())
                                    onSlotItemMeta.setCustomModelData(itemMeta.getCustomModelData());
                                itemOnSlot.setItemMeta(onSlotItemMeta);

                                PetManager.respawnPet(owner);

                                int amount = itemInMainHand.getAmount();
                                itemInMainHand.setAmount(amount - 1);
                            }
                        }
                    }
                } else if (rightClicked.isCustomNameVisible()) {
                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            List<Quest> questList = activeCharacter.getQuestList();
                            for (Quest quest : questList) {
                                quest.progressGiftTasks(player, itemMeta.getDisplayName(), itemInMainHand.getAmount(), rightClicked.getCustomName());
                            }
                        }
                    }
                }
            }
        }

        if (rightClicked instanceof Player) {
            if (player.isSneaking()) {
                Player rightClickedPlayer = (Player) rightClicked;

                NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
                if (npcRegistry.isNPC(rightClickedPlayer)) return;

                if (!player.getGameMode().equals(GameMode.SPECTATOR) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
                    GuiPlayerInterract gui = new GuiPlayerInterract(player, rightClickedPlayer);
                    gui.openInventory(player);
                }
            }
        }
    }
}
