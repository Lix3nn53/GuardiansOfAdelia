package io.github.lix3nn53.guardiansofadelia.rewards;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.DateUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.UUID;

public class DailyRewardHandler {

    private static final int MAX_DAYS = 7;

    private static final ItemStack[] itemPrizes = new ItemStack[MAX_DAYS];

    public static void setReward(int index, ItemStack itemStack) {
        itemPrizes[index] = itemStack;
    }

    public static void giveReward(Player player) {
        UUID uniqueId = player.getUniqueId();
        boolean hasGuardianData = GuardianDataManager.hasGuardianData(uniqueId);

        if (!hasGuardianData) return;

        GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);

        DailyRewardInfo dailyRewardInfo = guardianData.getDailyRewardInfo();

        boolean canGetReward = canGetReward(dailyRewardInfo);

        if (!canGetReward) return;

        dailyRewardInfo.setLastObtainDate(LocalDate.now());

        int dayOfTheWeek = DateUtils.getDayOfTheWeek();
        ItemStack itemPrize = itemPrizes[dayOfTheWeek];

        InventoryUtils.giveItemToPlayer(player, itemPrize);
    }

    private static boolean canGetReward(DailyRewardInfo dailyRewardInfo) {
        LocalDate lastPrizeDate = dailyRewardInfo.getLastObtainDate();

        if (lastPrizeDate == null) return true;

        //get day of the week for lastPrizeDate
        int lastPrizeDateDayOfTheWeek = lastPrizeDate.get(ChronoField.DAY_OF_WEEK);

        boolean dateInCurrentWeek = DateUtils.isDateInCurrentWeek(lastPrizeDate);

        //get day of the week for current day
        int dayOfTheWeek = DateUtils.getDayOfTheWeek();

        if (dateInCurrentWeek) { //do we need to check streak?
            return dayOfTheWeek - 1 == lastPrizeDateDayOfTheWeek; //return true if player got yesterday's prize, false otherwise
        }

        //if date is not in the current week, check if we are in the first day

        return dayOfTheWeek == 1;
    }
}
