package com.dudu.sieve.blocks

import com.dudu.sieve.items.Items
import net.minecraft.*
import net.xiaoyu233.fml.reload.event.ItemRegistryEvent
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent
import net.xiaoyu233.fml.reload.utils.IdUtil

object Blocks {
        val sieveId: Int = nextBlockId
        val sieveBlock: Block = BlockSieve(sieveId, Material.wood, BlockConstants())

        fun registerItemBlocks(registryEvent: ItemRegistryEvent) {
            registryEvent.registerItemBlock("sieve", "sieve:sieve", "sieve", sieveBlock)
        }

        //在这里注册影子物品合成配方
        fun registerRecipes(register: RecipeRegistryEvent) {
            register.registerShapedRecipe(
                ItemStack(sieveBlock),
                false,
                "ABA",
                "C C",
                'A',
                Block.planks,
                'B',
                Items.mesh,
                'C',
                Item.stick
            )
        }

        val nextBlockId: Int
            get() = IdUtil.getNextBlockID()

}
