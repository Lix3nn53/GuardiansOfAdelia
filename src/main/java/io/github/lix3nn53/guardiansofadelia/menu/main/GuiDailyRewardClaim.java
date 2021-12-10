package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.rewards.daily.DailyRewardHandler;
import io.github.lix3nn53.guardiansofadelia.rewards.daily.DailyRewardInfo;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuiDailyRewardClaim extends GuiGeneric {

    public GuiDailyRewardClaim(Player player) {
        super(27, ChatPalette.GRAY_DARK + "Daily Reward Claim", 0);

        if (!GuardianDataManager.hasGuardianData(player)) return;

        GuardianData guardianData = GuardianDataManager.getGuardianData(player);

        ItemStack pastFail = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = pastFail.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.RED + "You missed this reward");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "");
        itemMeta.setLore(lore);
        pastFail.setItemMeta(itemMeta);

        ItemStack pastSuccess = new ItemStack(Material.GREEN_WOOL);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "You claimed this reward");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "");
        itemMeta.setLore(lore);
        pastSuccess.setItemMeta(itemMeta);

        ItemStack available = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "You can claim today's reward");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "");
        itemMeta.setLore(lore);
        available.setItemMeta(itemMeta);

        ItemStack notAvailableToday = new ItemStack(Material.PURPLE_WOOL);
        itemMeta.setDisplayName(ChatPalette.PURPLE + "You can't claim today's reward");
        lore = new ArrayList<>();
        lore.add(ChatPalette.PURPLE + "because you missed yesterday's reward.");
        itemMeta.setLore(lore);
        notAvailableToday.setItemMeta(itemMeta);

        ItemStack notAvailable = new ItemStack(Material.GRAY_WOOL);
        itemMeta.setDisplayName(ChatPalette.GRAY + "Not available yet");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "");
        itemMeta.setLore(lore);
        notAvailable.setItemMeta(itemMeta);

        DailyRewardInfo dailyRewardInfo = guardianData.getDailyRewardInfo();
        LocalDate lastObtainDate = dailyRewardInfo.getLastObtainDate();

        int playerIndex = DailyRewardHandler.getIndexOfDate(lastObtainDate);
        int currentIndex = DailyRewardHandler.getCurrentIndex();

        //today
        if (currentIndex - 1 == playerIndex) {
            this.setItem(currentIndex, available);
            this.setItem(currentIndex + 18, available);
        } else {
            this.setItem(currentIndex, notAvailableToday);
            this.setItem(currentIndex + 18, notAvailableToday);
        }

        //past success
        for (int i = 1; i <= playerIndex; i++) {
            this.setItem(i, pastSuccess);
            this.setItem(i + 18, pastSuccess);
        }

        //past fail
        for (int i = playerIndex + 1; i < currentIndex; i++) {
            if (i == 0) continue;

            this.setItem(i, pastFail);
            this.setItem(i + 18, pastFail);
        }

        //future non-available
        for (int i = currentIndex + 1; i < 8; i++) {
            this.setItem(i, notAvailable);
            this.setItem(i + 18, notAvailable);
        }

        ItemStack filler = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        itemMeta.setDisplayName(ChatPalette.YELLOW + "Daily Rewards");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Every week, you bla bla TODO explanation");
        itemMeta.setLore(lore);
        filler.setItemMeta(itemMeta);

        int fillerIndex = 0;
        this.setItem(fillerIndex, filler);
        this.setItem(fillerIndex + 9, filler);
        this.setItem(fillerIndex + 18, filler);

        fillerIndex = 8;
        this.setItem(fillerIndex, filler);
        this.setItem(fillerIndex + 9, filler);
        this.setItem(fillerIndex + 18, filler);

        ItemStack[] rewards = DailyRewardHandler.getRewards();
        int i = 10;
        for (ItemStack itemStack : rewards) {
            if (itemStack == null) continue;

            this.setItem(i, itemStack);
            i++;
        }

        this.setLocked(true);
        this.lockOwnInventory();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            Material currentType = event.getCurrentItem().getType();

            if (currentType.equals(Material.LIME_WOOL)) {
                DailyRewardHandler.giveReward(player);

                GuiDailyRewardClaim gui = new GuiDailyRewardClaim(player);
                gui.openInventory(player);
            }
        }
    }
}
