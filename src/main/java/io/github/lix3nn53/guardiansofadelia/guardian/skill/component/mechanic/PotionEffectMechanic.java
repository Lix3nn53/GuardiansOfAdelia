package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PotionEffectMechanic extends MechanicComponent {

    private final PotionEffectType potionEffectType;
    private final List<Integer> tickList;
    private final List<Integer> amplifierList;

    public PotionEffectMechanic(PotionEffectType potionEffectType, List<Integer> tickList, List<Integer> amplifierList) {
        this.potionEffectType = potionEffectType;
        this.tickList = tickList;
        this.amplifierList = amplifierList;
    }

    public PotionEffectMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("potionEffectType")) {
            configLoadError("potionEffectType");
        }

        if (!configurationSection.contains("tickList")) {
            configLoadError("tickList");
        }

        if (!configurationSection.contains("amplifierList")) {
            configLoadError("amplifierList");
        }

        this.potionEffectType = PotionEffectType.getByName(configurationSection.getString("potionEffectType"));
        this.tickList = configurationSection.getIntegerList("tickList");
        this.amplifierList = configurationSection.getIntegerList("amplifierList");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        PotionEffect potionEffect = new PotionEffect(potionEffectType, tickList.get(skillLevel - 1), amplifierList.get(skillLevel - 1));

        for (LivingEntity target : targets) {
            target.addPotionEffect(potionEffect);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        String effectString = getEffectString();
        if (effectString != null) {
            if (skillLevel == 0) {
                additions.add(effectString + " duration: " + (tickList.get(skillLevel) / 20));
                additions.add(effectString + " tier: " + amplifierList.get(skillLevel));
            } else if (skillLevel == tickList.size()) {
                additions.add(effectString + " duration: " + (tickList.get(skillLevel - 1) / 20));
                additions.add(effectString + " tier: " + amplifierList.get(skillLevel - 1));
            } else {
                additions.add(effectString + " duration: " + (tickList.get(skillLevel - 1) / 20) + " -> " + (tickList.get(skillLevel) / 20));
                additions.add(effectString + " tier: " + amplifierList.get(skillLevel - 1) + " -> " + amplifierList.get(skillLevel));
            }
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    private String getEffectString() {
        if (potionEffectType.equals(PotionEffectType.SLOW)) {
            return ChatColor.GRAY + "Slow";
        } else if (potionEffectType.equals(PotionEffectType.JUMP)) {
            return ChatColor.YELLOW + "Jump";
        } else if (potionEffectType.equals(PotionEffectType.SPEED)) {
            return ChatColor.LIGHT_PURPLE + "Speed";
        } else if (potionEffectType.equals(PotionEffectType.LEVITATION)) {
            return ChatColor.AQUA + "Levitation";
        } else if (potionEffectType.equals(PotionEffectType.WITHER)) {
            return ChatColor.DARK_PURPLE + "Wither";
        } else if (potionEffectType.equals(PotionEffectType.POISON)) {
            return ChatColor.DARK_GREEN + "Poison";
        } else if (potionEffectType.equals(PotionEffectType.ABSORPTION)) {
            return ChatColor.YELLOW + "Shield";
        }
        return null;
    }
}
