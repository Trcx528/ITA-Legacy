package com.trcx.ita.client;

import com.trcx.ita.common.ITA;
import com.trcx.ita.common.material.ITAArmorProperties;
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
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        ArrayList<ITAArmorProperties> JetPacks = new ArrayList<ITAArmorProperties>();
        for (int i =0; i < 4 ; i++) {
            ItemStack is = player.getCurrentArmor(i);
            if (is != null && (is.getItem() == ITA.BasicBoots || is.getItem() == ITA.BasicLeggings ||
                    is.getItem() == ITA.BasicChestplate || is.getItem() == ITA.BasicHelmet)) {
                ITAArmorProperties props = new ITAArmorProperties(is);
                if (props.canFly())
                    JetPacks.add(props);
            }
        }
        int x =2 ;
        int y =2;
        DecimalFormat df = new DecimalFormat("#.#");
        for (ITAArmorProperties jetpack: JetPacks){
            this.drawString(Minecraft.getMinecraft().fontRenderer, "Fuel: " + df.format(jetpack.RemainingFlight) + "/"
                    + BasicFlightTrait.MAX_FLIGHTTIME, x, y, 0xFFFFFF);
            y += 12;
        }
    }
}
