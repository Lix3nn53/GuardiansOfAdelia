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
        Calendar night = getNightTime();
        Calendar noon = getNoonTime();

        Calendar currentTime = Calendar.getInstance();

        if (noon.after(currentTime)) {
            return noon.getTime();
        } else {
            return night.getTime();
        }
    }

    private static Calendar getNightTime() {
        Calendar midnight = Calendar.getInstance();

        midnight.set(Calendar.HOUR_OF_DAY, 1); //one hour before + 59 minutes
        midnight.set(Calendar.MINUTE, 59); //because delay of restart is 1 minute
        midnight.set(Calendar.SECOND, 0);

        return midnight;
    }

    private static Calendar getNoonTime() {
        Calendar noon = Calendar.getInstance();

        noon.set(Calendar.HOUR_OF_DAY, 13); //one hour before + 59 minutes
        noon.set(Calendar.MINUTE, 59); //because delay of restart is 1 minute
        noon.set(Calendar.SECOND, 0);

        return noon;
    }
}
