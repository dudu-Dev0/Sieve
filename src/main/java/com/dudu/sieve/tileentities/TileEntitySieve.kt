package com.dudu.sieve.tileentities

import com.dudu.sieve.SieveRegistry
import com.dudu.sieve.blocks.Blocks
import com.dudu.sieve.helpers.SiftReward
import com.dudu.sieve.particles.ParticleSieve
import net.minecraft.*
import net.xiaoyu233.fml.util.Log
import org.lwjgl.util.Color

class TileEntitySieve : TileEntity() {
    private val color = Color(1, 1, 1, 1)

    var contentID: Int = 0
    var contentMeta: Int = 0

    private var volume = 0f
    var mode: SieveMode = SieveMode.EMPTY

    private var timer = 0
    private var update = false
    private var particleMode = false
    private var timesClicked = 0

    enum class SieveMode
        (var value: Int) {
        EMPTY(0), FILLED(1)
    }

    init {
        mode = SieveMode.EMPTY
    }

    fun addSievable(blockID: Int, blockMeta: Int) {
        this.contentID = blockID
        this.contentMeta = blockMeta

        this.mode = SieveMode.FILLED

        volume = 1.0f
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
    }

    override fun updateEntity() {
        if (worldObj.isRemote && particleMode) {
            spawnFX(contentID, contentMeta)
        }

        timer++
        if (timer >= UPDATE_INTERVAL) {
            timesClicked = 0

            timer = 0
            disableParticles()

            if (update) {
                update()
            }
        }
    }

    fun ProcessContents(creative: Boolean) {
        if (creative) {
            volume = 0f
        } else {
            timesClicked++
            if (timesClicked <= 6) {
                volume -= PROCESSING_INTERVAL
            }
        }
        Log.warn("volume:" + volume + ",times:" + timesClicked + "remote?:" + worldObj.isRemote)
        if (volume <= 0) {
            mode = SieveMode.EMPTY
            //give rewards!
            if (!worldObj.isRemote) {
                Log.warn("try to give rewards")
                val rewards = SieveRegistry.getRewards(contentID, contentMeta)
                if (rewards.size > 0) {
                    val it = rewards.iterator()
                    while (it.hasNext()) {
                        val reward = it.next()!!

                        if (worldObj.rand.nextInt(reward.rarity) == 0) {
                            val entityitem = EntityItem(
                                worldObj,
                                xCoord.toDouble() + 0.5,
                                yCoord.toDouble() + 1.5,
                                zCoord.toDouble() + 0.5,
                                ItemStack(reward.id, 1, reward.meta)
                            )

                            val f3 = 0.05
                            entityitem.motionX = worldObj.rand.nextGaussian() * f3
                            entityitem.motionY = (0.2)
                            entityitem.motionZ = worldObj.rand.nextGaussian() * f3
                            //if (worldObj.isRemote) MinecraftServer.getServer().getEntityWorld().spawnEntityInWorld(entityitem);
                            worldObj.spawnEntityInWorld(entityitem)
                            Log.warn("give reward " + reward.id)
                            //System.out.println("Spawning: " + reward.id);
                        }
                    }
                }
            }
        } else {
            particleMode = true
        }

        update = true
    }

    private fun spawnFX(blockID: Int, blockMeta: Int) {
        val block = Block.blocksList[blockID]
        if (block != null) {
            val icon = block.getIcon(0, blockMeta)

            for (x in 0..3) {
                val dust = ParticleSieve(
                    worldObj,
                    xCoord + 0.8 * worldObj.rand.nextFloat() + 0.15,
                    yCoord + 0.69,
                    zCoord + 0.8 * worldObj.rand.nextFloat() + 0.15,
                    0.0, 0.0, 0.0, icon
                )

                Minecraft.getMinecraft().effectRenderer.addEffect(dust)
            }
        }
    }

    val adjustedVolume: Float
        get() {
            val capacity =
                MAX_RENDER_CAPACITY - MIN_RENDER_CAPACITY
            var adjusted = volume * capacity
            adjusted += MIN_RENDER_CAPACITY
            return adjusted
        }

    private fun update() {
        update = false
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
    }

    private fun disableParticles() {
        particleMode = false
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)

        when (compound.getInteger("mode")) {
            0 -> mode = SieveMode.EMPTY
            1 -> mode = SieveMode.FILLED
        }
        contentID = compound.getInteger("contentID")
        contentMeta = compound.getInteger("contentMeta")
        volume = compound.getFloat("volume")
        particleMode = compound.getBoolean("particles")
    }

    override fun writeToNBT(compound: NBTTagCompound) {
        super.writeToNBT(compound)
        compound.setInteger("mode", mode.value)
        compound.setInteger("contentID", contentID)
        compound.setInteger("contentMeta", contentMeta)
        compound.setFloat("volume", volume)
        compound.setBoolean("particles", particleMode)
    }

    override fun getDescriptionPacket(): Packet {
        val tag = NBTTagCompound()
        this.writeToNBT(tag)

        return Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, Blocks.sieveId, tag)
    } /*
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		NBTTagCompound tag = pkt.data;
		this.readFromNBT(tag);
	}
*/

    companion object {
        private const val MIN_RENDER_CAPACITY = 0.70f
        private const val MAX_RENDER_CAPACITY = 0.9f
        private const val PROCESSING_INTERVAL = 0.075f
        private const val UPDATE_INTERVAL = 20
    }
}
