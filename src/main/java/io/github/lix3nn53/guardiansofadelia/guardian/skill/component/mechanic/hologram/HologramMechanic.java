package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.hologram;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.Location;
import org.bukkit.Material;
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

    /**
     * passes created hologram to children
     *
     * @param caster
     * @param skillLevel
     * @param list
     * @return
     */
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> list, String castKey) {
        Location baseLocation = caster.getLocation();

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
                if (!model.isDead()) {
                    model.remove();
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20L * DURATION.get(skillLevel - 1));

        //pass ArmorStand to children
        List<LivingEntity> armorStandList = new ArrayList<>();
        armorStandList.add(model);

        return executeChildren(caster, skillLevel, armorStandList, castKey);
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        return new ArrayList<>();
    }
}
