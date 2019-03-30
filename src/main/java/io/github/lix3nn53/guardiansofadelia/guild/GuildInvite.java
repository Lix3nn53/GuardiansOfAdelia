package io.github.lix3nn53.guardiansofadelia.guild;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.Invite;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GuildInvite extends Invite {

    public GuildInvite(Player sender, Player receiver, String senderTitle, String receiverMessage, String receiverTitle) {
        super(sender, receiver, senderTitle, receiverMessage, receiverTitle);
    }

    @Override
    public void send() {
        GuardianData receiverData = GuardianDataManager.getGuardianData(getReceiver().getUniqueId());
        if (receiverData.isInGuild()) {
            getSender().sendMessage(ChatColor.RED + "This player is already in a guild.");
            return;
        }
        super.send();
    }

    @Override
    public void accept() {
        getSender().sendMessage(ChatColor.DARK_PURPLE + getReceiver().getName() + " accepted your party invite");
        GuardianData senderData = GuardianDataManager.getGuardianData(getSender().getUniqueId());
        if (senderData.isInGuild()) {
            Guild guild = senderData.getGuild();
            boolean isReceiverJoined = guild.addMember(getReceiver().getUniqueId());
            if (isReceiverJoined) {
                getReceiver().sendMessage(ChatColor.DARK_PURPLE + "You joined guild " + senderData.getGuild().getName());
            } else {
                getReceiver().sendMessage(ChatColor.RED + getSender().getName() + "'s guild is full");
                getSender().sendMessage(ChatColor.RED + "Your guild is full");
            }
        } else {
            getSender().sendMessage(ChatColor.RED + "You don't have a guild");
        }
        super.accept();
    }

    @Override
    public void reject() {
        getSender().sendMessage(ChatColor.RED + getReceiver().getName() + " rejected your guild invite");
        super.reject();
    }
}
