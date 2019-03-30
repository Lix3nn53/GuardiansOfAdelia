package io.github.lix3nn53.guardiansofadelia.economy.trading;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class TradeManager {

    private static HashMap<Player, Trade> playerToTrade = new HashMap<>();

    public static void startTrade(Player player1, Player player2, Trade trade) {
        playerToTrade.put(player1, trade);
        playerToTrade.put(player2, trade);
    }

    public static void removeTrade(Player player) {
        if (hasTrade(player)) {
            Trade trade = playerToTrade.get(player);
            playerToTrade.remove(player);

            Player otherPlayer = trade.getOtherPlayer(player);
            if (otherPlayer != null) {
                playerToTrade.remove(otherPlayer);
            }
        }
    }

    public static void removeTrade(Player player1, Player player2) {
        playerToTrade.remove(player1);
        playerToTrade.remove(player2);
    }

    public static Trade getTrade(Player player) {
        return playerToTrade.get(player);
    }

    public static boolean hasTrade(Player player) {
        return playerToTrade.containsKey(player);
    }
}
