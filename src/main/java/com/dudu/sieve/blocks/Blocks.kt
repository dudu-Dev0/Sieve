package com.dudu.sieve.blocks;

import com.dudu.sieve.items.Items;
import net.minecraft.Block;
import net.minecraft.BlockConstants;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import net.xiaoyu233.fml.FishModLoader;
import net.xiaoyu233.fml.reload.event.ItemRegistryEvent;
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent;
import net.xiaoyu233.fml.reload.utils.IdUtil;

public class Blocks extends Block {
    public static final int sieveId = getNextBlockId();
    public static final Block sieveBlock = new BlockSieve(sieveId,Material.wood,new BlockConstants());
    protected Blocks(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    //在这里注册影子物品
    public static void registerItemBlocks(ItemRegistryEvent registryEvent) {
        registryEvent.registerItemBlock("sieve","sieve:sieve","sieve",sieveBlock);
    }

    //在这里注册影子物品合成配方
    public static void registerRecipes(RecipeRegistryEvent register) {
        register.registerShapedRecipe(new ItemStack(sieveBlock),false,"ABA","C C",'A',Block.planks,'B',Items.mesh,'C',Item.stick);
    }
    public static int getNextBlockId() {
    	return IdUtil.getNextBlockID();
    }
}
