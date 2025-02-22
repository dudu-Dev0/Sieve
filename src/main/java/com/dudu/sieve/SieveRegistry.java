package com.dudu.sieve;

import java.util.ArrayList;
import java.util.Iterator;

import com.dudu.sieve.helpers.SiftReward;
import net.minecraft.Block;
import net.minecraft.BlockGravel;
import net.minecraft.INetworkManager;
import net.minecraft.Item;
import net.minecraft.ItemCoal;
import net.xiaoyu233.fml.util.Log;

public class SieveRegistry {
	
	public static ArrayList<SiftReward> rewards = new ArrayList<SiftReward>();
	
	public static boolean dropCopper = false;
	public static boolean dropTin = false;
	public static boolean dropSilver = false;
	public static boolean dropLead = false;
	public static boolean dropNickel = false;
	public static boolean dropPlatinum = false;
	public static boolean dropAluminum = false;
	
	public static boolean dropRubberSeeds = false;
	
	public static void register(int sourceID, int sourceMeta, int outputID, int outputMeta, int rarity)
	{
		SiftReward entry = new SiftReward(sourceID, sourceMeta, outputID, outputMeta, rarity);
		
		if(Block.blocksList[sourceID] != null)
		{
			rewards.add(entry);
		}else
		{
			Log.warn("Ex Nihilo: An item was added to the SieveRegistry which was not a block");
		}
	}
	
	public static void register(int sourceID, int outputID, int outputMeta, int rarity)
	{
		SiftReward entry = new SiftReward(sourceID, outputID, outputMeta, rarity);
		
		if(Block.blocksList[sourceID] != null)
		{
			rewards.add(entry);
		}else
		{
			Log.warn("Ex Nihilo: An item was added to the SieveRegistry which was not a block");
		}
	}
	
	public static ArrayList<SiftReward> getRewards(int id, int meta)
	{
		ArrayList<SiftReward> rewardList = new ArrayList();

		Iterator<SiftReward> it = rewards.iterator();
		while(it.hasNext())
		{
			SiftReward reward = it.next();

			if (reward.sourceID == id && reward.sourceMeta == meta)
			{
				rewardList.add(reward);
			}
		}
		
		return rewardList;
	}
	
	public static boolean Contains(int id, int meta)
	{
		Iterator<SiftReward> it = rewards.iterator();
		while(it.hasNext())
		{
			SiftReward reward = it.next();

			if (reward.sourceID == id && (reward.sourceMeta == meta || reward.ignoreMeta == true))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean Contains(int id)
	{
		Iterator<SiftReward> it = rewards.iterator();
		while(it.hasNext())
		{
			SiftReward reward = it.next();

			if (reward.sourceID == id&&reward.ignoreMeta)
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	public static void registerRewards()
	{
		//Gravel!
        register(BlockGravel.gravel.blockID, BlockGravel.gravel.blockID, 0, 2);
		register(BlockGravel.gravel.blockID, Item.chipFlint.itemID, 0, 3);
        register(BlockGravel.gravel.blockID, Item.copperNugget.itemID, 0, 9);
        register(BlockGravel.gravel.blockID, Item.silverNugget.itemID, 0, 26);
        register(BlockGravel.gravel.blockID, Item.flint.itemID, 0, 25);
        register(BlockGravel.gravel.blockID, Item.goldNugget.itemID, 0, 180);
        register(BlockGravel.gravel.blockID, Item.shardObsidian.itemID, 0, 380);
        register(BlockGravel.gravel.blockID, Item.shardEmerald.itemID, 0, 380);
        register(BlockGravel.gravel.blockID, Item.shardDiamond.itemID, 0, 600);
        register(BlockGravel.gravel.blockID, Item.mithrilNugget.itemID, 0, 1000);
        register(BlockGravel.gravel.blockID, Item.adamantiumNugget.itemID, 0, 1500);
        /*
		register(BlockGravel.gravel, 0, Items.coal, 0, 8);
		register(BlockGravel.gravel, 0, Items.dye, 4, 20); //Lapis Lazuli
		register(BlockGravel.gravel, 0, Items.diamond, 0, 128);
		register(BlockGravel.gravel, 0, Items.emerald, 0, 150);
        */
		
	}
}