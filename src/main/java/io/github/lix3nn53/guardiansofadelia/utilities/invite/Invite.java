package io.github.lix3nn53.guardiansofadelia.utilities.invite;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;

public class Invite {

    private final Player sender;
    private final String senderTitle;
    private final Player receiver;
    private final String receiverMessage;
    private final String receiverTitle;

    public Invite(Player sender, Player receiver, String senderTitle, String receiverMessage, String receiverTitle) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderTitle = senderTitle;
        this.receiverMessage = receiverMessage;
        this.receiverTitle = receiverTitle;
    }

    public Player getSender() {
        return sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void send() {
        GuardianData receiverData = GuardianDataManager.getGuardianData(receiver);
        if (!receiverData.hasPendingInvite()) {
            sender.sendTitle("", senderTitle, 20, 40, 20);
            receiver.sendMessage(receiverMessage);
            receiver.sendTitle("", receiverTitle, 20, 40, 20);

            TextComponent messageMain = new TextComponent("Accept");
            messageMain.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/invite accept"));
            messageMain.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Accept the invite")));
            messageMain.setBold(true);
            messageMain.setColor(ChatColor.GREEN);

            TextComponent messageReject = new TextComponent("Reject");
            messageReject.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/invite reject"));
            messageMain.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "Reject the invite")));
            messageReject.setBold(true);
            messageReject.setColor(ChatColor.RED);

            messageMain.addExtra("            ");
            messageMain.addExtra(messageReject);
            receiver.spigot().sendMessage(messageMain);

            receiverData.setPendingInvite(this, receiver);
        } else {
            sender.sendMessage(ChatColor.RED + receiver.getName() + " is answering another invite");
        }
    }

    public void accept() {
        GuardianData receiverData = GuardianDataManager.getGuardianData(receiver);
        receiverData.clearPendingInvite();
    }

    public void reject() {
        GuardianData receiverData = GuardianDataManager.getGuardianData(receiver);
        receiverData.clearPendingInvite();
    }
}
