package com.dudu.sieve

import net.fabricmc.api.ModInitializer
import net.xiaoyu233.fml.ModResourceManager
import net.xiaoyu233.fml.reload.event.MITEEvents

class SieveInitializer : ModInitializer {
    override fun onInitialize() {   //相当于main函数，万物起源
        ModResourceManager.addResourcePackDomain(MOD_ID)
        MITEEvents.MITE_EVENT_BUS.register(EventListener()) //注册一个事件监听类对象
        SieveRegistry.registerRewards()
    }

    companion object {
        const val MOD_ID: String = "sieve"
    }
}