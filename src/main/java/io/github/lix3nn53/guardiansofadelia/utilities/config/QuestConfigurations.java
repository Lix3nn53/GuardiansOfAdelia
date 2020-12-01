package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.config.ItemReferenceLoader;
import io.github.lix3nn53.guardiansofadelia.Items.config.WeaponReferenceData;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.quests.actions.ActionLoader;
import io.github.lix3nn53.guardiansofadelia.quests.task.Task;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskLoader;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
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

            int questCount = getChildComponentCount(questLineConfiguration, "quest");

            for (int i = 1; i <= questCount; i++) {
                ConfigurationSection section = questLineConfiguration.getConfigurationSection("quest" + i);
                int questID = section.getInt("questID");

                int npcToTakeFrom = section.getInt("npcToTakeFrom");
                int npcToComplete = section.getInt("npcToComplete");

                String name = section.getString("name");

                List<String> story = section.getStringList("story");

                String startMsg = "";
                if (section.contains("startMsg")) {
                    startMsg = section.getString("startMsg");
                }
                String turnInMsg = "";
                if (section.contains("turnInMsg")) {
                    turnInMsg = section.getString("turnInMsg");
                }

                List<String> objectiveTextList = section.getStringList("objectiveTextList");
                StringBuilder objectiveText = new StringBuilder(objectiveTextList.get(0));
                if (objectiveTextList.size() > 1) {
                    for (int lineIndex = 1; lineIndex < objectiveTextList.size(); lineIndex++) {
                        String line = objectiveTextList.get(lineIndex);
                        objectiveText.append("\n");
                        objectiveText.append(line);
                    }
                }

                int moneyPrize = section.getInt("moneyPrize");
                int expPrize = section.getInt("expPrize");
                int requiredLevel = section.getInt("requiredLevel");

                List<Integer> requiredQuests = new ArrayList<>();
                if (section.contains("requiredQuests")) {
                    requiredQuests = section.getIntegerList("requiredQuests");
                }

                Material advancementMaterial = Material.valueOf(section.getString("advancementMaterial"));

                int taskCount = getChildComponentCount(section, "task");
                List<Task> tasks = new ArrayList<>();
                for (int c = 1; c <= taskCount; c++) {
                    Task task = TaskLoader.load(section.getConfigurationSection("task" + c));
                    if (task == null) continue;
                    tasks.add(task);
                }

                int itemPrizeCount = getChildComponentCount(section, "itemPrize");
                List<ItemStack> itemPrizes = new ArrayList<>();
                for (int c = 1; c <= itemPrizeCount; c++) {
                    ItemStack item = ItemReferenceLoader.loadItemReference(section.getConfigurationSection("itemPrize" + c));
                    itemPrizes.add(item);
                }

                List<Action> onAcceptActions = new ArrayList<>();
                int onAcceptActionCount = getChildComponentCount(section, "onAcceptAction");
                for (int c = 1; c <= onAcceptActionCount; c++) {
                    Action action = ActionLoader.load(section.getConfigurationSection("onAcceptAction" + c));

                    onAcceptActions.add(action);
                }

                List<Action> onCompleteActions = new ArrayList<>();
                int onCompleteActionCount = getChildComponentCount(section, "onCompleteAction");
                for (int c = 1; c <= onCompleteActionCount; c++) {
                    Action action = ActionLoader.load(section.getConfigurationSection("onCompleteAction" + c));

                    onCompleteActions.add(action);
                }

                List<Action> onTurnInActions = new ArrayList<>();
                int onTurnInActionCount = getChildComponentCount(section, "onTurnInAction");
                for (int c = 1; c <= onTurnInActionCount; c++) {
                    Action action = ActionLoader.load(section.getConfigurationSection("onTurnInAction" + c));

                    onTurnInActions.add(action);
                }

                int itemPrizesSelectOneOfCount = getChildComponentCount(section, "itemPrizeSelectOneOf");
                List<ItemStack> itemPrizesSelectOneOf = new ArrayList<>();
                for (int c = 1; c <= itemPrizesSelectOneOfCount; c++) {
                    ItemStack item = ItemReferenceLoader.loadItemReference(section.getConfigurationSection("itemPrizeSelectOneOf" + c));
                    itemPrizesSelectOneOf.add(item);
                }

                WeaponReferenceData weaponPrizesSelectOneOf = null;
                if (section.contains("itemPrizeSelectOneOfWeapon")) {
                    ConfigurationSection itemPrizeSelectOneOfWeapon = section.getConfigurationSection("itemPrizeSelectOneOfWeapon");
                    weaponPrizesSelectOneOf = new WeaponReferenceData(itemPrizeSelectOneOfWeapon);
                }

                Quest quest = new Quest(questID, name, story,
                        startMsg, objectiveText.toString(),
                        turnInMsg,
                        tasks, itemPrizes, moneyPrize, expPrize, requiredLevel, requiredQuests, advancementMaterial, onAcceptActions, onCompleteActions, onTurnInActions, itemPrizesSelectOneOf, weaponPrizesSelectOneOf);

                QuestNPCManager.addQuest(quest, npcToTakeFrom, npcToComplete);
            }
        }
    }

    private static int getChildComponentCount(ConfigurationSection configurationSection, String text) {
        int count = 0;
        while (true) {
            boolean contains = configurationSection.contains(text + (count + 1));
            if (contains) {
                count++;
            } else {
                break;
            }
        }

        return count;
    }
}
