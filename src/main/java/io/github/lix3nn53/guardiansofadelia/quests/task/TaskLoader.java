package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.items.config.ItemReferenceLoader;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.Ingredient;
import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.quests.actions.ActionLoader;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.config.ConfigurationUtils;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TaskLoader {

    public static Task load(ConfigurationSection configurationSection) {
        String componentType = configurationSection.getString("taskType");

        if (componentType == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "NULL TASK TYPE");
            return null;
        }

        Task task = null;
        if (componentType.equals(TaskCollect.class.getSimpleName())) {
            List<String> keyOfMobsItemDropsFrom = configurationSection.getStringList("keyOfMobsItemDropsFrom");

            List<String> nameOfMobsItemDropsFrom = new ArrayList<>();
            for (String internalName : keyOfMobsItemDropsFrom) {
                MythicMob mythicMob = MythicBukkit.inst().getMobManager().getMythicMob(internalName);
                if (mythicMob == null) {
                    GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "TaskCollect mythicMob null: " + internalName);

                    return null;
                }
                String displayName = mythicMob.getDisplayName().get();
                GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.GREEN_DARK + "TaskCollect MM: " + internalName + "-" + displayName);

                nameOfMobsItemDropsFrom.add(internalName);
            }

            float chance = 0;
            if (!nameOfMobsItemDropsFrom.isEmpty()) {
                chance = (float) configurationSection.getDouble("chance");
            }
            ConfigurationSection itemDrop = configurationSection.getConfigurationSection("itemDrop");
            ItemStack questItem = ItemReferenceLoader.loadItemReference(itemDrop);
            int amountNeeded = configurationSection.getInt("amountNeeded");
            boolean removeOnTurnIn = configurationSection.getBoolean("removeOnTurnIn");

            task = new TaskCollect(nameOfMobsItemDropsFrom, chance, questItem, amountNeeded, removeOnTurnIn);
        } else if (componentType.equals(TaskCrafting.class.getSimpleName())) {
            int amountNeeded = configurationSection.getInt("amountNeeded");
            CraftingType craftingType = CraftingType.valueOf(configurationSection.getString("craftingType"));
            int minCraftingLevel = configurationSection.getInt("minCraftingLevel");
            String itemNameContains = configurationSection.contains("itemNameContains") ? configurationSection.getString("itemNameContains") : null;

            task = new TaskCrafting(craftingType, minCraftingLevel, itemNameContains, amountNeeded);
        } else if (componentType.equals(TaskDealDamage.class.getSimpleName())) {
            String internalName = configurationSection.getString("mobKey");

            MythicMob mythicMob = MythicBukkit.inst().getMobManager().getMythicMob(internalName);
            if (mythicMob == null) {
                GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "TaskDealDamage mythicMob null: " + internalName);

                return null;
            }
            String displayName = mythicMob.getDisplayName().get();
            GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.GREEN_DARK + "TaskDealDamage MM: " + internalName + "-" + displayName);

            int damageNeeded = configurationSection.getInt("damageNeeded");

            task = new TaskDealDamage(internalName, damageNeeded);
        } else if (componentType.equals(TaskGathering.class.getSimpleName())) {
            int ingredientIndex = configurationSection.getInt("ingredientIndex");
            Ingredient ingredient = GatheringManager.getIngredient(ingredientIndex);

            int amountNeeded = configurationSection.getInt("amountNeeded");

            task = new TaskGathering(ingredient, amountNeeded);
        } else if (componentType.equals(TaskGift.class.getSimpleName())) {
            String internalName = configurationSection.getString("mobKey");

            MythicMob mythicMob = MythicBukkit.inst().getMobManager().getMythicMob(internalName);
            if (mythicMob == null) {
                GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "TaskGift mythicMob null: " + internalName);

                return null;
            }
            String displayName = mythicMob.getDisplayName().get();
            GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.GREEN_DARK + "TaskGift MM: " + internalName + "-" + displayName);

            ItemStack itemStack = ItemReferenceLoader.loadItemReference(configurationSection.getConfigurationSection("item"));

            int amountNeeded = configurationSection.getInt("amountNeeded");

            task = new TaskGift(amountNeeded, itemStack, internalName);
        } else if (componentType.equals(TaskInteract.class.getSimpleName())) {
            int npcId = configurationSection.getInt("npcId");

            task = new TaskInteract(npcId);
        } else if (componentType.equals(TaskKill.class.getSimpleName())) {
            String internalName = configurationSection.getString("mobKey");

            MythicMob mythicMob = MythicBukkit.inst().getMobManager().getMythicMob(internalName);
            if (mythicMob == null) {
                GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "TaskKill mythicMob null: " + internalName);

                return null;
            }
            String displayName = mythicMob.getDisplayName().get();
            GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.GREEN_DARK + "TaskKill MM: " + internalName + "-" + displayName);

            int amountNeeded = configurationSection.getInt("amountNeeded");

            task = new TaskKill(internalName, amountNeeded);
        } else if (componentType.equals(TaskReach.class.getSimpleName())) {
            World world = Bukkit.getWorld(configurationSection.getString("world"));

            float x = (float) configurationSection.getDouble("x");
            float y = (float) configurationSection.getDouble("y");
            float z = (float) configurationSection.getDouble("z");

            Location location = new Location(world, x, y, z);

            Material blockMaterial = Material.valueOf(configurationSection.getString("blockMaterial"));

            task = new TaskReach(location, blockMaterial);
        }

        if (task == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "NO SUCH TASK IN LOADER");
            return null;
        }

        List<Action> onTaskCompleteActions = getOnTaskCompleteActions(configurationSection);
        for (Action action : onTaskCompleteActions) {
            task.addOnCompleteAction(action);
        }

        return task;
    }

    private static List<Action> getOnTaskCompleteActions(ConfigurationSection section) {
        List<Action> actions = new ArrayList<>();
        int count = ConfigurationUtils.getChildComponentCount(section, "onTaskCompleteAction");
        boolean hasOnePreventTaskCompilation = false;
        for (int c = 1; c <= count; c++) {
            Action action = ActionLoader.load(section.getConfigurationSection("onTaskCompleteAction" + c));

            boolean b = action.preventTaskCompilation();
            if (b) {
                if (!hasOnePreventTaskCompilation) {
                    hasOnePreventTaskCompilation = true;
                } else {
                    GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "ERROR WHILE LOADING QUEST TASK: ");
                    GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "Reason: A TASK can only have one action that prevents compilation");
                    GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "Section: " + section);
                }
            }
            actions.add(action);
        }

        return actions;
    }

}
