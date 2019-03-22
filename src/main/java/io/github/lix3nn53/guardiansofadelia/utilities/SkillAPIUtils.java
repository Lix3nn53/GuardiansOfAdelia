package io.github.lix3nn53.guardiansofadelia.utilities;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.enums.ExpSource;
import com.sucy.skill.api.player.PlayerAccounts;
import com.sucy.skill.api.player.PlayerData;
import com.sucy.skill.dynamic.DynamicSkill;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.entity.Player;

public class SkillAPIUtils {

    public static void forceUseSkill(Player player, String skillCode, int skillLevel) {
        final DynamicSkill skill = (DynamicSkill) SkillAPI.getSkill(skillCode);
        skill.cast(player, skillLevel);
    }

    public static PlayerData getCharacterData(Player player, int charNo) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        if (playerAccountData.hasData(charNo)) {
            PlayerData activeData = playerAccountData.getActiveData();
            if (activeData.hasClass()) {
                if (!activeData.getMainClass().getData().getName().contains("Tutorial")) {
                    return activeData;
                }
            }
        }
        return null;
    }

    public static void giveQuestExp(Player player, double exp) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        PlayerData activeData = playerAccountData.getActiveData();
        if (activeData.hasClass()) {
            activeData.giveExp(exp, ExpSource.QUEST);
        }
    }

    public static void giveLevels(Player player, int level) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        PlayerData activeData = playerAccountData.getActiveData();
        if (activeData.hasClass()) {
            activeData.giveLevels(level, ExpSource.COMMAND);
        }
    }

    public static void createCharacter(Player player, int charNo, RPGClass rpgClass) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        playerAccountData.setAccount(charNo, true);
        SkillAPI.getPlayerData(player).setClass(SkillAPI.getClass(rpgClass.getClassCode()));
    }

    public static void createTurorialCharacter(Player player, int charNo, RPGClass rpgClass) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        playerAccountData.setAccount(charNo, true);
        SkillAPI.getPlayerData(player).setClass(SkillAPI.getClass("tutorial" + rpgClass.getClassCode()));
    }

    public static void recoverMana(Player player) {
        PlayerData playerData = SkillAPI.getPlayerData(player);
        playerData.setMana(playerData.getMaxMana());
    }

    public static boolean isSafeToSave(Player player) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        PlayerData activeData = playerAccountData.getActiveData();
        if (activeData.hasClass()) {
            String className = activeData.getMainClass().getData().getName();
            return !className.contains("tutorial");
        }
        return false;
    }

    public static void openSkillMenu(Player player) {
        PlayerData playerData = SkillAPI.getPlayerData(player);
        playerData.showSkills();
    }

    public static void openAttributeMenu(Player player) {
        PlayerData playerData = SkillAPI.getPlayerData(player);
        playerData.openAttributeMenu();
    }

    public static void addBonusAttribute(Player player, String attr, int amount) {
        PlayerData sdata = SkillAPI.getPlayerData(player);
        sdata.addBonusAttributes(attr, amount);
    }

    public static void removeBonusAttribute(Player player, String attr, int amount) {
        PlayerData sdata = SkillAPI.getPlayerData(player);
        sdata.addBonusAttributes(attr, -amount);
    }

    public static int getBonusAttribute(Player player, String attr) {
        PlayerData sdata = SkillAPI.getPlayerData(player);
        int invested = sdata.getInvestedAttribute(attr);
        int total = sdata.getAttribute(attr);
        return total - invested;
    }
}
