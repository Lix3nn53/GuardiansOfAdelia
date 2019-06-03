package io.github.lix3nn53.guardiansofadelia.utilities.gui;

import java.util.ArrayList;
import java.util.List;

public class GuiPage {

    private final List<GuiLine> guiLines = new ArrayList<>();

    public List<GuiLine> getGuiLines() {
        return guiLines;
    }

    public void addLine(GuiLine guiLine) {
        if (isEmpty()) {
            guiLines.add(guiLine);
        }
    }

    public boolean isEmpty() {
        return guiLines.size() < 5;
    }

}
