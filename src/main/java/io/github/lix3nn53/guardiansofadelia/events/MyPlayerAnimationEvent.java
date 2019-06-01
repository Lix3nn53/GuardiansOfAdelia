package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.BazaarManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.Portal;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalManager;
import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;

import java.util.List;
import java.util.UUID;

public class MyPlayerAnimationEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerAnimationEvent event) {
        if (!event.getAnimationType().equals(PlayerAnimationType.ARM_SWING)) {
            return; // off hand packet, ignore.
        }

        Player player = event.getPlayer();

        if (player.getLocation().getWorld().getName().equals("world")) {
            Block targetBlock = player.getTargetBlock(null, 5);

            List<Entity> nearbyEntities = player.getNearbyEntities(1, 1, 1);
            for (Entity entity : nearbyEntities) {
                if (entity.getType().equals(EntityType.ARMOR_STAND)) {
                    ArmorStand armorStand = (ArmorStand) entity;
                    Portal portal = PortalManager.getPortalFromArmorStand(armorStand);
                    if (portal != null) { //portal model
                        DungeonTheme theme = MiniGameManager.getDungeonFromPortal(portal);
                        if (theme != null) {
                            theme.getJoinQueueGui().openInventory(player);
                        }
                    } else if (BazaarManager.isBazaar(armorStand)) {
                        Player owner = BazaarManager.getOwner(armorStand);
                        UUID uuid = owner.getUniqueId();
                        if (GuardianDataManager.hasGuardianData(uuid)) {
                            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                            if (guardianData.hasBazaar()) {
                                Bazaar bazaar = guardianData.getBazaar();
                                bazaar.showToCustomer(player);
                                break;
                            }
                        }
                    } else if (TombManager.hasTomb(player)) {
                        TombManager.onReachToTomb(player);
                    }
                }
            }
        }
    }
}
