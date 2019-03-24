package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.list.Ingredients;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Gathering {

    public void gatheringAnim(final Player player, final GatheringType type, final int gatherLevel) {
        if (GuardianDataManager.hasGuardianData(player.getUniqueId())) {
            final GuardianData guardianData = GuardianDataManager.getGuardianData(player.getUniqueId());
            if (guardianData.isFreeToAct()) {
                guardianData.setGathering(true);

                final double startPosX = player.getLocation().getX();
                final double startPosY = player.getLocation().getY();
                final double startPosZ = player.getLocation().getZ();

                new BukkitRunnable() {

                    // We don't want the task to run indefinitely
                    int secsRun;

                    @Override
                    public void run() {
                        secsRun++;

                        double differenceX = Math.abs(startPosX - player.getLocation().getX());
                        double differenceY = Math.abs(startPosY - player.getLocation().getY());
                        double differenceZ = Math.abs(startPosZ - player.getLocation().getZ());

                        if (secsRun == 0) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage("§cGathering has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Gathering...", ChatColor.YELLOW + "||||||||||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 1) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage("§cGathering has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Gathering...", ChatColor.GREEN + "||||" + ChatColor.YELLOW + "||||||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 2) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage("§cGathering has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Gathering...", ChatColor.GREEN + "||||||||" + ChatColor.YELLOW + "||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 3) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage("§cGathering has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Gathering...", ChatColor.GREEN + "||||||||||||" + ChatColor.YELLOW + "||||", 0, 50, 0);
                            }
                        } else if (secsRun == 4) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage("§cGathering has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Gathering...", ChatColor.GREEN + "||||||||||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 5) {
                            cancel();
                            directGather(player, type, gatherLevel);
                            guardianData.setGathering(false);
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20L);
            }
        }
    }

    public void directGather(Player player, GatheringType type, int gatherLevel) {
        // Obtain a number between [0 - 4].
        Random rand = new Random();
        int n = rand.nextInt(5);

        if (n > 0) {
            ItemStack ingredient = Ingredients.getIngredient(type, gatherLevel, n);
            player.sendTitle(ChatColor.GREEN + "Gathering Success", ChatColor.YELLOW + "" + ingredient.getAmount() + "x " + ingredient.getItemMeta().getDisplayName(), 30, 80, 30);
            InventoryUtils.giveItemToPlayer(player, ingredient);
        } else {
            player.sendTitle(ChatColor.RED + "Gathering Fail", ChatColor.YELLOW + "Maybe next time..", 30, 80, 30);
        }
    }

}
