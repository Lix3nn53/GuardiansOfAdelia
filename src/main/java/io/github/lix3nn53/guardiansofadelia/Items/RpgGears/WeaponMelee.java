package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatPassive;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.NBTTagUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.RPGItemUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WeaponMelee implements RPGGear {

    private final int itemID;
    private final ItemTier tier;
    private final String itemTag;
    private final int level;
    private final RPGClass rpgClass;
    private ItemStack itemStack;

    public WeaponMelee(String name, ItemTier tier, String itemTag, Material material, int durability, int level, RPGClass rpgClass, int damage,
                       double bonusPercent, AttackSpeed attackSpeed, int minStatValue, int maxStatValue, int minNumberOfStats
            , int itemID) {
        name = tier.getTierColor() + itemTag + " " + name;
        damage = (int) ((damage * bonusPercent) + 0.5);

        List<String> lore = new ArrayList<>();

        StatPassive statPassive = new StatPassive(minStatValue, maxStatValue, minNumberOfStats);

        lore.add("");
        lore.add(ChatColor.DARK_PURPLE + "Required Level: " + ChatColor.GRAY + level);
        lore.add(ChatColor.DARK_PURPLE + "Required Class: " + rpgClass.getClassString());
        lore.add(ChatColor.YELLOW + "----------------");
        lore.add(ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "+" + damage);
        lore.add(ChatColor.AQUA + "ø Attack Speed: " + attackSpeed.getLoreString());
        lore.add(tier.getTierString());
        lore.add(ChatColor.YELLOW + "----------------");
        if (statPassive.getFire() != 0) {
            lore.add(ChatColor.RED + "☄ " + ChatColor.GRAY + "Fire: " + ChatColor.GRAY + "+" + statPassive.getFire());
        }
        if (statPassive.getWater() != 0) {
            lore.add(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + ChatColor.GRAY + "+" + statPassive.getWater());
        }
        if (statPassive.getEarth() != 0) {
            lore.add(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + ChatColor.GRAY + "+" + statPassive.getEarth());
        }
        if (statPassive.getLightning() != 0) {
            lore.add(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + ChatColor.GRAY + "+" + statPassive.getLightning());
        }
        if (statPassive.getAir() != 0) {
            lore.add(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Air: " + ChatColor.GRAY + "+" + statPassive.getAir());
        }
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "#" + itemID);

        this.itemStack = new ItemStack(material);
        this.itemStack = NBTTagUtils.putInteger("reqLevel", level, this.itemStack);
        this.itemStack = NBTTagUtils.putString("reqClass", rpgClass.getClassCode(), this.itemStack);
        this.itemStack = RPGItemUtils.setAttackSpeed(this.itemStack, attackSpeed.getSppedValue());
        this.itemStack = RPGItemUtils.setDamageWhenInMainHand(this.itemStack, damage);

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(durability);
        }
        this.itemStack.setItemMeta(itemMeta);

        this.itemID = itemID;
        this.tier = tier;
        this.itemTag = itemTag;
        this.level = level;
        this.rpgClass = rpgClass;
    }

    @Override
    public int getItemID() {
        return itemID;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public ItemTier getTier() {
        return tier;
    }

    @Override
    public String getItemTag() {
        return itemTag;
    }

    @Override
    public int getLevel() {
        return level;
    }

    public RPGClass getRpgClass() {
        return rpgClass;
    }
}
