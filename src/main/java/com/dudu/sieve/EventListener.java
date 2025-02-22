package com.dudu.sieve;

import com.dudu.sieve.blocks.Blocks;
import com.dudu.sieve.blocks.models.ModelSieve;
import com.dudu.sieve.blocks.models.ModelSieveMesh;
import com.dudu.sieve.items.Items;
import com.dudu.sieve.render.RenderSieve;
import com.dudu.sieve.tileentities.TileEntitySieve;
import com.google.common.eventbus.Subscribe;
import net.minecraft.NetHandler;
import net.minecraft.NetworkListenThread;
import net.minecraft.TileEntity;
import net.xiaoyu233.fml.reload.event.*;
import net.xiaoyu233.fml.reload.utils.IdUtil;

import java.util.Objects;

public class EventListener {
    //物品注册
    @Subscribe
    public void onItemRegister(ItemRegistryEvent event) {
        Items.registerItems(event);
        Blocks.registerItemBlocks(event);
    }

    //合成方式注册
    @Subscribe
    public void onRecipeRegister(RecipeRegistryEvent event) {
        Items.registerRecipes(event);
        Blocks.registerRecipes(event);
    }

    //玩家登录事件
    @Subscribe
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
    }

    //指令事件
    @Subscribe
    public void handleChatCommand(HandleChatCommandEvent event) {
    }

    //实体注册
    @Subscribe
    public void onEntityRegister(EntityRegisterEvent event) {
    }

    //实体渲染注册
    @Subscribe
    public void onEntityRendererRegistry(EntityRendererRegistryEvent event) {
    }

    //方块实体注册
    @Subscribe
    public void onTileEntityRegister(TileEntityRegisterEvent event) {
        event.register(TileEntitySieve.class,"sieve:sieve_tileentity");
    }

    //方块实体渲染注册
    @Subscribe
    public void onTileEntityRendererRegister(TileEntityRendererRegisterEvent event) {
        //event.register(TileEntitySieve.class,"sieve:sieve_tileentity")
        event.register(TileEntitySieve.class,new RenderSieve(new ModelSieve(),new ModelSieveMesh()));
    }

    //声音注册
    @Subscribe
    public void onSoundsRegister(SoundsRegisterEvent event) {
    }

    public static int getNextEntityID() {
        return IdUtil.getNextEntityID();
    }
    public static int getNextItemID() {
    	return IdUtil.getNextItemID();
    }
}
