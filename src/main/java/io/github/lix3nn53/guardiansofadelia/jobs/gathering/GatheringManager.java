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
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GatheringManager {

    private final static HashMap<Integer, Ingredient> ingredientHashMap = new HashMap<>();

    // GATHERING MODELS
    private final static HashMap<Integer, GatheringModelData> modelIdToModelData = new HashMap<>();
    private static final HashMap<String, List<GatheringModelState>> chunkKeyToGatheringModels = new HashMap<>();

    // GATHERING FROM MOB KILL
    private final static HashMap<String, List<Integer>> mobKeyToIngredients = new HashMap<>();

    public static GatheringModelState getGatheringModelFromArmorStand(ArmorStand armorStand) {
        String chunkKey = LocationUtils.getChunkKey(armorStand.getLocation());

        if (chunkKeyToGatheringModels.containsKey(chunkKey)) {
            List<GatheringModelState> gatheringModelStates = chunkKeyToGatheringModels.get(chunkKey);
            for (GatheringModelState gatheringModelState : gatheringModelStates) {
                if (gatheringModelState.getArmorStand() != null) {
                    if (gatheringModelState.getArmorStand().equals(armorStand)) {
                        return gatheringModelState;
                    }
                }
            }
        }

        return null;
    }

    public static void onChunkLoad(String chunkKey) {
        if (chunkKeyToGatheringModels.containsKey(chunkKey)) {
            List<GatheringModelState> gatheringModelStates = chunkKeyToGatheringModels.get(chunkKey);
            for (GatheringModelState gatheringModelState : gatheringModelStates) {
                int id = gatheringModelState.getId();
                GatheringModelData gatheringModelData = modelIdToModelData.get(id);
                gatheringModelState.createModel(gatheringModelData);
            }
        }
    }

    public static void startGathering(Player player, ItemStack itemInHand, GatheringModelState gatheringModelState) {
        if (!canStartGathering(player, itemInHand, gatheringModelState)) return;

        List<Ingredient> ingredients = getIngredients(gatheringModelState);

        startGatheringAnimation(player, itemInHand, gatheringModelState, ingredients);
    }

    public static List<Ingredient> getIngredients(GatheringModelState gatheringModelState) {
        int id = gatheringModelState.getId();
        GatheringModelData gatheringModelData = modelIdToModelData.get(id);
        List<Integer> ingredientIds = gatheringModelData.getIngredients();
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i : ingredientIds) {
            Ingredient ingredient = ingredientHashMap.get(i);
            ingredients.add(ingredient);
        }

        return ingredients;
    }

    public static boolean canStartGathering(Player player, ItemStack itemInHand, GatheringModelState gatheringModelState) {
        if (gatheringModelState.isBeingGathered()) {
            // player.sendMessage(ChatColor.RED + "Resource is being gathered by another player");
            return false;
        }
        if (gatheringModelState.isOnCooldown()) {
            player.sendMessage(ChatColor.RED + "Resource is on cooldown");
            return false;
        }
        GatheringToolType gatheringToolType = GatheringToolType.materialToGatheringTool(itemInHand.getType());

        int id = gatheringModelState.getId();
        GatheringModelData gatheringModelData = modelIdToModelData.get(id);
        GatheringToolTier modelToolTier = gatheringModelData.getMinGatheringToolTier();
        GatheringToolType modelToolType = gatheringModelData.getGatheringToolType();

        final String error = ChatColor.RED + "Required gathering tool: " + modelToolTier.toString() + " " + modelToolType.toString();
        if (gatheringToolType == null) {
            player.sendMessage(error);
            return false;
        }

        if (!gatheringToolType.equals(modelToolType)) {
            player.sendMessage(error);
            return false;
        }

        if (!PersistentDataContainerUtil.hasString(itemInHand, "toolTier")) {
            player.sendMessage(ChatColor.RED + "toolTier error report to admin");
            return false;
        }

        String toolTierStr = PersistentDataContainerUtil.getString(itemInHand, "toolTier");
        GatheringToolTier gatheringToolTier = GatheringToolTier.valueOf(toolTierStr);

        if (gatheringToolTier.compareTo(modelToolTier) < 0) {
            player.sendMessage(error);
            return false;
        }

        return true;
    }

    public static ItemStack finishGathering(Player player, ItemStack itemInHand, List<Ingredient> ingredients) {
        decreaseDurability(player, itemInHand);

        return getGathered(player, ingredients);
    }

    private static ItemStack getGathered(Player player, List<Ingredient> ingredients) {
        Random random = new Random();

        int i = random.nextInt(ingredients.size());
        Ingredient ingredient = ingredients.get(i);

        int maxAmountPerGather = ingredient.getMaxAmountPerGather();
        int amount = random.nextInt(maxAmountPerGather) + 1;
        ItemStack ingredientItem = ingredient.getItemStack(amount);

        player.sendTitle("", ChatColor.GREEN + "Obtained " +
                ChatColor.GOLD + ingredientItem.getAmount() + "x " + ChatColor.YELLOW + ingredientItem.getItemMeta().getDisplayName(), 30, 80, 30);

        progressGatheringTasks(player, ingredient, amount);

        return ingredientItem;
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

    private static void startGatheringAnimation(final Player player, ItemStack itemStackTool, GatheringModelState gatheringModelState, List<Ingredient> ingredients) {
        if (GuardianDataManager.hasGuardianData(player)) {
            final GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.isFreeToAct()) {
                guardianData.setGathering(true);
                gatheringModelState.setBeingGathered(true);

                final double startPosX = player.getLocation().getX();
                final double startPosY = player.getLocation().getY();
                final double startPosZ = player.getLocation().getZ();

                long period = 20;
                if (BoostPremiumManager.isBoostActive(BoostPremium.GATHER)) {
                    period = period / 2;
                }

                int id = gatheringModelState.getId();
                GatheringModelData gatheringModelData = modelIdToModelData.get(id);
                new BukkitRunnable() {

                    // We don't want the task to run indefinitely
                    int secsRun;

                    @Override
                    public void run() {
                        double differenceX = Math.abs(startPosX - player.getLocation().getX());
                        double differenceY = Math.abs(startPosY - player.getLocation().getY());
                        double differenceZ = Math.abs(startPosZ - player.getLocation().getZ());

                        ArmorStand armorStand = gatheringModelState.getArmorStand();
                        if (secsRun == 0) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatColor.RED + "Gathering has been canceled because you moved.");
                                gatheringModelState.resetName(gatheringModelData);
                                gatheringModelState.setBeingGathered(false);
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
                                gatheringModelState.resetName(gatheringModelData);
                                gatheringModelState.setBeingGathered(false);
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
                                gatheringModelState.resetName(gatheringModelData);
                                gatheringModelState.setBeingGathered(false);
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
                                gatheringModelState.resetName(gatheringModelData);
                                gatheringModelState.setBeingGathered(false);
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
                                gatheringModelState.resetName(gatheringModelData);
                                gatheringModelState.setBeingGathered(false);
                            } else {
                                armorStand.setCustomName(ChatColor.GREEN + "||||||||||||||||");
                                CustomSound customSound = new CustomSound(Sound.BLOCK_STONE_HIT, 0.5f, 1f);
                                customSound.play(player.getLocation());

                                PacketPlayOutAnimation animation = new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 0);
                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(animation);
                            }
                        } else if (secsRun == 5) {
                            cancel();
                            ItemStack ingredient = finishGathering(player, itemStackTool, ingredients);
                            if (ingredient != null) {
                                InventoryUtils.giveItemToPlayer(player, ingredient);
                            }
                            guardianData.setGathering(false);
                            gatheringModelState.onLoot(gatheringModelData);
                        }
                        secsRun++;
                    }
                }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, period);
            }
        }
    }

    private static void progressGatheringTasks(Player player, Ingredient ingredient, int amount) {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
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

    public static void putGatheringModelData(int id, GatheringModelData gatheringModelData) {
        modelIdToModelData.put(id, gatheringModelData);
    }

    public static void putGatheringModelState(GatheringModelState gatheringModelState) {
        String chunkKey = LocationUtils.getChunkKey(gatheringModelState.getBaseLocation());
        List<GatheringModelState> gatheringModelStates;
        if (chunkKeyToGatheringModels.containsKey(chunkKey)) {
            gatheringModelStates = chunkKeyToGatheringModels.get(chunkKey);
        } else {
            gatheringModelStates = new ArrayList<>();
        }
        gatheringModelStates.add(gatheringModelState);
        chunkKeyToGatheringModels.put(chunkKey, gatheringModelStates);
    }

    public static void putMobKeyToIngredient(String mobKey, int ingredient) {
        List<Integer> ingredients = new ArrayList<>();
        if (mobKeyToIngredients.containsKey(mobKey)) {
            ingredients = mobKeyToIngredients.get(mobKey);
        }
        ingredients.add(ingredient);
        mobKeyToIngredients.put(mobKey, ingredients);
    }

    public static boolean dropsIngredient(String mobKey) {
        return mobKeyToIngredients.containsKey(mobKey);
    }

    public static ItemStack triggerIngredientDrop(String internalName) {
        if (mobKeyToIngredients.containsKey(internalName)) {
            double dropRandom = Math.random();

            double chance = 0.24;
            if (dropRandom <= chance) {
                List<Integer> ingredients = mobKeyToIngredients.get(internalName);

                Random random = new Random();
                int i = random.nextInt(ingredients.size());
                int ingredientNo = ingredients.get(i);

                Ingredient ingredient = ingredientHashMap.get(ingredientNo);

                int amount = random.nextInt(3) + 1;

                return ingredient.getItemStack(amount);
            }
        }

        return null;
    }

    public static HashMap<Integer, GatheringModelData> getModelIdToModelData() {
        return modelIdToModelData;
    }

    public static HashMap<String, List<GatheringModelState>> getChunkKeyToGatheringModels() {
        return chunkKeyToGatheringModels;
    }
}
