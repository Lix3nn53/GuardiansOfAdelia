package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GatheringManager {

    private final static HashMap<Integer, Ingredient> ingredientHashMap = new HashMap<>();

    //MINING
    public final static Material gatheringMaterial = Material.STONE_AXE;
    private final static HashMap<GatheringTool, List<Integer>> gatheringToolToCustomModelDatas = new HashMap<>();
    private final static HashMap<Integer, List<Integer>> customModelDataToIngredients = new HashMap<>();
    //MINING ENTITY MANAGER
    private static final HashMap<String, List<GatheringModel>> chunkKeyToGatheringCustomModelData = new HashMap<>();

    //OTHER
    private final static HashMap<GatheringType, List<Integer>> gatheringTypeToIngredients = new HashMap<>();

    public static GatheringModel getGatheringModelFromArmorStand(ArmorStand armorStand) {
        for (String key : chunkKeyToGatheringCustomModelData.keySet()) {
            List<GatheringModel> gatheringModels = chunkKeyToGatheringCustomModelData.get(key);
            for (GatheringModel gatheringModel : gatheringModels) {
                if (gatheringModel.getArmorStand() != null) {
                    if (gatheringModel.getArmorStand().equals(armorStand)) {
                        return gatheringModel;
                    }
                }
            }
        }
        return null;
    }

    public static void onChunkLoad(String chunkKey) {
        if (chunkKeyToGatheringCustomModelData.containsKey(chunkKey)) {
            List<GatheringModel> gatheringModels = chunkKeyToGatheringCustomModelData.get(chunkKey);
            for (GatheringModel gatheringModel : gatheringModels) {
                gatheringModel.createModel();
            }
        }
    }

    public static void startGathering(Player player, ItemStack itemInHand, GatheringModel gatheringModel) {
        if (gatheringModel.isBeingGathered()) {
            return;
        }
        if (gatheringModel.isOnCooldown()) {
            return;
        }

        GatheringTool gatheringTool = GatheringTool.materialToGatheringTool(itemInHand.getType());

        if (gatheringTool == null) return;

        if (gatheringToolToCustomModelDatas.containsKey(gatheringTool)) {
            List<Integer> customModelDatas = gatheringToolToCustomModelDatas.get(gatheringTool);

            int customModelData = gatheringModel.getCustomModelData();
            if (customModelDatas.contains(customModelData)) {
                startGatheringAnimation(player, gatheringTool, itemInHand, gatheringModel);
            }
        }
    }

    private static ItemStack finishGathering(Player player, ItemStack itemInHand, GatheringTool gatheringTool, int customModelData) {
        decreaseDurability(player, itemInHand);

        List<Ingredient> ingredients = getIngredients(gatheringTool, customModelData);

        if (ingredients == null) return null;

        return getGathered(player, ingredients);
    }

    public static ItemStack finishGathering(Player player, ItemStack itemInHand, GatheringType gatheringType) {
        decreaseDurability(player, itemInHand);

        List<Ingredient> ingredients = getIngredients(gatheringType);

        if (ingredients == null) return null;

        return getGathered(player, ingredients);
    }

    private static List<Ingredient> getIngredients(GatheringTool gatheringTool, int customModelData) {
        if (gatheringToolToCustomModelDatas.containsKey(gatheringTool)) {
            List<Integer> customModelDatas = gatheringToolToCustomModelDatas.get(gatheringTool);

            if (customModelDatas.contains(customModelData)) {
                List<Integer> integers = customModelDataToIngredients.get(customModelData);

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

            player.sendTitle("", ChatColor.GREEN + "Obtained " +
                    ChatColor.YELLOW + ingItemStack.getAmount() + "x " + ingItemStack.getItemMeta().getDisplayName(), 30, 80, 30);

            progressGatheringTasks(player, ingredient, amount);

            return ingItemStack;
        } else {
            player.sendTitle("", ChatColor.RED + "Gathering Failed", 30, 80, 30);
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

    private static void startGatheringAnimation(final Player player, GatheringTool gatheringTool, ItemStack itemStackTool, GatheringModel gatheringModel) {
        if (GuardianDataManager.hasGuardianData(player.getUniqueId())) {
            final GuardianData guardianData = GuardianDataManager.getGuardianData(player.getUniqueId());
            if (guardianData.isFreeToAct()) {
                guardianData.setGathering(true);
                gatheringModel.setBeingGathered(true);

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

                        ArmorStand armorStand = gatheringModel.getArmorStand();
                        if (secsRun == 0) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatColor.RED + "Gathering has been canceled because you moved.");
                                gatheringModel.resetName();
                                gatheringModel.setBeingGathered(false);
                            } else {
                                armorStand.setCustomName(ChatColor.YELLOW + "||||||||||||||||");
                                CustomSound customSound = new CustomSound(Sound.BLOCK_STONE_HIT, 0.5f, 1f);
                                customSound.play(player.getLocation());
                            }
                        } else if (secsRun == 1) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatColor.RED + "Gathering has been canceled because you moved.");
                                gatheringModel.resetName();
                                gatheringModel.setBeingGathered(false);
                            } else {
                                armorStand.setCustomName(ChatColor.GREEN + "||||" + ChatColor.YELLOW + "||||||||||||");
                                CustomSound customSound = new CustomSound(Sound.BLOCK_STONE_HIT, 0.5f, 1f);
                                customSound.play(player.getLocation());

                                PacketPlayOutAnimation animation = new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 0);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(animation);
                            }
                        } else if (secsRun == 2) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatColor.RED + "Gathering has been canceled because you moved.");
                                gatheringModel.resetName();
                                gatheringModel.setBeingGathered(false);
                            } else {
                                armorStand.setCustomName(ChatColor.GREEN + "||||||||" + ChatColor.YELLOW + "||||||||");
                                CustomSound customSound = new CustomSound(Sound.BLOCK_STONE_HIT, 0.5f, 1f);
                                customSound.play(player.getLocation());

                                PacketPlayOutAnimation animation = new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 0);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(animation);
                            }
                        } else if (secsRun == 3) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatColor.RED + "Gathering has been canceled because you moved.");
                                gatheringModel.resetName();
                                gatheringModel.setBeingGathered(false);
                            } else {
                                armorStand.setCustomName(ChatColor.GREEN + "||||||||||||" + ChatColor.YELLOW + "||||");
                                CustomSound customSound = new CustomSound(Sound.BLOCK_STONE_HIT, 0.5f, 1f);
                                customSound.play(player.getLocation());

                                PacketPlayOutAnimation animation = new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 0);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(animation);
                            }
                        } else if (secsRun == 4) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatColor.RED + "Gathering has been canceled because you moved.");
                                gatheringModel.resetName();
                                gatheringModel.setBeingGathered(false);
                            } else {
                                armorStand.setCustomName(ChatColor.GREEN + "||||||||||||||||");
                                CustomSound customSound = new CustomSound(Sound.BLOCK_STONE_HIT, 0.5f, 1f);
                                customSound.play(player.getLocation());

                                PacketPlayOutAnimation animation = new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 0);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(animation);
                            }
                        } else if (secsRun == 5) {
                            cancel();
                            int customModelData = gatheringModel.getCustomModelData();
                            ItemStack ingredient = finishGathering(player, itemStackTool, gatheringTool, customModelData);
                            if (ingredient != null) {
                                InventoryUtils.giveItemToPlayer(player, ingredient);
                            }
                            guardianData.setGathering(false);
                            gatheringModel.onLoot();
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

    public static void putToolToCustomModelData(GatheringTool gatheringTool, int customModelData) {
        List<Integer> customModelDatas = new ArrayList<>();
        if (gatheringToolToCustomModelDatas.containsKey(gatheringTool)) {
            customModelDatas = gatheringToolToCustomModelDatas.get(gatheringTool);
        }
        customModelDatas.add(customModelData);
        gatheringToolToCustomModelDatas.put(gatheringTool, customModelDatas);
    }

    public static void putCustomModelDataToIngredient(int customModelData, int ingredient) {
        List<Integer> ingredients = new ArrayList<>();
        if (customModelDataToIngredients.containsKey(customModelData)) {
            ingredients = customModelDataToIngredients.get(customModelData);
        }
        ingredients.add(ingredient);
        customModelDataToIngredients.put(customModelData, ingredients);
    }

    public static void putGatheringTypeToIngredient(GatheringType gatheringType, int ingredient) {
        List<Integer> ingredients = new ArrayList<>();
        if (gatheringTypeToIngredients.containsKey(gatheringType)) {
            ingredients = gatheringTypeToIngredients.get(gatheringType);
        }
        ingredients.add(ingredient);
        gatheringTypeToIngredients.put(gatheringType, ingredients);
    }

    public static void putGatheringModel(GatheringModel gatheringModel) {
        String chunkKey = LocationUtils.getChunkKey(gatheringModel.getBaseLocation());
        if (chunkKeyToGatheringCustomModelData.containsKey(chunkKey)) {
            List<GatheringModel> gatheringModels = chunkKeyToGatheringCustomModelData.get(chunkKey);
            gatheringModels.add(gatheringModel);
            chunkKeyToGatheringCustomModelData.put(chunkKey, gatheringModels);
        } else {
            List<GatheringModel> gatheringModels = new ArrayList<>();
            gatheringModels.add(gatheringModel);
            chunkKeyToGatheringCustomModelData.put(chunkKey, gatheringModels);
        }
    }
}
