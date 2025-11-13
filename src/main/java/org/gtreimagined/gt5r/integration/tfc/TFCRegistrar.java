package org.gtreimagined.gt5r.integration.tfc;

import com.google.gson.JsonObject;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood.BlockType;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.tfc.data.TFCGTFluids;
import org.gtreimagined.gt5r.integration.tfc.data.TFCMaterialEvents;
import org.gtreimagined.gt5r.integration.tfc.data.TFCMaterialTypes;
import org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes;
import org.gtreimagined.gt5r.integration.tfc.datagen.TFCBlockTagProvider;
import org.gtreimagined.gt5r.integration.tfc.datagen.TFCFluidTagProvider;
import org.gtreimagined.gt5r.integration.tfc.datagen.TFCItemTagProvider;
import org.gtreimagined.gt5r.integration.tfc.datagen.TFCLangProvider;
import org.gtreimagined.gt5r.integration.tfc.recipes.AlloyingRecipes;
import org.gtreimagined.gt5r.integration.tfc.recipes.MetalRecipes;
import org.gtreimagined.gt5r.integration.tfc.recipes.MiscTFCRecipes;
import org.gtreimagined.gt5r.integration.tfc.recipes.RockKnappingRecipes;
import org.gtreimagined.gt5r.integration.tfc.recipes.ToolCrafting;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.datagen.DynamicDataPack;
import org.gtreimagined.gtlib.datagen.GTLibDynamics;
import org.gtreimagined.gtlib.datagen.providers.GTBlockTagProvider;
import org.gtreimagined.gtlib.datagen.providers.GTFluidTagProvider;
import org.gtreimagined.gtlib.event.GTCraftingEvent;
import org.gtreimagined.gtlib.event.GTLoaderEvent;
import org.gtreimagined.gtlib.event.GTProvidersEvent;
import org.gtreimagined.gtlib.event.MaterialEvent;
import org.gtreimagined.gtlib.fluid.GTFluid;
import org.gtreimagined.gtlib.integration.xei.GTLibXEIPlugin;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.material.MaterialTypeFluid;
import org.gtreimagined.gtlib.material.TextureSet;
import org.gtreimagined.gtlib.ore.StoneType;
import org.gtreimagined.gtlib.recipe.loader.IRecipeRegistrate;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.tool.behaviour.BehaviourBlockTilling;
import org.gtreimagined.gtlib.tool.behaviour.BehaviourLogStripping;
import org.gtreimagined.gtlib.tool.behaviour.BehaviourTorchPlacing;
import org.gtreimagined.gtlib.tool.behaviour.BehaviourVanillaShovel;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.TagUtils;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.util.Helpers;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.BiConsumer;

import static net.dries007.tfc.common.blocks.soil.SoilBlockType.*;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.material.MaterialTags.TOOLS;

public class TFCRegistrar extends GTMod {

