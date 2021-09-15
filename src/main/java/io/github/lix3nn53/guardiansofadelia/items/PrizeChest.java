package io.github.lix3nn53.guardiansofadelia.items;

import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonPrizeChestManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PrizeChest {

    private final String code;
    private final String name;
    private final PrizeChestType type;
    private final List<Player> looted = new ArrayList<>();

    /**
     * @param dungeonTheme
     * @param type         0 for Weapon, 1 for Jewelry, 2 for Armor
     */
    public PrizeChest(DungeonTheme dungeonTheme, PrizeChestType type) {
        this.name = ChatPalette.GOLD + dungeonTheme.getName() + " Prize Chest (" + type.toString() + ")";
        this.code = dungeonTheme.getCode();
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void play(Player player, List<ItemStack> prizePool) {
        InventoryUtils.play(player, prizePool, getName());
    }

    public ItemStack getChest() {
        ItemStack chestItem = this.type.getChestItem();
        ItemMeta itemMeta = chestItem.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Right click while holding to open!");

        itemMeta.setDisplayName(getName());
        itemMeta.setLore(lore);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        chestItem.setItemMeta(itemMeta);

        PersistentDataContainerUtil.putString("prizeDungeon", code, chestItem);
        PersistentDataContainerUtil.putString("prizeType", type.name(), chestItem);

        return chestItem;
    }

    public void spawnChestModel(Location location) {
        ArmorStand model = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        model.setVisible(false);
        model.setInvulnerable(true);
        model.setSmall(true);
        model.setGravity(false);

        // if (HELMET != null) {
        ItemStack chestItem = type.getChestItem();
        EntityEquipment equipment = model.getEquipment();
        equipment.setHelmet(chestItem);

        // if (DISPLAY_TEXT != null) {
        // model.setCustomName(getName());
        // model.setCustomNameVisible(true);

        ArmorStand icon = (ArmorStand) location.add(0, 1.5, 0).getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        icon.setVisible(false);
        icon.setInvulnerable(true);
        icon.setSmall(true);
        icon.setGravity(false);

        // if (HELMET != null) {
        ItemStack chestIconItem = type.getChestIconItem();
        equipment = icon.getEquipment();
        equipment.setHelmet(chestIconItem);

        // if (DISPLAY_TEXT != null) {
        icon.setCustomName(getName());
        icon.setCustomNameVisible(true);

        DungeonPrizeChestManager.onSpawn(model, this, icon);
    }

    public boolean canLoot(Player player) {
        return !looted.contains(player);
    }

    public void onLoot(Player player) {
        looted.add(player);
    }
}
