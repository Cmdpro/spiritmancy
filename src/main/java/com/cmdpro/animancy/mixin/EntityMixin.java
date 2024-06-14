package com.cmdpro.animancy.mixin;

import com.cmdpro.animancy.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "lavaHurt", at = @At("HEAD"), cancellable = true)
    private void lavaHurt(CallbackInfo ci) {
        if (((Entity)(Object)this) instanceof LivingEntity livingEntity) {
            if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(ItemInit.STRIDERBOOTS.get()) && !livingEntity.level().getBlockState(BlockPos.containing(livingEntity.position().add(0, 0.5f, 0))).getFluidState().is(FluidTags.LAVA)) {
                ci.cancel();
            }
        }
    }
}
