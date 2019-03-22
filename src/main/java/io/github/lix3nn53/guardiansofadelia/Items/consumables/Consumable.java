package io.github.lix3nn53.guardiansofadelia.Items.consumables;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Consumable {

    private final String skillCode;
    private final int skillLevel;
    private final Material material;
    private final String consumableName;
    private final List<String> explanation;

    public Consumable(String skillCode, int skillLevel, Material material, String consumableName, List<String> explanation) {
        this.skillCode = skillCode;
        this.skillLevel = skillLevel;
        this.material = material;
        this.consumableName = consumableName;
        this.explanation = explanation;
    }

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
                            InventoryUtils.removeMaterialFromInventory(player.getInventory(), material, 1);
                            SkillAPIUtils.forceUseSkill(player, skillCode, skillLevel);
                            guardianData.setConsuming(false);
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20L);
            }
        }
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(this.material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.consumableName + " Tier-" + this.skillLevel);
        int reqLevel = (this.skillLevel * 10) - 10;
        if (reqLevel <= 0) {
            reqLevel = 1;
        }

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Required Level: " + reqLevel);
        lore.add("");
        lore.add(ChatColor.GRAY + "How to use:");
        lore.add(ChatColor.GRAY + "◯ Right click while holding");
        lore.add(ChatColor.GRAY + "◯ Open your inventory then shift + right click");
        lore.add("");
        lore.addAll(this.explanation);
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public String getSkillCode() {
        return skillCode;
    }

    public Material getMaterial() {
        return material;
    }

    public int getSkillLevel() {
        return skillLevel;
    }
}
