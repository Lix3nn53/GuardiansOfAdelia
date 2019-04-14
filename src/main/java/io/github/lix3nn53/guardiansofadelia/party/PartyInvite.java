package io.github.lix3nn53.guardiansofadelia.party;

import io.github.lix3nn53.guardiansofadelia.utilities.invite.Invite;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PartyInvite extends Invite {

    public PartyInvite(Player sender, Player receiver, String senderTitle, String receiverMessage, String receiverTitle) {
        super(sender, receiver, senderTitle, receiverMessage, receiverTitle);
    }

    @Override
    public void send() {
        if (PartyManager.inParty(getSender())) {
            Party party = PartyManager.getParty(getSender());
            if (!party.anyFreeSpace()) {
                getSender().sendMessage(ChatColor.RED + "Your party is full");
                return;
            } else {
                if (party.getMembers().contains(getReceiver())) {
                    getSender().sendMessage(getReceiver().getName() + ChatColor.RED + " is already in your party");
                    return;
                }
            }
        }
        if (PartyManager.inParty(getReceiver())) {
            getSender().sendMessage(getReceiver().getName() + ChatColor.RED + " is already in a party");
            return;
        }
        super.send();
    }

    @Override
    public void accept() {
        getSender().sendMessage(ChatColor.AQUA + getReceiver().getName() + " accepted your party invite");
        if (PartyManager.inParty(getSender())) {
            Party party = PartyManager.getParty(getSender());
            if (party.anyFreeSpace()) {
                boolean isReceiverJoined = party.addMember(getReceiver());
                if (isReceiverJoined) {
                    for (Player member : party.getMembers()) {
                        member.sendMessage(getReceiver().getName() + ChatColor.AQUA + " joined party (invited by" + getSender().getName() + ")");
                    }
                }
            } else {
                getReceiver().sendMessage(ChatColor.RED + getSender().getName() + "'s party is full");
            }
        } else {
            List<Player> members = new ArrayList<>();
            members.add(getSender());
            members.add(getReceiver());

            Party party = new Party(members, 4, 2);

            PartyManager.addParty(getSender(), getReceiver(), party);

            getSender().sendMessage(getReceiver().getName() + ChatColor.AQUA + " joined party (invited by" + getSender().getName() + ")");
            getReceiver().sendMessage(getReceiver().getName() + ChatColor.AQUA + " joined party (invited by" + getSender().getName() + ")");
        }
        super.accept();
    }

    @Override
    public void reject() {
        getSender().sendMessage(ChatColor.RED + getReceiver().getName() + " rejected your party invite");
        super.reject();
    }
}
