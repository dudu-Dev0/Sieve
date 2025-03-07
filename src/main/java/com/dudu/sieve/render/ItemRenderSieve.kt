package com.dudu.sieve.render

import com.dudu.sieve.blocks.BlockSieve
import com.dudu.sieve.blocks.models.ModelSieve
import com.dudu.sieve.blocks.models.ModelSieveMesh
import net.minecraft.EntityLivingBase
import net.minecraft.ItemRenderer
import net.minecraft.ItemStack
import net.minecraft.Minecraft
import org.lwjgl.opengl.GL11

class ItemRenderSieve(mc: Minecraft) : ItemRenderer(mc) {
    private val model = ModelSieve()
    private val mesh = ModelSieveMesh()

    override fun renderItem(arg0: EntityLivingBase, itemStack: ItemStack, partialTicks: Int) {
        GL11.glPushMatrix()

        // Render the item at the appropriate scale and position
        GL11.glTranslatef(0.5f, 0.5f, 0.5f)
        GL11.glScalef(1.0f, 1.0f, 1.0f)


        // Render the model for the sieve
        model.simpleRender(0.0625f)

        // Optionally render the mesh or contents based on the item
        renderMesh(itemStack)

        GL11.glPopMatrix()
    }

    private fun renderMesh(itemStack: ItemStack) {
        GL11.glPushMatrix()
        // Adjust position for mesh rendering
        GL11.glTranslatef(0.0f, 0.0f, 0.0f)

        // Render the mesh
        mesh.render(BlockSieve.meshIcon!!)
        GL11.glPopMatrix()
    }
}