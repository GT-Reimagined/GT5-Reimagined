package org.gtreimagined.gt5r.integration.tfc.mixin;

import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.dries007.tfc.compat.jei.JEIIntegration;
import net.dries007.tfc.util.Helpers;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JEIIntegration.class)
public class JEIIntegrationMixin {

    @Inject(method = "addRecipeCatalyst(Lmezz/jei/api/registration/IRecipeCatalystRegistration;Lnet/minecraft/tags/TagKey;Lmezz/jei/api/recipe/RecipeType;)V", at = @At("HEAD"), cancellable = true, remap = false)
    private static void gt5r$injectAddRecipeCatalyst(IRecipeCatalystRegistration registry, TagKey<Item> tag, RecipeType<?> recipeType, CallbackInfo ci){
        Helpers.getAllTagValues(tag, ForgeRegistries.ITEMS).stream().limit(20).forEach((item) -> registry.addRecipeCatalyst(new ItemStack(item), new mezz.jei.api.recipe.RecipeType[]{recipeType}));
        ci.cancel();
    }
}
