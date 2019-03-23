package io.github.lix3nn53.guardiansofadelia.creatures.drops;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

class MobDropGenerator {

    public static List<ItemStack> getDrops(Entity entity) {
        List<ItemStack> drops = new ArrayList<ItemStack>();

        drops.add(Weapons.getWeapon(RPGClass.KNIGHT, 5, ItemTier.COMMON, "Tag", 1, 1, 1));
        return drops;
    }

}
