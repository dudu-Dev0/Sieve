package com.dudu.sieve;

import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.ModResourceManager;
import net.xiaoyu233.fml.reload.event.MITEEvents;
import net.xiaoyu233.fml.reload.event.TileEntityRegisterEvent;

public class SieveInitializer implements ModInitializer {
    public static final String MOD_ID = "sieve";

    @Override
    public void onInitialize() {   //相当于main函数，万物起源
        ModResourceManager.addResourcePackDomain(MOD_ID);
        MITEEvents.MITE_EVENT_BUS.register(new EventListener());//注册一个事件监听类对象
        SieveRegistry.registerRewards();
    }
}