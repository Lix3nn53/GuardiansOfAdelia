package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.InitializeTrigger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Skill {

    private final String name;
    private final int maxSkillLevel;
    private final Material material;
    private final int customModelData;
    private final List<String> description;

    private final List<Integer> reqPlayerLevels;
    private final List<Integer> reqSkillPoints;

    //skill attributes
    private final List<Integer> manaCosts;
    private final List<Integer> cooldowns;

    private final List<SkillComponent> triggers = new ArrayList<>();

    public Skill(String name, int maxSkillLevel, Material material, int customModelData, List<String> description, List<Integer> reqPlayerLevels, List<Integer> reqSkillPoints, List<Integer> manaCosts, List<Integer> cooldowns) {
        this.name = name;
        this.maxSkillLevel = maxSkillLevel;
        this.material = material;
        this.customModelData = customModelData;
        this.description = description;
        this.reqPlayerLevels = reqPlayerLevels;
        this.reqSkillPoints = reqSkillPoints;
        this.manaCosts = manaCosts;
        this.cooldowns = cooldowns;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public int getMaxSkillLevel() {
        return reqPlayerLevels.size();
    }

    public int getReqPlayerLevel(int skillLevel) {
        if (skillLevel == maxSkillLevel) return 9000;
        return reqSkillPoints.get(skillLevel);
    }

    public int getReqSkillPoints(int skillLevel) {
        if (skillLevel == maxSkillLevel) return 9000;
        return reqSkillPoints.get(skillLevel);
    }

    public int getManaCost(int skillLevel) {
        if (skillLevel == 0) return manaCosts.get(0);
        return manaCosts.get(skillLevel - 1);
    }

    public int getCooldown(int skillLevel) {
        if (skillLevel == 0) return cooldowns.get(0);
        return cooldowns.get(skillLevel - 1);
    }

    public int getCurrentSkillLevel(int pointsInvested) {
        int skillLevel = 0;

        int totalReqPoint = 0;
        for (int reqPoint : reqSkillPoints) {
            totalReqPoint += reqPoint;
            if (pointsInvested >= totalReqPoint) {
                skillLevel++;
            } else {
                break;
            }
        }

        return skillLevel;
    }

    public List<String> getDescription() {
        return description;
    }

    public ItemStack getIcon(int playerLevel, int playerPoints, int investedPoints) {
        int skillLevel = getCurrentSkillLevel(investedPoints);

        ItemStack icon = new ItemStack(getMaterial());

        ItemMeta itemMeta = icon.getItemMeta();
        itemMeta.setCustomModelData(customModelData);

        itemMeta.setDisplayName(getName() + " (" + skillLevel + "/" + getMaxSkillLevel() + ")");
        List<String> lore = new ArrayList<>();

        int reqPlayerLevel = getReqPlayerLevel(skillLevel);
        ChatColor reqPlayerLevelColor = ChatColor.RED;
        if (playerLevel >= reqPlayerLevel) {
            reqPlayerLevelColor = ChatColor.GREEN;
        }

        int reqSkillPoints = getReqSkillPoints(skillLevel);
        ChatColor reqSkillPointsColor = ChatColor.RED;
        if (playerPoints >= reqSkillPoints) {
            reqSkillPointsColor = ChatColor.GREEN;
        }

        lore.add(reqPlayerLevelColor + "Required Level: " + reqPlayerLevel);
        lore.add(reqSkillPointsColor + "Required Skill Points: " + reqSkillPoints);

        lore.add("");
        if (skillLevel == 0) {
            lore.add(ChatColor.AQUA + "Mana cost: " + getManaCost(skillLevel));
            lore.add(ChatColor.BLUE + "Cooldown: " + getCooldown(skillLevel));
        } else if (skillLevel == maxSkillLevel) {
            lore.add(ChatColor.AQUA + "Mana cost: " + getManaCost(skillLevel - 1));
            lore.add(ChatColor.BLUE + "Cooldown: " + getCooldown(skillLevel - 1));
        } else {
            lore.add(ChatColor.AQUA + "Mana cost: " + getManaCost(skillLevel - 1) + " -> " + getManaCost(skillLevel));
            lore.add(ChatColor.BLUE + "Cooldown: " + getCooldown(skillLevel - 1) + " -> " + getCooldown(skillLevel));
        }

        lore.add("");
        //skill attributes
        for (SkillComponent trigger : triggers) {
            lore.addAll(trigger.getSkillLoreAdditions(new ArrayList<>(), skillLevel));
        }

        lore.add("");
        lore.addAll(getDescription());

        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

        icon.setItemMeta(itemMeta);
        return icon;
    }

    public boolean cast(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        boolean didCast = false;
        for (SkillComponent trigger : triggers) {
            didCast = trigger.execute(caster, skillLevel, targets) || didCast;
        }
        return didCast;
    }

    public void addTrigger(SkillComponent skillComponent) {
        triggers.add(skillComponent);
    }

    public InitializeTrigger getInitializeTrigger() {
        for (SkillComponent skillComponent : triggers) {
            if (skillComponent instanceof InitializeTrigger) {
                return (InitializeTrigger) skillComponent;
            }
        }
        return null;
    }
}