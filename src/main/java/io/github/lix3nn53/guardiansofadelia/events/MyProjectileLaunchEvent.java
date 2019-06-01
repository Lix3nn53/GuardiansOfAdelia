package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.PlayerTridentThrowManager;
import org.bukkit.ChatColor;
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

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        ProjectileSource shooter = projectile.getShooter();
        if (shooter instanceof Player) {
            Player player = (Player) shooter;

            PlayerInventory inventory = player.getInventory();
            ItemStack item = inventory.getItemInMainHand();

            if (item.getType().equals(Material.TRIDENT)) {
                RPGClass rpgClass = SkillAPIUtils.getRPGClass(player);
                if (rpgClass.equals(RPGClass.MONK)) {
                    if (PlayerTridentThrowManager.canThrow(player)) {
                        PlayerTridentThrowManager.onPlayerTridentThrow(player);
                        inventory.setItemInMainHand(item);
                    } else {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "You can't throw your spear again before it returns back");
                    }
                } else {
                    event.setCancelled(true);
                }
            } else if (item.getType().equals(Material.BOW) || item.getType().equals(Material.CROSSBOW)) {
                RPGClass rpgClass = SkillAPIUtils.getRPGClass(player);
                if (rpgClass.equals(RPGClass.ARCHER) || rpgClass.equals(RPGClass.HUNTER)) {
                    ItemStack arrow = OtherItems.getArrow(2);
                    inventory.setItemInOffHand(arrow);
                }
            }
        }
    }

}
