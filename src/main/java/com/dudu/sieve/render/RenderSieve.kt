package com.dudu.sieve.render

import com.dudu.sieve.blocks.BlockSieve
import com.dudu.sieve.blocks.models.ModelSieve
import com.dudu.sieve.blocks.models.ModelSieveContents
import com.dudu.sieve.blocks.models.ModelSieveMesh
import com.dudu.sieve.tileentities.TileEntitySieve
import com.dudu.sieve.tileentities.TileEntitySieve.SieveMode
import net.minecraft.*
import org.lwjgl.opengl.GL11

class RenderSieve(private val model: ModelSieve, private val mesh: ModelSieveMesh) : TileEntitySpecialRenderer() {
    private val contents = ModelSieveContents()

    override fun renderTileEntityAt(tileentity: TileEntity, x: Double, y: Double, z: Double, f: Float) {
        renderTable(tileentity, x, y, z, f)
        renderMesh(tileentity, x, y, z, f)
        renderContents(tileentity, x, y, z, f)
    }


    private fun renderTable(tileentity: TileEntity, x: Double, y: Double, z: Double, f: Float) {
        GL11.glPushMatrix()
        GL11.glTranslatef(x.toFloat() + 0.5f, y.toFloat() + 1.5f, z.toFloat() + 0.5f)
        GL11.glScalef(-1f, -1f, 1f)

        bindSieveTexture(tileentity.getBlockMetadata())
        model.simpleRender(0.0625f)

        GL11.glPopMatrix()
    }

    private fun renderMesh(tileentity: TileEntity, x: Double, y: Double, z: Double, f: Float) {
        GL11.glPushMatrix()
        GL11.glTranslatef(x.toFloat() + 0.5f, y.toFloat() + 0.69f, z.toFloat() + 0.5f)

        //GL11.glScalef(-1F, -1F, 1F);
        bindTexture(TextureMap.locationBlocksTexture)
        mesh.render(BlockSieve.meshIcon!!)

        GL11.glPopMatrix()
    }

    private fun renderContents(tileentity: TileEntity, x: Double, y: Double, z: Double, f: Float) {
        val sieve = tileentity as TileEntitySieve
        var icon: Icon? = null

        when (sieve.mode) {
            SieveMode.FILLED -> icon = Block.getBlock(sieve.contentID).getIcon(0, sieve.contentMeta)
            else -> {}
        }

        if (sieve.mode != SieveMode.EMPTY) {
            bindTexture(TextureMap.locationBlocksTexture)


            //TOP!
            GL11.glPushMatrix()
            GL11.glTranslatef(x.toFloat() + 0.5f, y.toFloat() + sieve.adjustedVolume, z.toFloat() + 0.5f)

            contents.renderTop(icon!!)

            GL11.glPopMatrix()


            //BOTTOM!
            GL11.glPushMatrix()
            GL11.glTranslatef(x.toFloat() + 0.5f, y.toFloat() + 0.70f, z.toFloat() + 0.5f)

            contents.renderBottom(icon)

            GL11.glPopMatrix()
        }
    }

    fun bindSieveTexture(meta: Int) {
        if (meta >= 0) {
            bindTexture(ModelSieve.textures[meta])
        }
    }
}
