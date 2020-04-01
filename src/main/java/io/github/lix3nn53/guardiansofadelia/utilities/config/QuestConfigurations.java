package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.config.ItemReferenceLoader;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.quests.actions.ActionLoader;
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

                String startMsg = "";
                if (questLineConfiguration.contains("i" + i + ".startMsg")) {
                    startMsg = questLineConfiguration.getString("i" + i + ".startMsg");
                }
                String turnInMsg = "";
                if (questLineConfiguration.contains("i" + i + ".turnInMsg")) {
                    turnInMsg = questLineConfiguration.getString("i" + i + ".turnInMsg");
                }

                List<String> objectiveTextList = questLineConfiguration.getStringList("i" + i + ".objectiveTextList");
                StringBuilder objectiveText = new StringBuilder(objectiveTextList.get(0));
                if (objectiveTextList.size() > 1) {
                    for (int lineIndex = 1; lineIndex < objectiveTextList.size(); lineIndex++) {
                        String line = objectiveTextList.get(lineIndex);
                        objectiveText.append("\n");
                        objectiveText.append(line);
                    }
                }

                int moneyPrize = questLineConfiguration.getInt("i" + i + ".moneyPrize");
                int expPrize = questLineConfiguration.getInt("i" + i + ".expPrize");
                int requiredLevel = questLineConfiguration.getInt("i" + i + ".requiredLevel");

                List<Integer> requiredQuests = new ArrayList<>();
                if (questLineConfiguration.contains("i" + i + ".requiredQuests")) {
                    requiredQuests = questLineConfiguration.getIntegerList("i" + i + ".requiredQuests");
                }

                Material advancementMaterial = Material.valueOf(questLineConfiguration.getString("i" + i + ".advancementMaterial"));

                List<Task> tasks = new ArrayList<>();
                if (questLineConfiguration.contains("i" + i + ".taskCount")) {
                    int childCount = questLineConfiguration.getInt("i" + i + ".taskCount");
                    for (int c = 1; c <= childCount; c++) {
                        Task task = TaskLoader.load(questLineConfiguration.getConfigurationSection("i" + i + ".task" + c));
                        tasks.add(task);
                    }
                }

                List<ItemStack> itemPrizes = new ArrayList<>();
                if (questLineConfiguration.contains("i" + i + ".itemPrizeCount")) {
                    int childCount = questLineConfiguration.getInt("i" + i + ".itemPrizeCount");
                    for (int c = 1; c <= childCount; c++) {
                        ItemStack item = ItemReferenceLoader.loadItemReference(questLineConfiguration.getConfigurationSection("i" + i + ".itemPrize" + c));
                        itemPrizes.add(item);
                    }
                }

                Quest quest = new Quest(questID, name, story,
                        startMsg, objectiveText.toString(),
                        turnInMsg,
                        tasks, itemPrizes, moneyPrize, expPrize, requiredLevel, requiredQuests, advancementMaterial);

                if (questLineConfiguration.contains("i" + i + ".onAcceptActionCount")) {
                    int childCount = questLineConfiguration.getInt("i" + i + ".onAcceptActionCount");
                    for (int c = 1; c <= childCount; c++) {
                        Action action = ActionLoader.load(questLineConfiguration.getConfigurationSection("i" + i + ".onAcceptAction" + c));

                        quest.addOnAcceptAction(action);
                    }
                }

                if (questLineConfiguration.contains("i" + i + ".onCompleteActionCount")) {
                    int childCount = questLineConfiguration.getInt("i" + i + ".onCompleteActionCount");
                    for (int c = 1; c <= childCount; c++) {
                        Action action = ActionLoader.load(questLineConfiguration.getConfigurationSection("i" + i + ".onCompleteAction" + c));

                        quest.addOnCompleteAction(action);
                    }
                }

                if (questLineConfiguration.contains("i" + i + ".onTurnInActionCount")) {
                    int childCount = questLineConfiguration.getInt("i" + i + ".onTurnInActionCount");
                    for (int c = 1; c <= childCount; c++) {
                        Action action = ActionLoader.load(questLineConfiguration.getConfigurationSection("i" + i + ".onTurnInAction" + c));

                        quest.addOnTurnInAction(action);
                    }
                }

                QuestNPCManager.addQuest(quest, npcToTakeFrom, npcToComplete);
            }
        }
    }
}
