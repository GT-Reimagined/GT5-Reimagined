package org.gtreimagined.gt5r.blockentity.single;

import lombok.Setter;
import org.gtreimagined.gt5r.blockentity.single.extender.BlockEntityUniversalExtender;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.blockentity.IExtendingBlockEntity;
import org.gtreimagined.gtlib.blockentity.IPostTickTile;
import org.gtreimagined.gtlib.capability.IFilterableHandler;
import org.gtreimagined.gtlib.capability.item.TrackedItemHandler;
import org.gtreimagined.gtlib.capability.machine.DefaultHeatHandler;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.event.IMachineEvent;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.network.GTLibNetwork;
import org.gtreimagined.gtlib.pipe.TileTicker;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.util.CodeUtils;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.data.ToolTypes;
import org.gtreimagined.gt5r.items.IItemReactorRod;
import org.gtreimagined.gtcore.blockentity.IInventorySyncTile;
import org.gtreimagined.gtcore.network.MessageTriggerInventorySync;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

import static org.gtreimagined.gtlib.Ref.B;
import static net.minecraft.core.Direction.*;
import static org.gtreimagined.gt5r.data.Materials.*;

public class BlockEntityNuclearReactorCore extends BlockEntityMachine<BlockEntityNuclearReactorCore> implements IFilterableHandler, IPostTickTile, IInventorySyncTile {
    public int[] mNeutronCounts = new int[]{0, 0, 0, 0};
    public int[] oNeutronCounts = new int[]{0, 0, 0, 0};
    public long oldHeat = 0;
    public byte mode = 0;
    public boolean oRunning, mRunning = false;
    public static final int[] S2103 = new int[] {0,0,2,1,0,3,0}, S0312 = new int[] {0,0,0,3,1,2,0};
    BlockEntityNuclearReactorCore[] adjacentReactors = new BlockEntityNuclearReactorCore[4];

