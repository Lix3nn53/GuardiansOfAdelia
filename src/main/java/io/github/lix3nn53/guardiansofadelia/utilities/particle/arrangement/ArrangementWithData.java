package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrangementWithData extends ArrangementSingle {
    protected List<Double> dataList = new ArrayList<>();

    protected ArrangementWithData(Particle particle, Particle.DustOptions dustOptions, double minHeight, double maxHeight, double gap) {
        super(particle, dustOptions, minHeight, maxHeight, gap);
    }

    public ArrangementWithData(ConfigurationSection configurationSection) {
        super(configurationSection);
    }

    public void addData(double data) {
        dataList.add(data);
    }

    public double getData(int index) {
        return dataList.get(index);
    }

    public void setDataList(List<Double> dataList) {
        this.dataList = dataList;
    }
}
