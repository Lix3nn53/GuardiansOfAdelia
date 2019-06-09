package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.Ingredient;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public enum GatheringType {
    WOODCUTTING,
    HARVESTING_FLOWER,
    FISHING,
    MINING_ORE,
    MINING_JEWELRY,
    HUNTING;

    public void startGatheringAnimation(final Player player, ItemStack itemStackTool) {
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
                                player.sendMessage(ChatColor.RED + "Gathering has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Gathering...", ChatColor.YELLOW + "||||||||||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 1) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatColor.RED + "Gathering has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Gathering...", ChatColor.GREEN + "||||" + ChatColor.YELLOW + "||||||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 2) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatColor.RED + "Gathering has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Gathering...", ChatColor.GREEN + "||||||||" + ChatColor.YELLOW + "||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 3) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatColor.RED + "Gathering has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Gathering...", ChatColor.GREEN + "||||||||||||" + ChatColor.YELLOW + "||||", 0, 50, 0);
                            }
                        } else if (secsRun == 4) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatColor.RED + "Gathering has been canceled because you moved.");
                            } else {
                                player.sendTitle(ChatColor.YELLOW + "Gathering...", ChatColor.GREEN + "||||||||||||||||", 0, 50, 0);
                            }
                        } else if (secsRun == 5) {
                            cancel();
                            ItemStack gatheredIngredient = playerGather(player, itemStackTool);
                            if (gatheredIngredient != null) {
                                InventoryUtils.giveItemToPlayer(player, gatheredIngredient);
                            }
                            guardianData.setGathering(false);
                        }
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 1L, 20L);
            }
        }
    }


    /**
     * @param player
     * @param itemStackTool item used to gather
     * @return null if gathering failed, ItemStack if gathering successful
     */
    public ItemStack playerGather(Player player, ItemStack itemStackTool) {
        if (PersistentDataContainerUtil.hasInteger(itemStackTool, "toolDurability")) {
            int toolDurability = PersistentDataContainerUtil.getInteger(itemStackTool, "toolDurability");
            if (toolDurability > 1) {
                PersistentDataContainerUtil.putInteger("toolDurability", toolDurability - 1, itemStackTool);
                ItemMeta itemMeta = itemStackTool.getItemMeta();
                if (itemMeta.hasDisplayName()) {
                    String displayName = itemMeta.getDisplayName();
                    String replace = displayName.replace("(" + toolDurability + " Uses left)", "(" + (toolDurability - 1) + " Uses left)");
                    itemMeta.setDisplayName(replace);
                    itemStackTool.setItemMeta(itemMeta);
                }
            } else {
                itemStackTool.setAmount(0);
                player.sendMessage(ChatColor.RED + "Your gathering tool is broken");
            }
        }

        // Obtain a number between [0 - 4].
        Random rand = new Random();
        int amount = rand.nextInt(5);

        if (amount > 0) {
            ItemStack ingredient = getGatheredIngredient(amount);
            player.sendTitle(ChatColor.GREEN + "Gathering Success", ChatColor.YELLOW + "" + ingredient.getAmount() + "x " + ingredient.getItemMeta().getDisplayName(), 30, 80, 30);
            return ingredient;
        } else {
            player.sendTitle(ChatColor.RED + "Gathering Failed", ChatColor.YELLOW + "Maybe next time..", 30, 80, 30);
        }
        return null;
    }

    public ItemStack onHunt(Player player) {
        // Obtain a number between [0 - 4].
        Random rand = new Random();
        int amount = rand.nextInt(5);

        if (amount > 0) {
            ItemStack ingredient = getGatheredIngredient(amount);
            player.sendTitle(ChatColor.GREEN + "Gathering Success", ChatColor.YELLOW + "" + ingredient.getAmount() + "x " + ingredient.getItemMeta().getDisplayName(), 30, 80, 30);
            return ingredient;
        } else {
            player.sendTitle(ChatColor.RED + "Gathering Failed", ChatColor.YELLOW + "Maybe next time..", 30, 80, 30);
        }
        return null;
    }

    public ItemStack getGatheredIngredient(int amount) {
        double random = Math.random();
        switch (this) {
            case WOODCUTTING:
                if (random < 0.7) {
                    return Ingredient.WOODCUTTING_LOG.getItemStack(amount);
                } else {
                    return Ingredient.WOODCUTTING_PLANK.getItemStack(amount);
                }
            case HARVESTING_FLOWER:
                if (random < 0.25) {
                    return Ingredient.HARVESTING_ROSE.getItemStack(amount);
                } else if (random < 0.5) {
                    return Ingredient.HARVESTING_STRING.getItemStack(amount);
                } else if (random < 0.65) {
                    return Ingredient.HARVESTING_CHERRY.getItemStack(amount);
                } else if (random < 0.8) {
                    return Ingredient.HARVESTING_SILK.getItemStack(amount);
                } else if (random < 0.9) {
                    return Ingredient.HARVESTING_PLUM_FLOWER.getItemStack(amount);
                } else if (random < 1D) {
                    return Ingredient.HARVESTING_SOFT_SILK.getItemStack(amount);
                }
            case FISHING:
                if (random < 0.7) {
                    return Ingredient.FISHING_COD.getItemStack(amount);
                } else {
                    return Ingredient.FISHING_SALMON.getItemStack(amount);
                }
            case MINING_ORE:
                if (random < 0.25) {
                    return Ingredient.MINING_ORE_COPPER.getItemStack(amount);
                } else if (random < 0.55) {
                    return Ingredient.MINING_ORE_IRON.getItemStack(amount);
                } else if (random < 0.75) {
                    return Ingredient.MINING_ORE_STEEL.getItemStack(amount);
                } else if (random < 0.9) {
                    return Ingredient.MINING_ORE_DIAMOND.getItemStack(amount);
                } else if (random < 1D) {
                    return Ingredient.MINING_ORE_TITANIUM.getItemStack(amount);
                }
            case MINING_JEWELRY:
                if (random < 0.25) {
                    return Ingredient.MINING_JEWEL_GOLD_DUST.getItemStack(amount);
                } else if (random < 0.55) {
                    return Ingredient.MINING_JEWEL_JADE.getItemStack(amount);
                } else if (random < 0.75) {
                    return Ingredient.MINING_JEWEL_AMETHYST.getItemStack(amount);
                } else if (random < 0.9) {
                    return Ingredient.MINING_JEWEL_SAPPHIRE.getItemStack(amount);
                } else if (random < 1D) {
                    return Ingredient.MINING_JEWEL_RUBY.getItemStack(amount);
                }
            case HUNTING:
                if (random < 0.4) {
                    return Ingredient.HUNTING_LEATHER_WORN.getItemStack(amount);
                } if (random < 0.7) {
                    return Ingredient.HUNTING_BEEF.getItemStack(amount);
                } else {
                    return Ingredient.HUNTING_LEATHER_HEAVY.getItemStack(amount);
                }
        }
        return Ingredient.WOODCUTTING_PLANK.getItemStack(amount);
    }
}

