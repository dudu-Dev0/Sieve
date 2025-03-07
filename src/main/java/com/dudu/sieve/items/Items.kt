package com.dudu.sieve.items

import net.minecraft.Item
import net.minecraft.ItemStack
import net.xiaoyu233.fml.reload.event.ItemRegistryEvent
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent
import net.xiaoyu233.fml.reload.utils.IdUtil

object Items : Item() {
    val meshId: Int = nextItemId
    val mesh: Item = ItemMesh(meshId)
    fun registerItems(event: ItemRegistryEvent) {
        event.register("sieve", "sieve:mesh", "mesh", mesh)
    }

    fun registerRecipes(register: RecipeRegistryEvent) {
        register.registerShapedRecipe(ItemStack(mesh), false, "AA", "AA", 'A', ItemStack(sinew))
        register.registerShapedRecipe(ItemStack(mesh), false, "AA", "AA", 'A', ItemStack(silk))
    }

    val nextItemId: Int
        get() = IdUtil.getNextItemID()
}
