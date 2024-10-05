package muramasa.gregtech.blockentity.multi;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.PlatformFluidHandler;
import io.github.gregtechintergalactical.gtcore.blockentity.BlockEntityMaterialBasicMultiMachine;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.blockentity.BlockEntityCache;
import muramasa.antimatter.capability.fluid.FluidTank;
import muramasa.antimatter.capability.fluid.FluidTanks;
import muramasa.antimatter.capability.machine.MachineFluidHandler;
import muramasa.antimatter.data.AntimatterTags;
import muramasa.antimatter.util.AntimatterPlatformUtils;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.GT5RRef;
import muramasa.gregtech.machine.MultiblockTankMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;
import tesseract.FluidPlatformUtils;
import tesseract.TesseractGraphWrappers;

import java.util.List;
import java.util.Optional;

import static muramasa.antimatter.data.AntimatterMaterials.Wood;
import static net.minecraft.core.Direction.UP;

public class BlockEntityLargeTank extends BlockEntityMaterialBasicMultiMachine<BlockEntityLargeTank> {
    MultiblockTankMachine tankMachine;
    public BlockEntityLargeTank(MultiblockTankMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.fluidHandler.set(() -> new LargeTankFluidHandler(this, type.getCapacity(), 10000, 1, 0));
        this.tankMachine = type;
    }

    @Override
    public boolean allowsFakeTiles() {
        return true;
    }

    public Block getCasing(){
        Block block = AntimatterAPI.get(Block.class, material.getId() + "_wall", GT5RRef.ID);
        if (block != null) return block;
        return Blocks.AIR;
    }

    @Override
    public List<String> getInfo(boolean simple) {
        List<String> list = super.getInfo(simple);
        fluidHandler.ifPresent(f -> {
            FluidHolder stack = f.getInputTanks().getFluidInTank(0);
            String addition = AntimatterPlatformUtils.isFabric() && !stack.isEmpty() ? "/" + stack.getFluidAmount() + "droplets" : "";
            list.add("Fluid: " + (stack.isEmpty() ? "Empty" : (stack.getFluidAmount() / TesseractGraphWrappers.dropletMultiplier) + "mb" + addition + " of " + FluidPlatformUtils.INSTANCE.getFluidDisplayName(stack).getString()));
        });
        return list;
    }

    public static class LargeTankFluidHandler extends MachineFluidHandler<BlockEntityLargeTank> {

        public LargeTankFluidHandler(BlockEntityLargeTank tile, int capacity, int pressure, int inputCount, int outputCount) {
            super(tile, capacity, pressure, inputCount, outputCount);
        }

        @Nullable
        @Override
        public FluidTanks getOutputTanks() {
            return super.getInputTanks();
        }

        @Override
        protected FluidTank getTank(int tank) {
            return getInputTanks().getTank(tank);
        }

        @Override
        public FluidTanks getTanks(int tank) {
            return getInputTanks();
        }

        @Override
        public void onUpdate() {
            super.onUpdate();
            Direction dir = tile.getFacing();
            if (getTank(0).getStoredFluid().getFluidAmount() > 0 && dir != UP){
                Optional<PlatformFluidHandler> cap = BlockEntityCache.getFluidHandlerCached(tile.getLevel(), tile.getBlockPos().relative(dir), dir.getOpposite());
                cap.ifPresent(other -> Utils.transferFluids(this.getOutputTanks(), other, 1000));
            }
        }

        @Override
        public boolean canInput(Direction direction) {
            return direction != tile.getFacing();
        }

        @Override
        public long insertFluid(FluidHolder fluid, boolean simulate) {
            if (tile.getMaterial() == Wood){
                if (FluidPlatformUtils.INSTANCE.isFluidGaseous(fluid.getFluid())) {
                    long inserted = super.insertFluid(fluid, true);
                    if (inserted > 0) {
                        if (!simulate) tile.getLevel().playSound(null, tile.getBlockPos(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
                        return inserted;
                    }
                    return 0;
                }
                if (FluidPlatformUtils.INSTANCE.getFluidTemperature(fluid.getFluid()) > 350){
                    long inserted = super.insertFluid(fluid, simulate);
                    if (inserted > 0 && !simulate){
                        meltdown();
                    }
                    return inserted;
                }
            }
            if (!tile.tankMachine.isAcidProof() && fluid.getFluid().is(AntimatterTags.ACID)){
                if (!simulate) {
                    tile.getLevel().setBlock(tile.getBlockPos(), Blocks.AIR.defaultBlockState(), 3);
                }
                return Math.min(16L, fluid.getFluidAmount());
            }
            return super.insertFluid(fluid, simulate);
        }

        public boolean meltdown() {
            BlockPos offset = tile.getBlockPos().relative(tile.getFacing().getOpposite());
            int tX = offset.getX(), tY = offset.getY(), tZ = offset.getZ();
            for (int i = -1; i <= 1; i++) for (int j = -1; j <= 1; j++) for (int k = -1; k <= 1; k++) {
                burn(tile.level, tX+i, tY+j, tZ+k);
                if (tile.getLevel().random.nextInt(4) == 0) tile.getLevel().setBlock(new BlockPos(tX+i, tY+j, tZ+k), Blocks.FIRE.defaultBlockState(), 3);
            }
            FluidHolder fluidHolder = getInputTanks().getTank(0).getStoredFluid();
            if (fluidHolder.getFluidAmount() >= 1000 * TesseractGraphWrappers.dropletMultiplier && fluidHolder.getFluid() == Fluids.LAVA){
                tile.getLevel().setBlock(offset, Blocks.LAVA.defaultBlockState(), 3);
            }
            tile.getLevel().setBlock(tile.getBlockPos(), Blocks.FIRE.defaultBlockState(), 3);
            return true;
        }

        public static void burn(Level aWorld, int aX, int aY, int aZ) {
            BlockPos pos = new BlockPos(aX, aY, aZ);
            for (Direction tSide : Direction.values()) {
                fire(aWorld, pos.relative(tSide), false);
            }
        }

        public static boolean fire(Level aWorld, BlockPos pos, boolean aCheckFlammability) {
            BlockState tBlock = aWorld.getBlockState(pos);
            if (tBlock.getMaterial() == Material.LAVA || tBlock.getMaterial() == Material.FIRE) return false;
            if (tBlock.getMaterial() == Material.CLOTH_DECORATION || tBlock.getCollisionShape(aWorld, pos).isEmpty()) {
                if (AntimatterPlatformUtils.getFlammability(tBlock, aWorld, pos, Direction.NORTH) > 0) return aWorld.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
                if (aCheckFlammability) {
                    for (Direction tSide : Direction.values()) {
                        BlockState tAdjacent = aWorld.getBlockState(pos.relative(tSide));
                        if (tAdjacent.getBlock() == Blocks.CHEST || tAdjacent.getBlock() == Blocks.TRAPPED_CHEST) return aWorld.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
                        if (AntimatterPlatformUtils.getFlammability(tAdjacent, aWorld, pos.relative(tSide), tSide.getOpposite()) > 0) return aWorld.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
                    }
                } else {
                    return aWorld.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
                }
            }
            return false;
        }
    }
}
