package io.github.lix3nn53.guardiansofadelia.jobs;

import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class RPGCharacterCraftingStats {

    private final HashMap<CraftingType, Integer> craftingTypeToTotalExperience = new HashMap<>();
    private final int maxLevel = 10;

    public RPGCharacterCraftingStats() {
        for (CraftingType craftingType : CraftingType.values()) {
            craftingTypeToTotalExperience.put(craftingType, 0);
        }
    }

    public void addExperience(Player player, CraftingType craftingType, int experienceToAdd) {
        int totalExp = craftingTypeToTotalExperience.get(craftingType);

        int currentLevel = getCurrentLevel(craftingType);

        if (currentLevel == maxLevel) return;

        totalExp += experienceToAdd;
        craftingTypeToTotalExperience.put(craftingType, totalExp);

        player.sendMessage(ChatPalette.YELLOW + "Gained " + experienceToAdd + " " + craftingType.getName() + ChatPalette.YELLOW + " experience");

        int totalRequiredExperience = getTotalRequiredExperience(currentLevel);

        if (totalExp >= totalRequiredExperience) {
            currentLevel++;
            player.sendMessage(ChatPalette.GREEN_DARK + "Crafting level up! Your new " + craftingType.getName() + ChatPalette.GREEN_DARK + " level is " + currentLevel);
        }
    }

    public int getTotalExperience(CraftingType craftingType) {
        return craftingTypeToTotalExperience.get(craftingType);
    }

    public void setExperience(CraftingType craftingType, int experience) {
        craftingTypeToTotalExperience.put(craftingType, experience);
    }

    public int getCurrentLevel(CraftingType craftingType) {
        int currentLevel = 1;

        int totalExp = craftingTypeToTotalExperience.get(craftingType);

        int totalRequiredExperience = getRequiredExperience(currentLevel);

        while (totalExp >= totalRequiredExperience) {
            currentLevel++;

            if (currentLevel == this.maxLevel) break;

            totalRequiredExperience += getRequiredExperience(currentLevel);
        }

        return currentLevel;
    }

    public int getTotalRequiredExperience(int level) {
        int totalRequiredExperience = 0;

        for (int i = 1; i <= level; i++) {
            totalRequiredExperience += getRequiredExperience(i);
        }

        return totalRequiredExperience;
    }

    private int getRequiredExperience(int level) {
        if (level == 1) {
            return 90;
        } else if (level == 2) {
            return 140;
        } else if (level == 3) {
            return 200;
        } else if (level == 4) {
            return 270;
        } else if (level == 5) {
            return 450;
        } else if (level == 6) {
            return 560;
        } else if (level == 7) {
            return 680;
        } else if (level == 8) {
            return 800;
        } else if (level == 9) {
            return 1000;
        } else if (level == 10) {
            return 1200;
        }

        return 999999;
    }

    public String getDatabaseString() {
        StringBuilder stringBuilder = new StringBuilder();

        int i = 0;
        for (CraftingType craftingType : CraftingType.values()) {
            if (i > 0) {
                stringBuilder.append(";");
            }

            stringBuilder.append(craftingType.toString());
            stringBuilder.append("-");

            stringBuilder.append(craftingTypeToTotalExperience.get(craftingType));
            i++;
        }

        return stringBuilder.toString();
    }

    public void loadDatabaseString(String craftingStatsString) {
        String[] craftingTypesWithTheirTotalExp = craftingStatsString.split(";");

        for (String craftingTypeAndTotalExp : craftingTypesWithTheirTotalExp) {
            if (!craftingTypeAndTotalExp.equals("")) {
                String[] craftingTypeAndTotalExpArray = craftingTypeAndTotalExp.split("-");

                CraftingType craftingType = CraftingType.valueOf(craftingTypeAndTotalExpArray[0]);
                int totalExp = Integer.parseInt(craftingTypeAndTotalExpArray[1]);

                craftingTypeToTotalExperience.put(craftingType, totalExp);
            }
        }
    }
}
