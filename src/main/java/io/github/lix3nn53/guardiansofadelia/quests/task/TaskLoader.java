package io.github.lix3nn53.guardiansofadelia.quests.task;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.config.ItemReferenceLoader;
import io.github.lix3nn53.guardiansofadelia.Items.list.QuestItems;
import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntityManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.Ingredient;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TaskLoader {

    public static Task load(ConfigurationSection configurationSection) {
        String componentType = configurationSection.getString("taskType");

        if (componentType == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NULL TASK TYPE");
            return null;
        }

        if (componentType.equals(TaskCollect.class.getSimpleName())) {
            List<String> keyOfMobsItemDropsFrom = configurationSection.getStringList("keyOfMobsItemDropsFrom");

            List<String> nameOfMobsItemDropsFrom = new ArrayList<>();
            for (String key : keyOfMobsItemDropsFrom) {
                String name = AdeliaEntityManager.getEntity(key).getName();

                nameOfMobsItemDropsFrom.add(name);
            }

            double chance = configurationSection.getDouble("chance");
            int questNoOfItem = configurationSection.getInt("questNoOfItem");
            ItemStack questItem = QuestItems.getQuestItem(questNoOfItem);
            int amountNeeded = configurationSection.getInt("amountNeeded");

            return new TaskCollect(nameOfMobsItemDropsFrom, chance, questItem, amountNeeded);
        } else if (componentType.equals(TaskDealDamage.class.getSimpleName())) {
            String mobKey = configurationSection.getString("mobKey");
            String name = AdeliaEntityManager.getEntity(mobKey).getName();

            int damageNeeded = configurationSection.getInt("damageNeeded");

            return new TaskDealDamage(name, damageNeeded);
        } else if (componentType.equals(TaskGathering.class.getSimpleName())) {
            int ingredientIndex = configurationSection.getInt("ingredientIndex");
            Ingredient ingredient = GatheringManager.getIngredient(ingredientIndex);

            int amountNeeded = configurationSection.getInt("amountNeeded");

            return new TaskGathering(ingredient, amountNeeded);
        } else if (componentType.equals(TaskGift.class.getSimpleName())) {
            String mobKey = configurationSection.getString("mobKey");
            String name = AdeliaEntityManager.getEntity(mobKey).getName();

            ItemStack itemStack = ItemReferenceLoader.loadItemReference(configurationSection.getConfigurationSection("item"));

            int amountNeeded = configurationSection.getInt("amountNeeded");

            return new TaskGift(amountNeeded, itemStack, name);
        } else if (componentType.equals(TaskInteract.class.getSimpleName())) {
            int npcId = configurationSection.getInt("npcId");

            return new TaskInteract(npcId);
        } else if (componentType.equals(TaskKill.class.getSimpleName())) {
            String mobKey = configurationSection.getString("mobKey");
            String name = AdeliaEntityManager.getEntity(mobKey).getName();

            int amountNeeded = configurationSection.getInt("amountNeeded");

            return new TaskKill(name, amountNeeded);
        } else if (componentType.equals(TaskReach.class.getSimpleName())) {
            World world = Bukkit.getWorld(configurationSection.getString("world"));

            double x = configurationSection.getDouble(".x");
            double y = configurationSection.getDouble(".y");
            double z = configurationSection.getDouble(".z");

            Location location = new Location(world, x, y, z);

            Material blockMaterial = Material.valueOf(configurationSection.getString("blockMaterial"));

            return new TaskReach(location, blockMaterial);
        }

        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NO SUCH TASK IN LOADER");

        return null;
    }

}
