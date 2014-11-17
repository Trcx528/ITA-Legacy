package com.trcx.ita.common.material;

import com.trcx.ita.common.ITA;
import com.trcx.ita.common.item.CompoundMaterial;
import com.trcx.ita.common.traits.BaseTrait;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by JPiquette on 11/11/2014.
 */
public class CompoundMaterialProperties extends BaseProperty {

    public CompoundMaterialProperties (ItemStack is){
        super(is);
    }

    public CompoundMaterialProperties () {};

    public CompoundMaterialProperties (BaseProperty bp){
        super(bp);
    }

}
