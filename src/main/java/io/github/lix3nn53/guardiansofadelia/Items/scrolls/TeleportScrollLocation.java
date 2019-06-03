package io.github.lix3nn53.guardiansofadelia.Items.scrolls;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public enum TeleportScrollLocation {
    ROUMEN,
    PORT_VELOA,
    ELDERINE,
    URUGA,
    ALBERSTOL_RUINS;

    public Location getLocation() {
        switch (this) {
            case PORT_VELOA:
                return TownManager.getTown(2).getLocation();
            case ELDERINE:
                return TownManager.getTown(3).getLocation();
            case URUGA:
                return TownManager.getTown(4).getLocation();
            case ALBERSTOL_RUINS:
                return TownManager.getTown(5).getLocation();
        }
        return TownManager.getTown(1).getLocation();
    }

    public String getName() {
        switch (this) {
            case PORT_VELOA:
                return "Port Veloa";
            case ELDERINE:
                return "Elderine";
            case URUGA:
                return "Uruga";
            case ALBERSTOL_RUINS:
                return "Alberstol Ruins";
        }
        return "Roumen";
    }

    public void teleport(Player player, ItemStack scroll) {
        if (GuardianDataManager.hasGuardianData(player.getUniqueId())) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player.getUniqueId());
            if (!guardianData.isFreeToAct()) {
                guardianData.setTeleporting(true);
                final double startPosX = player.getLocation().getX();
                final double startPosY = player.getLocation().getY();
                final double startPosZ = player.getLocation().getZ();

                ArmorStand hologramTop = new Hologram(player.getLocation().add(0.0, 2.6, 0.0),
                        ChatColor.BLUE + "< " + ChatColor.YELLOW + getName() + ChatColor.BLUE + " >").getArmorStand();
                ArmorStand hologramBottom = new Hologram(player.getLocation().add(0.0, 2.3, 0.0),
                        ChatColor.AQUA + "Teleporting.. 5").getArmorStand();

                new BukkitRunnable() {

                    // We don't want the task to run indefinitely
                    int ticksRun;

                    @Override
                    public void run() {
                        ticksRun++;
                        double differenceX = Math.abs(startPosX - player.getLocation().getX());
                        double differenceY = Math.abs(startPosY - player.getLocation().getY());
                        double differenceZ = Math.abs(startPosZ - player.getLocation().getZ());
                        if (ticksRun == 1) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                cancel();
                                guardianData.setTeleporting(false);
                                hologramTop.remove();
                                hologramBottom.remove();
                                player.sendMessage(ChatColor.RED + "Teleportation has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.BLUE + "Teleporting..", ChatColor.AQUA + "5", 5, 20, 5);
                                hologramTop.setCustomName(ChatColor.BLUE + "< " + ChatColor.YELLOW + getName() + ChatColor.BLUE + " >");
                                hologramBottom.setCustomName(ChatColor.AQUA + "Teleporting.. 5");
                            }
                        } else if (ticksRun == 4) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                cancel();
                                guardianData.setTeleporting(false);
                                hologramTop.remove();
                                hologramBottom.remove();
                                player.sendMessage(ChatColor.RED + "Teleportation has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.BLUE + "Teleporting..", ChatColor.AQUA + "4", 5, 20, 5);
                                hologramTop.setCustomName(ChatColor.BLUE + "< " + ChatColor.YELLOW + getName() + ChatColor.BLUE + " >");
                                hologramBottom.setCustomName(ChatColor.AQUA + "Teleporting.. 4");
                            }
                        } else if (ticksRun == 8) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                cancel();
                                guardianData.setTeleporting(false);
                                hologramTop.remove();
                                hologramBottom.remove();
                                player.sendMessage(ChatColor.RED + "Teleportation has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.BLUE + "Teleporting..", ChatColor.AQUA + "3", 5, 20, 5);
                                hologramTop.setCustomName(ChatColor.BLUE + "< " + ChatColor.YELLOW + getName() + ChatColor.BLUE + " >");
                                hologramBottom.setCustomName(ChatColor.AQUA + "Teleporting.. 3");
                            }
                        } else if (ticksRun == 12) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                cancel();
                                guardianData.setTeleporting(false);
                                hologramTop.remove();
                                hologramBottom.remove();
                                player.sendMessage(ChatColor.RED + "Teleportation has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.BLUE + "Teleporting..", ChatColor.AQUA + "2", 5, 20, 5);
                                hologramTop.setCustomName(ChatColor.BLUE + "< " + ChatColor.YELLOW + getName() + ChatColor.BLUE + " >");
                                hologramBottom.setCustomName(ChatColor.AQUA + "Teleporting.. 2");
                            }
                        } else if (ticksRun == 16) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                cancel();
                                guardianData.setTeleporting(false);
                                hologramTop.remove();
                                hologramBottom.remove();
                                player.sendMessage(ChatColor.RED + "Teleportation has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.BLUE + "Teleporting..", ChatColor.AQUA + "1", 5, 20, 5);
                                hologramTop.setCustomName(ChatColor.BLUE + "< " + ChatColor.YELLOW + getName() + ChatColor.BLUE + " >");
                                hologramBottom.setCustomName(ChatColor.AQUA + "Teleporting.. 1");
                            }
                        } else if (ticksRun > 20) { // 100 ticks = 5 seconds
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                cancel();
                                guardianData.setTeleporting(false);
                                hologramTop.remove();
                                hologramBottom.remove();
                                player.sendMessage(ChatColor.RED + "Teleportation has been canceled because you moved.");
                            } else {
                                cancel();
                                guardianData.setTeleporting(false);
                                hologramTop.remove();
                                hologramBottom.remove();
                                player.teleport(getLocation());
                                InventoryUtils.removeItemFromInventory(player.getInventory(), scroll, 1);
                                player.sendTitle(ChatColor.YELLOW + getName(), ChatColor.GREEN + "Teleported!", 50, 50, 50);
                            }
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 5L, 5L);
            }
        }
    }
}
