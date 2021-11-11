package io.github.lix3nn53.guardiansofadelia.guardian;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.chat.PremiumRank;
import io.github.lix3nn53.guardiansofadelia.chat.StaffRank;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.rewards.daily.DailyRewardInfo;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.Invite;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class GuardianData {

    private final GuiGeneric personalStorage = new GuiGeneric(54, "Personal Storage", 0);
    private final GuiGeneric bazaarStorage = new GuiGeneric(54, "Bazaar Storage", 0);
    private final GuiGeneric premiumStorage = new GuiGeneric(54, "Premium Storage", 0);

    private RPGCharacter activeCharacter;
    private int activeCharacterNo = 0;

    private Invite pendingInvite;
    private BukkitTask inviteTimeoutTask;

    private StaffRank staffRank = StaffRank.NONE;
    private PremiumRank premiumRank = PremiumRank.NONE;

    private List<Player> friends = new ArrayList<>();

    private Gui activeGui;

    private boolean isTeleporting;
    private boolean isGathering;

    private Bazaar bazaar;

    private final DailyRewardInfo dailyRewardInfo = new DailyRewardInfo();

    private String language = null;

    public GuardianData() {
        personalStorage.setLocked(false);
    }

    public RPGCharacter getActiveCharacter() {
        return activeCharacter;
    }

    public void setActiveCharacter(RPGCharacter activeCharacter, int activeCharacterNo) {
        this.activeCharacter = activeCharacter;
        this.activeCharacterNo = activeCharacterNo;
    }

    public boolean hasActiveCharacter() {
        return this.activeCharacterNo != 0;
    }

    public int getActiveCharacterNo() {
        return activeCharacterNo;
    }

    public boolean isFreeToAct() {
        return !(isGathering || isTeleporting);
    }

    public boolean hasPendingInvite() {
        return this.pendingInvite != null;
    }

    public void clearPendingInvite() {
        this.pendingInvite = null;
        if (this.inviteTimeoutTask != null) {
            this.inviteTimeoutTask.cancel();
        }
    }

    public Invite getPendingInvite() {
        return pendingInvite;
    }

    public void setPendingInvite(Invite pendingInvite, Player receiver) {
        this.pendingInvite = pendingInvite;

        if (this.inviteTimeoutTask != null) {
            this.inviteTimeoutTask.cancel();
        }
        this.inviteTimeoutTask = new BukkitRunnable() {

            @Override
            public void run() {
                if (hasPendingInvite()) {
                    clearPendingInvite();
                    receiver.sendMessage(ChatPalette.RED + "Invite timeout");
                }
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 20L * 30);
    }

    public ItemStack[] getPersonalStorage() {
        return personalStorage.getContents();
    }

    public void setPersonalStorage(ItemStack[] personalStorage) {
        this.personalStorage.setContents(personalStorage);
    }

    public ItemStack[] getBazaarStorage() {
        return bazaarStorage.getContents();
    }

    public void setBazaarStorage(ItemStack[] bazaarStorage) {
        this.bazaarStorage.setContents(bazaarStorage);
    }

    public ItemStack[] getPremiumStorage() {
        return premiumStorage.getContents();
    }

    public void setPremiumStorage(ItemStack[] premiumStorage) {
        this.premiumStorage.setContents(premiumStorage);
    }

    public GuiGeneric getPersonalStorageGui() {
        return this.personalStorage;
    }

    public GuiGeneric getBazaarStorageGui() {
        return this.bazaarStorage;
    }

    public GuiGeneric getPremiumStorageGui() {
        return this.premiumStorage;
    }

    public boolean addToBazaarStorage(ItemStack itemStack) {
        if (this.bazaarStorage.anyEmpty()) {
            //inventory has empty slot
            this.bazaarStorage.addItem(itemStack);
            return true;
        }
        return false;
    }

    public boolean addToPremiumStorage(ItemStack itemStack) {
        if (this.premiumStorage.anyEmpty()) {
            //inventory has empty slot
            this.premiumStorage.addItem(itemStack);
            return true;
        }
        return false;
    }

    public boolean bazaarStorageIsEmpty() {
        return this.bazaarStorage.anyEmpty();
    }

    public void removeFromBazaarStorage(ItemStack itemStack, int amount) {
        this.bazaarStorage.removeItem(itemStack, amount);
    }

    public void setTeleporting(boolean teleporting) {
        this.isTeleporting = teleporting;
    }

    public void setGathering(boolean gathering) {
        this.isGathering = gathering;
    }

    public boolean hasActiveGui() {
        return this.activeGui != null;
    }

    public void clearActiveGui() {
        this.activeGui = null;
    }

    public Gui getActiveGui() {
        return this.activeGui;
    }

    public void setActiveGui(Gui gui) {
        this.activeGui = gui;
    }

    public Bazaar getBazaar() {
        return bazaar;
    }

    public void setBazaar(Bazaar bazaar) {
        this.bazaar = bazaar;
    }

    public boolean hasBazaar() {
        return this.bazaar != null;
    }

    public List<Player> getFriends() {
        return friends;
    }

    public void setFriends(List<Player> friends) {
        this.friends = friends;
    }

    public StaffRank getStaffRank() {
        return staffRank;
    }

    public void setStaffRank(StaffRank staffRank) {
        this.staffRank = staffRank;
    }

    public PremiumRank getPremiumRank() {
        return premiumRank;
    }

    public void setPremiumRank(PremiumRank premiumRank) {
        this.premiumRank = premiumRank;
    }

    public DailyRewardInfo getDailyRewardInfo() {
        return dailyRewardInfo;
    }

    public void setLanguage(Player player, String language) {
        player.sendMessage(ChatPalette.YELLOW + "Changing to language: " + language);
        if (!Translation.exists(language)) {
            language = Translation.DEFAULT_LANG;
            player.sendMessage(ChatPalette.RED + "This language does not exist, using default: " + language);
        } else {
            player.sendMessage(ChatPalette.GREEN_DARK + "Successfully changed to " + language);
        }
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        if (!Translation.exists(language)) {
            language = Translation.DEFAULT_LANG;
        }
        this.language = language;
    }
}
