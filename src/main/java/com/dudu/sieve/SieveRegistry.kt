package com.dudu.sieve

import com.dudu.sieve.helpers.SiftReward
import net.minecraft.Block
import net.minecraft.BlockGravel
import net.minecraft.Item
import net.xiaoyu233.fml.util.Log

object SieveRegistry {
    var rewards: ArrayList<SiftReward> = ArrayList()

    var dropCopper: Boolean = false
    var dropTin: Boolean = false
    var dropSilver: Boolean = false
    var dropLead: Boolean = false
    var dropNickel: Boolean = false
    var dropPlatinum: Boolean = false
    var dropAluminum: Boolean = false

    var dropRubberSeeds: Boolean = false

    fun register(sourceID: Int, sourceMeta: Int, outputID: Int, outputMeta: Int, rarity: Int) {
        val entry = SiftReward(sourceID, sourceMeta, outputID, outputMeta, rarity)

        if (Block.blocksList[sourceID] != null) {
            rewards.add(entry)
        } else {
            Log.warn("Ex Nihilo: An item was added to the SieveRegistry which was not a block")
        }
    }

    fun register(sourceID: Int, outputID: Int, outputMeta: Int, rarity: Int) {
        val entry = SiftReward(sourceID, outputID, outputMeta, rarity)

        if (Block.blocksList[sourceID] != null) {
            rewards.add(entry)
        } else {
            Log.warn("Ex Nihilo: An item was added to the SieveRegistry which was not a block")
        }
    }

    fun getRewards(id: Int, meta: Int): ArrayList<SiftReward?> {
        val rewardList = ArrayList<SiftReward?>()

        val it: Iterator<SiftReward> = rewards.iterator()
        while (it.hasNext()) {
            val reward = it.next()

            if (reward.sourceID == id && reward.sourceMeta == meta) {
                rewardList.add(reward)
            }
        }

        return rewardList
    }

    fun Contains(id: Int, meta: Int): Boolean {
        val it: Iterator<SiftReward> = rewards.iterator()
        while (it.hasNext()) {
            val reward = it.next()

            if (reward.sourceID == id && (reward.sourceMeta == meta || reward.ignoreMeta == true)) {
                return true
            }
        }

        return false
    }

    fun Contains(id: Int): Boolean {
        val it: Iterator<SiftReward> = rewards.iterator()
        while (it.hasNext()) {
            val reward = it.next()

            if (reward.sourceID == id && reward.ignoreMeta) {
                return true
            }
        }

        return false
    }


    fun registerRewards() {
        //Gravel!
        register(BlockGravel.gravel.blockID, BlockGravel.gravel.blockID, 0, 2)
        register(BlockGravel.gravel.blockID, Item.chipFlint.itemID, 0, 3)
        register(BlockGravel.gravel.blockID, Item.copperNugget.itemID, 0, 9)
        register(BlockGravel.gravel.blockID, Item.silverNugget.itemID, 0, 26)
        register(BlockGravel.gravel.blockID, Item.flint.itemID, 0, 25)
        register(BlockGravel.gravel.blockID, Item.goldNugget.itemID, 0, 180)
        register(BlockGravel.gravel.blockID, Item.shardObsidian.itemID, 0, 380)
        register(BlockGravel.gravel.blockID, Item.shardEmerald.itemID, 0, 380)
        register(BlockGravel.gravel.blockID, Item.shardDiamond.itemID, 0, 600)
        register(BlockGravel.gravel.blockID, Item.mithrilNugget.itemID, 0, 1000)
        register(BlockGravel.gravel.blockID, Item.adamantiumNugget.itemID, 0, 1500)

        /*
		register(BlockGravel.gravel, 0, Items.coal, 0, 8);
		register(BlockGravel.gravel, 0, Items.dye, 4, 20); //Lapis Lazuli
		register(BlockGravel.gravel, 0, Items.diamond, 0, 128);
		register(BlockGravel.gravel, 0, Items.emerald, 0, 150);
        */
    }
}