    public BlockEntityNuclearReactorCore(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.fluidHandler.set(() -> new MachineFluidHandler<>(this, 64000, 1, 1));
        this.itemHandler.set(() -> new MachineItemHandler<>(this){
            @Override
            protected TrackedItemHandler<BlockEntityNuclearReactorCore> createTrackedHandler(SlotType<?> type, BlockEntityNuclearReactorCore tile) {
                if (type == SlotType.STORAGE){
                    return new TrackedItemHandler<>(tile, type, 4, type.allowExternalOutput(), type.allowExternalInput(), type.getTester(), 1){
                        @Override
                        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                            if (getStackInSlot(slot).getItem() instanceof IItemReactorRod reactorRod && reactorRod.isReactorRod(getStackInSlot(slot))){
                                return ItemStack.EMPTY;
                            }
                            return super.extractItem(slot, amount, simulate);
                        }
                    };
                }
                return super.createTrackedHandler(type, tile);
            }
        });
        this.heatHandler.set(() -> new DefaultHeatHandler(this, Integer.MAX_VALUE, 0, 0));
    }

    public ItemStack getRod(int slot){
        return itemHandler.map(i -> i.getHandler(SlotType.STORAGE).getStackInSlot(slot)).orElse(ItemStack.EMPTY);
    }

    public void setRod(int slot, ItemStack stack){
        itemHandler.ifPresent(i -> i.getHandler(SlotType.STORAGE).setStackInSlot(slot, stack));
    }

    @Override
    public void onFirstTickClient(Level level, BlockPos pos, BlockState state) {
        super.onFirstTickClient(level, pos, state);
        GTLibNetwork.NETWORK.sendToServer(new MessageTriggerInventorySync(this.getBlockPos()));
    }
    @Setter
    boolean syncSlots;

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        if (getMachineState() == MachineState.ACTIVE) setMachineState(MachineState.IDLE);

        if (syncSlots){
            syncSlots();
            syncSlots = false;
        }
    }

    @Override
    public boolean wrenchMachine(Player player, BlockHitResult res, boolean crouch) {
        return setOutputFacing(player, Utils.getInteractSide(res)) || setFacing(Utils.getInteractSide(res));
    }

    public void syncSlots(){
        if (getLevel() != null && isServerSide()){
            sidedSync(true);
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        TileTicker.SERVER_TICK_POST.remove(this);
        TileTicker.SERVER_TICK_PO2T.remove(this);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        TileTicker.SERVER_TICK_POST.add(this);
        TileTicker.SERVER_TICK_PO2T.add(this);
    }

    private BlockEntityNuclearReactorCore getCachedReactor(Direction side){
        BlockEntity cached = getCachedBlockEntity(side);
        return cached instanceof BlockEntityNuclearReactorCore core ? core : null;
    }

    @Override
    protected BlockEntity findBlockEntity(Direction side) {
        BlockEntity entity;
        entity = level.getBlockEntity(this.getBlockPos().relative(side));
        if (entity instanceof BlockEntityUniversalExtender universalExtender) {
            entity = universalExtender.getCachedBlockEntity(universalExtender.getFacing() == side.getOpposite() ? universalExtender.getOutputFacing() : universalExtender.getFacing());
        }
        return entity;
    }

    // 0 and 2 are at SIDE_Z_NEG(North)    1 3      -->X+
    // 1 and 3 are at SIDE_Z_POS(South)  2|0 2|0   |0 2
    // 0 and 1 are at SIDE_X_NEG(West)   3|1 3|1   v1 3
    // 2 and 3 are at SIDE_X_POS(East)     0 2     Z+
    public boolean isReactorRodModerated(int aSlot) {
        if (getRod(aSlot).getItem() instanceof IItemReactorRod reactorRod) {
            boolean isModerated = reactorRod.isModerated(this, aSlot, getRod(aSlot));
            if (getMachineState() == MachineState.DISABLED || (mode & B[aSlot]) != 0) return false;
            return isModerated;
        }
        return false;
    }

    public void updateReactorRodModeration(int aSlot) {
        if (getRod(aSlot).getItem() instanceof IItemReactorRod reactorRod){
            reactorRod.updateModeration(this, aSlot, getRod(aSlot));
        }
    }

    public int getReactorRodNeutronEmission(int aSlot) {
        if (getMachineState() != MachineState.DISABLED && (mode & B[aSlot]) == 0 && getRod(aSlot).getItem() instanceof IItemReactorRod reactorRod){
            return reactorRod.getReactorRodNeutronEmission(this, aSlot, getRod(aSlot));
        }
        mNeutronCounts[aSlot] = 0;
        return 0;
    }

    public boolean getReactorRodNeutronReaction(int aSlot) {
        if (getLevel().getGameTime() % 20 == 18) mNeutronCounts[aSlot] -= oNeutronCounts[aSlot];
        if (getMachineState() != MachineState.DISABLED && (mode & B[aSlot]) == 0 && getRod(aSlot).getItem() instanceof IItemReactorRod reactorRod){
            return reactorRod.getReactorRodNeutronReaction(this, aSlot, getRod(aSlot));
        }
        return false;
    }

    public int getReactorRodNeutronReflection(int aSlot, int aNeutrons, boolean aModerated) {
        if (getMachineState() != MachineState.DISABLED && (mode & B[aSlot]) == 0 && getRod(aSlot).getItem() instanceof IItemReactorRod reactorRod){
            return reactorRod.getReactorRodNeutronReflection(this, aSlot, getRod(aSlot), aNeutrons, aModerated);
        }
        return 0;
    }

    @Override
    public InteractionResult onInteractServer(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, @Nullable GTToolType type) {
        ItemStack held = player.getItemInHand(hand);
        if (hit.getDirection() == Direction.UP && getCover(UP).isEmpty()){
            if ((held.getItem() instanceof IItemReactorRod reactorRod && reactorRod.isReactorRod(held)) || type == ToolTypes.PINCERS) {
                Vec3 vec = hit.getLocation();
                double x = vec.x - pos.getX();
                double z = vec.z - pos.getZ();
                int tSlot = x < 0.5 ? z < 0.5 ? 0 : 1 : z < 0.5 ? 2 : 3;
                ItemStack stack = getRod(tSlot);
                if (stack.isEmpty() && type != ToolTypes.PINCERS){
                    setRod(tSlot, Utils.ca(1, held));
                    held.shrink(1);
                    if (getMachineState() != MachineState.DISABLED) {
                        disableMachine();
                    }
                    level.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.BLOCKS, 10.f, 1.0f);
                    return InteractionResult.SUCCESS;
                } else if (!stack.isEmpty() && type == ToolTypes.PINCERS){
                    if (!player.addItem(stack)){
                        player.drop(stack, true);
                    }
                    setRod(tSlot, ItemStack.EMPTY);
                    Utils.damageStack(held, player);
                    level.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.BLOCKS, 10.f, 1.0f);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        if (held.getItem() == GT5RItems.GeigerCounter){
            player.displayClientMessage(Utils.translatable("message.gt5r.nuclear_reactor.neutron_levels", oNeutronCounts[0], oNeutronCounts[1], oNeutronCounts[2], oNeutronCounts[3]), false);
            player.displayClientMessage(Utils.translatable("message.gt5r.nuclear_reactor." + (getMachineState() == MachineState.DISABLED ? "off": "on")), false);
            return InteractionResult.SUCCESS;
        }
        return super.onInteractServer(state, world, pos, player, hand, hit, type);
    }

    @Override
    public boolean test(SlotType<?> slotType, int slot, ItemStack stack) {
        return stack.getItem() instanceof IItemReactorRod reactorRod && reactorRod.isReactorRod(stack);
    }

    @Override
    public void onUnregisterPost() {

    }

    @Override
    public Function<Direction, Texture> getMultiTexture() {
        return direction -> {
            return type.getBaseTexture(this.tier, UP, this.machineState);
        };
    }

    @Override
    public void onServerTickPost(Level level, BlockPos pos, boolean aFirst) {
        if (aFirst){
            // It is == 19 because the Sensors react to == 0, so this is the realistic fastest a Sensor can display.
            if (level.getGameTime() % 20 == 19){
                if (machineState != MachineState.DISABLED){
                    BlockEntityNuclearReactorCore[] adjacentReactors = new BlockEntityNuclearReactorCore[4];
                    Direction.Plane.HORIZONTAL.forEach(d -> {
                        BlockEntityNuclearReactorCore core = getCachedReactor(d);
                        if (core != null) {
                            adjacentReactors[d.get2DDataValue()] = core;
                        }
                    });
                    BlockEntityNuclearReactorCore tAdjacent;
                    int tNeutronCount = getReactorRodNeutronEmission(0);
                    boolean tModerated = isReactorRodModerated(0);
                    if (tNeutronCount != 0 || tModerated) {
                        mNeutronCounts[0] += getReactorRodNeutronReflection(1, tNeutronCount, tModerated);
                        mNeutronCounts[0] += getReactorRodNeutronReflection(2, tNeutronCount, tModerated);
                        tAdjacent = adjacentReactors[NORTH.get2DDataValue()];
                        if (tAdjacent != null) mNeutronCounts[0] += tAdjacent.getReactorRodNeutronReflection(S2103[SOUTH.get3DDataValue()], tNeutronCount, tModerated);
                        tAdjacent = adjacentReactors[WEST.get2DDataValue()];
                        if (tAdjacent != null) mNeutronCounts[0] += tAdjacent.getReactorRodNeutronReflection(S0312[EAST.get3DDataValue()], tNeutronCount, tModerated);
                    }
                    tNeutronCount = getReactorRodNeutronEmission(1);
                    tModerated = isReactorRodModerated(1);
                    if (tNeutronCount != 0 || tModerated) {
                        mNeutronCounts[1] += getReactorRodNeutronReflection(0, tNeutronCount, tModerated);
                        mNeutronCounts[1] += getReactorRodNeutronReflection(3, tNeutronCount, tModerated);
                        tAdjacent = adjacentReactors[SOUTH.get2DDataValue()];
                        if (tAdjacent != null) mNeutronCounts[1] += tAdjacent.getReactorRodNeutronReflection(S0312[NORTH.get3DDataValue()], tNeutronCount, tModerated);
                        tAdjacent = adjacentReactors[WEST.get2DDataValue()];
                        if (tAdjacent != null) mNeutronCounts[1] += tAdjacent.getReactorRodNeutronReflection(S2103[EAST.get3DDataValue()], tNeutronCount, tModerated);
                    }
                    tNeutronCount = getReactorRodNeutronEmission(2);
                    tModerated = isReactorRodModerated(2);
                    if (tNeutronCount != 0 || tModerated) {
                        mNeutronCounts[2] += getReactorRodNeutronReflection(0, tNeutronCount, tModerated);
                        mNeutronCounts[2] += getReactorRodNeutronReflection(3, tNeutronCount, tModerated);
                        tAdjacent = adjacentReactors[NORTH.get2DDataValue()];
                        if (tAdjacent != null) mNeutronCounts[2] += tAdjacent.getReactorRodNeutronReflection(S0312[SOUTH.get3DDataValue()], tNeutronCount, tModerated);
                        tAdjacent = adjacentReactors[EAST.get2DDataValue()];
                        if (tAdjacent != null) mNeutronCounts[2] += tAdjacent.getReactorRodNeutronReflection(S2103[WEST.get3DDataValue()], tNeutronCount, tModerated);
                    }

                    tNeutronCount = getReactorRodNeutronEmission(3);
                    tModerated = isReactorRodModerated(3);
                    if (tNeutronCount != 0 || tModerated) {
                        mNeutronCounts[3] += getReactorRodNeutronReflection(1, tNeutronCount, tModerated);
                        mNeutronCounts[3] += getReactorRodNeutronReflection(2, tNeutronCount, tModerated);
                        tAdjacent = adjacentReactors[SOUTH.get2DDataValue()];
                        if (tAdjacent != null) mNeutronCounts[3] += tAdjacent.getReactorRodNeutronReflection(S2103[NORTH.get3DDataValue()], tNeutronCount, tModerated);
                        tAdjacent = adjacentReactors[EAST.get2DDataValue()];
                        if (tAdjacent != null) mNeutronCounts[3] += tAdjacent.getReactorRodNeutronReflection(S0312[WEST.get3DDataValue()], tNeutronCount, tModerated);
                    }
                }
            }
        } else {
            //if (machineState == MachineState.DISABLED) return;
            if (level.getGameTime() % 20 == 19) {
                updateReactorRodModeration(0);
                updateReactorRodModeration(1);
                updateReactorRodModeration(2);
                updateReactorRodModeration(3);
            }
            long tCalc = CodeUtils.divup((oNeutronCounts[0] = mNeutronCounts[0]) + (oNeutronCounts[1] = mNeutronCounts[1]) + (oNeutronCounts[2] = mNeutronCounts[2]) + (oNeutronCounts[3] = mNeutronCounts[3]), 256);
            // TODO Raycasting through Lead, Water and similar Blocks.
            if (tCalc > 0 && level.getGameTime() % 20 == 10) {
                for (LivingEntity tEntity : level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.offset(-200, 0, -200).atY(level.getMinBuildHeight()), pos.offset(200, 0, 200).atY(level.getMaxBuildHeight())))){
                    int tStrength = CodeUtils.bindInt((long)(tCalc - tEntity.distanceToSqr(pos.getX(), pos.getY(), pos.getZ())));
                    if (tStrength > 0) Utils.applyRadioactivity(tEntity, (int)CodeUtils.divup(tStrength, 10), tStrength);
                }
            }
            oRunning = mRunning;
            mRunning = tCalc != 0;
            DefaultHeatHandler handler = heatHandler.orElse(null);
            MachineFluidHandler<?> fluidHandler1 = fluidHandler.orElse(null);
            int tEnergy = handler.getHeat();
            if (getReactorRodNeutronReaction(0)) mRunning = true;
            if (getReactorRodNeutronReaction(1)) mRunning = true;
            if (getReactorRodNeutronReaction(2)) mRunning = true;
            if (getReactorRodNeutronReaction(3)) mRunning = true;
            if (oRunning != mRunning) sidedSync(true);
            FluidStack coldCoolant = fluidHandler1.getInputTanks().getFluidInTank(0);
            int tDivider = 1;
            if (coldCoolant.getFluid().is(Sodium.getFluidTag())) tDivider = 6;
            if (coldCoolant.getFluid().is(Tin.getFluidTag())) tDivider = 3;
            handler.setCurrentHeat((int) (CodeUtils.divup(handler.getHeat() - tEnergy, tDivider) + tEnergy));
            oldHeat = handler.getHeat() - tEnergy;
            if (handler.getHeat() > 0){
                boolean isExploding = false;
                Material hotCoolant = null;
                int heatPerCoolant = 0;

                if (coldCoolant.getFluid().is(Coolant.getFluidTag())){
                    heatPerCoolant = 20;
                    hotCoolant = HotCoolant;
                } else if (coldCoolant.getFluid().is(DistilledWater.getFluidTag())){
                    heatPerCoolant = 80;
                    hotCoolant = Steam;
                } else if (coldCoolant.getFluid().is(Tin.getFluidTag())){
                    heatPerCoolant = 40;
                    hotCoolant = HotMoltenTin;
                } else if (coldCoolant.getFluid().is(Sodium.getFluidTag())){
                    heatPerCoolant = 30;
                    hotCoolant = HotMoltenSodium;
                } else if (coldCoolant.getFluid().is(SemiheavyWater.getFluidTag())){
                    heatPerCoolant = 40;
                    hotCoolant = HotSemiheavyWater;
                } else if (coldCoolant.getFluid().is(HeavyWater.getFluidTag())){
                    heatPerCoolant = 50;
                    hotCoolant = HotHeavyWater;
                } else if (coldCoolant.getFluid().is(TritiatedWater.getFluidTag())){
                    heatPerCoolant = 60;
                    hotCoolant = HotTritiatedWater;
                } else if (coldCoolant.getFluid().is(LithiumChloride.getFluidTag())){
                    heatPerCoolant = 15;
                    hotCoolant = HotMoltenLithiumChloride;
                } else if (coldCoolant.getFluid().is(CarbonDioxide.getFluidTag())){
                    heatPerCoolant = 20;
                    hotCoolant = HotCarbonDioxide;
                } else if (coldCoolant.getFluid().is(Helium.getFluidTag())){
                    heatPerCoolant = 30;
                    hotCoolant = HotHelium;
                } else if (coldCoolant.getFluid().is(ThoriumSalt.getFluidTag())){
                    heatPerCoolant = 2560000;
                    hotCoolant = HotCoolant;
                }
                if (heatPerCoolant == 0 || hotCoolant == null) {
                    isExploding = true;
                } else {
                    tEnergy = handler.getHeat() / heatPerCoolant;
                    int multiplier = hotCoolant == Steam ? 160 : 1;
                    int toFill = hotCoolant == Steam ? tEnergy * 160 : tEnergy;
                    if (tEnergy > 0){
                        FluidStack fluidHolder = hotCoolant.has(GTMaterialTypes.GAS) ? hotCoolant.getGas(tEnergy * multiplier) : hotCoolant.getLiquid(tEnergy);
                        if (coldCoolant.getAmount() >= tEnergy && fluidHandler1.fillOutput(fluidHolder.copy(), FluidAction.SIMULATE) == (long) tEnergy * multiplier){
                            fluidHandler1.fillOutput(fluidHolder, FluidAction.EXECUTE);
                            handler.extractInternal(tEnergy * heatPerCoolant, false);
                            fluidHandler1.drainInput(tEnergy, FluidAction.EXECUTE);
                        }
                    }
                }
                if (isExploding && !itemHandler.get().getHandler(SlotType.STORAGE).isEmpty()){
                    level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    tCalc *= 2;
                    setRod(0, ItemStack.EMPTY);
                    setRod(1, ItemStack.EMPTY);
                    setRod(2, ItemStack.EMPTY);
                    setRod(3, ItemStack.EMPTY);
                    for (LivingEntity tEntity : level.getEntitiesOfClass(LivingEntity.class, new AABB(pos.offset(-500, 0, -500).atY(level.getMinBuildHeight()), pos.offset(500, 0, 500).atY(level.getMaxBuildHeight())))){
                        int tStrength = CodeUtils.bindInt((long)(tCalc - tEntity.distanceToSqr(pos.getX(), pos.getY(), pos.getZ())));
                        if (tStrength > 0) Utils.applyRadioactivity(tEntity, (int)CodeUtils.divup(tStrength, 10), tStrength);
                    }
                }

            }

        }
    }

    @Override
    public void onMachineEvent(IMachineEvent event, Object... data) {
        if (event == SlotType.STORAGE){
            sidedSync(true);
        }
        super.onMachineEvent(event, data);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putByte("mode", mode);
        tag.putLong("oldHeat", oldHeat);
        ListTag currentNeutronCountTag = new ListTag();
        ListTag oldNeutronCountTag = new ListTag();
        for (int i = 0; i < 4; i++) {
            currentNeutronCountTag.add(IntTag.valueOf(mNeutronCounts[i]));
            oldNeutronCountTag.add(IntTag.valueOf(oNeutronCounts[i]));
        }
        tag.put("currentNeutronCounts", currentNeutronCountTag);
        tag.put("oldNeutronCounts", oldNeutronCountTag);
        tag.putBoolean("oRunning", oRunning);
        tag.putBoolean("mRunning", mRunning);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        itemHandler.ifPresent(e -> {
            CompoundTag in = new CompoundTag();
            e.getAll().forEach((f,i) -> {
                in.put(f.getId(), serializeWithEmpty(i, new CompoundTag()));
            });
            nbt.put(Ref.KEY_MACHINE_ITEMS, in);
        });
        nbt.putBoolean("oRunning", oRunning);
        nbt.putBoolean("mRunning", mRunning);
        return nbt;
    }

    public CompoundTag serializeWithEmpty(IItemHandler container, CompoundTag nbt) {
        ListTag nbtTagList = new ListTag();


        for(int i = 0; i < container.getSlots(); ++i) {
            CompoundTag itemTag = new CompoundTag();
            itemTag.putInt("Slot", i);
            container.getStackInSlot(i).save(itemTag);
            itemTag.putInt("count", container.getStackInSlot(i).getCount());
            nbtTagList.add(itemTag);
        }

        nbt.put("Items", nbtTagList);
        return nbt;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        mode = tag.getByte("mode");
        oldHeat = tag.getLong("oldHeat");
        mRunning = tag.getBoolean("mRunning");
        oRunning = tag.getBoolean("oRunning");
        ListTag currentNeutronCountTag = tag.getList("currentNeutronCounts", Tag.TAG_INT);
        ListTag oldNeutronCountTag = tag.getList("oldNeutronCounts", Tag.TAG_INT);
        for (int i = 0; i < 4; i++) {
            mNeutronCounts[i] = currentNeutronCountTag.getInt(i);
            oNeutronCounts[i] = oldNeutronCountTag.getInt(i);
        }
    }

    @Override
    public boolean canPlayerOpenGui(Player playerEntity, Direction side) {
        return playerEntity.isCreative();
    }
}
