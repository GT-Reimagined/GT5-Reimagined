package org.gtreimagined.gt5r.cover

import brachy.modularui.api.drawable.Text
import brachy.modularui.factory.SidedPosGuiData
import brachy.modularui.screen.ModularPanel
import brachy.modularui.screen.UISettings
import brachy.modularui.value.sync.IntSyncValue
import brachy.modularui.value.sync.PanelSyncManager
import brachy.modularui.widgets.ButtonWidget
import com.ibm.icu.impl.ValidIdentifiers
import net.minecraft.client.gui.screens.Screen
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import org.apache.commons.lang3.ObjectUtils.mode
import org.gtreimagined.gt5r.blockentity.single.extender.BlockEntityExtender
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine
import org.gtreimagined.gtlib.blockentity.pipe.BlockEntityPipe
import org.gtreimagined.gtlib.capability.ICoverHandler
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler
import org.gtreimagined.gtlib.cover.CoverFactory
import org.gtreimagined.gtlib.gui.SlotTypes
import org.gtreimagined.gtlib.gui.event.GuiEvents
import org.gtreimagined.gtlib.gui.event.IGuiEvent
import org.gtreimagined.gtlib.machine.Tier
import org.gtreimagined.gtlib.mui.getSyncedValue
import org.gtreimagined.gtlib.util.ca
import org.gtreimagined.gtlib.util.literal

class CoverRobotArm(source: ICoverHandler<*>, tier: Tier, side: Direction, factory: CoverFactory) : CoverItemRegulator(source, tier, side, factory) {
    var slot: Int = 0
    override fun addWidgets(modularPanel: ModularPanel<*>, sidedPosGuiData: SidedPosGuiData, syncManager: PanelSyncManager, uiSettings: UISettings) {
        super.addWidgets(modularPanel, sidedPosGuiData, syncManager, uiSettings)
        syncManager.syncValue("slot", IntSyncValue { this.slot })
        modularPanel.child(
            ButtonWidget()
                .overlay(Text.dynamic {
                    val slot =
                        getSyncedValue<Int>("slot", syncManager.modularSyncManager)
                            .orElse(0)
                    literal("Slot: $slot")
                })
                .onMousePressed { _, _ ->
                    syncManager.callSyncedAction("extra_button_event") { packet ->
                        packet.writeVarIntArray(intArrayOf(if (Screen.hasShiftDown()) 1 else 0, 4))
                    }
                    true
                }
                .size(36, 12).pos(70, 69)
        )
    }

    override fun getRenderId(): String {
        return "conveyor"
    }

    override fun onTransfer(stack: Any, inputSide: Boolean, simulate: Boolean): Boolean {
        val machine = handler.tile
        if (stack is ItemStack && !exportMode.isExport && machine is BlockEntityMachine<*> && inputSide) {
            if (machine.itemHandler.isPresent) {
                if (stack.isEmpty) return true
                if (slotLimit > 0 && stack.count < slotLimit) return true
                val toInsert = if (slotLimit > 0) ca(slotLimit, stack) else stack.copy()
                val itemHandler: MachineItemHandler<*>? = machine.itemHandler.get()
                if (itemHandler!!.inputCount > 0) {
                    val inserted = itemHandler.inputHandler.insertItem(slot, toInsert, true)
                    if (inserted.isEmpty) {
                        if (!simulate) {
                            itemHandler.inputHandler.insertItem(slot, toInsert, false)
                        }
                        stack.count = 0
                    } else if (inserted.count < toInsert.count) {
                        if (!simulate) {
                            itemHandler.inputHandler.insertItem(slot, toInsert, false)
                        }
                        stack.count = toInsert.count - inserted.count
                    }
                } else if (itemHandler.getHandler(SlotTypes.STORAGE).slots > 0) {
                    val inserted = itemHandler.getHandler(SlotTypes.STORAGE).insertItem(slot, toInsert, true)
                    if (inserted.isEmpty) {
                        if (!simulate) {
                            itemHandler.getHandler(SlotTypes.STORAGE).insertItem(slot, toInsert, false)
                        }
                        stack.count = 0
                    } else if (inserted.count < toInsert.count) {
                        if (!simulate) {
                            itemHandler.getHandler(SlotTypes.STORAGE).insertItem(slot, toInsert, false)
                        }
                        stack.count = toInsert.count - inserted.count
                    }
                }
                return true
            }
        }
        return false
    }

    override fun onGuiEvent(event: IGuiEvent, playerEntity: Player?) {
        val previous = exportMode
        super.onGuiEvent(event, playerEntity)
        if (event.getFactory() === GuiEvents.EXTRA_BUTTON) {
            val ev = event as GuiEvents.GuiEvent
            val tile = handler.tile
            val button = ev.data[1]
            if (button == 1) {
                if (previous.isExport != exportMode.isExport) {
                    slot = 0
                    tile.setChanged()
                }
            }
            if (button == 4) {
                var machine: BlockEntityMachine<*>? = null
                if (tile is BlockEntityMachine<*>) machine = tile
                if (tile is BlockEntityExtender) {
                    val extended: BlockEntity? = tile.getExtendedBlockEntity(side)
                    if (extended is BlockEntityMachine<*>) machine = extended
                }
                if (tile is BlockEntityPipe<*> && !exportMode.isExport){
                    val neighbor = tile.getCachedBlockEntity(side)
                    if (neighbor is BlockEntityMachine<*>) machine = neighbor
                }
                machine?.itemHandler?.ifPresent { h ->
                    if (exportMode.isExport) {
                        if (h.outputCount > 0) {
                            slot++
                            if (slot >= h.outputCount) slot = 0
                        } else {
                            slot = 0
                        }
                    }
                    if (!exportMode.isExport) {
                        if (h.inputCount > 0) {
                            slot++
                            if (slot >= h.inputCount) slot = 0
                        } else if (h.getHandler(SlotTypes.STORAGE).slots > 0) {
                            slot++
                            if (slot >= h.getHandler(SlotTypes.STORAGE).slots) slot = 0
                        } else {
                            slot = 0
                        }
                    }
                    machine.setChanged()
                }
            }
        }
    }

    override fun deserialize(nbt: CompoundTag) {
        super.deserialize(nbt)
        slot = nbt.getInt("slot")
    }

    override fun serialize(): CompoundTag {
        val nbt = super.serialize()
        nbt.putInt("slot", slot)
        return nbt
    }
}
