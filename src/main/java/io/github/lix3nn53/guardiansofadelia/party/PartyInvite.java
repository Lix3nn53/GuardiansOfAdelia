package io.github.lix3nn53.guardiansofadelia.party;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.Invite;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.InviteGeneric;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PartyInvite implements Invite {

    private final InviteGeneric inviteBase;
    private final String senderTitle = ChatColor.AQUA + "Sent party invitation";
    private final String receiverMessage;
    private final String receiverTitle = ChatColor.AQUA + "Received party invitation";

    public PartyInvite(Player sender, Player receiver) {
        this.receiverMessage = ChatColor.AQUA + sender.getName() + " invites you to his/her party";
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
        GuardianData receiverData = GuardiansOfAdelia.getGuardianDataManager().getGuardianData(inviteBase.getReceiver().getUniqueId());
        if (receiverData.isInParty()) {
            inviteBase.getSender().sendMessage(ChatColor.RED + "This player is already in a party.");
            return;
        }
        inviteBase.send();
    }

    @Override
    public void accept() {
        this.inviteBase.getSender().sendMessage(ChatColor.AQUA + this.inviteBase.getReceiver().getName() + " accepted your party invite");
        GuardianData senderData = GuardiansOfAdelia.getGuardianDataManager().getGuardianData(inviteBase.getSender().getUniqueId());
        if (senderData.isInParty()) {
            Party party = senderData.getParty();
            boolean isReceiverJoined = party.addMember(inviteBase.getReceiver());
            if (isReceiverJoined) {
                this.inviteBase.getReceiver().sendMessage(ChatColor.AQUA + "You joined " + this.inviteBase.getSender().getName() + "'s party");
            } else {
                this.inviteBase.getReceiver().sendMessage(ChatColor.RED + this.inviteBase.getSender().getName() + "'s party is full");
                this.inviteBase.getSender().sendMessage(ChatColor.RED + "Your party is full");
            }
        } else {
            List<Player> members = new ArrayList<>();
            members.add(inviteBase.getSender());
            members.add(inviteBase.getReceiver());
            Party party = new Party(members, inviteBase.getSender(), 4);
            this.inviteBase.getReceiver().sendMessage(ChatColor.AQUA + "You joined " + this.inviteBase.getSender().getName() + "'s party");
        }
        inviteBase.accept();
    }

    @Override
    public void reject() {
        this.inviteBase.getSender().sendMessage(ChatColor.RED + this.inviteBase.getReceiver().getName() + " rejected your party invite");
        inviteBase.reject();
    }
}
