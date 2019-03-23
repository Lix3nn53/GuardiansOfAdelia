package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.chat.ChatTag;
import io.github.lix3nn53.guardiansofadelia.jobs.Job;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class RPGCharacter {

    private final RPGInventory rpgInventory = new RPGInventory();
    private List<Quest> questList = new ArrayList<>();
    private List<Integer> turnedInQuests = new ArrayList<Integer>();
    private Job job;

    private ChatTag chatTag = ChatTag.NEWBIE;

    public boolean addQuest(Quest quest, Player player) {
        if (!hasQuest(quest.getQuestID())) {
            if (this.questList.size() < 5) {
                this.questList.add(quest);
                quest.onAccept(player);
                return true;
            }
        }
        return false;
    }

    public boolean completeQuest(int questID, Player player) {
        Optional<Quest> questOptional = this.questList.stream()
                .filter(characterQuest -> characterQuest.getQuestID() == questID)
                .findAny();
        if (questOptional.isPresent()) {
            Quest quest = questOptional.get();
            if (quest.isCompleted()) {
                quest.onTurnIn(player);
                this.questList.remove(quest);
                this.turnedInQuests.add(questID);
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

    public RPGClass getRPGClass() {
        return RPGClass.ARCHER;
    }

    public RPGInventory getRpgInventory() {
        return rpgInventory;
    }

    public boolean hasJob() {
        return job != null;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
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
}
