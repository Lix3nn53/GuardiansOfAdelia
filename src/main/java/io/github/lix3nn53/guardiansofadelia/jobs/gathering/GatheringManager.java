package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.Ingredient;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GatheringManager {

    private final static HashMap<GatheringTool, List<Material>> gatheringToolToBlocks = new HashMap<>();
    private final static HashMap<Material, List<Ingredient>> blockToIngredients = new HashMap<>();
    private final static HashMap<GatheringType, List<Ingredient>> gatheringTypeToIngredients = new HashMap<>();

    static {
        initToolToBlockMap();
        initBlockToIngredientMap();
        initGatheringTypeToIngredientMap();
    }

    public static void startGathering(Player player, ItemStack itemInHand, Material targetBlock) {
        player.sendMessage("1");

        GatheringTool gatheringTool = GatheringTool.materialToGatheringTool(itemInHand.getType());

        if (gatheringTool == null) return;
        player.sendMessage("2");

        if (gatheringToolToBlocks.containsKey(gatheringTool)) {
            player.sendMessage("3");
            List<Material> materials = gatheringToolToBlocks.get(gatheringTool);

            if (materials.contains(targetBlock)) {
                player.sendMessage("4");
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
                return blockToIngredients.get(targetBlock);
            }
        }

        return null;
    }

    private static List<Ingredient> getIngredients(GatheringType gatheringType) {
        if (gatheringTypeToIngredients.containsKey(gatheringType)) {
            return gatheringTypeToIngredients.get(gatheringType);
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
                            finishGathering(player, itemStackTool, gatheringTool, targetBlock);
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

    private static void initToolToBlockMap() {
        List<Material> axeBlocks = new ArrayList<>();
        axeBlocks.add(Material.ACACIA_WOOD);
        axeBlocks.add(Material.BIRCH_WOOD);
        axeBlocks.add(Material.DARK_OAK_WOOD);
        axeBlocks.add(Material.JUNGLE_WOOD);
        axeBlocks.add(Material.OAK_WOOD);
        axeBlocks.add(Material.SPRUCE_WOOD);
        axeBlocks.add(Material.ACACIA_LOG);
        axeBlocks.add(Material.BIRCH_LOG);
        axeBlocks.add(Material.DARK_OAK_LOG);
        axeBlocks.add(Material.JUNGLE_LOG);
        axeBlocks.add(Material.OAK_LOG);
        axeBlocks.add(Material.SPRUCE_LOG);
        gatheringToolToBlocks.put(GatheringTool.AXE, axeBlocks);

        List<Material> pickaxeBlocks = new ArrayList<>();
        pickaxeBlocks.add(Material.EMERALD_ORE);
        pickaxeBlocks.add(Material.GOLD_ORE);
        pickaxeBlocks.add(Material.REDSTONE_ORE);
        pickaxeBlocks.add(Material.LAPIS_ORE);
        pickaxeBlocks.add(Material.COAL_ORE);
        pickaxeBlocks.add(Material.IRON_ORE);
        pickaxeBlocks.add(Material.DIAMOND_ORE);
        gatheringToolToBlocks.put(GatheringTool.PICKAXE, pickaxeBlocks);

        List<Material> hoeBlocks = new ArrayList<>();
        hoeBlocks.add(Material.DANDELION);
        hoeBlocks.add(Material.POPPY);
        hoeBlocks.add(Material.BLUE_ORCHID);
        hoeBlocks.add(Material.ALLIUM);
        hoeBlocks.add(Material.AZURE_BLUET);
        hoeBlocks.add(Material.RED_TULIP);
        hoeBlocks.add(Material.ORANGE_TULIP);
        hoeBlocks.add(Material.WHITE_TULIP);
        hoeBlocks.add(Material.PINK_TULIP);
        hoeBlocks.add(Material.OXEYE_DAISY);
        hoeBlocks.add(Material.CORNFLOWER);
        hoeBlocks.add(Material.LILY_OF_THE_VALLEY);
        hoeBlocks.add(Material.WITHER_ROSE);
        hoeBlocks.add(Material.SUNFLOWER);
        hoeBlocks.add(Material.LILAC);
        hoeBlocks.add(Material.ROSE_BUSH);
        hoeBlocks.add(Material.PEONY);
        gatheringToolToBlocks.put(GatheringTool.HOE, hoeBlocks);
    }

    private static void initBlockToIngredientMap() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredient.WOODCUTTING_LOG);
        ingredients.add(Ingredient.WOODCUTTING_PLANK);
        blockToIngredients.put(Material.ACACIA_WOOD, ingredients);
        blockToIngredients.put(Material.BIRCH_WOOD, ingredients);
        blockToIngredients.put(Material.DARK_OAK_WOOD, ingredients);
        blockToIngredients.put(Material.JUNGLE_WOOD, ingredients);
        blockToIngredients.put(Material.OAK_WOOD, ingredients);
        blockToIngredients.put(Material.SPRUCE_WOOD, ingredients);
        blockToIngredients.put(Material.ACACIA_LOG, ingredients);
        blockToIngredients.put(Material.BIRCH_LOG, ingredients);
        blockToIngredients.put(Material.DARK_OAK_LOG, ingredients);
        blockToIngredients.put(Material.JUNGLE_LOG, ingredients);
        blockToIngredients.put(Material.OAK_LOG, ingredients);
        blockToIngredients.put(Material.SPRUCE_LOG, ingredients);

        ingredients = new ArrayList<>();
        ingredients.add(Ingredient.HARVESTING_ROSE);
        ingredients.add(Ingredient.HARVESTING_STRING);
        ingredients.add(Ingredient.HARVESTING_CHERRY);
        ingredients.add(Ingredient.HARVESTING_SILK);
        ingredients.add(Ingredient.HARVESTING_PLUM_FLOWER);
        ingredients.add(Ingredient.HARVESTING_SOFT_SILK);
        blockToIngredients.put(Material.DANDELION, ingredients);
        blockToIngredients.put(Material.POPPY, ingredients);
        blockToIngredients.put(Material.BLUE_ORCHID, ingredients);
        blockToIngredients.put(Material.ALLIUM, ingredients);
        blockToIngredients.put(Material.AZURE_BLUET, ingredients);
        blockToIngredients.put(Material.RED_TULIP, ingredients);
        blockToIngredients.put(Material.ORANGE_TULIP, ingredients);
        blockToIngredients.put(Material.WHITE_TULIP, ingredients);
        blockToIngredients.put(Material.PINK_TULIP, ingredients);
        blockToIngredients.put(Material.OXEYE_DAISY, ingredients);
        blockToIngredients.put(Material.CORNFLOWER, ingredients);
        blockToIngredients.put(Material.LILY_OF_THE_VALLEY, ingredients);
        blockToIngredients.put(Material.WITHER_ROSE, ingredients);
        blockToIngredients.put(Material.SUNFLOWER, ingredients);
        blockToIngredients.put(Material.LILAC, ingredients);
        blockToIngredients.put(Material.ROSE_BUSH, ingredients);
        blockToIngredients.put(Material.PEONY, ingredients);

        ingredients = new ArrayList<>();
        ingredients.add(Ingredient.MINING_JEWEL_JADE);
        blockToIngredients.put(Material.EMERALD_ORE, ingredients);

        ingredients = new ArrayList<>();
        ingredients.add(Ingredient.MINING_ORE_COPPER);
        blockToIngredients.put(Material.COAL_ORE, ingredients);

        ingredients = new ArrayList<>();
        ingredients.add(Ingredient.MINING_ORE_IRON);
        ingredients.add(Ingredient.MINING_ORE_STEEL);
        blockToIngredients.put(Material.IRON_ORE, ingredients);

        ingredients = new ArrayList<>();
        ingredients.add(Ingredient.MINING_ORE_DIAMOND);
        ingredients.add(Ingredient.MINING_ORE_TITANIUM);
        blockToIngredients.put(Material.DIAMOND_ORE, ingredients);

        ingredients = new ArrayList<>();
        ingredients.add(Ingredient.MINING_JEWEL_GOLD_DUST);
        blockToIngredients.put(Material.GOLD_ORE, ingredients);

        ingredients = new ArrayList<>();
        ingredients.add(Ingredient.MINING_JEWEL_AMETHYST);
        ingredients.add(Ingredient.MINING_JEWEL_SAPPHIRE);
        blockToIngredients.put(Material.LAPIS_ORE, ingredients);

        ingredients = new ArrayList<>();
        ingredients.add(Ingredient.MINING_JEWEL_RUBY);
        blockToIngredients.put(Material.REDSTONE_ORE, ingredients);

    }

    private static void initGatheringTypeToIngredientMap() {
        List<Ingredient> huntingIngredients = new ArrayList<>();
        huntingIngredients.add(Ingredient.HUNTING_LEATHER_WORN);
        huntingIngredients.add(Ingredient.HUNTING_BEEF);
        huntingIngredients.add(Ingredient.HUNTING_LEATHER_HEAVY);
        gatheringTypeToIngredients.put(GatheringType.HUNTING, huntingIngredients);

        List<Ingredient> fishingIngredients = new ArrayList<>();
        fishingIngredients.add(Ingredient.FISHING_COD);
        fishingIngredients.add(Ingredient.FISHING_SALMON);
        gatheringTypeToIngredients.put(GatheringType.FISHING, fishingIngredients);
    }
}
