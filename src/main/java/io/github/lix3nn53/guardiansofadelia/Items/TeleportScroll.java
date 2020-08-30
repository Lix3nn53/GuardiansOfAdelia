package io.github.lix3nn53.guardiansofadelia.Items;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class TeleportScroll {
    private final Location location;
    private final String name;

    public TeleportScroll(Location location, String name) {
        this.location = location;
        this.name = name;
    }

    public TeleportScroll(String toString) {
        String[] split = toString.split("\\.");
        World world = Bukkit.getWorld(split[0]);
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        int z = Integer.parseInt(split[3]);
        float yaw = Float.parseFloat(split[4]);
        float pitch = Float.parseFloat(split[5]);
        String name = split[6];

        this.location = new Location(world, x, y, z, yaw, pitch);
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(location.getWorld().getName());
        sb.append(".");
        sb.append(location.getBlockX()).append(".");
        sb.append(location.getBlockY()).append(".");
        sb.append(location.getBlockZ()).append(".");
        sb.append(location.getYaw()).append(".");
        sb.append(location.getPitch()).append(".");
        sb.append(name);
        return sb.toString();
    }

    public void teleport(Player player, ItemStack scroll) {
        if (GuardianDataManager.hasGuardianData(player.getUniqueId())) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player.getUniqueId());
            if (guardianData.isFreeToAct()) {
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

    public ItemStack getScroll(int amount, int level) {
        ItemStack scroll = new ItemStack(Material.PAPER, amount);
        ItemMeta itemMeta = scroll.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Teleport Scroll " + ChatColor.DARK_PURPLE +
                "(" + ChatColor.AQUA + getName() + ChatColor.DARK_PURPLE + ")");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.YELLOW + "Required Level: " + level);
            add("");
            add(ChatColor.BLUE + "Right click while holding this");
            add(ChatColor.BLUE + "to start teleporting.");
            add(ChatColor.BLUE + "You will be teleported to");
            add(ChatColor.AQUA + getName() + ChatColor.BLUE + " in 5 seconds.");
            add("");
            add(ChatColor.RED + "If you move, the teleportation will be canceled!");
        }});
        scroll.setItemMeta(itemMeta);
        PersistentDataContainerUtil.putInteger("reqLevel", level, scroll);
        PersistentDataContainerUtil.putString("teleportScroll", toString(), scroll);
        return scroll;
    }
}
