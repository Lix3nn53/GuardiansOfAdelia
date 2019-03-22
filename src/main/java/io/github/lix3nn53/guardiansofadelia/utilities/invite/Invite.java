package io.github.lix3nn53.guardiansofadelia.utilities.invite;

import org.bukkit.entity.Player;

public interface Invite {

    Player getSender();

    Player getReceiver();

    void send();

    void accept();

    void reject();
}
