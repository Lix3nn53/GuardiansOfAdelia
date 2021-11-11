package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class GodDamageMechanic extends MechanicComponent {

    private final List<Float> damageList;
    private final List<Float> damagePercentList;

    public GodDamageMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("damageList") && !configurationSection.contains("damagePercentList")) {
            configLoadError("damageList");
            configLoadError("damagePercentList");
        }

        if (configurationSection.contains("damageList")) {
            this.damageList = configurationSection.getFloatList("damageList");
        } else {
            this.damageList = new ArrayList<>();
        }

        if (configurationSection.contains("damagePercentList")) {
            this.damagePercentList = configurationSection.getFloatList("damagePercentList");
        } else {
            this.damagePercentList = new ArrayList<>();
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;


        for (LivingEntity ent : targets) {
            float damage = 0;
            if (!damagePercentList.isEmpty()) {
                AttributeInstance attribute = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (attribute == null) continue;

                float damagePercent = damagePercentList.get(skillLevel - 1);
                float value = (float) attribute.getValue();
                damage = value * damagePercent;
            }
            if (!damageList.isEmpty()) {
                float damageAmount = damageList.get(skillLevel - 1);
                damage += damageAmount;
            }

            ent.setNoDamageTicks(0);
            ent.damage(damage);

            new BukkitRunnable() {
                @Override
                public void run() {
                    ent.setNoDamageTicks(0);
                }
            }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 1L);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            if (!damagePercentList.isEmpty()) {
                additions.add("God Damage: " + (damagePercentList.get(skillLevel) + 0.5) + "%");
            }
            if (!damageList.isEmpty()) {
                additions.add("God Damage: " + (int) (damageList.get(skillLevel) + 0.5));
            }
        } else if (skillLevel == damageList.size() || skillLevel == damagePercentList.size()) {
            if (!damagePercentList.isEmpty()) {
                additions.add("God Damage: " + (damagePercentList.get(skillLevel - 1) + 0.5) + "%");
            }
            if (!damageList.isEmpty()) {
                additions.add("God Damage: " + (int) (damageList.get(skillLevel - 1) + 0.5));
            }
        } else {
            if (!damagePercentList.isEmpty()) {
                String lore1 = "God Damage: " + (damagePercentList.get(skillLevel - 1) + 0.5) + "%";
                String lore2 = "God Damage: " + (damagePercentList.get(skillLevel) + 0.5) + "%";
                additions.add(lore1 + " -> " + lore2);
            }
            if (!damageList.isEmpty()) {
                String lore1 = "God Damage: " + (int) (damageList.get(skillLevel - 1) + 0.5);
                String lore2 = "God Damage: " + (int) (damageList.get(skillLevel) + 0.5);
                additions.add(lore1 + " -> " + lore2);
            }
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
