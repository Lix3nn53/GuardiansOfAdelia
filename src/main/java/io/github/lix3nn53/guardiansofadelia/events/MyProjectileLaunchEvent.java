package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.projectiles.ProjectileSource;

public class MyProjectileLaunchEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        ProjectileSource shooter = projectile.getShooter();
        if (shooter instanceof Player) {
            Player player = (Player) shooter;

            RPGClass rpgClass = SkillAPIUtils.getRPGClass(player);

            if (rpgClass.equals(RPGClass.MONK)) {
                PlayerInventory inventory = player.getInventory();
                ItemStack item = inventory.getItemInMainHand();

                if (item.getType().equals(Material.TRIDENT)) {
                    inventory.setItemInMainHand(item);
                }
            } else if (rpgClass.equals(RPGClass.ARCHER)) {
                PlayerInventory inventory = player.getInventory();
                ItemStack item = inventory.getItemInMainHand();

                if (item.getType().equals(Material.BOW)) {
                    ItemStack arrow = OtherItems.getArrow(2);
                    inventory.setItemInOffHand(arrow);
                } else {
                    inventory.setItemInOffHand(new ItemStack(Material.AIR));
                }
            }
        }
    }

}
