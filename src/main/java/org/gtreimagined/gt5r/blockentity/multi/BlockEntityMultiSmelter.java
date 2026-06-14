package org.gtreimagined.gt5r.blockentity.multi;

import brachy.modularui.screen.viewport.ModularGuiContext;
import brachy.modularui.theme.WidgetThemeEntry;
import brachy.modularui.value.sync.PanelSyncManager;
import brachy.modularui.value.sync.StringSyncValue;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.event.IMachineEvent;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.mui.widgets.GTInfoRenderWidget;
import org.gtreimagined.gtlib.recipe.map.IRecipeMap;
import org.gtreimagined.gtlib.recipe.map.RecipeMap;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.block.BlockCoil;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gt5r.machine.caps.ParallelRecipeHandler;
import org.gtreimagined.gtcore.item.ItemSelectorTag;

import java.util.List;

public class BlockEntityMultiSmelter extends BlockEntityParallelMultiblock<BlockEntityMultiSmelter> {
    @Getter
    @Setter
    private BlockCoil.CoilData coilData;
    private IRecipeMap recipeMap = RecipeMaps.ELECTRIC_FURNACE;

    public BlockEntityMultiSmelter(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new ParallelRecipeHandler<>(this, 1){
            @Override
            protected int maxSimultaneousRecipes(){
                if (coilData == null) return 0;
                return coilData.maxSimultaneousRecipes();
            }

            @Override
            protected boolean canRecipeContinue() {
                if (activeRecipe != null && !activeRecipe.getMapLoc().equals(recipeMap.getLoc())){
                    return false;
                }
                return super.canRecipeContinue();
            }

            @Override
            public IRecipeMap getRecipeMap() {
                return recipeMap;
            }
        });
    }

    @Override
    public void onFirstTickServer(Level level, BlockPos pos, BlockState state) {
        super.onFirstTickServer(level, pos, state);
        onMachineEvent(SlotType.STORAGE);
    }

    @Override
    public void drawInfo(GTInfoRenderWidget widget, ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
        super.drawInfo(widget, context, widgetTheme);
        int add = getMachineState() == MachineState.ACTIVE ? 40 : 16;
        RecipeMap<?> map = GTAPI.get(RecipeMap.class, new ResourceLocation(widget.getSyncedValue("recipeMap", String.class).orElse("")));
        if (map != null){
            widget.drawText(context, widgetTheme, 0, add, Utils.literal("Recipe map: ").append(map.getDisplayName()), 0xFAFAFF);
        }
    }

    @Override
    public void registerSyncHandlers(PanelSyncManager manager) {
        super.registerSyncHandlers(manager);
        manager.syncValue("recipeMap", new StringSyncValue(() -> this.recipeMap.getLoc().toString()));
    }

    @Override
    public void onMachineEvent(IMachineEvent event, Object... data) {
        if (event == SlotType.STORAGE){
            ItemStack circuit = itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getStackInSlot(0)).orElse(ItemStack.EMPTY);
            if (circuit.getItem() instanceof ItemSelectorTag tag && tag.circuitId == 1){
                this.recipeMap = RecipeMaps.ALLOY_SMELTER;
            } else {
                this.recipeMap = RecipeMaps.ELECTRIC_FURNACE;
            }
        }
        super.onMachineEvent(event, data);
    }

    @Override
    public List<String> getInfo(boolean simple) {
        return super.getInfo(simple);
    }
}
