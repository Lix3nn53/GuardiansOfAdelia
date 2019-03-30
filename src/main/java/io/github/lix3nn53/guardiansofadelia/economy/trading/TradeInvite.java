package io.github.lix3nn53.guardiansofadelia.economy.trading;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.Invite;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TradeInvite extends Invite {

    public TradeInvite(Player sender, Player receiver, String senderTitle, String receiverMessage, String receiverTitle) {
        super(sender, receiver, senderTitle, receiverMessage, receiverTitle);
    }

    @Override
    public void accept() {
        getSender().sendMessage(ChatColor.AQUA + getReceiver().getName() + " accepted your trade invite");
        GuardianData receiverData = GuardianDataManager.getGuardianData(getReceiver().getUniqueId());
        if (receiverData.hasActiveGui()) {
            getSender().sendMessage(ChatColor.RED + "This player is busy.");
            return;
        }
        GuardianData senderData = GuardianDataManager.getGuardianData(getSender().getUniqueId());
        if (senderData.hasActiveGui()) {
            getReceiver().sendMessage(ChatColor.RED + "This player is busy.");
            return;
        } else {
            Trade trade = new Trade(getSender(), getReceiver());
            TradeManager.startTrade(getSender(), getReceiver(), trade);
            getReceiver().sendMessage(ChatColor.GOLD + "Trade start");
            getSender().sendMessage(ChatColor.GOLD + "Trade start");
        }
        super.accept();
    }

    @Override
    public void reject() {
        getSender().sendMessage(ChatColor.RED + getReceiver().getName() + " rejected your trade invite");
        super.reject();
    }
}
