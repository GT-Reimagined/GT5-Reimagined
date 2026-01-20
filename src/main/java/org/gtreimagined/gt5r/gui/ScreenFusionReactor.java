package org.gtreimagined.gt5r.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import org.gtreimagined.gtlib.gui.container.ContainerMultiMachine;
import org.gtreimagined.gtlib.gui.screen.ScreenMultiMachine;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.gtreimagined.gt5r.blockentity.multi.BlockEntityFusionReactor;

public class ScreenFusionReactor<T extends ContainerMultiMachine<BlockEntityFusionReactor>> extends ScreenMultiMachine<BlockEntityFusionReactor, T> {
    public ScreenFusionReactor(T container, Inventory inv, Component name) {
        super(container, inv, name);
        this.imageHeight = 182;
    }

    @Override
    protected void drawTitle(GuiGraphics stack, int mouseX, int mouseY) {

    }
}
