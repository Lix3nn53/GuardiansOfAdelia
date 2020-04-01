package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.config.ItemReferenceLoader;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.task.Task;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskLoader;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestConfigurations {

    private static final List<FileConfiguration> questLineConfigurations = new ArrayList<>();
    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        createConfig("config.yml", true);
        createQuestLineConfigs();
    }

    static void loadConfigs() {
        loadQuestLineConfigs();
    }

    private static void createConfig(String fileName, boolean isMain) {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "quests";
        File customConfigFile = new File(filePath, fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.load(customConfigFile);
            if (isMain) {
                fileConfiguration = yamlConfiguration;
            } else {
                questLineConfigurations.add(yamlConfiguration);
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void createQuestLineConfigs() {
        List<String> questLineList = fileConfiguration.getStringList("questLineList");

        for (String fileName : questLineList) {
            createConfig(fileName + ".yml", false);
        }
    }

    private static void loadQuestLineConfigs() {
        for (FileConfiguration questLineConfiguration : questLineConfigurations) {

            int count = questLineConfiguration.getInt("count");

            for (int i = 1; i <= count; i++) {
                int questID = questLineConfiguration.getInt("i" + i + ".questID");

                int npcToTakeFrom = questLineConfiguration.getInt("i" + i + ".npcToTakeFrom");
                int npcToComplete = questLineConfiguration.getInt("i" + i + ".npcToComplete");

                String name = questLineConfiguration.getString("i" + i + ".name");

                List<String> story = questLineConfiguration.getStringList("i" + i + ".story");

                String startMsg = questLineConfiguration.getString("i" + i + ".startMsg");
                String objectiveText = questLineConfiguration.getString("i" + i + ".objectiveText");
                String turnInMsg = questLineConfiguration.getString("i" + i + ".turnInMsg");

                int moneyPrize = questLineConfiguration.getInt("i" + i + ".moneyPrize");
                int expPrize = questLineConfiguration.getInt("i" + i + ".expPrize");
                int requiredLevel = questLineConfiguration.getInt("i" + i + ".requiredLevel");

                List<Integer> requiredQuests = questLineConfiguration.getIntegerList("i" + i + ".requiredQuests");

                Material advancementMaterial = Material.valueOf(questLineConfiguration.getString("i" + i + ".advancementMaterial"));

                int taskCount = questLineConfiguration.getInt("i" + i + ".taskCount");
                List<Task> tasks = new ArrayList<>();
                for (int t = 1; t <= taskCount; t++) {
                    Task task = TaskLoader.load(questLineConfiguration.getConfigurationSection("i" + i + ".task" + t));
                    tasks.add(task);
                }

                int itemPrizeCount = questLineConfiguration.getInt("i" + i + ".itemPrizeCount");
                List<ItemStack> itemPrizes = new ArrayList<>();
                for (int t = 1; t <= itemPrizeCount; t++) {
                    ItemStack item = ItemReferenceLoader.loadItemReference(questLineConfiguration.getConfigurationSection("i" + i + ".itemPrize" + t));
                    itemPrizes.add(item);
                }

                Quest quest = new Quest(questID, name, story,
                        startMsg, objectiveText,
                        turnInMsg,
                        tasks, itemPrizes, moneyPrize, expPrize, requiredLevel, requiredQuests, advancementMaterial);

                QuestNPCManager.addQuest(quest, npcToTakeFrom, npcToComplete);
            }
        }
    }
}