    public static Material[] array;
    public TFCRegistrar(){
        super();
        if (isEnabled()) {
            FMLJavaModLoadingContext.get().getModEventBus().register(this);
            MinecraftForge.EVENT_BUS.addListener(this::registerRecipeLoaders);
            GTLibDynamics.clientProvider(GT5Reimagined.ID, () -> new TFCLangProvider(GT5Reimagined.ID, "TFC en_us Lang", "en_us"));
        }
    }
    @Override
    public String getId() {
        return Ref.MOD_TFC;
    }

    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {
        if (event == RegistrationEvent.DATA_INIT){
            TFCMaterialTypes.init();
            TFCToolTypes.init();
            TFCGTFluids.init();
            Helpers.mapOfKeys(Rock.class, (rock) -> {
                Material material = Material.get(rock.name().toLowerCase());
                if (material == Material.NULL){
                    material = GTAPI.register(Material.class, new Material(GT5Reimagined.ID, rock.name().toLowerCase(), rock.color().col, TextureSet.NONE));
                    material.flags(DUST);
                }
                GTAPI.register(StoneType.class, new StoneType(GT5Reimagined.ID, "raw_" + rock.name().toLowerCase(), material, new Texture(Ref.MOD_TFC, "block/rock/raw/" + rock.name().toLowerCase()), SoundType.STONE, false).setStateSupplier(() -> rock.getBlock(Rock.BlockType.RAW).get().defaultBlockState()).setHardnessAndResistance(rock.category().hardness(6.5F), 10.0F).setHarvestLevel(1));
                GTAPI.register(StoneType.class, new StoneType(GT5Reimagined.ID, rock.name().toLowerCase() + "_gravel", material, new Texture(Ref.MOD_TFC, "block/rock/gravel/" + rock.name().toLowerCase()), SoundType.GRAVEL, false).setSandLike(true).setHardnessAndResistance(rock.category().hardness(2.0F)).setStateSupplier(() -> rock.getBlock(Rock.BlockType.GRAVEL).get().defaultBlockState()).setHarvestLevel(1).setRequiresTool(true));
                return true;
            });
            Helpers.mapOfKeys(SandBlockType.class, (sand) -> {
                Material material = Material.get(sand.name().toLowerCase() + "_sand");
                if (material == Material.NULL){
                    material = GTAPI.register(Material.class, new Material(GT5Reimagined.ID, sand.name().toLowerCase() + "_sand", sand.getDustColor(), TextureSet.NONE));
                    material.flags(DUST);
                }
                GTAPI.register(StoneType.class, new StoneType(GT5Reimagined.ID, sand.name().toLowerCase() + "_sand", material, new Texture(Ref.MOD_TFC,"block/sand/" + sand.name().toLowerCase()), SoundType.SAND, false)).setSandLike(true).setRequiresTool(true).setFallingDustColor(sand.getDustColor()).setStateSupplier(() -> RegistryUtils.getBlockFromId(Ref.MOD_TFC, "sand/" + sand.name().toLowerCase()).defaultBlockState());
                GTAPI.register(StoneType.class, new StoneType(GT5Reimagined.ID, sand.name().toLowerCase() + "_raw_sandstone", material, new Texture(Ref.MOD_TFC, "block/sandstone/bottom/" + sand.name().toLowerCase()), SoundType.SAND, false).setStateSupplier(() -> RegistryUtils.getBlockFromId(Ref.MOD_TFC, "raw_sandstone/" + sand.name().toLowerCase()).defaultBlockState()));
                return true;
            });
            GTMaterialTypes.LIQUID.set((m, i) -> {
                if (m == null || !GTMaterialTypes.LIQUID.allowGen(m)) return MaterialTypeFluid.getEmptyFluidAndLog(GTMaterialTypes.LIQUID, m);
                if (m.getId().equals("water")) return new FluidStack(Fluids.WATER, i);
                else if (m.getId().equals("lava")) return new FluidStack(Fluids.LAVA, i);
                else if (m == SaltWater) return new FluidStack(TFCFluids.SALT_WATER.getSource(), i);
                GTFluid fluid = GTAPI.get(GTFluid.class, GTMaterialTypes.LIQUID.getId() + "_" + m.getId());
                if (fluid == null) throw new IllegalStateException("Tried to get null fluid");
                return new FluidStack(fluid.getFluid(), i);
            });
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("extend", "tfc:field_guide");
            jsonObject.addProperty("name", "gt5r field_guide extension");
            jsonObject.addProperty("landing_text", "gt5r field_guide extension");
            GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "patchouli_books/field_guide/book.json"), jsonObject.toString().getBytes());
        }
        if (event == RegistrationEvent.DATA_READY) {
            TFCOreGen.init();
            // Make TFC logs strippable with GT tools
            Helpers.mapOfKeys(Wood.class, (woodType) -> {
                var log = woodType.getBlock(BlockType.LOG).get();
                var log_stripped = woodType.getBlock(BlockType.STRIPPED_LOG).get();
                var wood = woodType.getBlock(BlockType.WOOD).get();
                var wood_stripped = woodType.getBlock(BlockType.STRIPPED_WOOD).get();
                BehaviourLogStripping.addStrippedBlock(log, log_stripped);
                BehaviourLogStripping.addStrippedBlock(wood, wood_stripped);
                return true;
            });
            // Make TFC dirt hoe-able with GT hoes and path-able with GT shovels
            Helpers.mapOfKeys(SoilBlockType.class, (soil) -> {
                switch (soil) {
                    case DIRT, GRASS, CLAY, CLAY_GRASS -> {
                        for (SoilBlockType.Variant vary : SoilBlockType.Variant.values()) {
                            var dirt = vary.getBlock(soil).get();
                            var farmland = vary.getBlock(FARMLAND).get();
                            var path = vary.getBlock(GRASS_PATH).get();
                            BehaviourBlockTilling.addStrippedBlock(dirt, farmland);
                            BehaviourVanillaShovel.addStrippedBlock(dirt, path);
                        }
                    }
                    case ROOTED_DIRT -> {
                        for (SoilBlockType.Variant vary : SoilBlockType.Variant.values()) {
                            var rootdirt = vary.getBlock(soil).get();
                            var dirt = vary.getBlock(DIRT).get();
                            BehaviourBlockTilling.addStrippedBlock(rootdirt, dirt);
                        }
                    }
                }
                return true;
            });
            BehaviourTorchPlacing.addTorch(TFCItems.TORCH.get(), TFCBlocks.TORCH.get(), TFCBlocks.WALL_TORCH.get());
            GTLibXEIPlugin.addItemsToHide(DUST.get(Fireclay), GTCoreItems.CompressedFireClay, GTCoreItems.FireBrick);
            MaterialTags.HAS_MORTAR.all().forEach(m -> {
                if (m.has(TOOLS) && TOOLS.get(m).toolTypes().contains(GTTools.MORTAR)){
                    GTLibXEIPlugin.addItemsToHide(GTTools.MORTAR.getToolItem(m));
                }
            });
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }

    public void registerRecipeLoaders(GTLoaderEvent event){
        BiConsumer<String, IRecipeRegistrate.IRecipeLoader> loader = (a, b) -> event.registrat.add(GT5Reimagined.ID, a, b);
        loader.accept("tfc_machine_recipes", MachineRecipes::init);
    }

    @SubscribeEvent
    public void registerRecipes(GTCraftingEvent event){
        event.addLoader(MiscTFCRecipes::initRecipes);
        event.addLoader(ToolCrafting::init);
        event.addLoader(AlloyingRecipes::init);
        event.addLoader(RockKnappingRecipes::init);
        event.addLoader(MetalRecipes::init);
    }

    @SubscribeEvent
    public void onProviders(GTProvidersEvent ev) {
        GTBlockTagProvider[] blockTagProviders = new GTBlockTagProvider[1];
        blockTagProviders[0] = new TFCBlockTagProvider( Ref.MOD_TFC, "TFC Block Tags", false);
        ev.addProvider(() -> new TFCItemTagProvider(Ref.MOD_TFC, "TFC Item Tags", false,  blockTagProviders[0]));
        ev.addProvider(TFCFluidTagProvider::new);
        ev.addProvider(() -> blockTagProviders[0]);

    }

    @Override
    public void onMaterialEvent(MaterialEvent event) {
        TFCMaterialEvents.onMaterialEvent(event);
        Metals.init();
    }

    @Override
    public boolean isEnabled() {
        return GTAPI.isModLoaded(Ref.MOD_TFC);
    }
}
