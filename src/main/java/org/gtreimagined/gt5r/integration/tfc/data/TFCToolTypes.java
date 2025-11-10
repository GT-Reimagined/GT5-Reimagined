package org.gtreimagined.gt5r.integration.tfc.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.block.Blocks;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.integration.tfc.item.MaterialJavelin;
import org.gtreimagined.gt5r.integration.tfc.item.ProspectingBehaviour;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.recipe.material.MaterialRecipe;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.tool.IGTTool;
import org.gtreimagined.gtlib.tool.behaviour.BehaviourTorchPlacing;
import org.gtreimagined.tesseract.api.forge.TesseractCaps;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.UNSPLIT_FUNCTION;
import static org.gtreimagined.gtlib.material.MaterialTags.FLINT;

public class TFCToolTypes {
    public static final GTToolType PROPICK = GTAPI.register(GTToolType.class, new GTToolType(GT5Reimagined.ID, "propick", 1, 2, 10, 1.0F, -2.8F, false)).setHasContainer(false).setMaterialTypeItem(TFCMaterialTypes.PROPICK_HEAD).setMaterialTypeItemPredicate(m -> !m.has(FLINT));
    public static final GTToolType JAVELIN = GTAPI.register(GTToolType.class, new GTToolType(GT5Reimagined.ID, "javelin", 2, 1, 10, 3.0f, -2.2f, false)).addEffectiveBlocks(Blocks.COBWEB).setUseAction(UseAnim.SPEAR).setToolSupplier(MaterialJavelin::new).setMaterialTypeItem(TFCMaterialTypes.JAVELIN_HEAD);

    public static final MaterialRecipe.Provider PART_BUILDER = MaterialRecipe.registerProvider("part_builder", GT5Reimagined.ID, id -> new MaterialRecipe.ItemBuilder() {

        @Override
        public ItemStack build(CraftingContainer inv, MaterialRecipe.Result mats) {
            int forgeQuality = (Integer) mats.mats.get("primary");
            ResourceLocation location = new ResourceLocation(id.replace('-', '_'));
            IGTTool type = GTAPI.get(IGTTool.class, location.getPath(), location.getNamespace());

            ItemStack tool = type.asItemStack(type.getPrimaryMaterial(ItemStack.EMPTY), type.getSecondaryMaterial(ItemStack.EMPTY));
            if (forgeQuality > 0){
                tool.getOrCreateTag().putInt("tfc:forging_bonus", forgeQuality);
            }
            return tool;
        }

        @Override
        public Map<String, Object> getFromResult(@NotNull ItemStack stack) {
            return ImmutableMap.of();
        }
    });

    public static void init(){
        GTTools.SCYTHE.addTags("hoe").addBehaviour(ScythHarvestBehaviour.INSTANCE);
        PROPICK.setCustomName("Prospector's Pick").addBehaviour(ProspectingBehaviour.INSTANCE);
    }
}
