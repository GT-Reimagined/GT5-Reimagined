package org.gtreimagined.gt5r.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.gui.GuiInstance;
import org.gtreimagined.gtlib.gui.IGuiElement;
import org.gtreimagined.gtlib.gui.Widget;
import org.gtreimagined.gtlib.gui.container.ContainerMachine;
import org.gtreimagined.gtlib.gui.widget.WidgetSupplier;
import org.gtreimagined.gtlib.integration.xei.GTLibXEIPlugin;
import org.gtreimagined.gtlib.mixin.client.AbstractContainerScreenAccessor;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.gtreimagined.gt5r.blockentity.single.BlockEntityCoalBoiler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.gtreimagined.gtlib.gui.ICanSyncData.SyncDirection.SERVER_TO_CLIENT;

public class CoalBoilerFuelWidget extends Widget {
    private int fuel = 0, maxFuel = 0;
    protected CoalBoilerFuelWidget(@NotNull GuiInstance gui, @Nullable IGuiElement parent) {
        super(gui, parent);
    }

    public static WidgetSupplier build() {
        return builder(CoalBoilerFuelWidget::new);
    }

    @Override
    public void init() {
        super.init();
        gui.syncInt(() -> ((BlockEntityCoalBoiler)((ContainerMachine<?>)gui.container).getTile()).getFuel(), i -> fuel = i, SERVER_TO_CLIENT);
        gui.syncInt(() -> ((BlockEntityCoalBoiler)((ContainerMachine<?>)gui.container).getTile()).getMaxFuel(), i -> maxFuel = i, SERVER_TO_CLIENT);
    }

    @Override
    public void render(GuiGraphics graphics, double mouseX, double mouseY, float partialTicks) {
        if (fuel > 0) {
            float per = (float) fuel / maxFuel;
            if (per > 1.0F) {
                per = 1.0F;
            }
            int lvl = (int) (per * (float) 18);
            if (lvl < 0) {
                return;
            }
            int y = (realY() + 18) - lvl;
            drawTexture(graphics, gui.handler.getGuiTexture(), realX(), y, ((AbstractContainerScreenAccessor)gui.screen).getImageWidth(), 18 - lvl, 18, lvl);
        }
    }

    @Override
    public void mouseOver(GuiGraphics graphics, double mouseX, double mouseY, float partialTicks) {
        super.mouseOver(graphics, mouseX, mouseY, partialTicks);
        renderTooltip(graphics,"Show Recipes", mouseX, mouseY, 0, 0, 18, 18);
        renderTooltip(graphics,"Fuel: " + fuel, mouseX, mouseY + 10, 0, 10, 18, 18);
    }

    @OnlyIn(Dist.CLIENT)
    protected void renderTooltip(GuiGraphics graphics, String text, double mouseX, double mouseY, int x, int y, int w, int h) {
        if (isInside(x, y, w, h, mouseX, mouseY)){
            renderTooltip(graphics, Utils.literal(text), mouseX, mouseY);
        }

    }

    public boolean isInside(int x, int y, int w, int h, double mouseX, double mouseY) {
        int realX = realX() + x;
        int realY = realY() + y;
        return ((mouseX >= realX && mouseX <= realX + w) && (mouseY >= realY && mouseY <= realY + h));
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        super.onClick(mouseX, mouseY, button);
        if (this.gui.handler instanceof BlockEntityMachine<?> machine) {
            GTLibXEIPlugin.showCategory(machine.getMachineType(), machine.getMachineTier());
        }
    }
}
