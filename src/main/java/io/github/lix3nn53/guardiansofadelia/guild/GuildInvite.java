package io.github.lix3nn53.guardiansofadelia.guild;

import io.github.lix3nn53.guardiansofadelia.utilities.TablistUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.Invite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GuildInvite extends Invite {

    public GuildInvite(Player sender, Player receiver, String senderTitle, String receiverMessage, String receiverTitle) {
        super(sender, receiver, senderTitle, receiverMessage, receiverTitle);
    }

    @Override
    public void send() {
        if (GuildManager.inGuild(getSender())) {
            Guild guild = GuildManager.getGuild(getSender());
            if (!guild.isEmpty()) {
                getSender().sendMessage(ChatColor.RED + "Your guild is full");
                return;
            } else {
                if (guild.getMembers().contains(getReceiver().getUniqueId())) {
                    getSender().sendMessage(getReceiver().getName() + ChatColor.RED + " is already in your guild");
                    return;
                }
            }
            if (GuildManager.inGuild(getReceiver())) {
                getSender().sendMessage(getReceiver().getName() + ChatColor.RED + " is already in a guild");
                return;
            }
            super.send();
        }
    }

    @Override
    public void accept() {
        getSender().sendMessage(ChatColor.DARK_PURPLE + getReceiver().getName() + " accepted your guild invite");
        if (GuildManager.inGuild(getSender())) {
            Guild guild = GuildManager.getGuild(getSender());
            if (guild.isEmpty()) {
                boolean isReceiverJoined = guild.addMember(getReceiver().getUniqueId());
                if (isReceiverJoined) {
                    for (UUID uuid : guild.getMembers()) {
                        Player member = Bukkit.getPlayer(uuid);
                        if (member != null) {
                            member.sendMessage(getReceiver().getName() + ChatColor.DARK_PURPLE + " joined guild " + guild.getName() + " (invited by" + getSender().getName() + ")");
                            TablistUtils.updateTablist(member);
                        }
                    }
                }
            } else {
                getReceiver().sendMessage(ChatColor.RED + getSender().getName() + "'s guild is full");
            }
        }
        super.accept();
    }

    @Override
    public void reject() {
        getSender().sendMessage(ChatColor.RED + getReceiver().getName() + " rejected your guild invite");
        super.reject();
    }
}
