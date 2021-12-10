package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.items.config.ArmorReferenceData;
import io.github.lix3nn53.guardiansofadelia.items.config.ItemReferenceLoader;
import io.github.lix3nn53.guardiansofadelia.items.config.WeaponReferenceData;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.actions.Action;
import io.github.lix3nn53.guardiansofadelia.quests.actions.ActionLoader;
import io.github.lix3nn53.guardiansofadelia.quests.task.Task;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskLoader;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuestConfigurations {

    private static final List<FileConfiguration> questLineConfigurations = new ArrayList<>();
    private static FileConfiguration fileConfiguration;
    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "quests";

    static void createConfigs() {
        fileConfiguration = ConfigurationUtils.createConfig(filePath, "config.yml");
        createQuestLineConfigs();
    }

    static void loadConfigs() {
        loadQuestLineConfigs();
    }

    private static void createQuestLineConfigs() {
        List<String> questLineList = fileConfiguration.getStringList("questLineList");

        for (String fileName : questLineList) {
            questLineConfigurations.add(ConfigurationUtils.createConfig(filePath, fileName + ".yml"));
        }
    }

    private static void loadQuestLineConfigs() {
        for (FileConfiguration questLineConfiguration : questLineConfigurations) {

            int questCount = ConfigurationUtils.getChildComponentCount(questLineConfiguration, "quest");

            for (int i = 1; i <= questCount; i++) {
                ConfigurationSection section = questLineConfiguration.getConfigurationSection("quest" + i);
                int questID = section.getInt("questID");

                int npcToTakeFrom = section.getInt("npcToTakeFrom");
                int npcToComplete = section.getInt("npcToComplete");

                String name = section.getString("name");

                int storyLineCount = 0;
                ConfigurationSection storySection = section.getConfigurationSection("story");
                Set<String> storyLanguages = storySection.getKeys(false);
                for (String lang : storyLanguages) {
                    List<String> storyOfLang = storySection.getStringList(lang);

                    int lineCounter = 0;
                    for (String storyLine : storyOfLang) {
                        lineCounter++;
                        String key = questID + ".story.l" + lineCounter;
                        Translation.add(lang, "quest", key, storyLine);
                    }
                    storyLineCount = lineCounter;
                }

                String startMsgCode = "";
                if (section.contains("startMsg")) {
                    startMsgCode = questID + ".startMsg";
                    ConfigurationSection sectionInner = section.getConfigurationSection("startMsg");
                    Set<String> sectionInnerLanguages = sectionInner.getKeys(false);
                    for (String lang : sectionInnerLanguages) {
                        String valueOfLang = sectionInner.getString(lang);

                        Translation.add(lang, "quest", startMsgCode, valueOfLang);
                    }
                }
                String turnInMsg = "";
                if (section.contains("turnInMsg")) {
                    turnInMsg = questID + ".turnInMsg";
                    ConfigurationSection sectionInner = section.getConfigurationSection("turnInMsg");
                    Set<String> sectionInnerLanguages = sectionInner.getKeys(false);
                    for (String lang : sectionInnerLanguages) {
                        String valueOfLang = sectionInner.getString(lang);

                        Translation.add(lang, "quest", turnInMsg, valueOfLang);
                    }
                }

                String objectiveTextKey = "";
                if (section.contains("objectiveTextList")) {
                    objectiveTextKey = questID + ".objectiveTextList";
                    ConfigurationSection sectionInner = section.getConfigurationSection("objectiveTextList");
                    Set<String> sectionInnerLanguages = sectionInner.getKeys(false);
                    for (String lang : sectionInnerLanguages) {
                        List<String> valueOfLang = sectionInner.getStringList(lang);

                        StringBuilder objectiveText = new StringBuilder(valueOfLang.get(0));
                        if (valueOfLang.size() > 1) {
                            for (int lineIndex = 1; lineIndex < valueOfLang.size(); lineIndex++) {
                                String line = valueOfLang.get(lineIndex);
                                objectiveText.append("\n");
                                objectiveText.append(line);
                            }
                        }

                        Translation.add(lang, "quest", objectiveTextKey, objectiveText.toString());
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

                int taskCount = ConfigurationUtils.getChildComponentCount(section, "task");
                List<Task> tasks = new ArrayList<>();
                for (int c = 1; c <= taskCount; c++) {
                    Task task = TaskLoader.load(section.getConfigurationSection("task" + c));
                    if (task == null) continue;
                    tasks.add(task);
                }

                int itemPrizeCount = ConfigurationUtils.getChildComponentCount(section, "itemPrize");
                List<ItemStack> itemPrizes = new ArrayList<>();
                for (int c = 1; c <= itemPrizeCount; c++) {
                    ItemStack item = ItemReferenceLoader.loadItemReference(section.getConfigurationSection("itemPrize" + c));
                    itemPrizes.add(item);
                }

                List<Action> onAcceptActions = new ArrayList<>();
                int onAcceptActionCount = ConfigurationUtils.getChildComponentCount(section, "onAcceptAction");
                for (int c = 1; c <= onAcceptActionCount; c++) {
                    Action action = ActionLoader.load(section.getConfigurationSection("onAcceptAction" + c));

                    onAcceptActions.add(action);
                }

                List<Action> onCompleteActions = new ArrayList<>();
                int onCompleteActionCount = ConfigurationUtils.getChildComponentCount(section, "onCompleteAction");
                for (int c = 1; c <= onCompleteActionCount; c++) {
                    Action action = ActionLoader.load(section.getConfigurationSection("onCompleteAction" + c));

                    onCompleteActions.add(action);
                }

                List<Action> onTurnInActions = new ArrayList<>();
                int onTurnInActionCount = ConfigurationUtils.getChildComponentCount(section, "onTurnInAction");
                for (int c = 1; c <= onTurnInActionCount; c++) {
                    Action action = ActionLoader.load(section.getConfigurationSection("onTurnInAction" + c));

                    onTurnInActions.add(action);
                }

                int itemPrizesSelectOneOfCount = ConfigurationUtils.getChildComponentCount(section, "itemPrizeSelectOneOf");
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

                ArmorReferenceData armorPrizesSelectOneOf = null;
                if (section.contains("itemPrizeSelectOneOfArmor")) {
                    ConfigurationSection itemPrizeSelectOneOfWeapon = section.getConfigurationSection("itemPrizeSelectOneOfArmor");
                    armorPrizesSelectOneOf = new ArmorReferenceData(itemPrizeSelectOneOfWeapon);
                }

                int itemPrizeForPlayerWeaponCount = ConfigurationUtils.getChildComponentCount(section, "itemPrizeForPlayerWeapon");
                List<WeaponReferenceData> weaponForPlayer = new ArrayList<>();
                for (int c = 1; c <= itemPrizeForPlayerWeaponCount; c++) {
                    ConfigurationSection itemPrizeSelectOneOfWeapon = section.getConfigurationSection("itemPrizeForPlayerWeapon" + c);
                    WeaponReferenceData data = new WeaponReferenceData(itemPrizeSelectOneOfWeapon);
                    weaponForPlayer.add(data);
                }

                int itemPrizeForPlayerArmorCount = ConfigurationUtils.getChildComponentCount(section, "itemPrizeForPlayerArmor");
                List<ArmorReferenceData> armorForPlayer = new ArrayList<>();
                for (int c = 1; c <= itemPrizeForPlayerArmorCount; c++) {
                    ConfigurationSection itemPrizeSelectOneOfWeapon = section.getConfigurationSection("itemPrizeForPlayerArmor" + c);
                    ArmorReferenceData data = new ArmorReferenceData(itemPrizeSelectOneOfWeapon);
                    armorForPlayer.add(data);
                }

                Quest quest = new Quest(questID, name, storyLineCount,
                        startMsgCode, objectiveTextKey,
                        turnInMsg,
                        tasks, itemPrizes, moneyPrize, expPrize, requiredLevel, requiredQuests, advancementMaterial, onAcceptActions,
                        onCompleteActions, onTurnInActions, itemPrizesSelectOneOf, weaponPrizesSelectOneOf, armorPrizesSelectOneOf,
                        weaponForPlayer, armorForPlayer);

                QuestNPCManager.addQuest(quest, npcToTakeFrom, npcToComplete);
            }
        }
    }
}
