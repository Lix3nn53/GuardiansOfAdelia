package io.github.lix3nn53.guardiansofadelia.economy.trading;

import io.github.lix3nn53.guardiansofadelia.menu.ActiveGuiManager;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.invite.Invite;
import org.bukkit.entity.Player;

public class TradeInvite extends Invite {

    public TradeInvite(Player sender, Player receiver, String senderTitle, String receiverMessage, String receiverTitle) {
        super(sender, receiver, senderTitle, receiverMessage, receiverTitle);
    }

    @Override
    public void accept() {
        Player sender = getSender();
        Player receiver = getReceiver();
        sender.sendMessage(ChatPalette.BLUE_LIGHT + receiver.getName() + " accepted your trade invite");

        if (ActiveGuiManager.hasActiveGui(receiver)) {
            sender.sendMessage(ChatPalette.RED + "This player is busy.");
            return;
        }

        if (ActiveGuiManager.hasActiveGui(sender)) {
            receiver.sendMessage(ChatPalette.RED + "This player is busy.");
            return;
        } else {
            Trade trade = new Trade(sender, receiver);
            TradeManager.startTrade(sender, receiver, trade);
            receiver.sendMessage(ChatPalette.GOLD + "Trade start");
            sender.sendMessage(ChatPalette.GOLD + "Trade start");
        }
        super.accept();
    }

    @Override
    public void reject() {
        getSender().sendMessage(ChatPalette.RED + getReceiver().getName() + " rejected your trade invite");
        super.reject();
    }
}
