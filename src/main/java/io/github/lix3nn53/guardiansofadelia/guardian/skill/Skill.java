package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Skill {

    String name;
    Material material;
    List<String> description;
    int maxSkillLevel;

    int reqPlayerLevel;
    double reqPlayerLevelIncreamentPerLevel;
    int reqPoints;
    double reqPointsIncreamentPerLevel;

    private int investedPoints;

    //skill attributes
    int manaCost;
    double manaCostIncreamentPerLevel;
    int cooldown;
    double cooldownIncreamentPerLevel;

    private final List<SkillComponent> triggers = new ArrayList<>();

    public Skill(String name, Material material, List<String> description, int maxSkillLevel, int reqPlayerLevel) {
        this.name = name;
        this.material = material;
        this.maxSkillLevel = maxSkillLevel;
        this.reqPlayerLevel = reqPlayerLevel;
        this.description = description;


    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public int getMaxSkillLevel() {
        return maxSkillLevel;
    }

    public int getReqPlayerLevel(int skillLevel) {
        return (int) (reqPlayerLevel + (skillLevel * reqPlayerLevelIncreamentPerLevel) + 0.5);
    }

    public int getReqSkillPoints(int skillLevel) {
        return (int) (reqPoints + (skillLevel * reqPointsIncreamentPerLevel) + 0.5);
    }

    public List<String> getDescription() {
        return description;
    }

    public ItemStack getIcon(int skillLevel, int playerLevel, int playerPoints) {

        ItemStack icon = new ItemStack(getMaterial());

        ItemMeta itemMeta = icon.getItemMeta();

        itemMeta.setDisplayName(getName() + " (" + investedPoints + "/" + getMaxSkillLevel() + ")");
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

        lore.add(reqPlayerLevelColor + "Required Level: " + reqSkillPoints);
        lore.add(reqSkillPointsColor + "Required Skill Points: " + reqSkillPoints);

        lore.add(ChatColor.YELLOW + "------------------------------");
        lore.add(ChatColor.AQUA + "Mana cost: " + getManaCost(playerLevel));
        lore.add(ChatColor.BLUE + "Cooldown: " + getCooldown(playerLevel));

        lore.add(ChatColor.YELLOW + "------------------------------");
        //skill attributes
        for (SkillComponent trigger : triggers) {
            lore.addAll(trigger.getSkillLoreAdditions(skillLevel));
        }

        lore.add(ChatColor.YELLOW + "------------------------------");
        lore.addAll(getDescription());

        itemMeta.setLore(lore);

        icon.setItemMeta(itemMeta);
        return icon;
    }

    public int getManaCost(int skillLevel) {
        return (int) (manaCost + (skillLevel * manaCostIncreamentPerLevel) + 0.5);
    }

    public int getCooldown(int skillLevel) {
        return (int) (cooldown + (skillLevel * cooldownIncreamentPerLevel) + 0.5);
    }

    public boolean canPlayersCast() {
        //TODO
        return true;
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
}