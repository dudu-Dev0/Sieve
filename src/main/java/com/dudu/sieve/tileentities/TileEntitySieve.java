package com.dudu.sieve.tileentities;

import com.dudu.sieve.blocks.BlockSieve;
import com.dudu.sieve.blocks.Blocks;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.Block;
import net.minecraft.EntityItem;
import net.minecraft.INetworkManager;
import net.minecraft.Icon;
import net.minecraft.ItemStack;
import net.minecraft.Minecraft;
import net.minecraft.NBTTagCompound;
import net.minecraft.NetClientHandler;
import net.minecraft.NetServerHandler;
import net.minecraft.Packet;
import net.minecraft.Packet132TileEntityData;
import net.minecraft.TileEntity;
import com.dudu.sieve.particles.ParticleSieve;
import com.dudu.sieve.SieveRegistry;
import com.dudu.sieve.helpers.SiftReward;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.fml.util.Log;
import org.lwjgl.util.Color;


public class TileEntitySieve extends TileEntity{
	private static final float MIN_RENDER_CAPACITY = 0.70f;
	private static final float MAX_RENDER_CAPACITY = 0.9f;
	private static final float PROCESSING_INTERVAL = 0.075f;
	private static final int UPDATE_INTERVAL = 20;
	private Color color = new Color(1,1,1,1);

	public int contentID = 0;
	public int contentMeta = 0;

	private float volume = 0;
	public SieveMode mode = SieveMode.EMPTY;

	private int timer = 0;
	private boolean update = false;
	private boolean particleMode = false;
	private int timesClicked = 0;

	public enum SieveMode
	{EMPTY(0), FILLED(1);
	private SieveMode(int v){this.value = v;}
	public int value;
	}

	public TileEntitySieve()
	{
		mode = mode.EMPTY;
        
	}

	public void addSievable(int blockID, int blockMeta)
	{
		this.contentID = blockID;
		this.contentMeta = blockMeta;

		this.mode = SieveMode.FILLED;

		volume = 1.0f;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote && particleMode)
		{
			spawnFX(contentID, contentMeta);
		}

		timer++;
		if (timer >= UPDATE_INTERVAL)
		{
			timesClicked = 0;
			
			timer = 0;
			disableParticles();

			if (update)
			{
				update();
			}
		}
	}

	public void ProcessContents(boolean creative)
	{	
		if (creative)
		{
			volume = 0;
		}else
		{
			timesClicked++;
			if (timesClicked <= 6)
			{
				volume -= PROCESSING_INTERVAL;
			}
		}
        Log.warn("volume:"+volume+",times:"+timesClicked+"remote?:"+worldObj.isRemote);
		if (volume <= 0)
		{
			mode = mode.EMPTY;
			//give rewards!
			if (!worldObj.isRemote)
			{
                Log.warn("try to give rewards");
				ArrayList<SiftReward> rewards = SieveRegistry.getRewards(contentID, contentMeta);
				if (rewards.size() > 0)
				{
					Iterator<SiftReward> it = rewards.iterator();
					while(it.hasNext())
					{
						SiftReward reward = it.next();

						if (worldObj.rand.nextInt(reward.rarity) == 0)
						{
							EntityItem entityitem = new EntityItem(worldObj, (double)xCoord + 0.5D, (double)yCoord + 1.5D, (double)zCoord + 0.5D, new ItemStack(reward.id, 1, reward.meta));

							double f3 = 0.05F;
							entityitem.motionX = worldObj.rand.nextGaussian() * f3;
							entityitem.motionY = (0.2d);
							entityitem.motionZ = worldObj.rand.nextGaussian() * f3;
                            //if (worldObj.isRemote) MinecraftServer.getServer().getEntityWorld().spawnEntityInWorld(entityitem);
							worldObj.spawnEntityInWorld(entityitem);
                            Log.warn("give reward "+reward.id);
							//System.out.println("Spawning: " + reward.id);
						}
					}
				}
			}
		}
		else
		{
			particleMode = true;
		}

		update = true;
	}
    
	private void spawnFX(int blockID, int blockMeta)
	{
		Block block = Block.blocksList[blockID];
		if (block != null)
		{
			Icon icon = block.getIcon(0, blockMeta);

			for (int x = 0; x < 4; x++)
			{	
				ParticleSieve dust = new ParticleSieve(worldObj, 
						xCoord + 0.8d * worldObj.rand.nextFloat() + 0.15d, 
						yCoord + 0.69d, 
						zCoord + 0.8d * worldObj.rand.nextFloat() + 0.15d, 
						0.0d, 0.0d, 0.0d, icon);
				
				Minecraft.getMinecraft().effectRenderer.addEffect(dust);
			}
		}
	}

	public float getAdjustedVolume()
	{
		float capacity = MAX_RENDER_CAPACITY - MIN_RENDER_CAPACITY;
		float adjusted = volume * capacity;		
		adjusted += MIN_RENDER_CAPACITY;
		return adjusted;
	}

	private void update()
	{
		update = false;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	private void disableParticles()
	{
		particleMode = false;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		switch (compound.getInteger("mode"))
		{
		case 0:
			mode = SieveMode.EMPTY;
			break;

		case 1:
			mode = SieveMode.FILLED;
			break;
		}
		contentID = compound.getInteger("contentID");
		contentMeta = compound.getInteger("contentMeta");
		volume = compound.getFloat("volume");
		particleMode = compound.getBoolean("particles");
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("mode", mode.value);
		compound.setInteger("contentID", contentID);
		compound.setInteger("contentMeta", contentMeta);
		compound.setFloat("volume", volume);
		compound.setBoolean("particles", particleMode);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
        
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord,Blocks.sieveId, tag);
	}
    
/*
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		NBTTagCompound tag = pkt.data;
		this.readFromNBT(tag);
	}
*/
}
