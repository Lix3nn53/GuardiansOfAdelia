package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PotionEffectMechanic extends MechanicComponent {

    private final List<PotionEffectType> potionEffectTypes = new ArrayList<>();
    private final List<Integer> tickList;
    private final List<Integer> amplifierList;

    private final String multiplyDurationValue;

    public PotionEffectMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("potionEffectType")) {
            configLoadError("potionEffectType");
        }

        if (!configurationSection.contains("tickList")) {
            configLoadError("tickList");
        }

        if (!configurationSection.contains("amplifierList")) {
            configLoadError("amplifierList");
        }

        List<String> potionEffectTypeStr = configurationSection.getStringList("potionEffectType");
        for (String potionStr : potionEffectTypeStr) {
            PotionEffectType byName = PotionEffectType.getByName(potionStr);
            potionEffectTypes.add(byName);
        }
        this.tickList = configurationSection.getIntegerList("tickList");
        this.amplifierList = configurationSection.getIntegerList("amplifierList");

        this.multiplyDurationValue = configurationSection.contains("multiplyDurationValue") ? configurationSection.getString("multiplyDurationValue") : null;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        int ticks = tickList.get(skillLevel - 1);
        for (PotionEffectType potionEffectType : potionEffectTypes) {
            for (LivingEntity target : targets) {
                int ticksCurrent = ticks;
                if (multiplyDurationValue != null) {
                    int value = SkillDataManager.getValue(target, multiplyDurationValue);
                    if (value > 0) {
                        ticksCurrent *= value;
                    }
                }

                PotionEffect potionEffect = new PotionEffect(potionEffectType, ticksCurrent, amplifierList.get(skillLevel - 1));

                target.addPotionEffect(potionEffect);
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);

        for (PotionEffectType potionEffectType : potionEffectTypes) {
            String effectString = getEffectString(potionEffectType);
            if (effectString != null) {
                if (skillLevel == 0) {
                    String s = effectString + " duration: " + (tickList.get(skillLevel) / 20);

                    if (multiplyDurationValue != null) {
                        s += " x[" + multiplyDurationValue + "]";
                    }

                    additions.add(s);
                    additions.add(effectString + " tier: " + amplifierList.get(skillLevel));
                } else if (skillLevel == tickList.size()) {
                    String s = effectString + " duration: " + (tickList.get(skillLevel - 1) / 20);

                    if (multiplyDurationValue != null) {
                        s += " x[" + multiplyDurationValue + "]";
                    }

                    additions.add(s);
                    additions.add(effectString + " tier: " + amplifierList.get(skillLevel - 1));
                } else {
                    String s = effectString + " duration: " + (tickList.get(skillLevel - 1) / 20) + " -> " + (tickList.get(skillLevel) / 20);

                    if (multiplyDurationValue != null) {
                        s += " x[" + multiplyDurationValue + "]";
                    }

                    additions.add(s);
                    additions.add(effectString + " tier: " + amplifierList.get(skillLevel - 1) + " -> " + amplifierList.get(skillLevel));
                }
            }
        }

        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }

    private String getEffectString(PotionEffectType potionEffectType) {
        if (potionEffectType.equals(PotionEffectType.SLOW)) {
            return ChatPalette.GRAY + "Slow";
        } else if (potionEffectType.equals(PotionEffectType.JUMP)) {
            return ChatPalette.YELLOW + "Jump";
        } else if (potionEffectType.equals(PotionEffectType.SPEED)) {
            return ChatPalette.PURPLE_LIGHT + "Speed";
        } else if (potionEffectType.equals(PotionEffectType.LEVITATION)) {
            return ChatPalette.BLUE_LIGHT + "Levitation";
        } else if (potionEffectType.equals(PotionEffectType.WITHER)) {
            return ChatPalette.PURPLE + "Wither";
        } else if (potionEffectType.equals(PotionEffectType.POISON)) {
            return ChatPalette.GREEN_DARK + "Poison";
        } else if (potionEffectType.equals(PotionEffectType.ABSORPTION)) {
            return ChatPalette.YELLOW + "Shield";
        }
        return null;
    }
}
