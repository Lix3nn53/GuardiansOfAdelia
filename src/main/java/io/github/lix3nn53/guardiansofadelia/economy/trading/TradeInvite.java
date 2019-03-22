package io.github.lix3nn53.guardiansofadelia.economy.trading;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.Invite;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.InviteGeneric;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TradeInvite implements Invite {

    private final InviteGeneric inviteBase;
    private final String senderTitle = ChatColor.GOLD + "Sent trade invitation";
    private final String receiverMessage;
    private final String receiverTitle = ChatColor.GOLD + "Received trade invitation";

    public TradeInvite(Player sender, Player receiver) {
        this.receiverMessage = ChatColor.GOLD + sender.getName() + " invites you to trade";
        this.inviteBase = new InviteGeneric(sender, receiver, senderTitle, receiverMessage, receiverTitle);
    }


    @Override
    public Player getSender() {
        return inviteBase.getSender();
    }

    @Override
    public Player getReceiver() {
        return inviteBase.getReceiver();
    }

    @Override
    public void send() {
        inviteBase.send();
    }

    @Override
    public void accept() {
        this.inviteBase.getSender().sendMessage(ChatColor.AQUA + this.inviteBase.getReceiver().getName() + " accepted your trade invite");
        GuardianData receiverData = GuardiansOfAdelia.getGuardianDataManager().getGuardianData(inviteBase.getReceiver().getUniqueId());
        if (receiverData.isTrading()) {
            inviteBase.getSender().sendMessage(ChatColor.RED + "This player is already trading.");
            return;
        }
        GuardianData senderData = GuardiansOfAdelia.getGuardianDataManager().getGuardianData(inviteBase.getSender().getUniqueId());
        if (senderData.isTrading()) {
            inviteBase.getReceiver().sendMessage(ChatColor.RED + "This player is already trading.");
            return;
        } else {
            inviteBase.getReceiver().sendMessage(ChatColor.GOLD + "Trade start");
            inviteBase.getSender().sendMessage(ChatColor.GOLD + "Trade start");

        }
        inviteBase.accept();
    }

    @Override
    public void reject() {
        this.inviteBase.getSender().sendMessage(ChatColor.RED + this.inviteBase.getReceiver().getName() + " rejected your trade invite");
        inviteBase.reject();
    }
}
