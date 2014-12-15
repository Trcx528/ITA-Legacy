package com.trcx.ita.common;

import com.trcx.ita.CommonProxy;
import com.trcx.ita.common.network.PacketKey;
import com.trcx.ita.common.utility.EventListener;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler; // used in 1.6.2
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;


@Mod(modid="ITA", name="Infinitely Tweakable Armor", version="0.2.2")

//TODO Armor Repair Block
//TODO Ingot Properties viewer
//TODO In Game Documentation

public class Main {
    public static SimpleNetworkWrapper Network;

    @Instance(value = "ITA")
        public static Main instance;
        
        @SidedProxy(clientSide="com.trcx.ita.ClientProxy", serverSide="com.trcx.ita.CommonProxy")
        public static CommonProxy proxy;
       
        @EventHandler
        public void preInit(FMLPreInitializationEvent event) {
            new EventListener();
            ITA.DefineItems();
            ITA.RegisterItems();
            ITA.RegisterTraits();
            ITA.RegisterMaterials();
            ITA.RegisterRecipes();
            Network = NetworkRegistry.INSTANCE.newSimpleChannel("ITAKeySync");
            Network.registerMessage(PacketKey.class, PacketKey.KeyMessage.class, 0, Side.SERVER);
        }
       
        @EventHandler
        public void load(FMLInitializationEvent event) {
            proxy.registerRenderers();
            proxy.registerKeybindings();
        }
}