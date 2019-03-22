package io.github.lix3nn53.guardiansofadelia.Items.consumables;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BuffScroll extends Consumable {

    private final int timeLimitInMinutes;

    public BuffScroll(String skillCode, int skillLevel, Material material, String consumableName, List<String> explanation, int timeLimitInMinutes) {
        super(skillCode, skillLevel, material, consumableName, explanation);
        this.timeLimitInMinutes = timeLimitInMinutes;
    }

    @Override
    public void startConsuming(Player player) {
        if (GuardiansOfAdelia.getGuardianDataManager().hasGuardianData(player.getUniqueId())) {
            GuardianData guardianData = GuardiansOfAdelia.getGuardianDataManager().getGuardianData(player.getUniqueId());
            if (guardianData.isFreeToAct()) {
                guardianData.setConsuming(true);

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
                                guardianData.setConsuming(false);
                                cancel();
                                player.sendMessage("§cConsuming has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Consuming...", ChatColor.YELLOW + "||||||||||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 1) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setConsuming(false);
                                cancel();
                                player.sendMessage("§cConsuming has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Consuming...", ChatColor.GREEN + "||||" + ChatColor.YELLOW + "||||||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 2) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setConsuming(false);
                                cancel();
                                player.sendMessage("§cConsuming has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Consuming...", ChatColor.GREEN + "||||||||" + ChatColor.YELLOW + "||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 3) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setConsuming(false);
                                cancel();
                                player.sendMessage("§cConsuming has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Consuming...", ChatColor.GREEN + "||||||||||||" + ChatColor.YELLOW + "||||", 0, 50, 0);
                            }
                        } else if (secsRun == 4) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setConsuming(false);
                                cancel();
                                player.sendMessage("§cConsuming has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Consuming...", ChatColor.GREEN + "||||||||||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 5) {
                            cancel();
                            Buff buff = getBuff();
                            if (buff.addBuff(guardianData)) {
                                InventoryUtils.removeMaterialFromInventory(player.getInventory(), getMaterial(), 1);
                                SkillAPIUtils.forceUseSkill(player, getSkillCode(), getSkillLevel());
                            }
                            guardianData.setConsuming(false);
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20L);
            }
        }
    }

    private Buff getBuff() {
        return new Buff(getSkillCode(), this.timeLimitInMinutes);
    }
}
