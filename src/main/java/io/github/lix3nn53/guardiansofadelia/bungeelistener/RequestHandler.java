package io.github.lix3nn53.guardiansofadelia.bungeelistener;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.*;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.web.WebProduct;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.web.WebProductType;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.web.WebPurchase;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.web.WebResponse;
import io.github.lix3nn53.guardiansofadelia.chat.PremiumRank;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseQueries;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class RequestHandler {

    private final static HashMap<Integer, WebProduct> productIdToWebProduct = new HashMap<>();

    static {
        //list of ranks
        productIdToWebProduct.put(1, new WebProduct(ChatColor.GREEN + "Hero Rank", WebProductType.RANK, 200, null, PremiumRank.HERO));
        productIdToWebProduct.put(2, new WebProduct(ChatColor.GOLD + "Legend Rank", WebProductType.RANK, 400, null, PremiumRank.LEGEND));
        productIdToWebProduct.put(3, new WebProduct(ChatColor.LIGHT_PURPLE + "Titan Rank", WebProductType.RANK, 600, null, PremiumRank.TITAN));

        //List of items
        ItemStack itemSkinScroll1 = WeaponOrShieldSkinScroll.getItemStack(1);
        ItemStack itemSkinScroll2 = WeaponOrShieldSkinScroll.getItemStack(2);
        ItemStack itemSkinScroll5 = WeaponOrShieldSkinScroll.getItemStack(5);
        productIdToWebProduct.put(4, new WebProduct(ChatColor.LIGHT_PURPLE + "Weapon/Shield Skin Scroll x1", WebProductType.ITEM, 50, itemSkinScroll1));
        productIdToWebProduct.put(5, new WebProduct(ChatColor.LIGHT_PURPLE + "Weapon/Shield Skin Scroll x2", WebProductType.ITEM, 100, itemSkinScroll2));
        productIdToWebProduct.put(6, new WebProduct(ChatColor.LIGHT_PURPLE + "Weapon/Shield Skin Scroll x5", WebProductType.ITEM, 200, itemSkinScroll5));

        ItemStack helmetSkin1 = HelmetSkin.WINGS_ANGEL.getItemStack();
        ItemStack helmetSkin2 = HelmetSkin.WINGS_DEMON.getItemStack();
        ItemStack helmetSkin3 = HelmetSkin.WINGS_DRAGON_DARK.getItemStack();
        ItemStack helmetSkin4 = HelmetSkin.WINGS_DRAGON_WHITE.getItemStack();
        ItemStack helmetSkin5 = HelmetSkin.CROWN.getItemStack();
        productIdToWebProduct.put(7, new WebProduct(ChatColor.YELLOW + "Angel Wings(Helmet Skin)", WebProductType.ITEM, 100, helmetSkin1));
        productIdToWebProduct.put(8, new WebProduct(ChatColor.DARK_PURPLE + "Demon Wings(Helmet Skin)", WebProductType.ITEM, 100, helmetSkin2));
        productIdToWebProduct.put(9, new WebProduct(ChatColor.LIGHT_PURPLE + "Dark Dragon Wings(Helmet Skin)", WebProductType.ITEM, 100, helmetSkin3));
        productIdToWebProduct.put(10, new WebProduct(ChatColor.AQUA + "White Dragon Wings(Helmet Skin)", WebProductType.ITEM, 100, helmetSkin4));
        productIdToWebProduct.put(11, new WebProduct(ChatColor.GOLD + "Golden Crown(Helmet Skin)", WebProductType.ITEM, 50, helmetSkin5));

        ItemStack petSkin1 = PetSkin.BEE.getItemStack();
        ItemStack petSkin2 = PetSkin.FOX_RED.getItemStack();
        ItemStack petSkin3 = PetSkin.FOX_SNOW.getItemStack();
        ItemStack petSkin4 = PetSkin.ICE_CREAM.getItemStack();
        ItemStack petSkin5 = PetSkin.VEX.getItemStack();
        ItemStack petSkin6 = PetSkin.MINI_DRAGON.getItemStack();

        productIdToWebProduct.put(12, new WebProduct(ChatColor.YELLOW + "Bee(Pet Skin)", WebProductType.ITEM, 100, petSkin1));
        productIdToWebProduct.put(13, new WebProduct(ChatColor.GOLD + "Red Fox(Pet Skin)", WebProductType.ITEM, 100, petSkin2));
        productIdToWebProduct.put(14, new WebProduct(ChatColor.AQUA + "Snow Fox(Pet Skin)", WebProductType.ITEM, 100, petSkin3));
        productIdToWebProduct.put(15, new WebProduct(ChatColor.LIGHT_PURPLE + "Ice Cream(Pet Skin)", WebProductType.ITEM, 100, petSkin4));
        productIdToWebProduct.put(16, new WebProduct(ChatColor.DARK_AQUA + "Vex(Pet Skin)", WebProductType.ITEM, 100, petSkin5));
        productIdToWebProduct.put(17, new WebProduct(ChatColor.RED + "Baby Dragon(Pet Skin)", WebProductType.ITEM, 100, petSkin6));

        ItemStack skinChest1 = new SkinChest().getItemStack(1);
        ItemStack skinChest2 = new SkinChest().getItemStack(2);
        ItemStack skinChest5 = new SkinChest().getItemStack(5);
        productIdToWebProduct.put(18, new WebProduct(ChatColor.GOLD + "Skin Chest x1", WebProductType.ITEM, 100, skinChest1));
        productIdToWebProduct.put(19, new WebProduct(ChatColor.GOLD + "Skin Chest x2", WebProductType.ITEM, 200, skinChest2));
        productIdToWebProduct.put(20, new WebProduct(ChatColor.GOLD + "Skin Chest x5", WebProductType.ITEM, 400, skinChest5));

        ItemStack boostExp1 = BoostPremium.EXPERIENCE.getItemStack(1);
        ItemStack boostExp2 = BoostPremium.EXPERIENCE.getItemStack(2);
        ItemStack boostExp5 = BoostPremium.EXPERIENCE.getItemStack(5);
        ItemStack boostLoot1 = BoostPremium.LOOT.getItemStack(1);
        ItemStack boostLoot2 = BoostPremium.LOOT.getItemStack(2);
        ItemStack boostLoot5 = BoostPremium.LOOT.getItemStack(5);
        ItemStack boostEnchant1 = BoostPremium.ENCHANT.getItemStack(1);
        ItemStack boostEnchant2 = BoostPremium.ENCHANT.getItemStack(2);
        ItemStack boostEnchant5 = BoostPremium.ENCHANT.getItemStack(5);
        ItemStack boostGather1 = BoostPremium.GATHER.getItemStack(1);
        ItemStack boostGather2 = BoostPremium.GATHER.getItemStack(2);
        ItemStack boostGather5 = BoostPremium.GATHER.getItemStack(5);
        productIdToWebProduct.put(21, new WebProduct(ChatColor.LIGHT_PURPLE + "Experience Boost x1", WebProductType.ITEM, 50, boostExp1));
        productIdToWebProduct.put(22, new WebProduct(ChatColor.LIGHT_PURPLE + "Experience Boost x2", WebProductType.ITEM, 100, boostExp2));
        productIdToWebProduct.put(23, new WebProduct(ChatColor.LIGHT_PURPLE + "Experience Boost x5", WebProductType.ITEM, 200, boostExp5));
        productIdToWebProduct.put(24, new WebProduct(ChatColor.YELLOW + "Loot Boost x1", WebProductType.ITEM, 50, boostLoot1));
        productIdToWebProduct.put(25, new WebProduct(ChatColor.YELLOW + "Loot Boost x2", WebProductType.ITEM, 100, boostLoot2));
        productIdToWebProduct.put(26, new WebProduct(ChatColor.YELLOW + "Loot Boost x5", WebProductType.ITEM, 200, boostLoot5));
        productIdToWebProduct.put(27, new WebProduct(ChatColor.AQUA + "Enchant Boost x1", WebProductType.ITEM, 50, boostEnchant1));
        productIdToWebProduct.put(28, new WebProduct(ChatColor.AQUA + "Enchant Boost x2", WebProductType.ITEM, 100, boostEnchant2));
        productIdToWebProduct.put(29, new WebProduct(ChatColor.AQUA + "Enchant Boost x5", WebProductType.ITEM, 200, boostEnchant5));
        productIdToWebProduct.put(30, new WebProduct(ChatColor.GREEN + "Gather Boost x1", WebProductType.ITEM, 50, boostGather1));
        productIdToWebProduct.put(31, new WebProduct(ChatColor.GREEN + "Gather Boost x2", WebProductType.ITEM, 100, boostGather2));
        productIdToWebProduct.put(32, new WebProduct(ChatColor.GREEN + "Gather Boost x5", WebProductType.ITEM, 200, boostGather5));
    }

    static WebResponse onPurchase(WebPurchase webPurchase) {
        String minecraftUuidString = webPurchase.getMinecraftUuid();
        UUID minecraftUuid = UUID.fromString(minecraftUuidString);
        int productId = webPurchase.getProductId();
        int payment = webPurchase.getPayment();

        String minecraftUsername = "NULL";

        if (!productIdToWebProduct.containsKey(productId)) {
            return new WebResponse(false, "No such product1", minecraftUuidString, minecraftUsername, productId);
        }

        WebProduct webProduct = productIdToWebProduct.get(productId);
        int cost = webProduct.getCost();

        if (cost != payment) {
            return new WebResponse(false, "No such product2", minecraftUuidString, minecraftUsername, productId);
        }

        WebProductType type = webProduct.getType();
        if (type.equals(WebProductType.ITEM)) {
            ItemStack itemStack = webProduct.getItemStack();

            Player player = Bukkit.getPlayer(minecraftUuid);
            if (player != null) {
                minecraftUsername = player.getName();
                InventoryView openInventory = player.getOpenInventory();
                String title = openInventory.getTitle();

                if (title.contains("Premium Storage")) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                        }
                    }.runTask(GuardiansOfAdelia.getInstance());
                }

                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                    boolean success = guardianData.addToPremiumStorage(itemStack);
                    if (!success) {
                        return new WebResponse(false, "Your premium-storage is full!", minecraftUuidString, minecraftUsername, productId);
                    }
                }
            } else { //player is offline
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(minecraftUuid);
                UUID uuid = offlinePlayer.getUniqueId();

                if (!uuidExists(uuid)) {
                    return new WebResponse(false, "You must be logged in to game server at least once!", minecraftUuidString, minecraftUsername, productId);
                }

                minecraftUsername = offlinePlayer.getName();

                try {
                    ItemStack[] premiumStorage = DatabaseQueries.getPremiumStorage(uuid);

                    List<ItemStack> list = new ArrayList<>();

                    if (premiumStorage != null) list = new ArrayList<>(Arrays.asList(premiumStorage));

                    if (list.size() >= 54) {
                        return new WebResponse(false, "Your premium-storage is full!", minecraftUuidString, minecraftUsername, productId);
                    }

                    list.add(itemStack);
                    ItemStack[] newPremiumStorage = list.toArray(new ItemStack[0]);
                    DatabaseQueries.setPremiumStorage(uuid, newPremiumStorage);
                } catch (Exception e) {
                    e.printStackTrace();

                    return new WebResponse(false, "A database error occurred.", minecraftUuidString, minecraftUsername, productId);
                }
            }
        } else if (type.equals(WebProductType.RANK)) {
            PremiumRank premiumRank = webProduct.getPremiumRank();

            Player player = Bukkit.getPlayer(minecraftUuid);
            if (player != null) {
                minecraftUsername = player.getName();
                PremiumRank currentRank = null;
                GuardianData guardianData;
                if (GuardianDataManager.hasGuardianData(player)) {
                    guardianData = GuardianDataManager.getGuardianData(player);
                    currentRank = guardianData.getPremiumRank();
                    if (currentRank.equals(PremiumRank.NONE)) {
                        if (currentRank.ordinal() >= premiumRank.ordinal()) {
                            return new WebResponse(false, "You already have a rank that is higher or equal to this rank.", minecraftUuidString, minecraftUsername, productId);
                        }
                    }
                    guardianData.setPremiumRank(premiumRank);
                }

                try {
                    UUID uuid = player.getUniqueId();
                    DatabaseQueries.setPremiumRankWithDate(uuid, premiumRank);
                } catch (Exception e) {
                    e.printStackTrace();
                    //Revert online player rank if we get a database error
                    if (currentRank != null) {
                        guardianData = GuardianDataManager.getGuardianData(player);
                        guardianData.setPremiumRank(currentRank);
                    }
                    return new WebResponse(false, "A database error occurred.", minecraftUuidString, minecraftUsername, productId);
                }
            } else { //player is offline
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(minecraftUuid);
                UUID uuid = offlinePlayer.getUniqueId();

                if (!uuidExists(uuid)) {
                    return new WebResponse(false, "You must be logged in to game server at least once!", minecraftUuidString, minecraftUsername, productId);
                }

                try {
                    PremiumRank currentRank = DatabaseQueries.getPremiumRank(uuid);
                    if (currentRank.equals(PremiumRank.NONE)) {
                        if (currentRank.ordinal() >= premiumRank.ordinal()) {
                            return new WebResponse(false, "You already have a rank that is higher or equal to this rank.", minecraftUuidString, minecraftUsername, productId);
                        }
                    }

                    DatabaseQueries.setPremiumRankWithDate(uuid, premiumRank);

                    minecraftUsername = offlinePlayer.getName();
                } catch (Exception e) {
                    e.printStackTrace();

                    return new WebResponse(false, "A database error occurred.", minecraftUuidString, minecraftUsername, productId);
                }
            }
        }

        GuardiansOfAdelia.getInstance().getLogger().info("Web purchase: " + minecraftUsername + " bought " + webProduct.getProductName() + " for " + payment + " credits!");
        return new WebResponse(true, "Item purchased successfully!", minecraftUuidString, minecraftUsername, productId);
    }

    public static void test(int productId, Player player) {
        WebProduct webProduct = productIdToWebProduct.get(productId);

        WebProductType type = webProduct.getType();
        if (type.equals(WebProductType.ITEM)) {
            ItemStack itemStack = webProduct.getItemStack();


            InventoryView openInventory = player.getOpenInventory();
            String title = openInventory.getTitle();

            if (title.contains("Premium Storage")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.closeInventory();
                    }
                }.runTask(GuardiansOfAdelia.getInstance());
            }

            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                boolean success = guardianData.addToPremiumStorage(itemStack);
            }

        } else if (type.equals(WebProductType.RANK)) {
            PremiumRank premiumRank = webProduct.getPremiumRank();

            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                guardianData.setPremiumRank(premiumRank);
            }
        }


        //Bukkit.broadcastMessage(ChatColor.GOLD + "Thanks for your support! " + ChatColor.GRAY + player.getName() + " bought " + webProduct.getProductName() + ChatColor.GRAY + " from web-store!");
    }

    private static boolean uuidExists(UUID uuid) {
        return DatabaseQueries.uuidExists(uuid);
    }

    public static List<ItemStack> getSkinChestItemPool() {
        List<ItemStack> skinPool = new ArrayList<>();

        for (int id : productIdToWebProduct.keySet()) {
            WebProduct webProduct = productIdToWebProduct.get(id);

            WebProductType type = webProduct.getType();

            if (type.equals(WebProductType.ITEM)) {
                if (id < 18) {
                    skinPool.add(webProduct.getItemStack());
                }
            }
        }

        return skinPool;
    }
}
