package io.github.lix3nn53.guardiansofadelia.transportation;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportationUtils {

    public static void teleport(Player player, Location location, String destination, int stepCount, ItemStack itemCost, int cost) {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.isFreeToAct()) {
                guardianData.setTeleporting(true);
                final double startPosX = player.getLocation().getX();
                final double startPosY = player.getLocation().getY();
                final double startPosZ = player.getLocation().getZ();

                ArmorStand hologramTop = new Hologram(player.getLocation().add(0.0, 2.6, 0.0),
                        ChatColor.BLUE + "< " + ChatColor.YELLOW + destination + ChatColor.BLUE + " >").getArmorStand();
                ArmorStand hologramBottom = new Hologram(player.getLocation().add(0.0, 2.3, 0.0),
                        ChatColor.AQUA + "Teleporting.. " + stepCount).getArmorStand();
                player.sendTitle(ChatColor.BLUE + "Teleporting..", ChatColor.AQUA.toString() + stepCount, 5, 20, 5);

                new BukkitRunnable() {

                    // We don't want the task to run indefinitely
                    int ticksRun;

                    @Override
                    public void run() {
                        ticksRun++;

                        boolean doesDivide = ticksRun % 4 == 0;
                        if (doesDivide) {
                            int currentStep = ticksRun / 4;

                            double differenceX = Math.abs(startPosX - player.getLocation().getX());
                            double differenceY = Math.abs(startPosY - player.getLocation().getY());
                            double differenceZ = Math.abs(startPosZ - player.getLocation().getZ());

                            if (currentStep < stepCount) {
                                if (isTeleportCanceled(differenceX, differenceY, differenceZ)) {
                                    cancelTeleportation(this, guardianData, hologramTop, hologramBottom, player);
                                } else {
                                    nextStep(player, hologramTop, hologramBottom, destination, stepCount - currentStep);
                                }
                            } else {
                                if (isTeleportCanceled(differenceX, differenceY, differenceZ)) {
                                    cancelTeleportation(this, guardianData, hologramTop, hologramBottom, player);
                                } else {
                                    finishTeleportation(this, guardianData, hologramTop, hologramBottom,
                                            player, location, destination, itemCost, cost);
                                }
                            }
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 5L, 5L);
            }
        }
    }

    private static boolean isTeleportCanceled(double differenceX, double differenceY, double differenceZ) {
        return differenceX > 1 || differenceY > 1 || differenceZ > 1;
    }

    private static void cancelTeleportation(BukkitRunnable runnable, GuardianData guardianData,
                                            ArmorStand hologramTop, ArmorStand hologramBottom, Player player) {
        runnable.cancel();
        guardianData.setTeleporting(false);
        hologramTop.remove();
        hologramBottom.remove();
        player.sendMessage(ChatColor.RED + "Teleportation has been canceled because you moved.");
    }

    private static void nextStep(Player player, ArmorStand hologramTop, ArmorStand hologramBottom, String destination, int countDown) {
        player.sendTitle(ChatColor.BLUE + "Teleporting..", ChatColor.AQUA.toString() + countDown, 5, 20, 5);
        hologramTop.setCustomName(ChatColor.BLUE + "< " + ChatColor.YELLOW + destination + ChatColor.BLUE + " >");
        hologramBottom.setCustomName(ChatColor.AQUA + "Teleporting.. " + countDown);
    }

    private static void finishTeleportation(BukkitRunnable runnable, GuardianData guardianData,
                                            ArmorStand hologramTop, ArmorStand hologramBottom, Player player,
                                            Location location, String destination, ItemStack itemCost, int cost) {
        if (itemCost != null) {
            boolean b = InventoryUtils.removeItemFromInventory(player.getInventory(), itemCost, 1);

            if (!b) {
                player.sendMessage(ChatColor.RED + "Teleportation has been canceled because you don't have required item.");
                return;
            }
        }

        if (cost > 0) {
            boolean b = EconomyUtils.pay(player, cost);

            if (!b) {
                player.sendMessage(ChatColor.RED + "Teleportation has been canceled because you don't have enough coins.");
                return;
            }
        }

        runnable.cancel();
        guardianData.setTeleporting(false);
        hologramTop.remove();
        hologramBottom.remove();
        player.teleport(location);
        player.sendTitle(ChatColor.YELLOW + destination, ChatColor.BLUE + "Teleported!", 20, 40, 20);
    }
}
