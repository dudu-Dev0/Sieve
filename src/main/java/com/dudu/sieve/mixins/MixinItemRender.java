package com.dudu.sieve.mixins;

import com.dudu.sieve.blocks.Blocks;
import com.dudu.sieve.blocks.models.ModelSieve;
import com.dudu.sieve.blocks.models.ModelSieveMesh;
import com.dudu.sieve.render.ItemRenderSieve;
import net.minecraft.EntityLivingBase;
import net.minecraft.ItemRenderer;
import net.minecraft.ItemStack;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRender {

    private static final ModelSieve model = new ModelSieve();
    private static final ModelSieveMesh mesh = new ModelSieveMesh();

    // 注入到 ItemRenderer 的 renderItem 方法
    @Inject(method = "renderItem", at = @At(value = "HEAD", target = "Lnet/minecraft/RenderBlocks;renderBlockAsItem(Lnet/minecraft/Block;IF)V"))
    public void renderItem(EntityLivingBase base,ItemStack stack, int partialTicks, CallbackInfo ci) {
        if (stack.getItem().itemID == Blocks.INSTANCE.getSieveId()) {
            // 这里调用你的渲染器
            new ItemRenderSieve(Minecraft.getMinecraft()).renderItem(base,stack, (int)partialTicks);
        }
    }
}