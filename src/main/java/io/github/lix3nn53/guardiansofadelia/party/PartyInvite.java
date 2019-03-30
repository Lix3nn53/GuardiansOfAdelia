package io.github.lix3nn53.guardiansofadelia.party;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
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
        GuardianData receiverData = GuardianDataManager.getGuardianData(getReceiver().getUniqueId());
        if (receiverData.isInParty()) {
            getSender().sendMessage(ChatColor.RED + "This player is already in a party.");
            return;
        }
        super.send();
    }

    @Override
    public void accept() {
        getSender().sendMessage(ChatColor.AQUA + getReceiver().getName() + " accepted your party invite");
        GuardianData senderData = GuardianDataManager.getGuardianData(getSender().getUniqueId());
        if (senderData.isInParty()) {
            Party party = senderData.getParty();
            boolean isReceiverJoined = party.addMember(getReceiver());
            if (isReceiverJoined) {
                getReceiver().sendMessage(ChatColor.AQUA + "You joined " + getSender().getName() + "'s party");
            } else {
                getReceiver().sendMessage(ChatColor.RED + getSender().getName() + "'s party is full");
                getSender().sendMessage(ChatColor.RED + "Your party is full");
            }
        } else {
            List<Player> members = new ArrayList<>();
            members.add(getSender());
            members.add(getReceiver());
            Party party = new Party(members, getSender(), 4);
            getReceiver().sendMessage(ChatColor.AQUA + "You joined " + getSender().getName() + "'s party");
        }
        super.accept();
    }

    @Override
    public void reject() {
        getSender().sendMessage(ChatColor.RED + getReceiver().getName() + " rejected your party invite");
        super.reject();
    }
}
