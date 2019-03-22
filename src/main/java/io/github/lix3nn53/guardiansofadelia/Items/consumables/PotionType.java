package io.github.lix3nn53.guardiansofadelia.Items.consumables;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum PotionType {
    HEALTH,
    MANA,
    HYBRID,
    REGENERATION;

    public String getSkillCode() {
        switch (this) {
            case HEALTH:
                return "healthPotion";
            case MANA:
                return "manaPotion";
            case HYBRID:
                return "hybridPotion";
            case REGENERATION:
                return "regenerationPotion";
        }
        return "";
    }

    public Material getMaterial() {
        switch (this) {
            case HEALTH:
                return Material.COAL;
            case MANA:
                return Material.GLISTERING_MELON_SLICE;
            case HYBRID:
                return Material.WHEAT;
            case REGENERATION:
                return Material.BOWL;
        }
        return Material.AIR;
    }

    public String getName() {
        switch (this) {
            case HEALTH:
                return ChatColor.RED + "Health Potion";
            case MANA:
                return ChatColor.AQUA + "Mana Potion";
            case HYBRID:
                return ChatColor.LIGHT_PURPLE + "Hybrid Potion";
            case REGENERATION:
                return ChatColor.GOLD + "Regeneration Potion";
        }
        return "";
    }

    public List<String> getDescribtion() {
        List<String> lore = new ArrayList<>();
        switch (this) {
            case HEALTH:
                lore.add("Restores health");
            case MANA:
                lore.add("Restores mana");
            case HYBRID:
                lore.add("Restores health & mana");
            case REGENERATION:
                lore.add("Restores health every 2 sec for 20 sec");
        }
        return lore;
    }
}
