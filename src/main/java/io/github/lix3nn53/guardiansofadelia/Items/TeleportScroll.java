package io.github.lix3nn53.guardiansofadelia.Items;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.NBTTagUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class TeleportScroll {

    private final Location location;
    private final int level;
    private final String locationName;

    public TeleportScroll(Location location, int level, String locationName) {
        this.location = location;
        this.level = level;
        this.locationName = locationName;
    }

    public ItemStack getScroll(int amount) {
        ItemStack scroll = new ItemStack(Material.PAPER, amount);
        ItemMeta itemMeta = scroll.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Teleport Scroll " + ChatColor.DARK_PURPLE +
                "(" + ChatColor.AQUA + locationName + ChatColor.DARK_PURPLE + ")");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.YELLOW + "Required Level: " + level);
            add("");
            add(ChatColor.BLUE + "Right click while holding this");
            add(ChatColor.BLUE + "to start teleporting.");
            add(ChatColor.BLUE + "You will be teleported to");
            add(ChatColor.AQUA + locationName + ChatColor.BLUE + " in 5 seconds.");
            add("");
            add(ChatColor.RED + "If you move, the teleportation will be canceled!");
        }});
        scroll.setItemMeta(itemMeta);
        scroll = NBTTagUtils.putInteger("reqLevel", level, scroll);
        return scroll;
    }

    public void teleport(Player player) {
        if (GuardianDataManager.hasGuardianData(player.getUniqueId())) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player.getUniqueId());
            if (!guardianData.isFreeToAct()) {
                guardianData.setTeleporting(true);
                final double startPosX = player.getLocation().getX();
                final double startPosY = player.getLocation().getY();
                final double startPosZ = player.getLocation().getZ();

                ArmorStand hologramTop = new Hologram(player.getLocation().add(0.0, 2.6, 0.0),
                        ChatColor.BLUE + "< " + ChatColor.YELLOW + locationName + ChatColor.BLUE + " >").getArmorStand();
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
                                hologramTop.setCustomName(ChatColor.BLUE + "< " + ChatColor.YELLOW + locationName + ChatColor.BLUE + " >");
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
                                hologramTop.setCustomName(ChatColor.BLUE + "< " + ChatColor.YELLOW + locationName + ChatColor.BLUE + " >");
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
                                hologramTop.setCustomName(ChatColor.BLUE + "< " + ChatColor.YELLOW + locationName + ChatColor.BLUE + " >");
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
                                hologramTop.setCustomName(ChatColor.BLUE + "< " + ChatColor.YELLOW + locationName + ChatColor.BLUE + " >");
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
                                hologramTop.setCustomName(ChatColor.BLUE + "< " + ChatColor.YELLOW + locationName + ChatColor.BLUE + " >");
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
                                player.teleport(location);
                                InventoryUtils.removeItemFromInventory(player.getInventory(), getScroll(1), 1);
                                player.sendTitle(ChatColor.YELLOW + locationName, ChatColor.GREEN + "Teleported!", 50, 50, 50);
                            }
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 5L, 5L);
            }
        }
    }

}
