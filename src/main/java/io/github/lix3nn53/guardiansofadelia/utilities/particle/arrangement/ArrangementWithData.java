package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrangementWithData extends ArrangementSingle {
    protected List<Float> dataList = new ArrayList<>();

    protected ArrangementWithData(Particle particle, Particle.DustOptions dustOptions, float minHeight, float maxHeight, float gap) {
        super(particle, dustOptions, minHeight, maxHeight, gap);
    }

    public ArrangementWithData(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    public void addData(float data) {
        dataList.add(data);
    }

    public float getData(int index) {
        return dataList.get(index);
    }

    public void setDataList(List<Float> dataList) {
        this.dataList = dataList;
    }
}
