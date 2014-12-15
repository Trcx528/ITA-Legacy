package com.trcx.ita.common.item;
import com.trcx.ita.client.BasicArmorRenderer;
import com.trcx.ita.common.ITA;
import com.trcx.ita.common.properties.ITAArmorProperties;
import com.trcx.ita.common.traits.BaseTrait;
import com.trcx.ita.common.utility.ColorHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import org.lwjgl.input.Keyboard;

import java.util.List;


public class ItemBasicArmor extends ItemArmor implements ISpecialArmor {

    private BasicArmorRenderer model = null;

    public ItemBasicArmor(ArmorMaterial material, int armorType) {
        super(material, 0, armorType);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
        ITAArmorProperties props = new ITAArmorProperties(itemStack);
        if (!props.Invisible) {
            if (this.model == null) {
                this.model = new BasicArmorRenderer();
            }
            int type = ((ItemArmor) itemStack.getItem()).armorType;
            this.model.bipedHead.showModel = (type == 0);
            this.model.bipedHeadwear.showModel = (type == 0);
            this.model.bipedBody.showModel = ((type == 1) || (type == 2));
            this.model.bipedLeftArm.showModel = (type == 1);
            this.model.bipedRightArm.showModel = (type == 1);
            this.model.bipedLeftLeg.showModel = (type == 2 || type == 3);
            this.model.bipedRightLeg.showModel = (type == 2 || type == 3);
            this.model.isSneak = entityLiving.isSneaking();

            this.model.isRiding = entityLiving.isRiding();
            this.model.isChild = entityLiving.isChild();

            this.model.aimedBow = false;
            this.model.heldItemRight = (entityLiving.getHeldItem() != null ? 1 : 0);
            this.model.setColor( ColorHelper.intFromHex(new ITAArmorProperties(itemStack).ColorHex));
            return this.model;
        } else {
            return null;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister){
        super.registerIcons(iconRegister);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getArmorTexture(ItemStack stack, Entity e, int slot, String type) {
        ITAArmorProperties props = new ITAArmorProperties(stack);
        if (props.Invisible)
            return "ITA:textures/models/armor/invisble.png";
        if (this.armorType == 2) {
            return "ITA:textures/models/armor/basicarmor_2.png";
        } else {
            return "ITA:textures/models/armor/basicarmor_1.png";
        }
    }


    @Override
    public void onCreated(ItemStack is, World world, EntityPlayer player){
        //super.onCreated(is, world, player);
        //this.setMaxDamage(is.stackTagCompound.getShort("maxDurability"));
    }

    @Override
    public int getItemEnchantability(ItemStack is){
        return new ITAArmorProperties(is).Enchantability;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderPasses(int meta){
        return 1;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        ArmorProperties ap= new ArmorProperties(0, 0, armor.getMaxDamage() +1  - armor.getItemDamage());
        ITAArmorProperties bap = new ITAArmorProperties(armor);
        if (!source.isUnblockable()) {
            ap.AbsorbRatio = bap.ArmorFactor;
            ap.AbsorbRatio /= 100;
        }
        for (String traitName: bap.Traits.keySet()){
            BaseTrait trait = ITA.getTrait(traitName);
            ap.AbsorbRatio += trait.getDamageRatio(player, source, damage, bap.Traits.get(traitName));
        }
        //System.out.println("Props: Max: " + ap.AbsorbMax + " Ratio: " + ap.AbsorbRatio  + "(" + damage +")" + " Unblockable: " + source.isUnblockable());
        return ap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasColor(ItemStack is){ return true; }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack is, int pass){
        return getColor(is);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColor(ItemStack is){
        return ColorHelper.intFromHex(new ITAArmorProperties(is).ColorHex);
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2){
        return false;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return new ITAArmorProperties(armor).ShieldDisplayValue;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List dataList, boolean bool) {
        if (player.inventoryContainer.getInventory().contains(itemStack)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                new ITAArmorProperties(itemStack).getToolTip(dataList);
            } else {
                dataList.add(EnumChatFormatting.WHITE + "<Shift For Details>");
            }
        } else {
            new ITAArmorProperties(itemStack).getToolTip(dataList);
        }
    }

    @Override
    public int getMaxDamage(ItemStack is){
        return new ITAArmorProperties(is).MaxDurability;
    }

    @Override
    public  int getMaxDamage(){ return 1;}

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack,DamageSource source, int damage, int slot) {
        stack.damageItem(damage, entity);
    }
}
