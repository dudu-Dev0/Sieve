package com.dudu.sieve

import com.dudu.sieve.blocks.Blocks
import com.dudu.sieve.blocks.models.ModelSieve
import com.dudu.sieve.blocks.models.ModelSieveMesh
import com.dudu.sieve.items.Items
import com.dudu.sieve.render.RenderSieve
import com.dudu.sieve.tileentities.TileEntitySieve
import com.google.common.eventbus.Subscribe
import net.xiaoyu233.fml.reload.event.*
import net.xiaoyu233.fml.reload.utils.IdUtil

class EventListener {
    //物品注册
    @Subscribe
    fun onItemRegister(event: ItemRegistryEvent) {
        Items.registerItems(event)
        Blocks.registerItemBlocks(event)
    }

    //合成方式注册
    @Subscribe
    fun onRecipeRegister(event: RecipeRegistryEvent) {
        Items.registerRecipes(event)
        Blocks.registerRecipes(event)
    }

    //玩家登录事件
    @Subscribe
    fun onPlayerLoggedIn(event: PlayerLoggedInEvent?) {
    }

    //指令事件
    @Subscribe
    fun handleChatCommand(event: HandleChatCommandEvent?) {
    }

    //实体注册
    @Subscribe
    fun onEntityRegister(event: EntityRegisterEvent?) {
    }

    //实体渲染注册
    @Subscribe
    fun onEntityRendererRegistry(event: EntityRendererRegistryEvent?) {
    }

    //方块实体注册
    @Subscribe
    fun onTileEntityRegister(event: TileEntityRegisterEvent) {
        event.register(TileEntitySieve::class.java, "sieve:sieve_tileentity")
    }

    //方块实体渲染注册
    @Subscribe
    fun onTileEntityRendererRegister(event: TileEntityRendererRegisterEvent) {
        //event.register(TileEntitySieve.class,"sieve:sieve_tileentity")
        event.register(TileEntitySieve::class.java, RenderSieve(ModelSieve(), ModelSieveMesh()))
    }

    //声音注册
    @Subscribe
    fun onSoundsRegister(event: SoundsRegisterEvent?) {
    }

    companion object {
        val nextEntityID: Int
            get() = IdUtil.getNextEntityID()
        val nextItemID: Int
            get() = IdUtil.getNextItemID()
    }
}
