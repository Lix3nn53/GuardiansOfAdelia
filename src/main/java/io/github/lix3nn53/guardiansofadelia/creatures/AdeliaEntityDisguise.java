package io.github.lix3nn53.guardiansofadelia.creatures;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.LivingWatcher;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

public enum AdeliaEntityDisguise {
    ALEESIA;

    public MobDisguise getDisguise(double maxHealth) {
        switch (this) {
            case ALEESIA:
                MobDisguise disguise = new MobDisguise(DisguiseType.WITHER_SKELETON, false);

                LivingWatcher watcher = disguise.getWatcher();

                watcher.setCustomNameVisible(true);
                watcher.setCustomName(ChatColor.DARK_PURPLE + "Aleesia");
                watcher.setInvisible(true);
                watcher.setNoGravity(true);
                watcher.addPotionEffect(PotionEffectType.GLOWING);
                watcher.setMaxHealth(maxHealth);
                watcher.setHealth((float) maxHealth);

                EntityEquipment mobEquipment = watcher.getEquipment();

                ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(11);
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                mobEquipment.setHelmet(itemStack);

                return disguise;
        }

        return null;
    }
}
