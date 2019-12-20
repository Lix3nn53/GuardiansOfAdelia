package io.github.lix3nn53.guardiansofadelia.socket;

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

import java.util.*;

public class RequestHandler {

    private final static HashMap<Integer, WebProduct> productIdToWebProduct = new HashMap<>();

    static {
        //List of items
        ItemStack weapon = Weapons.getWeapon(RPGClass.WARRIOR, 1, ItemTier.COMMON, "", 1, 1, 1);
        productIdToWebProduct.put(1, new WebProduct(1, weapon));
    }

    public static WebResponse onPurchase(WebPurchase webPurchase) {
        String minecraftUsername = webPurchase.getMinecraftUsername();
        int productId = webPurchase.getProductId();
        int payment = webPurchase.getPayment();

        if (!productIdToWebProduct.containsKey(productId)) {
            return new WebResponse(false, "No such product1", minecraftUsername, productId);
        }

        WebProduct webProduct = productIdToWebProduct.get(productId);
        int cost = webProduct.getCost();

        if (cost != payment) {
            return new WebResponse(false, "No such product2", minecraftUsername, productId);
        }

        ItemStack itemStack = webProduct.getItemStack();

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
                boolean success = guardianData.addToPremiumStorage(itemStack);
                if (!success) {
                    return new WebResponse(false, "Your premium-storage is full!", minecraftUsername, productId);
                }
            }
        } else { //player is offline
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(minecraftUsername);
            UUID uuid = offlinePlayer.getUniqueId();

            if (!uuidExists(uuid)) {
                return new WebResponse(false, "You must be logged in to game server at least once!", minecraftUsername, productId);
            }

            try {
                ItemStack[] premiumStorage = DatabaseQueries.getPremiumStorage(uuid);

                List<ItemStack> list = new ArrayList<>();

                if (premiumStorage != null) list = new ArrayList<>(Arrays.asList(premiumStorage));

                if (list.size() >= 54) {
                    return new WebResponse(false, "Your premium-storage is full!", minecraftUsername, productId);
                }

                list.add(itemStack);
                ItemStack[] newPremiumStorage = list.toArray(new ItemStack[0]);
                DatabaseQueries.setPremiumStorage(uuid, newPremiumStorage);
            } catch (Exception e) {
                e.printStackTrace();

                return new WebResponse(false, "A database error occurred.", minecraftUsername, productId);
            }
        }

        GuardiansOfAdelia.getInstance().getLogger().info("Web purchase: " + minecraftUsername + " bought " + itemStack.getItemMeta().getDisplayName() + " for " + payment + " credits!");
        return new WebResponse(true, "Item purchased successfully!", minecraftUsername, productId);
    }

    private static boolean uuidExists(UUID uuid) {
        return DatabaseQueries.uuidExists(uuid);
    }
}
