package com.dudu.sieve.render;

import com.dudu.sieve.blocks.BlockSieve;
import com.dudu.sieve.blocks.Blocks;
import net.minecraft.EntityLivingBase;
import net.minecraft.Icon;
import net.minecraft.ItemRenderer;
import net.minecraft.ItemStack;
import net.minecraft.Minecraft;
import net.minecraft.TextureMap;
import org.lwjgl.opengl.GL11;

import com.dudu.sieve.blocks.models.ModelSieve;
import com.dudu.sieve.blocks.models.ModelSieveMesh;
import com.dudu.sieve.blocks.models.ModelSieveContents;

public class ItemRenderSieve extends ItemRenderer {
    private ModelSieve model;
    private ModelSieveMesh mesh;

    public ItemRenderSieve(Minecraft mc) {
        super(mc);
        this.model = new ModelSieve();
        this.mesh = new ModelSieveMesh();
    }
    
    @Override
    public void renderItem(EntityLivingBase arg0, ItemStack itemStack, int partialTicks) {
        GL11.glPushMatrix();

        // Render the item at the appropriate scale and position
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);


        // Render the model for the sieve
        model.simpleRender(0.0625F);

        // Optionally render the mesh or contents based on the item
        renderMesh(itemStack);

        GL11.glPopMatrix();
    }

    private void renderMesh(ItemStack itemStack) {
        GL11.glPushMatrix();
        // Adjust position for mesh rendering
        GL11.glTranslatef(0.0F, 0.0F, 0.0F);

        // Render the mesh
        mesh.render(BlockSieve.meshIcon);
        GL11.glPopMatrix();
    }

}