package io.github.lix3nn53.guardiansofadelia.utilities.hologram;

class FakeEntityManager {

    private static int LASTEST_ENTITY_ID = 1700500;

    static void onFakeEntityCreate() {
        LASTEST_ENTITY_ID++;
    }

    static int getNextEntityID() {
        return LASTEST_ENTITY_ID;
    }

}
