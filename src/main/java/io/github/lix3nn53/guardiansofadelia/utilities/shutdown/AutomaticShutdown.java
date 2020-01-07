package io.github.lix3nn53.guardiansofadelia.utilities.shutdown;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class AutomaticShutdown {

    public static void onEnable() {
        Timer timer = new Timer();
        timer.schedule(new ShutdownTask(), getNextRestartTime());

        GuardiansOfAdelia.getInstance().getLogger().info("Scheduled restart on " + getNextRestartTime().toString());
    }

    private static Date getNextRestartTime() {
        Calendar midnight = getMidnight();
        Calendar noon = getNoon();

        Calendar currentTime = Calendar.getInstance();

        if (noon.after(currentTime)) {
            return noon.getTime();
        } else {
            return midnight.getTime();
        }
    }

    private static Calendar getMidnight() {
        Calendar midnight = Calendar.getInstance();

        midnight.set(Calendar.HOUR_OF_DAY, 23);
        midnight.set(Calendar.MINUTE, 59);
        midnight.set(Calendar.SECOND, 59);

        return midnight;
    }

    private static Calendar getNoon() {
        Calendar noon = Calendar.getInstance();

        noon.set(Calendar.HOUR_OF_DAY, 12);
        noon.set(Calendar.MINUTE, 0);
        noon.set(Calendar.SECOND, 0);

        return noon;
    }
}
