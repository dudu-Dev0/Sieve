package com.dudu.sieve.blocks.models

import net.minecraft.Icon
import net.minecraft.Tessellator
import org.lwjgl.opengl.GL11

class ModelSieveContents {
    fun renderTop(icon: Icon) {
        val tessellator = Tessellator.instance

        GL11.glDisable(GL11.GL_LIGHTING)

        //GL11.glDisable(GL11.GL_CULL_FACE);
        val length = 1.0
        val width = 1.0
        val x = 0 - width / 2
        val y = 0.0
        val z = 0 - length / 2

        val minU = icon.minU.toDouble()
        val maxU = icon.maxU.toDouble()
        val minV = icon.minV.toDouble()
        val maxV = icon.maxV.toDouble()

        tessellator.startDrawingQuads()
        tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f)

        tessellator.addVertexWithUV(x + width, y, z + length, minU, minV)
        tessellator.addVertexWithUV(x + width, y, z, minU, maxV)
        tessellator.addVertexWithUV(x, y, z, maxU, maxV)
        tessellator.addVertexWithUV(x, y, z + length, maxU, minV)

        tessellator.draw()

        GL11.glEnable(GL11.GL_LIGHTING)
        //GL11.glEnable(GL11.GL_CULL_FACE);
    }

    fun renderBottom(icon: Icon) {
        val tessellator = Tessellator.instance

        GL11.glDisable(GL11.GL_LIGHTING)

        //GL11.glDisable(GL11.GL_CULL_FACE);
        val length = 1.0
        val width = 1.0
        val x = 0 - width / 2
        val y = 0.0
        val z = 0 - length / 2

        val minU = icon.minU.toDouble()
        val maxU = icon.maxU.toDouble()
        val minV = icon.minV.toDouble()
        val maxV = icon.maxV.toDouble()

        tessellator.startDrawingQuads()
        tessellator.setColorRGBA_F(.7f, .7f, .7f, 1.0f)

        tessellator.addVertexWithUV(x, y, z + length, maxU, minV)
        tessellator.addVertexWithUV(x, y, z, maxU, maxV)
        tessellator.addVertexWithUV(x + width, y, z, minU, maxV)
        tessellator.addVertexWithUV(x + width, y, z + length, minU, minV)

        tessellator.draw()

        GL11.glEnable(GL11.GL_LIGHTING)
    }
}
