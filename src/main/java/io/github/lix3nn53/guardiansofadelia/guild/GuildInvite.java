package io.github.lix3nn53.guardiansofadelia.guild;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.Invite;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.InviteGeneric;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GuildInvite implements Invite {

    private final InviteGeneric inviteBase;
    private final String senderTitle = ChatColor.DARK_PURPLE + "Sent guild invitation";
    private final String receiverMessage;
    private final String receiverTitle = ChatColor.DARK_PURPLE + "Received guild invitation";

    public GuildInvite(Player sender, Player receiver) {
        GuardianData senderData = GuardianDataManager.getGuardianData(sender.getUniqueId());
        this.receiverMessage = ChatColor.DARK_PURPLE + sender.getName() + " invites you to " + senderData.getGuild().getName() + " guild";
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
        GuardianData receiverData = GuardianDataManager.getGuardianData(inviteBase.getReceiver().getUniqueId());
        if (receiverData.isInGuild()) {
            inviteBase.getSender().sendMessage(ChatColor.RED + "This player is already in a guild.");
            return;
        }
        inviteBase.send();
    }

    @Override
    public void accept() {
        this.inviteBase.getSender().sendMessage(ChatColor.DARK_PURPLE + this.inviteBase.getReceiver().getName() + " accepted your party invite");
        GuardianData senderData = GuardianDataManager.getGuardianData(inviteBase.getSender().getUniqueId());
        if (senderData.isInGuild()) {
            Guild guild = senderData.getGuild();
            boolean isReceiverJoined = guild.addMember(inviteBase.getReceiver().getUniqueId());
            if (isReceiverJoined) {
                this.inviteBase.getReceiver().sendMessage(ChatColor.DARK_PURPLE + "You joined guild " + senderData.getGuild().getName());
            } else {
                this.inviteBase.getReceiver().sendMessage(ChatColor.RED + this.inviteBase.getSender().getName() + "'s guild is full");
                this.inviteBase.getSender().sendMessage(ChatColor.RED + "Your guild is full");
            }
        } else {
            this.inviteBase.getSender().sendMessage(ChatColor.RED + "You don't have a guild");
        }
        inviteBase.accept();
    }

    @Override
    public void reject() {
        this.inviteBase.getSender().sendMessage(ChatColor.RED + this.inviteBase.getReceiver().getName() + " rejected your guild invite");
        inviteBase.reject();
    }
}
