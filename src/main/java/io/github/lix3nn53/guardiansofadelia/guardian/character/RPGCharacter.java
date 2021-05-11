package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.chat.ChatTag;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillBar;
import io.github.lix3nn53.guardiansofadelia.jobs.RPGCharacterCraftingStats;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public final class RPGCharacter {

    private final RPGInventory rpgInventory = new RPGInventory();

    private final HashMap<String, RPGClassStats> unlockedClasses;
    private String rpgClassStr;
    private SkillBar skillBar;

    private final RPGCharacterStats rpgCharacterStats;

    private List<Quest> questList = new ArrayList<>();
    private List<Integer> turnedInQuests = new ArrayList<>();

    private final RPGCharacterCraftingStats craftingStats = new RPGCharacterCraftingStats();

    private ChatTag chatTag = ChatTag.NOVICE;

    public RPGCharacter(String rpgClassStr, Player player, int one, int two, int three, int passive, int ultimate, HashMap<String, RPGClassStats> unlockedClasses) {
        rpgCharacterStats = new RPGCharacterStats(player, rpgClassStr);
        this.rpgClassStr = rpgClassStr.toUpperCase();
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        this.skillBar = new SkillBar(player, one, two, three, passive, ultimate, rpgClass.getSkillSet(), false);

        this.unlockedClasses = unlockedClasses;
    }

    public RPGCharacter(String rpgClassStr, Player player) {
        rpgCharacterStats = new RPGCharacterStats(player, rpgClassStr);
        this.rpgClassStr = rpgClassStr.toUpperCase();
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        this.skillBar = new SkillBar(player, 0, 0, 0, 0, 0, rpgClass.getSkillSet(), false);

        this.unlockedClasses = new HashMap<>();
        RPGClassStats rpgClassStats = new RPGClassStats(0, 0, 0, 0, 0);
        this.unlockedClasses.put(rpgClassStr, rpgClassStats);
    }

    public String getRpgClassStr() {
        return rpgClassStr;
    }

    public HashMap<String, RPGClassStats> getUnlockedClasses() {
        return unlockedClasses;
    }

    public RPGClassStats getRPGClassStats(String rpgClassStr) {
        return unlockedClasses.get(rpgClassStr.toUpperCase());
    }

    public RPGClassStats getRPGClassStats() {
        return unlockedClasses.get(this.rpgClassStr.toUpperCase());
    }

    public void unlockClass(String newClass) {
        String s = newClass.toUpperCase();
        if (!unlockedClasses.containsKey(s)) {
            RPGClassStats rpgClassStats = new RPGClassStats();
            unlockedClasses.put(s, rpgClassStats);
        }
    }

    public boolean changeClass(Player player, String newClassStr) {
        String s = newClassStr.toUpperCase();
        if (!unlockedClasses.containsKey(s)) {
            return false;
        }

        if (this.rpgClassStr.equals(s)) return false;

        this.rpgClassStr = s;
        RPGClassStats rpgClassStats = unlockedClasses.get(s);
        int one = rpgClassStats.getOne();
        int two = rpgClassStats.getTwo();
        int three = rpgClassStats.getThree();
        int passive = rpgClassStats.getPassive();
        int ultimate = rpgClassStats.getUltimate();

        RPGClass rpgClass = RPGClassManager.getClass(s);
        this.skillBar = new SkillBar(player, one, two, three, passive, ultimate, rpgClass.getSkillSet(), true);
        skillBar.remakeSkillBar();

        rpgCharacterStats.setRpgClassStr(s);
        rpgCharacterStats.recalculateEquipment(rpgClassStr);

        return true;
    }

    public boolean acceptQuest(Quest quest, Player player) {
        if (!hasQuest(quest.getQuestID())) {
            if (this.questList.size() < 5) {
                this.questList.add(quest);
                quest.onAccept(player);
                return true;
            }
        }
        return false;
    }

    public boolean turnInQuest(int questID, Player player, boolean ignoreCompilation) {
        Optional<Quest> questOptional = this.questList.stream()
                .filter(characterQuest -> characterQuest.getQuestID() == questID)
                .findAny();
        if (questOptional.isPresent()) {
            Quest quest = questOptional.get();
            if (ignoreCompilation) {
                this.questList.remove(quest);
                this.turnedInQuests.add(questID);
                quest.onTurnIn(player);
                return true;
            } else if (quest.isCompleted()) {
                this.questList.remove(quest);
                this.turnedInQuests.add(questID);
                quest.onTurnIn(player);
                return true;
            }
        }
        return false;
    }

    public List<Quest> getQuestList() {
        return this.questList;
    }

    public void setQuestList(List<Quest> questList) {
        this.questList = questList;
    }

    public List<Integer> getTurnedInQuests() {
        return this.turnedInQuests;
    }

    public void setTurnedInQuests(List<Integer> turnedInQuests) {
        this.turnedInQuests = turnedInQuests;
    }

    public RPGInventory getRpgInventory() {
        return rpgInventory;
    }

    public ChatTag getChatTag() {
        return chatTag;
    }

    public void setChatTag(ChatTag chatTag) {
        this.chatTag = chatTag;
    }

    public boolean hasQuest(int questId) {
        return this.questList.stream()
                .anyMatch(playerQuest -> playerQuest.getQuestID() == questId);
    }

    public boolean progressTaskOfQuestWithIndex(Player player, int questId, int taskIndex) {
        Quest quest = null;
        for (Quest i : this.questList) {
            if (i.getQuestID() == questId) {
                quest = i;
                break;
            }
        }

        if (quest == null) return false;

        return quest.progressTaskWithIndex(player, taskIndex);
    }

    public RPGCharacterStats getRpgCharacterStats() {
        return rpgCharacterStats;
    }

    public SkillBar getSkillBar() {
        return skillBar;
    }

    public RPGCharacterCraftingStats getCraftingStats() {
        return craftingStats;
    }
}
