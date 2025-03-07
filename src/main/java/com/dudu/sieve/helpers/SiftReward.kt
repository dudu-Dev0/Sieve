package com.dudu.sieve.helpers

class SiftReward {
    @JvmField
	var sourceID: Int
    @JvmField
	var sourceMeta: Int
    @JvmField
	var ignoreMeta: Boolean
    @JvmField
	var id: Int
    @JvmField
	var meta: Int
    @JvmField
	var rarity: Int

    constructor(sourceID: Int, sourceMeta: Int, id: Int, meta: Int, rarity: Int) {
        this.sourceID = sourceID
        this.sourceMeta = sourceMeta
        this.ignoreMeta = false
        this.id = id
        this.meta = meta
        this.rarity = calculateRarity(rarity)
    }

    constructor(sourceID: Int, id: Int, meta: Int, rarity: Int) {
        this.sourceID = sourceID
        this.sourceMeta = 0
        this.ignoreMeta = true
        this.id = id
        this.meta = meta
        this.rarity = calculateRarity(rarity)
    }

    companion object {
        private fun calculateRarity(base: Int): Int {
            val multiplier =  /* ModData.SIEVE_PAIN_MULTIPLIER +*/1

            //int rarity = (base * multiplier) + (int)((float)multiplier / 2.0f);
            return base
        }
    }
}
