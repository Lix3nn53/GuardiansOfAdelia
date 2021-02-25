package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AbsorptionHeartMechanic extends MechanicComponent {

    private final List<Integer> heartAmountList;
    private final int maxHearts;
    private final String multiplyWithValue;

    public AbsorptionHeartMechanic(List<Integer> heartAmountList, int maxHearts, @Nullable String multiplyWithValue) {
        this.heartAmountList = heartAmountList;
        this.maxHearts = maxHearts;
        this.multiplyWithValue = multiplyWithValue;
    }

    public AbsorptionHeartMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("heartAmountList")) {
            configLoadError("heartAmountList");
        }

        if (!configurationSection.contains("maxHearts")) {
            configLoadError("maxHearts");
        }

        if (configurationSection.contains("heartAmountList")) {
            this.heartAmountList = configurationSection.getIntegerList("heartAmountList");
        } else {
            this.heartAmountList = new ArrayList<>();
        }

        if (configurationSection.contains("maxHearts")) {
            this.maxHearts = configurationSection.getInt("maxHearts");
        } else {
            this.maxHearts = 100;
        }

        if (configurationSection.contains("multiplyWithValue")) {
            this.multiplyWithValue = configurationSection.getString("multiplyWithValue");
        } else {
            this.multiplyWithValue = null;
        }

    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        boolean healed = false;
        for (LivingEntity ent : targets) {
            if (!(ent instanceof EntityPlayer)) continue;

            EntityPlayer entityPlayer = (EntityPlayer) ent;

            float currentHearts = entityPlayer.getAbsorptionHearts();

            if (currentHearts >= maxHearts) continue;

            int heartAmount = 0;
            if (!heartAmountList.isEmpty()) {
                heartAmount = heartAmountList.get(skillLevel - 1);
            }

            if (multiplyWithValue != null) {
                int value = SkillDataManager.getValue(caster, multiplyWithValue);
                heartAmount *= value;
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
        if (!heartAmountList.isEmpty()) {
            if (skillLevel == 0) {
                String lore = ChatColor.GREEN + "Absorption Heart: " + heartAmountList.get(skillLevel) + "(Max " + maxHearts + ")";

                if (multiplyWithValue != null) {
                    lore = lore + "x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else if (skillLevel == heartAmountList.size()) {
                String lore = ChatColor.GREEN + "Absorption Heart: " + heartAmountList.get(skillLevel - 1);

                if (multiplyWithValue != null) {
                    lore = lore + "x[" + multiplyWithValue + "]";
                }

                additions.add(lore);
            } else {
                String lore1 = "Absorption Heart: " + heartAmountList.get(skillLevel - 1);
                String lore2 = heartAmountList.get(skillLevel) + "";

                if (multiplyWithValue != null) {
                    lore1 = lore1 + "x[" + multiplyWithValue + "]";
                    lore2 = lore2 + "x[" + multiplyWithValue + "]";
                }

                additions.add(ChatColor.GREEN + lore1 + " -> " + lore2);
            }
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
