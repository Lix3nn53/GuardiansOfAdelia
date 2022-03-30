package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.ToolSlot;
import io.github.lix3nn53.guardiansofadelia.sounds.CustomSound;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.LocationUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public static void startGathering(Player player, GatheringModelState gatheringModelState) {
        if (!canStartGathering(player, gatheringModelState)) return;

        List<Ingredient> ingredients = getIngredients(gatheringModelState);

        startGatheringAnimation(player, gatheringModelState, ingredients);
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

    public static boolean canStartGathering(Player player, GatheringModelState gatheringModelState) {
        if (gatheringModelState.isBeingGathered()) {
            // player.sendMessage(ChatPalette.RED + "Resource is being gathered by another player");
            return false;
        }
        if (gatheringModelState.isOnCooldown()) {
            player.sendMessage(ChatPalette.RED + "Resource is on cooldown");
            return false;
        }

        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
        if (guardianData == null) {
            return false;
        }
        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
        RPGInventory rpgInventory = activeCharacter.getRpgInventory();


        int id = gatheringModelState.getId();
        GatheringModelData gatheringModelData = modelIdToModelData.get(id);
        GatheringToolTier modelToolTier = gatheringModelData.getMinGatheringToolTier();
        GatheringToolType modelToolType = gatheringModelData.getGatheringToolType();

        ToolSlot toolSlot = rpgInventory.getToolSlot(modelToolType);
        ItemStack itemOnSlot = toolSlot.getItemOnSlot();

        GatheringToolType gatheringToolType = null;
        if (!InventoryUtils.isAirOrNull(itemOnSlot)) {
            gatheringToolType = GatheringToolType.materialToGatheringTool(itemOnSlot.getType());
        }

        final String wrongToolError = ChatPalette.RED + "Required gathering tool: " + modelToolTier.toString() + " " + modelToolType;
        if (gatheringToolType == null) {
            player.sendMessage(wrongToolError);
            return false;
        }

        if (!gatheringToolType.equals(modelToolType)) {
            player.sendMessage(wrongToolError);
            return false;
        }

        if (!PersistentDataContainerUtil.hasString(itemOnSlot, "toolTier")) {
            player.sendMessage(ChatPalette.RED + "toolTier error report to admin");
            return false;
        }

        String toolTierStr = PersistentDataContainerUtil.getString(itemOnSlot, "toolTier");
        GatheringToolTier gatheringToolTier = GatheringToolTier.valueOf(toolTierStr);

        if (gatheringToolTier.compareTo(modelToolTier) < 0) {
            player.sendMessage(wrongToolError);
            return false;
        }

        return true;
    }

    public static boolean canStartFishing(Player player, ItemStack itemInHand, GatheringModelState gatheringModelState) {
        if (gatheringModelState.isBeingGathered()) {
            // player.sendMessage(ChatPalette.RED + "Resource is being gathered by another player");
            return false;
        }
        if (gatheringModelState.isOnCooldown()) {
            player.sendMessage(ChatPalette.RED + "Resource is on cooldown");
            return false;
        }

        GatheringToolType gatheringToolType = null;
        if (!InventoryUtils.isAirOrNull(itemInHand)) {
            gatheringToolType = GatheringToolType.materialToGatheringTool(itemInHand.getType());
        }

        int id = gatheringModelState.getId();
        GatheringModelData gatheringModelData = modelIdToModelData.get(id);
        GatheringToolTier modelToolTier = gatheringModelData.getMinGatheringToolTier();
        GatheringToolType modelToolType = gatheringModelData.getGatheringToolType();

        final String wrongToolError = ChatPalette.RED + "Required gathering tool: " + modelToolTier.toString() + " " + modelToolType.toString();
        if (gatheringToolType == null) {
            player.sendMessage(wrongToolError);
            return false;
        }

        if (!gatheringToolType.equals(modelToolType)) {
            player.sendMessage(wrongToolError);
            return false;
        }

        if (!PersistentDataContainerUtil.hasString(itemInHand, "toolTier")) {
            player.sendMessage(ChatPalette.RED + "toolTier error report to admin");
            return false;
        }

        String toolTierStr = PersistentDataContainerUtil.getString(itemInHand, "toolTier");
        GatheringToolTier gatheringToolTier = GatheringToolTier.valueOf(toolTierStr);

        if (gatheringToolTier.compareTo(modelToolTier) < 0) {
            player.sendMessage(wrongToolError);
            return false;
        }

        return true;
    }

    public static ItemStack finishGathering(Player player, ItemStack itemInHand, List<Ingredient> ingredients) {
        decreaseDurability(player, itemInHand);

        return getGathered(player, ingredients);
    }

    private static ItemStack getGathered(Player player, List<Ingredient> ingredients) {
        int i = GuardiansOfAdelia.RANDOM.nextInt(ingredients.size());
        Ingredient ingredient = ingredients.get(i);

        int gather = ingredient.gather(false);
        if (gather == 0) {
            player.sendTitle(ChatColor.WHITE + "", ChatPalette.RED + "Failed...", 30, 80, 30);
            return null;
        }

        ItemStack ingredientItem = ingredient.getItemStack(gather);

        player.sendTitle(ChatColor.WHITE + "", ChatPalette.GREEN_DARK + "Obtained " +
                ChatPalette.GOLD + ingredientItem.getAmount() + "x " + ChatPalette.GOLD + ingredientItem.getItemMeta().getDisplayName(), 30, 80, 30);

        progressGatheringTasks(player, ingredient, gather);

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
                player.sendMessage(ChatPalette.RED + "Your gathering tool is broken");
            }
        }
    }

    public static Ingredient getIngredient(int i) {
        return ingredientHashMap.get(i);
    }

    private static void startGatheringAnimation(final Player player, GatheringModelState gatheringModelState, List<Ingredient> ingredients) {
        if (GuardianDataManager.hasGuardianData(player)) {
            final GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.isFreeToAct()) {
                guardianData.setGathering(true);
                gatheringModelState.setBeingGathered(true);

                final float startPosX = (float) player.getLocation().getX();
                final float startPosY = (float) player.getLocation().getY();
                final float startPosZ = (float) player.getLocation().getZ();

                int period = 16; // ticks to wait between each step
                if (BoostPremiumManager.isBoostActive(BoostPremium.GATHER)) {
                    period = (int) (BoostPremium.GATHER.applyTo(period) + 0.5);
                }

                int id = gatheringModelState.getId();
                GatheringModelData gatheringModelData = modelIdToModelData.get(id);
                new BukkitRunnable() {

                    // We don't want the task to run indefinitely
                    int secsRun;

                    @Override
                    public void run() {
                        float differenceX = Math.abs(startPosX - (float) player.getLocation().getX());
                        float differenceY = Math.abs(startPosY - (float) player.getLocation().getY());
                        float differenceZ = Math.abs(startPosZ - (float) player.getLocation().getZ());

                        ArmorStand armorStand = gatheringModelState.getArmorStand();
                        if (secsRun == 0) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatPalette.RED + "Gathering has been canceled because you moved.");
                                gatheringModelState.resetName(gatheringModelData);
                                gatheringModelState.setBeingGathered(false);
                            } else {
                                armorStand.setCustomName(ChatPalette.GOLD + "||||||||||||||||");
                                CustomSound customSound = new CustomSound(Sound.BLOCK_STONE_HIT, 0.5f, 1f);
                                customSound.play(player.getLocation());
                            }
                        } else if (secsRun == 1) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatPalette.RED + "Gathering has been canceled because you moved.");
                                gatheringModelState.resetName(gatheringModelData);
                                gatheringModelState.setBeingGathered(false);
                            } else {
                                armorStand.setCustomName(ChatPalette.GREEN_DARK + "||||" + ChatPalette.GOLD + "||||||||||||");
                                CustomSound customSound = new CustomSound(Sound.BLOCK_STONE_HIT, 0.5f, 1f);
                                customSound.play(player.getLocation());

                                //PacketPlayOutAnimation animation = new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 0);
                                //((CraftPlayer) player).getHandle().b.a(animation);
                            }
                        } else if (secsRun == 2) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatPalette.RED + "Gathering has been canceled because you moved.");
                                gatheringModelState.resetName(gatheringModelData);
                                gatheringModelState.setBeingGathered(false);
                            } else {
                                armorStand.setCustomName(ChatPalette.GREEN_DARK + "||||||||" + ChatPalette.GOLD + "||||||||");
                                CustomSound customSound = new CustomSound(Sound.BLOCK_STONE_HIT, 0.5f, 1f);
                                customSound.play(player.getLocation());

                                //PacketPlayOutAnimation animation = new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 0);
                                //((CraftPlayer) player).getHandle().b.a(animation);
                            }
                        } else if (secsRun == 3) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatPalette.RED + "Gathering has been canceled because you moved.");
                                gatheringModelState.resetName(gatheringModelData);
                                gatheringModelState.setBeingGathered(false);
                            } else {
                                armorStand.setCustomName(ChatPalette.GREEN_DARK + "||||||||||||" + ChatPalette.GOLD + "||||");
                                CustomSound customSound = new CustomSound(Sound.BLOCK_STONE_HIT, 0.5f, 1f);
                                customSound.play(player.getLocation());

                                //PacketPlayOutAnimation animation = new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 0);
                                //((CraftPlayer) player).getHandle().b.a(animation);
                            }
                        } else if (secsRun == 4) {
                            if (differenceX > 1 || differenceY > 1 || differenceZ > 1) {
                                guardianData.setGathering(false);
                                cancel();
                                player.sendMessage(ChatPalette.RED + "Gathering has been canceled because you moved.");
                                gatheringModelState.resetName(gatheringModelData);
                                gatheringModelState.setBeingGathered(false);
                            } else {
                                armorStand.setCustomName(ChatPalette.GREEN_DARK + "||||||||||||||||");
                                CustomSound customSound = new CustomSound(Sound.BLOCK_STONE_HIT, 0.5f, 1f);
                                customSound.play(player.getLocation());

                                //PacketPlayOutAnimation animation = new PacketPlayOutAnimation(((CraftPlayer) player).getHandle(), 0);
                                //((CraftPlayer) player).getHandle().b.a(animation);
                            }
                        } else if (secsRun == 5) {
                            cancel();
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            RPGInventory rpgInventory = activeCharacter.getRpgInventory();
                            int id = gatheringModelState.getId();
                            GatheringModelData gatheringModelData = modelIdToModelData.get(id);
                            ToolSlot toolSlot = rpgInventory.getToolSlot(gatheringModelData.getGatheringToolType());
                            ItemStack itemOnSlot = toolSlot.getItemOnSlot();

                            ItemStack ingredient = finishGathering(player, itemOnSlot, ingredients);
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

    public static ItemStack triggerIngredientDrop(String internalName, boolean isDungeon) {
        if (mobKeyToIngredients.containsKey(internalName)) {
            List<Integer> ingredients = mobKeyToIngredients.get(internalName);

            int ingredientNo = ingredients.get(0);

            if (ingredients.size() > 1) {
                int i = GuardiansOfAdelia.RANDOM.nextInt(ingredients.size());
                ingredientNo = ingredients.get(i);
            }

            Ingredient ingredient = ingredientHashMap.get(ingredientNo);

            int gather = ingredient.gather(isDungeon);

            if (gather == 0) {
                return null;
            }

            return ingredient.getItemStack(gather);
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
