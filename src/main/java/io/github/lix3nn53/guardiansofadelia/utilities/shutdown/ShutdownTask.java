package io.github.lix3nn53.guardiansofadelia.utilities.shutdown;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseQueries;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.TimerTask;

public class ShutdownTask extends TimerTask {

    private static void showMessage(String message) {
        if (!message.isEmpty()) {
            Bukkit.broadcastMessage(message);
        }
    }

    private static void executeCommand(String command) {
        if (!command.isEmpty()) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    private static void executeDatabaseOperation(DatabaseOperation databaseOperation) {
        if (databaseOperation != null) {
            try {
                databaseOperation.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        runCountDown();
    }

    private void runCountDown() {
        for (final Stage stage : Stage.values()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(GuardiansOfAdelia.getInstance(), new ShutdownRunnable(stage), 20L * stage.getDelay());
        }
    }

    enum Stage {
        MINUTE_10("Server restarting in 5 minutes!", 0, DatabaseOperation.CLEAR_EXPIRED_PREMIUM_RANKS),
        MINUTE_5("Server restarting in 5 minutes!", 5 * 60),
        MINUTE_4("Server restarting in 5 minutes!", 6 * 60),
        MINUTE_3("Server restarting in 5 minutes!", 7 * 60),
        MINUTE_2("Server restarting in 5 minutes!", 8 * 60),
        MINUTE_1("Server restarting in 60 seconds!", 9 * 60),
        SECONDS_30("Server restarting in 30 seconds!", 9 * 60 + 30),
        SECONDS_20("Server restarting in 20 seconds!", 9 * 60 + 40),
        SECONDS_15("Server restarting in 15 seconds!", 9 * 60 + 45),
        SECONDS_10("Server restarting in 10 seconds!", 9 * 60 + 50),
        SECONDS_5("Server restarting in 5 seconds!", 9 * 60 + 55),
        STOP("Stopping the server!", "stop", 9 * 60 + 60);

        private final String message;
        private final String command;
        private final int delay;
        private DatabaseOperation databaseOperation;

        Stage() {
            this("");
        }

        Stage(String message) {
            this(message, 5);
        }

        Stage(String message, int delay) {
            this(message, "", delay);
        }

        Stage(String message, int delay, DatabaseOperation databaseOperation) {
            this(message, "", delay, databaseOperation);
        }

        Stage(String message, String command) {
            this(message, command, 3);
        }

        Stage(String message, String command, int delay) {
            this.delay = delay;
            this.message = message;
            this.command = command;
        }

        Stage(String message, String command, int delay, DatabaseOperation databaseOperation) {
            this.delay = delay;
            this.message = message;
            this.command = command;
            this.databaseOperation = databaseOperation;
        }

        public String getMessage() {
            return message;
        }

        public int getDelay() {
            return delay;
        }

        public String getCommand() {
            return command;
        }

        public DatabaseOperation getDatabaseOperation() {
            return databaseOperation;
        }
    }

    enum DatabaseOperation {
        CLEAR_EXPIRED_PREMIUM_RANKS;

        public void execute() throws SQLException {
            if (this.equals(DatabaseOperation.CLEAR_EXPIRED_PREMIUM_RANKS)) {
                DatabaseQueries.clearExpiredPremiumRanks();
            }
        }
    }

    static class ShutdownRunnable implements Runnable {
        private final Stage stage;

        ShutdownRunnable(Stage stage) {
            this.stage = stage;
        }

        public void run() {
            showMessage(stage.getMessage());
            executeCommand(stage.getCommand());
            executeDatabaseOperation(stage.getDatabaseOperation());
        }
    }
}
