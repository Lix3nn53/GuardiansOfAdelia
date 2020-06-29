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
    private List<Integer> turnedInQuests = new ArrayList<Integer>();

    private final RPGCharacterCraftingStats craftingStats = new RPGCharacterCraftingStats();

    private ChatTag chatTag = ChatTag.NOVICE;

    public RPGCharacter(String rpgClassStr, HashMap<String, RPGClassStats> unlockedClasses, Player player, int one, int two, int three, int passive, int ultimate) {
        rpgCharacterStats = new RPGCharacterStats(player, rpgClassStr);
        this.rpgClassStr = rpgClassStr;
        this.unlockedClasses = unlockedClasses;
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        this.skillBar = new SkillBar(player, one, two, three, passive, ultimate, rpgClass.getSkillSet());
    }

    public RPGCharacter(String rpgClassStr, HashMap<String, RPGClassStats> unlockedClasses, Player player) {
        rpgCharacterStats = new RPGCharacterStats(player, rpgClassStr);
        this.rpgClassStr = rpgClassStr;
        this.unlockedClasses = unlockedClasses;
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        this.skillBar = new SkillBar(player, 0, 0, 0, 0, 0, rpgClass.getSkillSet());
    }

    public String getRpgClassStr() {
        return rpgClassStr;
    }

    public HashMap<String, RPGClassStats> getUnlockedClasses() {
        return unlockedClasses;
    }

    public RPGClassStats getRPGClassStats(RPGClass rpgClass) {
        return unlockedClasses.get(rpgClass);
    }

    public RPGClassStats getRPGClassStats() {
        return unlockedClasses.get(this.rpgClassStr);
    }

    public void unlockClass(String newClass) {
        if (!unlockedClasses.containsKey(newClass)) {
            RPGClassStats rpgClassStats = new RPGClassStats();
            unlockedClasses.put(newClass, rpgClassStats);
        }
    }

    public boolean changeClass(Player player, String newClassStr) {
        if (!unlockedClasses.containsKey(newClassStr)) {
            return false;
        }

        this.rpgClassStr = newClassStr;
        RPGClassStats rpgClassStats = unlockedClasses.get(newClassStr);
        int one = rpgClassStats.getOne();
        int two = rpgClassStats.getTwo();
        int three = rpgClassStats.getThree();
        int passive = rpgClassStats.getPassive();
        int ultimate = rpgClassStats.getUltimate();

        RPGClass rpgClass = RPGClassManager.getClass(newClassStr);
        this.skillBar = new SkillBar(player, one, two, three, passive, ultimate, rpgClass.getSkillSet());
        skillBar.remakeSkillBar();

        rpgCharacterStats.setRpgClassStr(newClassStr);
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

    public boolean turnInQuest(int questID, Player player) {
        Optional<Quest> questOptional = this.questList.stream()
                .filter(characterQuest -> characterQuest.getQuestID() == questID)
                .findAny();
        if (questOptional.isPresent()) {
            Quest quest = questOptional.get();
            if (quest.isCompleted()) {
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
