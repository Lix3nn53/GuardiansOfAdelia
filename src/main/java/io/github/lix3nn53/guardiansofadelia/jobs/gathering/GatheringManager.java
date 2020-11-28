package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GatheringManager {

    private final static HashMap<Integer, Ingredient> ingredientHashMap = new HashMap<>();

    //MINING
    private final static HashMap<GatheringTool, List<Material>> gatheringToolToBlocks = new HashMap<>();
    private final static HashMap<Material, List<Integer>> blockToIngredients = new HashMap<>();

    //OTHER
    private final static HashMap<GatheringType, List<Integer>> gatheringTypeToIngredients = new HashMap<>();

    public static void startGathering(Player player, ItemStack itemInHand, Material targetBlock) {
        GatheringTool gatheringTool = GatheringTool.materialToGatheringTool(itemInHand.getType());

        if (gatheringTool == null) return;

        if (gatheringToolToBlocks.containsKey(gatheringTool)) {
            List<Material> materials = gatheringToolToBlocks.get(gatheringTool);

            if (materials.contains(targetBlock)) {
                startGatheringAnimation(player, gatheringTool, itemInHand, targetBlock);
            }
        }
    }

    private static ItemStack finishGathering(Player player, ItemStack itemInHand, GatheringTool gatheringTool, Material targetBlock) {
        decreaseDurability(player, itemInHand);

        List<Ingredient> ingredients = getIngredients(gatheringTool, targetBlock);

        if (ingredients == null) return null;

        return getGathered(player, ingredients);
    }

    public static ItemStack finishGathering(Player player, ItemStack itemInHand, GatheringType gatheringType) {
        decreaseDurability(player, itemInHand);

        List<Ingredient> ingredients = getIngredients(gatheringType);

        if (ingredients == null) return null;

        return getGathered(player, ingredients);
    }

    private static List<Ingredient> getIngredients(GatheringTool gatheringTool, Material targetBlock) {
        if (gatheringToolToBlocks.containsKey(gatheringTool)) {
            List<Material> materials = gatheringToolToBlocks.get(gatheringTool);

            if (materials.contains(targetBlock)) {
                List<Integer> integers = blockToIngredients.get(targetBlock);

                List<Ingredient> ingredients = new ArrayList<>();

                for (int i : integers) {
                    ingredients.add(ingredientHashMap.get(i));
                }

                return ingredients;
            }
        }

        return null;
    }

    private static List<Ingredient> getIngredients(GatheringType gatheringType) {
        if (gatheringTypeToIngredients.containsKey(gatheringType)) {
            List<Integer> integers = gatheringTypeToIngredients.get(gatheringType);

            List<Ingredient> ingredients = new ArrayList<>();

            for (int i : integers) {
                ingredients.add(ingredientHashMap.get(i));
            }

            return ingredients;
        }

        return null;
    }

    private static ItemStack getGathered(Player player, List<Ingredient> ingredients) {
        Random random = new Random();

        int amount = random.nextInt(5);

        if (amount > 0) {
            int i = random.nextInt(ingredients.size());
            Ingredient ingredient = ingredients.get(i);

            ItemStack ingItemStack = ingredient.getItemStack(amount);

            player.sendTitle(ChatColor.GREEN + "Gathering Success", ChatColor.YELLOW + "" + ingItemStack.getAmount() + "x " + ingItemStack.getItemMeta().getDisplayName(), 30, 80, 30);

            progressGatheringTasks(player, ingredient, amount);

            return ingItemStack;
        } else {
            player.sendTitle(ChatColor.RED + "Gathering Failed", ChatColor.YELLOW + "Maybe next time..", 30, 80, 30);
        }

        return null;
    }

    private static void decreaseDurability(Player player, ItemStack toolToDecrease) {
        if (toolToDecrease == null) return;

        if (PersistentDataContainerUtil.hasInteger(toolToDecrease, "toolDurability")) {
            int toolDurability = PersistentDataContainerUtil.getInteger(toolToDecrease, "toolDurability");
            if (toolDurability > 1) {
                PersistentDataContainerUtil.putInteger("toolDurability", toolDurability - 1, toolToDecrease);
                ItemMeta itemMeta = toolToDecrease.getItemMeta();
                if (itemMeta.hasDisplayName()) {
                    String displayName = itemMeta.getDisplayName();
                    String replace = displayName.replace("(" + toolDurability + " Uses left)", "(" + (toolDurability - 1) + " Uses left)");
                    itemMeta.setDisplayName(replace);
                    toolToDecrease.setItemMeta(itemMeta);
                }
            } else {
                toolToDecrease.setAmount(0);
                player.sendMessage(ChatColor.RED + "Your gathering tool is broken");
            }
        }
    }

    public static Ingredient getIngredient(int i) {
        return ingredientHashMap.get(i);
    }

    private static void startGatheringAnimation(final Player player, GatheringTool gatheringTool, ItemStack itemStackTool, Material targetBlock) {
        if (GuardianDataManager.hasGuardianData(player.getUniqueId())) {
            final GuardianData guardianData = GuardianDataManager.getGuardianData(player.getUniqueId());
            if (guardianData.isFreeToAct()) {
                guardianData.setGathering(true);

                final double startPosX = player.getLocation().getX();
                final double startPosY = player.getLocation().getY();
                final double startPosZ = player.getLocation().getZ();

                long period = 20;
                if (BoostPremiumManager.isBoostActive(BoostPremium.GATHER)) {
                    period = period / 2;
                }

                new BukkitRunnable() {

                    // We don't want the task to run indefinitely
                    int secsRun;

                    @Override
                    public void run() {
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
                            ItemStack ingredient = finishGathering(player, itemStackTool, gatheringTool, targetBlock);
                            if (ingredient != null) {
                                InventoryUtils.giveItemToPlayer(player, ingredient);
                            }
                            guardianData.setGathering(false);
                        }
                        secsRun++;
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, period);
            }
        }
    }

    private static void progressGatheringTasks(Player player, Ingredient ingredient, int amount) {
        UUID uniqueId = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uniqueId)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                List<Quest> questList = activeCharacter.getQuestList();
                for (Quest quest : questList) {
                    quest.progressGatheringTasks(player, ingredient, amount);
                }
            }
        }
    }

    public static void putIngredient(int i, Ingredient ingredient) {
        ingredientHashMap.put(i, ingredient);
    }

    public static void putToolToBlock(GatheringTool gatheringTool, Material block) {
        List<Material> blocks = new ArrayList<>();
        if (gatheringToolToBlocks.containsKey(gatheringTool)) {
            blocks = gatheringToolToBlocks.get(gatheringTool);
        }
        blocks.add(block);
        gatheringToolToBlocks.put(gatheringTool, blocks);
    }

    public static void putBlockToIngredient(Material block, int ingredient) {
        List<Integer> ingredients = new ArrayList<>();
        if (blockToIngredients.containsKey(block)) {
            ingredients = blockToIngredients.get(block);
        }
        ingredients.add(ingredient);
        blockToIngredients.put(block, ingredients);
    }

    public static void putGatheringTypeToIngredient(GatheringType gatheringType, int ingredient) {
        List<Integer> ingredients = new ArrayList<>();
        if (gatheringTypeToIngredients.containsKey(gatheringType)) {
            ingredients = gatheringTypeToIngredients.get(gatheringType);
        }
        ingredients.add(ingredient);
        gatheringTypeToIngredients.put(gatheringType, ingredients);
    }
}
