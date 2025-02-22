package com.dudu.sieve.blocks;

import com.dudu.sieve.render.ItemRenderSieve;
import java.util.List;

import net.minecraft.BlockConstants;
import net.minecraft.BlockContainer;
import net.minecraft.BlockGravel;
import net.minecraft.BlockWood;
import net.minecraft.CreativeTabs;
import net.minecraft.EntityClientPlayerMP;
import net.minecraft.EnumFace;
import net.minecraft.Icon;
import net.minecraft.IconRegister;
import net.minecraft.Material;
import net.minecraft.Block;
import net.minecraft.BlockContainer;
import net.minecraft.EntityPlayer;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.TileEntity;
import net.minecraft.World;
import com.dudu.sieve.tileentities.TileEntitySieve;
import com.dudu.sieve.tileentities.TileEntitySieve.SieveMode;
import com.dudu.sieve.SieveRegistry;
import net.xiaoyu233.fml.util.Log;

public class BlockSieve extends BlockContainer {
    public static Icon meshIcon;

	public BlockSieve(int id,Material material,BlockConstants constants) {
        super(id,material,constants);
		setCreativeTab(CreativeTabs.tabDecorations);
		setHardness(2.0f);
        setUnlocalizedName("sieve");
        setTextureName("sieve:sieve");
	}
    @Override
    public void registerIcons(IconRegister register) {
		blockIcon = register.registerIcon("sieve" + ":" + "item_sieve");
		meshIcon = register.registerIcon("sieve" + ":" + "sieveMesh");
    }
    /*
	@Override
	public void registerBlockIcons(IconRegister register)
	{
		blockIcon = BlockWood.planks.getIcon(0,0);
		meshIcon = register.registerIcon("sieve" + ":" + "sieveMesh");
	}*/
	@Override
	public void getItemStacks(int item, CreativeTabs tabs, List subItems) {
		for (int i = 0; i < 6; i++) {
			subItems.add(new ItemStack(item, 1, i));
		}
        //super.getItemStacks(item,tabs,subItems);
	}
    
    
	@Override
	public int getRenderType()
	{
		return -1;
	}
    
    @Override
    public boolean isStandardFormCube(boolean[] is_standard_form_cube, int metadata) {
        return false;
    }
    /*
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
*/
	/*
	public boolean renderAsNormalBlock()
	{
		return false;
	}
*/
	@Override
	public boolean hasTileEntity()
	{
		return true;
	}
/*
	@Override
	public int damageDropped (int metadata) {
		return metadata;
	}
*/
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySieve();
	}
    
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace side, float hitX, float hitY, float hitZ)
	{
        Log.warn("right click sieve");
		if (player == null)
		{
			return false;
		}

		TileEntitySieve sieve = (TileEntitySieve) world.getBlockTileEntity(x, y, z);

		if (sieve.mode == SieveMode.EMPTY && player.inventory.getCurrentItemStack() != null)
		{
			ItemStack held = player.inventory.getCurrentItemStack();
			Log.warn("try to add Sievable itemId "+held.getItem().itemID + "gravelId" +BlockGravel.gravel.blockID);
			if (SieveRegistry.Contains(held.getItem().itemID))
			{
                Log.warn("successfully add sievable");
				sieve.addSievable(held.getItem().itemID, held.getItemDamage());
				removeCurrentItem(player);
			}
		}else
		{
			if (world.isRemote)
			{
                Log.warn("remote world!");
				sieve.ProcessContents(false);
			}else
			{
				if (sieve.mode != SieveMode.EMPTY)
				{
						sieve.ProcessContents(false);
				}
			}
		}

		return true;
	}

	private boolean isHuman(EntityPlayer player)
	{
		boolean isHuman = (player instanceof EntityClientPlayerMP);

		return isHuman;
	}

	private void removeCurrentItem(EntityPlayer player)
	{
		ItemStack item = player.inventory.getCurrentItemStack();

		if (!player.capabilities.isCreativeMode)
		{
			item.stackSize -= 1;
			if (item.stackSize == 0)
			{
				item = null;
			}
		}

	}
}
