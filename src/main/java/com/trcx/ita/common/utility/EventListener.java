package com.trcx.ita.common.utility;

import com.trcx.ita.common.properties.BaseMaterialProperty;
import com.trcx.ita.common.ITA;
import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.PlayerProperties;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;

/**
 * Created by JPiquette on 11/10/2014.
 */
public class EventListener {

    public EventListener() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void materialTooltip(ItemTooltipEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            for (int id : OreDictionary.getOreIDs(event.itemStack)) {
                String oreDictName = OreDictionary.getOreName(id);
                if (oreDictName.startsWith("ingot")) {
                    BaseMaterialProperty props = ITA.getMaterialProperties(oreDictName);
                    DecimalFormat df = new DecimalFormat("#.##");
                    event.toolTip.add(EnumChatFormatting.GREEN + "Armor Factor: " + df.format(props.ArmorFactor));
                    event.toolTip.add(EnumChatFormatting.AQUA + "Enchantability: " + props.Enchantability);
                    event.toolTip.add(EnumChatFormatting.DARK_PURPLE +"Durability: " + props.MaxDurability);
                    event.toolTip.add(EnumChatFormatting.GRAY+"Weight: " + df.format(1 + props.Weight));
                    event.toolTip.add(EnumChatFormatting.RED+"Resistance: " + df.format(props.Resistance));
                    for (String trait: props.Traits.keySet()){
                        event.toolTip.add(ITA.getTrait(trait).getToolTip(new BaseProperty(event.itemStack)));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        float speedModifier = Math.min((float) (new PlayerProperties(player).Weight - 1) / 10, 0.49F);
        //if ((player.onGround || player.capabilities.isFlying) && player.moveForward > 0F && !player.isInsideOfMaterial(Material.water)) {
        if (player.moveForward > 0F && !player.isInsideOfMaterial(Material.water)) {
            player.moveFlying(0F, 1F, 0.00F - speedModifier);
        }

    }
}
