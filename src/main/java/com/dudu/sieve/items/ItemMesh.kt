package com.dudu.sieve.items

import net.minecraft.CreativeTabs
import net.minecraft.IconRegister
import net.minecraft.Item
import net.minecraft.ItemStack

class ItemMesh(id: Int) : Item(id, "sieve:mesh") {
    init {
        creativeTab = CreativeTabs.tabMisc
    }

    override fun getUnlocalizedName(): String {
        return "mesh"
    }

    override fun getUnlocalizedName(item: ItemStack?): String {
        return "mesh"
    }

    override fun registerIcons(register: IconRegister) {
        this.itemIcon = register.registerIcon("sieve:mesh")
    }
}
