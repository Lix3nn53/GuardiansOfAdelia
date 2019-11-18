package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.menu.MenuList;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
import java.util.UUID;

public class MyPlayerInteractEntityEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerInteractEntityEvent event) {
        if (!event.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        Entity rightClicked = event.getRightClicked();
        if (rightClicked.getType().equals(EntityType.VILLAGER)) {
            event.setCancelled(true);
        }

        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.hasItemMeta()) {
            ItemMeta itemMeta = itemInMainHand.getItemMeta();
            if (itemMeta.hasDisplayName()) {
                if (itemInMainHand.getType().equals(Material.LAPIS_LAZULI)) { //right click with pet-food
                    String displayName = itemMeta.getDisplayName();
                    EntityType type = rightClicked.getType();
                    if (type.equals(EntityType.HORSE) || type.equals(EntityType.WOLF)) {
                        LivingEntity livingEntity = (LivingEntity) rightClicked;
                        AttributeInstance attribute = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                        double maxHealth = attribute.getValue();
                        double currentHealth = livingEntity.getHealth();
                        if (currentHealth < maxHealth) {
                            int healAmount = 100;
                            if (displayName.contains("2")) {
                                healAmount = 500;
                            } else if (displayName.contains("3")) {
                                healAmount = 1000;
                            } else if (displayName.contains("4")) {
                                healAmount = 2000;
                            }
                            double setHealthAmount = currentHealth + healAmount;
                            if (setHealthAmount > maxHealth) {
                                setHealthAmount = maxHealth;
                            }
                            livingEntity.setHealth(setHealthAmount);
                        } else {
                            player.sendMessage(ChatColor.RED + "Pet health is already full");
                        }
                    }
                } else if (rightClicked.isCustomNameVisible()) {
                    UUID uniqueId = player.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(uniqueId)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
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
                    GuiGeneric guiGeneric = MenuList.onShiftRightClickPlayer(rightClickedPlayer);
                    guiGeneric.openInventory(player);
                }
            }
        }
    }
}
