package io.github.lix3nn53.guardiansofadelia.guardian;

import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.economy.trading.Trade;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.utilities.StaffRank;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.Invite;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GuardianData {

    private final GuiGeneric personalStorage = new GuiGeneric(54, "Personal Storage", 0);
    private final GuiGeneric bazaarStorage = new GuiGeneric(54, "Bazaar Storage", 0);
    private RPGCharacter activeCharacter;
    private int activeCharacterNo = 0;
    private Invite pendingInvite;
    private StaffRank staffRank = StaffRank.NONE;
    private List<Player> friends = new ArrayList<>();

    private Guild guild;
    private Party party;

    private Trade trade;
    private Gui activeGui;

    private LivingEntity pet;

    private boolean isTeleporting;
    private boolean isGathering;
    private boolean isConsuming;

    private List<String> activeBuffCodes = new ArrayList<>();

    private Bazaar bazaar;

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
        boolean isFree = isGathering && isTeleporting && isConsuming;
        return isFree;
    }

    public boolean isInParty() {
        return this.party != null;
    }

    public void clearParty() {
        this.party = null;
    }

    public Party getParty() {
        return this.party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public boolean isTrading() {
        return this.trade != null;
    }

    public void removeTrade() {
        this.trade = null;
    }

    public Trade getTrade() {
        return this.trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public boolean isInGuild() {
        return this.guild != null;
    }

    public void clearGuild() {
        this.guild = null;
    }

    public Guild getGuild() {
        return this.guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public boolean hasPendingInvite() {
        return this.pendingInvite != null;
    }

    public void setPendingInvite(Invite pendingInvite) {
        this.pendingInvite = pendingInvite;
    }

    public void clearPendingInvite() {
        this.pendingInvite = null;
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

    public GuiGeneric getPersonalStorageGui() {
        return this.personalStorage;
    }

    public GuiGeneric getBazaarStorageGui() {
        return this.bazaarStorage;
    }

    public boolean addToBazaarStorage(ItemStack itemStack) {
        if (this.bazaarStorage.anyEmpty()) {
            //inventory has empty slot
            this.bazaarStorage.addItem(itemStack);
            return true;
        }
        return false;
    }

    public boolean bazaarStorageIsEmpty() {
        return this.bazaarStorage.anyEmpty();
    }

    public void removeFromBazaarStorage(ItemStack itemStack) {
        this.bazaarStorage.removeItem(itemStack, 1);
    }

    public void setTeleporting(boolean teleporting) {
        this.isTeleporting = teleporting;
    }

    public void setGathering(boolean gathering) {
        this.isGathering = gathering;
    }

    public void setConsuming(boolean consuming) {
        this.isConsuming = consuming;
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

    public void addBuff(String buff) {
        this.activeBuffCodes.add(buff);
    }

    public void removeBuff(String buff) {
        this.activeBuffCodes.remove(buff);
    }

    public void clearBuffs() {
        this.activeBuffCodes.clear();
    }

    public boolean hasBuff(String buff) {
        Optional<String> buffOptional = this.activeBuffCodes.stream()
                .filter(item -> item.equals(buff))
                .findAny();
        return buffOptional.isPresent();
    }

    public LivingEntity getPet() {
        return pet;
    }

    public void setPet(LivingEntity pet) {
        this.pet = pet;
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
}
