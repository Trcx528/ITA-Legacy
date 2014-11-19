package com.trcx.ita.common.item;

import com.trcx.ita.common.properties.CompoundMaterialProperties;
import com.trcx.ita.common.properties.ITAArmorProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.List;

/**
 * Created by JPiquette on 11/11/2014.
 */
public class CompoundMaterial extends Item {

    public void addProperties(ITAArmorProperties props){
    }

    public Boolean isValidForType(int ArmorType){
        return true;
    }

    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List data, boolean bool) {
        if (player.inventoryContainer.getInventory().contains(is)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                new CompoundMaterialProperties(is).getToolTip(data);
            } else {
                data.add(EnumChatFormatting.WHITE + "<Shift For Details>");
            }
        } else {
            new CompoundMaterialProperties(is).getToolTip(data);
        }
    }

    @Override
    public boolean isDamageable() {
        return false;
    }
}
