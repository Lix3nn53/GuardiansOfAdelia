package io.github.lix3nn53.guardiansofadelia.socket;

import com.corundumstudio.socketio.SocketIOClient;
import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseQueries;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.*;

public class RequestHandler {

    private final static HashMap<String, WebProduct> productIdToWebProduct = new HashMap<>();

    public static boolean onPurchase(SocketIOClient socketIOClient, WebPurchase webPurchase) {
        String responseMessage = "noResponse";

        String minecraftUsername = webPurchase.getMinecraftUsername();
        String productId = webPurchase.getProductId();
        int payment = webPurchase.getPayment();

        if (!productIdToWebProduct.containsKey(productId)) {
            socketIOClient.sendEvent("purchaseFail", webPurchase);
            return false;
        }

        GuardiansOfAdelia.getInstance().getLogger().info("minecraftUsername: " + minecraftUsername);
        GuardiansOfAdelia.getInstance().getLogger().info("productId: " + productId);
        GuardiansOfAdelia.getInstance().getLogger().info("payment: " + payment);

        ItemStack weapon = Weapons.getWeapon(RPGClass.WARRIOR, 1, ItemTier.COMMON, "", 1, 1, 1);

        Player playerExact = Bukkit.getPlayerExact(minecraftUsername);
        if (playerExact != null) {
            InventoryView openInventory = playerExact.getOpenInventory();
            String title = openInventory.getTitle();

            if (title.contains("Premium Storage")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        playerExact.closeInventory();
                    }
                }.runTask(GuardiansOfAdelia.getInstance());
            }

            UUID uuid = playerExact.getUniqueId();
            if (GuardianDataManager.hasGuardianData(uuid)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                boolean success = guardianData.addToPremiumStorage(weapon);
                if (!success) responseMessage = "Your premium-storage is full!";
            }
        } else { //player is offline
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(minecraftUsername);
            UUID uuid = offlinePlayer.getUniqueId();

            if (!uuidExists(uuid)) {
                responseMessage = "You must be logged in to game server at least once!";
                return false;
            }

            try {
                ItemStack[] premiumStorage = DatabaseQueries.getPremiumStorage(uuid);

                List<ItemStack> list = new ArrayList<>();

                if (premiumStorage != null) list = new ArrayList<>(Arrays.asList(premiumStorage));

                if (list.size() >= 54) {
                    responseMessage = "Your premium-storage is full!";
                    return false;
                }

                list.add(weapon);
                ItemStack[] newPremiumStorage = list.toArray(new ItemStack[0]);
                DatabaseQueries.setPremiumStorage(uuid, newPremiumStorage);

                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            responseMessage = "A database error occurred.";
            return false;
        }

        return true;
    }

    private static boolean uuidExists(UUID uuid) {
        return DatabaseQueries.uuidExists(uuid);
    }
}
