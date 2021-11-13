package io.github.lix3nn53.guardiansofadelia.menu.main.character;

import io.github.lix3nn53.guardiansofadelia.chat.ChatTag;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiCharacterChatTag extends GuiGeneric {

    private final HashMap<Integer, ChatTag> slotToChatTag = new HashMap<>();

    public GuiCharacterChatTag(Player player) {
        super(27, ChatPalette.GRAY_DARK + "Chat Tag", 0);

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                List<Integer> turnedInQuests = activeCharacter.getTurnedInQuests();

                int i = 0;
                for (ChatTag chatTag : ChatTag.values()) {
                    int requiredQuest = chatTag.getRequiredQuest();
                    ItemStack itemStack = new ItemStack(Material.RED_WOOL);
                    ChatPalette questColor = ChatPalette.RED;
                    if (turnedInQuests.contains(requiredQuest)) {
                        itemStack = new ItemStack(Material.LIME_WOOL);
                        questColor = ChatPalette.GREEN_DARK;
                    }

                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(chatTag.getChatPalette() + chatTag.toString());

                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(questColor + Translation.t(guardianData, "quest.required") + ": " + requiredQuest);
                    lore.add("");
                    lore.add(ChatPalette.GRAY + "Click to select this chat tag");
                    itemMeta.setLore(lore);

                    itemStack.setItemMeta(itemMeta);

                    this.setItem(i, itemStack);
                    slotToChatTag.put(i, chatTag);
                    i++;
                }
            }
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GuardianData guardianData;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);
        } else {
            return;
        }

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            int slot = event.getSlot();
            if (slotToChatTag.containsKey(slot)) {
                ChatTag chatTag = slotToChatTag.get(slot);

                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();
                rpgCharacter.setChatTag(chatTag);

                MessageUtils.sendCenteredMessage(player, ChatPalette.GOLD + "You selected a new chat tag: " + chatTag.getChatPalette() + chatTag);
            }
        }
    }
}
