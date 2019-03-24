package io.github.lix3nn53.guardiansofadelia.utilities.invite;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class InviteGeneric implements Invite {

    private final Player sender;
    private final String senderTitle;
    private final Player receiver;
    private final String receiverMessage;
    private final String receiverTitle;

    public InviteGeneric(Player sender, Player receiver, String senderTitle, String receiverMessage, String receiverTitle) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderTitle = senderTitle;
        this.receiverMessage = receiverMessage;
        this.receiverTitle = receiverTitle;
    }

    @Override
    public Player getSender() {
        return sender;
    }

    @Override
    public Player getReceiver() {
        return receiver;
    }

    @Override
    public void send() {
        GuardianData receiverData = GuardianDataManager.getGuardianData(receiver.getUniqueId());
        if (!receiverData.hasPendingInvite()) {
            sender.sendTitle("", senderTitle, 20, 40, 20);
            receiver.sendMessage(receiverMessage);
            receiver.sendTitle("", receiverTitle, 20, 40, 20);

            TextComponent messageMain = new TextComponent("Accept");
            messageMain.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/invite accept"));
            messageMain.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Accept the invite").color(ChatColor.GREEN).create()));
            messageMain.setBold(true);
            messageMain.setColor(ChatColor.GREEN);

            TextComponent messageReject = new TextComponent("Reject");
            messageReject.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/invite reject"));
            messageReject.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Reject the invite").color(ChatColor.RED).create()));
            messageReject.setBold(true);
            messageReject.setColor(ChatColor.RED);

            messageMain.addExtra("            ");
            messageMain.addExtra(messageReject);
            receiver.spigot().sendMessage(messageMain);

            receiverData.setPendingInvite(this);
            new BukkitRunnable() {

                @Override
                public void run() {
                    receiverData.clearPendingInvite();
                }
            }.runTaskTimerAsynchronously(GuardiansOfAdelia.getInstance(), 20L * 30, 20L);
        } else {
            sender.sendMessage(ChatColor.RED + receiver.getName() + " is answering another invite");
        }
    }

    @Override
    public void accept() {
        GuardianData receiverData = GuardianDataManager.getGuardianData(receiver.getUniqueId());
        receiverData.getActiveCharacter();
    }

    @Override
    public void reject() {
        GuardianData receiverData = GuardianDataManager.getGuardianData(receiver.getUniqueId());
        receiverData.getActiveCharacter();
    }
}
