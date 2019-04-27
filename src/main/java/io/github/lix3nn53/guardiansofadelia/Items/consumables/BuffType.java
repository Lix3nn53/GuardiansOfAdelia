package io.github.lix3nn53.guardiansofadelia.Items.consumables;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum BuffType {
    PHYSICAL_DAMAGE,
    PHYSICAL_DEFENSE,
    MAGICAL_DAMAGE,
    MAGICAL_DEFENSE;

    public String getSkillCode() {
        switch (this) {
            case PHYSICAL_DAMAGE:
                return "physicalDamageBuff";
            case PHYSICAL_DEFENSE:
                return "physicalDefenseBuff";
            case MAGICAL_DAMAGE:
                return "magicalDamageBuff";
            case MAGICAL_DEFENSE:
                return "magicalDefenseBuff";
        }
        return "";
    }

    public Material getMaterial() {
        switch (this) {
            case PHYSICAL_DAMAGE:
                return Material.INK_SAC;
            case PHYSICAL_DEFENSE:
                return Material.GREEN_DYE;
            case MAGICAL_DAMAGE:
                return Material.RED_DYE;
            case MAGICAL_DEFENSE:
                return Material.COCOA_BEANS;
        }
        return Material.AIR;
    }

    public String getName() {
        switch (this) {
            case PHYSICAL_DAMAGE:
                return ChatColor.RED + "Physical Damage Buff Scroll";
            case PHYSICAL_DEFENSE:
                return ChatColor.AQUA + "Physical Defense Buff Scroll";
            case MAGICAL_DAMAGE:
                return ChatColor.LIGHT_PURPLE + "Magical Damage Buff Scroll";
            case MAGICAL_DEFENSE:
                return ChatColor.GREEN + "Magical Defense Buff Scroll";
        }
        return "";
    }

    public List<String> getDescribtion() {
        List<String> lore = new ArrayList<>();
        switch (this) {
            case PHYSICAL_DAMAGE:
                lore.add("Increases physical damage for 1 hour");
            case PHYSICAL_DEFENSE:
                lore.add("Increases physical defense for 1 hour");
            case MAGICAL_DAMAGE:
                lore.add("Increases magical damage for 1 hour");
            case MAGICAL_DEFENSE:
                lore.add("Increases magical defense for 1 hour");
        }
        return lore;
    }

    public int getDuration() {
        return 60;
    }
}
