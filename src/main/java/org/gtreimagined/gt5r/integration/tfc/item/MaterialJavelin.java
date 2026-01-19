package org.gtreimagined.gt5r.integration.tfc.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import net.dries007.tfc.client.render.blockentity.JavelinItemRenderer;
import net.dries007.tfc.common.items.JavelinItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.NonNullLazy;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.tfc.client.GTJavelinItemRenderer;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.behaviour.IBehaviour;
import org.gtreimagined.gtlib.behaviour.IDestroySpeed;
import org.gtreimagined.gtlib.capability.energy.ItemEnergyHandler;
import org.gtreimagined.gtlib.datagen.builder.GTItemModelBuilder;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.tool.GTItemTier;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.tool.IBasicGTTool;
import org.gtreimagined.gtlib.tool.IGTTool;
import org.gtreimagined.tesseract.api.context.TesseractItemContext;
import org.gtreimagined.tesseract.api.eu.IEnergyHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

public class MaterialJavelin extends JavelinItem implements IGTTool {
    protected String domain;
    protected GTToolType type;
    protected GTItemTier itemTier;
    public static final ResourceLocation OVERLAY_LOCATION = new ResourceLocation(GT5Reimagined.ID, "textures/entity/javelin_overlay.png");
    public MaterialJavelin(String domain, GTToolType type, GTItemTier tier, Properties properties) {
        super(tier, 0, type.getBaseAttackDamage() + tier.getAttackDamageBonus(), type.getBaseAttackSpeed(), properties, new ResourceLocation(GT5Reimagined.ID, "textures/entity/javelin.png"));  // 0 as base attack as it adds
        this.domain = domain;
        this.type = type;
        this.itemTier = tier;
        GTAPI.register(IGTTool.class, this);
    }

    @Override
    public String getId() {
        if (type.isSimple()) return String.join("_", itemTier.getPrimary().getId(),type.getId());;
        return type.getId();
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @NotNull
    @Override
    public GTToolType getGTToolType() {
        return type;
    }

    @Override
    public GTItemTier getGTItemTier() {
        return itemTier;
    }

    /*@Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            private final NonNullLazy<GTJavelinItemRenderer> renderer = NonNullLazy.of(() -> new GTJavelinItemRenderer(MaterialJavelin.this.getTextureLocation(), OVERLAY_LOCATION));

            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return (BlockEntityWithoutLevelRenderer)this.renderer.get();
            }
        });
    }*/

    @NotNull
    @Override
    public ItemStack asItemStack(@NotNull Material primary, @NotNull Material secondary) {
        return resolveStack(primary, secondary, 0, 0);
    }

    @Override
    public int getEnergyTier() {
        return 0;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader world, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> list) {
        onGenericFillItemGroup(group, list, 0);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        onGenericAddInformation(stack, tooltip, flag);
        super.appendHoverText(stack, world, tooltip, flag);
    }

    //TODO figure this out
    //@Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return type.getUseAction();
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return type.getUseAction() == UseAnim.NONE ? super.getUseDuration(stack) : 72000;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return getTier(stack).getUses();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return onGenericHitEntity(stack, target, attacker, 0.75F, 0.75F);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        return onGenericItemUse(ctx);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        return genericInteractLivingEntity(stack, player, interactionTarget, usedHand);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        InteractionResultHolder<ItemStack> result = onGenericRightclick(level, player, usedHand);
        if (result.getResult().shouldAwardStats()){
            return result;
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return type.getToolTypes().contains(BlockTags.MINEABLE_WITH_AXE);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slotType, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        if (slotType == EquipmentSlot.MAINHAND) {
            modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", type.getBaseAttackDamage() + getTier(stack).getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
            modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", type.getBaseAttackSpeed(), AttributeModifier.Operation.ADDITION));
        }
        return modifiers;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return (entity instanceof Player && ((Player) entity).isCreative()) ? 0 : damage(stack, amount);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return getTier(stack).getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return !type.isPowered() && getTier(toRepair).getRepairIngredient().test(repair);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (type.getBlacklistedEnchantments().contains(enchantment)) return false;
        return type.isPowered() ? enchantment != Enchantments.UNBREAKING : enchantment.category.canEnchant(stack.getItem());
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return type.hasContainer();
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack oldStack) {
        return getGenericContainerItem(oldStack);
    }

    @Override
    public IEnergyHandlerItem createEnergyHandler(TesseractItemContext context) {
        return null;
    }

    @Override
    public void onItemModelBuild(ItemLike item, GTItemModelProvider prov) {
        var b = prov.getBuilder(item).loader(new ResourceLocation("forge", "separate-perspective")).override().model(new ResourceLocation(GT5Reimagined.ID, "item/" + this.getId() + "_throwing")).predicate(new ResourceLocation("tfc", "throwing"), 1.0f).end()
                .property("gui_light", "front");
        JsonObject base = new JsonObject();
        base.addProperty("parent", GT5Reimagined.ID + ":item/" + this.getId() + "_in_hand");
        JsonObject gui = new JsonObject();
        gui.addProperty("parent", GT5Reimagined.ID + ":item/" + this.getId() + "_gui");
        JsonObject perspectives = new JsonObject();
        perspectives.add("none", gui);
        perspectives.add("fixed", gui);
        perspectives.add("ground", gui);
        perspectives.add("gui", gui);
        b.property("base", base).property("perspectives", perspectives);
        GTItemModelBuilder builder = prov.getBuilder(getId() + "_gui");
        builder.parent(new ResourceLocation("item/generated"));
        for (int i = 0; i < getTextures().length; i++) {
            builder.texture("layer" + i, getTextures()[i]);
        }
        builder = prov.getBuilder(getId() + "_in_hand");
        builder.parent(new ResourceLocation("item/trident_in_hand"));
        builder.texture("particle", getTextures()[0]);
        builder = prov.getBuilder(getId() + "_throwing_base");
        builder.parent(new ResourceLocation("item/trident_throwing"));
        builder.texture("particle", getTextures()[0]);
        b = prov.getBuilder(getId() + "_throwing").loader(new ResourceLocation("forge", "separate-perspective")).property("gui_light", "front");
        base = new JsonObject();
        base.addProperty("parent", GT5Reimagined.ID + ":item/" + this.getId() + "_throwing_base");
        b.property("base", base).property("perspectives", perspectives);

    }
}
