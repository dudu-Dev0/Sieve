package com.dudu.sieve.items;

import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.xiaoyu233.fml.reload.event.ItemRegistryEvent;
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent;
import net.xiaoyu233.fml.reload.utils.IdUtil;

public class Items extends Item {
    public static final int meshId = getNextItemId();
    public static final Item mesh = new ItemMesh(meshId);
    public static void registerItems(ItemRegistryEvent event) {
        event.register("sieve","sieve:mesh","mesh",mesh);
    }

    public static void registerRecipes(RecipeRegistryEvent register) {
        register.registerShapedRecipe(new ItemStack(mesh),false,"AA","AA",'A',new ItemStack(Item.sinew));
        register.registerShapedRecipe(new ItemStack(mesh),false,"AA","AA",'A',new ItemStack(Item.silk));
    }

    public static int getNextItemId() {
    	return IdUtil.getNextItemID();
    }
}
