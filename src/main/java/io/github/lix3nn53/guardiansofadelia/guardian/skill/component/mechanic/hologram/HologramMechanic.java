package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.hologram;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class HologramMechanic extends MechanicComponent {

    private final Material HELMET;
    private final int CUSTOMMODELDATA;
    private final List<Integer> DURATION;
    private String DISPLAYTEXT = "displayText";

    public HologramMechanic(Material helmet, int custommodeldata, List<Integer> seconds) {
        HELMET = helmet;
        CUSTOMMODELDATA = custommodeldata;
        DURATION = seconds;
    }

    public HologramMechanic(Material helmet, int custommodeldata, List<Integer> seconds, String displayText) {
        HELMET = helmet;
        CUSTOMMODELDATA = custommodeldata;
        DURATION = seconds;
        DISPLAYTEXT = displayText;
    }

    public HologramMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("helmetType")) {
            configLoadError("helmetType");
        }

        if (!configurationSection.contains("customModelData")) {
            configLoadError("customModelData");
        }

        if (!configurationSection.contains("durations")) {
            configLoadError("durations");
        }

        this.HELMET = Material.valueOf(configurationSection.getString("helmetType"));
        this.CUSTOMMODELDATA = configurationSection.getInt("customModelData");
        this.DURATION = configurationSection.getIntegerList("durations");

        if (configurationSection.contains("displayText")) {
            this.DISPLAYTEXT = configurationSection.getString("displayText");
        }
    }

    /**
     * passes created hologram to children
     *
     * @param caster
     * @param skillLevel
     * @param targets
     * @return
     */
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> armorStandList = new ArrayList<>();

        for (LivingEntity target : targets) {
            Location baseLocation = target.getLocation();

            ArmorStand model = (ArmorStand) baseLocation.getWorld().spawnEntity(baseLocation, EntityType.ARMOR_STAND);
            if (HELMET != null) {
                ItemStack itemStack = new ItemStack(HELMET);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(CUSTOMMODELDATA);
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                model.setHelmet(itemStack);
            }

            if (!DISPLAYTEXT.equals("displayText")) {
                final String text = DISPLAYTEXT.replaceAll("%caster%", caster.getName());
                model.setCustomName(text);
                model.setCustomNameVisible(true);
            }

            model.setInvulnerable(true);
            model.setGravity(false);
            model.setVisible(false);
            model.setSmall(true);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (model.isValid()) {
                        model.remove();
                    }
                }
            }.runTaskLater(GuardiansOfAdelia.getInstance(), 20L * DURATION.get(skillLevel - 1));

            //pass ArmorStand to children
            armorStandList.add(model);
        }

        return executeChildren(caster, skillLevel, armorStandList, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
