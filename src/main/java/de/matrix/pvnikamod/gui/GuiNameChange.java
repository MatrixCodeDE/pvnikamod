package de.matrix.pvnikamod.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiNameChange extends GuiBase {

    private final GuiTextField[] origin = new GuiTextField[5];
    private final GuiTextField[] translate = new GuiTextField[5];

    public GuiNameChange(GuiScreen lastScreen) {
        super(lastScreen);
    }
}
