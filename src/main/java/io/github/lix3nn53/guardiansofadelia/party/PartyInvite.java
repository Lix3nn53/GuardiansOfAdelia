package io.github.lix3nn53.guardiansofadelia.party;

import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
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
                getSender().sendMessage(ChatPalette.RED + "Your party is full");
                return;
            } else {
                if (party.getMembers().contains(getReceiver())) {
                    getSender().sendMessage(getReceiver().getName() + ChatPalette.RED + " is already in your party");
                    return;
                }
            }
        }
        if (PartyManager.inParty(getReceiver())) {
            getSender().sendMessage(getReceiver().getName() + ChatPalette.RED + " is already in a party");
            return;
        }
        super.send();
    }

    @Override
    public void accept() {
        Player sender = getSender();
        Player receiver = getReceiver();
        if (MiniGameManager.isInMinigame(sender)) {
            receiver.sendMessage(ChatPalette.RED + "You can't join to a minigame party!");
            return;
        }
        sender.sendMessage(ChatPalette.BLUE_LIGHT + receiver.getName() + " accepted your party invite");
        if (PartyManager.inParty(sender)) {
            Party party = PartyManager.getParty(sender);
            if (party.anyFreeSpace()) {
                boolean isReceiverJoined = party.addMember(receiver);
                if (isReceiverJoined) {
                    for (Player member : party.getMembers()) {
                        member.sendMessage(receiver.getName() + ChatPalette.BLUE_LIGHT + " joined party (invited by" + sender.getName() + ")");
                    }
                }
            } else {
                receiver.sendMessage(ChatPalette.RED + sender.getName() + "'s party is full");
            }
        } else {
            List<Player> members = new ArrayList<>();
            members.add(sender);
            members.add(receiver);

            Party party = new Party(members, 4, 2, ChatColor.AQUA);

            PartyManager.addParty(sender, receiver, party);

            sender.sendMessage(receiver.getName() + ChatPalette.BLUE_LIGHT + " joined party (invited by" + sender.getName() + ")");
            receiver.sendMessage(receiver.getName() + ChatPalette.BLUE_LIGHT + " joined party (invited by" + sender.getName() + ")");
        }
        super.accept();
    }

    @Override
    public void reject() {
        getSender().sendMessage(ChatPalette.RED + getReceiver().getName() + " rejected your party invite");
        super.reject();
    }
}
