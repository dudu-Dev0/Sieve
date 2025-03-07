package com.dudu.sieve.blocks

import com.dudu.sieve.SieveRegistry
import com.dudu.sieve.tileentities.TileEntitySieve
import com.dudu.sieve.tileentities.TileEntitySieve.SieveMode
import net.minecraft.*
import net.xiaoyu233.fml.util.Log

class BlockSieve(id: Int, material: Material?, constants: BlockConstants) : BlockContainer(id, material, constants) {
    init {
        setCreativeTab(CreativeTabs.tabDecorations)
        setHardness(2.0f)
        unlocalizedName = "sieve"
        setTextureName("sieve:sieve")
    }

    override fun registerIcons(register: IconRegister) {
        blockIcon = register.registerIcon("sieve" + ":" + "item_sieve")
        meshIcon = register.registerIcon("sieve" + ":" + "sieveMesh")
    }

    /*
	@Override
	public void registerBlockIcons(IconRegister register)
	{
		blockIcon = BlockWood.planks.getIcon(0,0);
		meshIcon = register.registerIcon("sieve" + ":" + "sieveMesh");
	}*/



    override fun getRenderType(): Int {
        return -1
    }

    override fun isStandardFormCube(is_standard_form_cube: BooleanArray, metadata: Int): Boolean {
        return false
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
    override fun hasTileEntity(): Boolean {
        return true
    }

    /*
	@Override
	public int damageDropped (int metadata) {
		return metadata;
	}
*/
    override fun createNewTileEntity(world: World): TileEntity {
        return TileEntitySieve()
    }

    override fun onBlockActivated(
        world: World,
        x: Int,
        y: Int,
        z: Int,
        player: EntityPlayer?,
        side: EnumFace,
        hitX: Float,
        hitY: Float,
        hitZ: Float
    ): Boolean {
        Log.warn("right click sieve")
        if (player == null) {
            return false
        }

        val sieve = world.getBlockTileEntity(x, y, z) as TileEntitySieve

        if (sieve.mode == SieveMode.EMPTY && player.inventory.currentItemStack != null) {
            val held = player.inventory.currentItemStack
            Log.warn("try to add Sievable itemId " + held.item.itemID + "gravelId" + gravel.blockID)
            if (SieveRegistry.Contains(held.item.itemID)) {
                Log.warn("successfully add sievable")
                sieve.addSievable(held.item.itemID, held.itemDamage)
                removeCurrentItem(player)
            }
        } else {
            if (world.isRemote) {
                Log.warn("remote world!")
                sieve.ProcessContents(false)
            } else {
                if (sieve.mode != SieveMode.EMPTY) {
                    sieve.ProcessContents(false)
                }
            }
        }

        return true
    }

    private fun isHuman(player: EntityPlayer): Boolean {
        val isHuman = (player is EntityClientPlayerMP)

        return isHuman
    }

    private fun removeCurrentItem(player: EntityPlayer) {
        var item = player.inventory.currentItemStack

        if (!player.capabilities.isCreativeMode) {
            item!!.stackSize -= 1
            if (item!!.stackSize == 0) {
                item = null
            }
        }
    }

    companion object {
        @JvmField
        var meshIcon: Icon? = null
    }
}
