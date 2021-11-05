package io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.mechanics;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.ArmorStandWatcher;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class MMMechanicDisguise extends SkillMechanic implements ITargetedEntitySkill {

    private final DisguiseType disguiseType;
    private final boolean isAdult;
    private final String name;
    private final int duration;

    // State
    private final boolean invisible;

    // ArmorStand
    private final boolean marker;
    private final boolean small;

    // HeadItem
    private final Material material;
    private final int customModelData;

    public MMMechanicDisguise(MythicLineConfig config) {
        super(config.getLine(), config);
        this.setAsyncSafe(false);
        this.setTargetsCreativePlayers(false);

        this.disguiseType = DisguiseType.valueOf(config.getString(new String[]{"type", "ARMOR_STAND"}, "d").toUpperCase());
        this.isAdult = config.getBoolean(new String[]{"adult", "a"}, true);
        this.name = ChatColor.translateAlternateColorCodes('&', config.getString(new String[]{"name", "b"}, null));
        this.duration = config.getInteger(new String[]{"duration", "d"}, 0);

        this.invisible = config.getBoolean(new String[]{"invisible", "i"}, true);

        this.marker = config.getBoolean(new String[]{"marker", "m"}, false);
        this.small = config.getBoolean(new String[]{"small", "s"}, false);

        Material helmetMat = Material.valueOf(config.getString(new String[]{"helmet", "helmet"}, "STONE").toUpperCase());
        if (helmetMat == Material.STONE) {
            this.material = null;
        } else {
            this.material = helmetMat;
        }
        this.customModelData = config.getInteger(new String[]{"customModelData", "model"}, 0);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity abstractEntity) {
        LivingEntity target = (LivingEntity) BukkitAdapter.adapt(abstractEntity);

        MobDisguise disguise = new MobDisguise(disguiseType, isAdult);
        disguise = disguise.setReplaceSounds(true);
        LivingWatcher watcher = disguise.getWatcher();
        watcher.setInvisible(invisible);

        ItemStack nullItem = new ItemStack(Material.AIR);
        watcher.setItemInMainHand(nullItem);
        watcher.setItemInOffHand(nullItem);
        watcher.setArmor(new ItemStack[]{nullItem, nullItem, nullItem, nullItem});

        if (name != null) {
            watcher.setCustomName(name);
            watcher.setCustomNameVisible(true);
        }

        if (marker) {
            ArmorStandWatcher armorStandWatcher = (ArmorStandWatcher) watcher;
            armorStandWatcher.setMarker(true);
        }

        if (small) {
            ArmorStandWatcher armorStandWatcher = (ArmorStandWatcher) watcher;
            armorStandWatcher.setSmall(true);
        }

        if (material != null) {
            ItemStack itemStack = new ItemStack(material);
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (customModelData != 0) {
                itemMeta.setCustomModelData(customModelData);
            }
            itemStack.setItemMeta(itemMeta);

            watcher.setHelmet(itemStack);
        }

        DisguiseAPI.disguiseToAll(target, disguise);

        if (duration == 0) return true;

        new BukkitRunnable() {

            @Override
            public void run() {
                DisguiseAPI.undisguiseToAll(target);
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), duration);

        return true;
    }
}
