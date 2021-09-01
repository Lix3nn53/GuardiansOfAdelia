package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AbsorptionHeartMechanic extends MechanicComponent {

    private final List<Integer> heartAmountList;
    private final List<Integer> maxHeartList;

    public AbsorptionHeartMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("heartAmountList")) {
            configLoadError("heartAmountList");
        }

        if (!configurationSection.contains("maxHeartList")) {
            configLoadError("maxHeartList");
        }

        if (configurationSection.contains("heartAmountList")) {
            this.heartAmountList = configurationSection.getIntegerList("heartAmountList");
        } else {
            this.heartAmountList = new ArrayList<>();
        }

        if (configurationSection.contains("maxHeartList")) {
            this.maxHeartList = configurationSection.getIntegerList("maxHeartList");
        } else {
            this.maxHeartList = new ArrayList<>();
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        boolean healed = false;
        for (LivingEntity ent : targets) {
            if (!(ent instanceof Player)) continue;
            Player player = (Player) ent;

            CraftPlayer craftPlayer = (CraftPlayer) player;
            EntityPlayer entityPlayer = craftPlayer.getHandle();

            float currentHearts = entityPlayer.getAbsorptionHearts();

            int maxHearts = 0;
            if (!maxHeartList.isEmpty()) {
                maxHearts = maxHeartList.get(skillLevel - 1);
            }

            if (currentHearts >= maxHearts) continue;

            int heartAmount = 0;
            if (!heartAmountList.isEmpty()) {
                heartAmount = heartAmountList.get(skillLevel - 1);
            }

            if (heartAmount <= 0) {
                return false;
            }

            float nextHeart = currentHearts + heartAmount;

            if (nextHeart > maxHearts) {
                nextHeart = maxHearts;
            }

            entityPlayer.setAbsorptionHearts(nextHeart);
            healed = true;
        }

        return healed;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (!heartAmountList.isEmpty()) {
            if (skillLevel == 0) {
                String lore = ChatColor.GREEN + "Golden Heart: +" + heartAmountList.get(skillLevel) + "[Max " + maxHeartList.get(skillLevel) + "]";

                additions.add(lore);
            } else if (skillLevel == heartAmountList.size()) {
                String lore = ChatColor.GREEN + "Golden Heart: +" + heartAmountList.get(skillLevel - 1) + "[Max " + maxHeartList.get(skillLevel - 1) + "]";

                additions.add(lore);
            } else {
                String lore1 = ChatColor.GREEN + "Golden Heart: +" + heartAmountList.get(skillLevel - 1) + "[Max " + maxHeartList.get(skillLevel - 1) + "]";
                String lore2 = heartAmountList.get(skillLevel) + "[Max " + maxHeartList.get(skillLevel) + "]";

                additions.add(ChatColor.GREEN + lore1 + " -> " + lore2);
            }
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
