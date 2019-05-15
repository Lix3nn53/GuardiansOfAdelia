package io.github.lix3nn53.guardiansofadelia.events;

import org.bukkit.ChatColor;
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

public class MyPlayerInteractEntityEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerInteractEntityEvent event) {
        if (!event.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.hasItemMeta()) {
            ItemMeta itemMeta = itemInMainHand.getItemMeta();
            if (itemMeta.hasDisplayName()) {
                if (itemInMainHand.getType().equals(Material.LAPIS_LAZULI)) { //right click with pet-food
                    String displayName = itemMeta.getDisplayName();
                    Entity rightClicked = event.getRightClicked();
                    if (rightClicked instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) rightClicked;
                        EntityType type = livingEntity.getType();
                        if (type.equals(EntityType.HORSE) || type.equals(EntityType.WOLF)) {
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
                    }
                }
            }
        }
    }
}
