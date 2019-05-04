package io.github.lix3nn53.guardiansofadelia.utilities;

import com.google.common.collect.ImmutableList;
import com.sucy.skill.dynamic.ComponentType;
import com.sucy.skill.dynamic.custom.CustomEffectComponent;
import com.sucy.skill.dynamic.custom.EditorOption;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.ChatColor;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SkillAPIHologramMechanic extends CustomEffectComponent {

    private static final String HELMET = "helmet";
    private static final String CUSTOMMODELDATA = "customModelData";
    private static final String ITEMTYPE = "itemType";
    private static final String DISPLAYTEXT = "displayText";
    private static final String TEXT = "text";
    private static final String DURATION = "duration";
    private final String key = "Custom Hologram";

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.MECHANIC;
    }

    @Override
    public String getDescription() {
        return "Spawn a hologram";
    }

    @Override
    public List<EditorOption> getOptions() {
        List<String> materials = Stream.of(Material.values())
                .map(Material::name)
                .collect(Collectors.toList());
        return ImmutableList.of(
                EditorOption.dropdown(
                        "helmet",
                        "Equip Helmet",
                        "Whether or not to equip helmet",
                        ImmutableList.of("False", "True")),
                EditorOption.number(
                        "customModelData",
                        "Custom Model Data",
                        "CustomModelData of item on head",
                        10000001,
                        0),
                EditorOption.dropdown(
                        "itemType",
                        "Item Type",
                        "Item on head of hologram",
                        ImmutableList.copyOf(materials)),
                EditorOption.dropdown(
                        "displayText",
                        "Display Text",
                        "Whether or not to display text",
                        ImmutableList.of("False", "True")),
                EditorOption.text(
                        "text",
                        "Text",
                        "Hologram text",
                        "text"),
                EditorOption.number(
                        "duration",
                        "Duration",
                        "How long in seconds hologram will stay",
                        30,
                        0)
        );
    }

    @Override
    public boolean execute(LivingEntity caster, int level, List<LivingEntity> list) {
        final boolean helmet = settings.getBool(HELMET, false);
        final boolean displayText = settings.getBool(DISPLAYTEXT, false);

        final int duration = (int) (parseValues(caster, DURATION, level, 4) + 0.5);

        Location baseLocation = caster.getLocation();
        ArmorStand model = (ArmorStand) baseLocation.getWorld().spawnEntity(baseLocation, EntityType.ARMOR_STAND);
        if (helmet) {
            final int customModelData = (int) (parseValues(caster, CUSTOMMODELDATA, level, 4) + 0.5);
            final String itemType = settings.getString(ITEMTYPE);

            ItemStack itemStack = new ItemStack(Material.valueOf(itemType));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setCustomModelData(customModelData);
            itemMeta.setUnbreakable(true);
            itemStack.setItemMeta(itemMeta);
            model.setHelmet(itemStack);
        }
        if (displayText) {
            final String text = ChatColor.translateAlternateColorCodes('&', settings.getString(TEXT)).replaceAll("%caster%", caster.getName());
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
        executeChildren(caster, level, armorStandList);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!model.isDead()) {
                    model.remove();
                }
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20L * duration);
        return true;
    }
}
