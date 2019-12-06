package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class DamageMechanic extends MechanicComponent {

    private List<Double> damage;
    private DamageType damageType;

    public DamageMechanic(List<Double> damage, DamageType damageType) {
        this.damage = damage;
        this.damageType = damageType;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
        if (targets.isEmpty()) return false;

        double calcDamage = damage.get(skillLevel - 1);

        for (LivingEntity ent : targets) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage("caster: " + caster.getName() + "");
                player.sendMessage("Target: " + ent.getName() + "");
                player.sendMessage("Damage Mechanic: " + calcDamage + "");
            }
            SkillUtils.setDamageType(damageType);
            ent.setNoDamageTicks(0);
            ent.damage(calcDamage, caster);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(damageType.toString() + ": " + (int) (damage.get(skillLevel) + 0.5));
        } else if (skillLevel == damage.size()) {
            additions.add(damageType.toString() + ": " + (int) (damage.get(skillLevel - 1) + 0.5));
        } else {
            additions.add(damageType.toString() + ": " + (int) (damage.get(skillLevel - 1) + 0.5) + " -> " + (int) (damage.get(skillLevel) + 0.5));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }


    public enum DamageType {
        MAGIC, MELEE, RANGED;

        @Override
        public String toString() {
            switch (this) {
                case MAGIC:
                    return ChatColor.BLUE + "Magic Damage";
                case MELEE:
                    return ChatColor.RED + "Melee Damage";
                case RANGED:
                    return ChatColor.GOLD + "Ranged Damage";
            }
            return "NULL";
        }
    }
}
