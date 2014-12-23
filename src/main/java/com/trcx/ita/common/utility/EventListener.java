package com.trcx.ita.common.utility;

import com.trcx.ita.common.properties.BaseMaterialProperty;
import com.trcx.ita.common.ITA;
import com.trcx.ita.common.properties.BaseProperty;
import com.trcx.ita.common.properties.PlayerProperties;
import com.trcx.ita.common.traits.BaseTrait;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

    private int TickCounter = 0;

    public EventListener() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
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
                    for (String trait: props.Traits.keySet()){
                        event.toolTip.add(ITA.getTrait(trait).getToolTip(new BaseProperty(event.itemStack)));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void serverTick(TickEvent.ServerTickEvent event){
        if (event.phase == TickEvent.Phase.END) {
            if (TickCounter>=1000){
                TickCounter = 0;
            } else {
                TickCounter++;
            }
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayer player = event.player;
            PlayerProperties PP = new PlayerProperties(player);
            for (BaseTrait trait : PP.Traits.keySet()) {
                trait.tick(PP.Traits.get(trait), PP, TickCounter);
            }
            float speedModifier = Math.min((float) (PP.Weight - 1) / 10, 0.49F);
            if (player.moveForward > 0F && !player.isInsideOfMaterial(Material.water)) {
                player.moveFlying(0F, 1F, 0.00F - speedModifier);
            }
            if (PP.StepAssist && PP.Fuel > 10D) {
                player.stepHeight = 1.000528f;
            } else if (player.stepHeight == 1.000528f) {
                player.stepHeight = 0.5f;
            }
            PP.regenFuel();
            PP.save();
        }
    }
}
