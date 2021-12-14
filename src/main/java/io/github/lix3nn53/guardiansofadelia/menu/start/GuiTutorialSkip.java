package io.github.lix3nn53.guardiansofadelia.menu.start;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CharacterSelectionScreenManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiTutorialSkip extends GuiGeneric {

    private final int charNo;

    public GuiTutorialSkip(GuardianData guardianData, int charNo) {
        super(27, CustomCharacterGui.MENU_27_FLAT.toString() + ChatPalette.BLACK +
                Translation.t(guardianData, "general.tutorial.question") + " #" + charNo, 0);
        this.charNo = charNo;

        ItemStack yes = new ItemStack(Material.LIME_WOOL);
        ItemMeta itemMeta = yes.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + Translation.t(guardianData, "general.tutorial.true"));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        yes.setItemMeta(itemMeta);
        this.setItem(11, yes);

        ItemStack no = new ItemStack(Material.RED_WOOL);
        itemMeta.setDisplayName(ChatPalette.RED + Translation.t(guardianData, "general.tutorial.false"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        itemMeta.setLore(lore);
        no.setItemMeta(itemMeta);
        this.setItem(15, no);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        int slot = event.getSlot();
        if (slot == 11) {
            CharacterSelectionScreenManager.createCharacter(player, charNo);
        } else if (slot == 15) {
            CharacterSelectionScreenManager.createCharacterWithoutTutorial(player, charNo);
        }
    }
}
