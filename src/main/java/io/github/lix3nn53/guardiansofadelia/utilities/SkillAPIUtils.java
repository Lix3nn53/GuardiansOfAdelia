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

    public static boolean hasValidData(Player player, int charNo) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        if (playerAccountData.hasData(charNo)) {
            PlayerData playerData = playerAccountData.getData(charNo);
            if (playerData.hasClass()) {
                return !playerData.getMainClass().getData().getName().contains("Tutorial");
            }
        }
        return false;
    }

    public static int getActiveCharacterNo(Player player) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        return playerAccountData.getActiveId();
    }

    public static String getClassName(Player player, int charNo) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        PlayerData playerData = playerAccountData.getData(charNo);
        return playerData.getMainClass().getData().getName();
    }

    public static int getHealth(Player player, int charNo) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        PlayerData playerData = playerAccountData.getData(charNo);
        return (int) (playerData.getMainClass().getHealth() + 0.5);
    }

    public static int getMana(Player player, int charNo) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        PlayerData playerData = playerAccountData.getData(charNo);
        return (int) (playerData.getMainClass().getMana() + 0.5);
    }

    public static int getMaxMana(Player player, int charNo) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        PlayerData playerData = playerAccountData.getData(charNo);
        return (int) (playerData.getMaxMana() + 0.5);
    }

    public static int getLevel(Player player, int charNo) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        PlayerData playerData = playerAccountData.getData(charNo);
        return playerData.getMainClass().getLevel();
    }

    public static int getExp(Player player, int charNo) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        PlayerData playerData = playerAccountData.getData(charNo);
        return (int) (playerData.getMainClass().getExp() + 0.5);
    }

    public static int getTotalExp(Player player, int charNo) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        PlayerData playerData = playerAccountData.getData(charNo);
        return (int) (playerData.getMainClass().getTotalExp() + 0.5);
    }

    public static int getRequiredExp(Player player, int charNo) {
        PlayerAccounts playerAccountData = SkillAPI.getPlayerAccountData(player);
        PlayerData playerData = playerAccountData.getData(charNo);
        return (int) (playerData.getMainClass().getRequiredExp() + 0.5);
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

    public static int getInvestedAttribute(Player player, String attr) {
        PlayerData sdata = SkillAPI.getPlayerData(player);
        return sdata.getInvestedAttribute(attr);
    }

    public static void resetClass(Player player) {
        PlayerData playerData = SkillAPI.getPlayerData(player);
        playerData.resetAll();
    }

    public static void setClass(Player player, String className) {
        PlayerData playerData = SkillAPI.getPlayerData(player);
        playerData.setClass(SkillAPI.getClass(className));
    }

    public static void refunAttributes(Player player) {
        PlayerData playerData = SkillAPI.getPlayerData(player);
        playerData.refundAttributes();
    }

    public static void clearBonuses(Player player) {
        PlayerData playerData = SkillAPI.getPlayerData(player);
        playerData.clearBonuses();
    }
}
