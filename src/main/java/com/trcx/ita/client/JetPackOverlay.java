package com.trcx.ita.client;

import com.trcx.ita.common.ITA;
import com.trcx.ita.common.properties.ITAArmorProperties;
import com.trcx.ita.common.properties.PlayerProperties;
import com.trcx.ita.common.traits.BasicFlightTrait;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by JPiquette on 11/14/2014.
 */
public class JetPackOverlay extends Gui {

    public JetPackOverlay(){
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void renderBar(RenderGameOverlayEvent event) {
        if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        PlayerProperties PlayerProps = new PlayerProperties(Minecraft.getMinecraft().thePlayer);
        if (PlayerProps.MaxFuel > 0) {
            DecimalFormat df = new DecimalFormat("#");
            this.drawString(Minecraft.getMinecraft().fontRenderer, "Fuel: " + df.format(PlayerProps.Fuel) + "/" + df.format(PlayerProps.MaxFuel), 2 /*x*/, 2 /*y*/, 0xFFFFFF);
        }
    }
}
