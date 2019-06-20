package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.hologram;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
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

public class HologramMechanic {

    private final Material HELMET;
    private final int CUSTOMMODELDATA;
    private final int DURATION;
    private String DISPLAYTEXT = "displayText";

    public HologramMechanic(Material helmet, int custommodeldata, int seconds) {
        HELMET = helmet;
        CUSTOMMODELDATA = custommodeldata;
        DURATION = seconds;
    }

    public HologramMechanic(Material helmet, int custommodeldata, int seconds, String displayText) {
        HELMET = helmet;
        CUSTOMMODELDATA = custommodeldata;
        DURATION = seconds;
        DISPLAYTEXT = displayText;
    }

    public boolean execute(LivingEntity caster, int level, List<LivingEntity> list) {
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

        //pass ArmorStand to children
        List<LivingEntity> armorStandList = new ArrayList<>();
        armorStandList.add(model);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!model.isDead()) {
                    model.remove();
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20L * DURATION);
        return true;
    }
}
