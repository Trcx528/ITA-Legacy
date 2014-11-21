package com.trcx.ita.common;

import com.trcx.ita.common.properties.*;
import com.trcx.ita.common.item.ItemBasicArmor;
import com.trcx.ita.common.recipes.AmorDyeRecipe;
import com.trcx.ita.common.recipes.ArmorRecipe;
import com.trcx.ita.common.item.*;
import com.trcx.ita.common.traits.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

import java.util.HashMap;
import java.util.Map;

public class ITA {
	
	public static Item BasicHelmet;
	public static Item BasicChestplate;
	public static Item BasicLeggings;
	public static Item BasicBoots;
    public static Item AlloyBinder;
    public static Item Alloy;
    public static Item Thruster;
    public static Item ThrusterCasing;
    public static Item ThrusterRSEngine;
    public static Item BasicCapacitor;
    public static Item StepAssistModule;
    public static Item SprintBooster;
    public static Item FlyBooster;
    public static Item SwimBooster;
    public static Item NightVision;
    public static Item RocketThruster;

    private static Map<String, BaseMaterialProperty> ArmorMaterialRegistry = new HashMap<String, BaseMaterialProperty>();
    private static Map<String, BaseTrait> ArmorTraitRegistry = new HashMap<String, BaseTrait>();

    public static BaseMaterialProperty getMaterialProperties(String ingotName){
        if (ArmorMaterialRegistry.containsKey(ingotName)){
            return ArmorMaterialRegistry.get(ingotName);
        } else {
            return ArmorMaterialRegistry.get("default");
        }
    }

    public static BaseTrait getTrait(String traitName){
        if (ArmorTraitRegistry.containsKey(traitName)){
            return ArmorTraitRegistry.get(traitName);
        } else {
            return new BaseTrait(null,null,null,null);
        }
    }
	
	private static void RegisterArmorMaterial(double protectionFactor, int enchantability, double weight, int durability, Double resistance, String color, String name){
        ArmorMaterialRegistry.put(name, new BaseMaterialProperty(name, protectionFactor, (short) enchantability, weight, (short) durability, color ,resistance));
    }

    private static void RegisterTrait(BaseTrait trait){
        ArmorTraitRegistry.put(trait.traitName, trait);
    }

    private static void RegisterTrait(String name, String toolTip, GenericPotionEffect[] potionEffects, GenericSpecialProtection[] specialProtections){
        ArmorTraitRegistry.put(name, new BaseTrait(name,toolTip,potionEffects,specialProtections));
    }

    public static void RegisterTraits(){
        GenericSpecialProtection[] magicProtection = new GenericSpecialProtection[1];
        magicProtection[0] = new GenericSpecialProtection(DamageSource.magic);

        GenericSpecialProtection[] voidProtection = new GenericSpecialProtection[1];
        voidProtection[0] = new GenericSpecialProtection(DamageSource.outOfWorld, true);

        GenericSpecialProtection[] fallProtection = new GenericSpecialProtection[1];
        fallProtection[0] = new GenericSpecialProtection(DamageSource.fall);

        GenericSpecialProtection[] witherProtection = new GenericSpecialProtection[1];
        witherProtection[0] = new GenericSpecialProtection(DamageSource.wither);

        GenericSpecialProtection[] starveProtection = new GenericSpecialProtection[1];
        starveProtection[0] = new GenericSpecialProtection(DamageSource.starve);

        GenericPotionEffect[] saturationEffect = new GenericPotionEffect[1];
        saturationEffect[0] = new GenericPotionEffect(23, true,0,100);

        GenericPotionEffect[] hungerEffect = new GenericPotionEffect[1];
        hungerEffect[0] = new GenericPotionEffect(Potion.hunger.id, true,0,100);


        RegisterTrait(TraitNames.DAMAGE_FALL,"Fall Protection", null, fallProtection);
        RegisterTrait(TraitNames.DAMAGE_MAGIC, "Magical Protection", null,magicProtection);
        RegisterTrait(TraitNames.DAMAGE_VOID, "Void Protection", null,voidProtection);
        RegisterTrait(TraitNames.DAMAGE_WITHER, "Wither Protection", null,witherProtection);
        RegisterTrait(TraitNames.DAMAGE_STARVE, "Starvation Negation", null,starveProtection);
        RegisterTrait(TraitNames.POTION_EFFECT_SATURATION, "Saturating!", saturationEffect, null);
        RegisterTrait(TraitNames.POTION_EFFECT_HUNGER, "Always Hungry", hungerEffect, null);
        RegisterTrait(new BasicFlightTrait(TraitNames.ABILITY_BASIC_FLIGHT));
        RegisterTrait(new BaseTrait(TraitNames.ABILITY_BASIC_STEPASSIST));
        RegisterTrait(new BoostTrait(TraitNames.ABILITY_SPRINT_BOOST, BoostTrait.GROUND_ONLY));
        RegisterTrait(new BoostTrait(TraitNames.ABILITY_FLIGHT_BOOST, BoostTrait.AIR_ONLY));
        RegisterTrait(new BoostTrait(TraitNames.ABILITY_SWIM_BOOST, BoostTrait.WATER_ONLY));
        RegisterTrait(new NightVision(TraitNames.POTION_EFFECT_NIGHTVISION));
        RegisterTrait(new RocketTrait(TraitNames.ABILITY_BASIC_ROCKET));
    }

	public static void RegisterMaterials(){
        //armor factor values
        //4.166666 is 100% protection
        //3.33333 is 80% protection (diamond level)
        //2.5 iron
        //2 chainmail
        //1.83333 gold
        //1.166664 leather

        //RegisterArmorMaterial(protection, Enchantability, Weight, MaxDurability, Resistance, color, name);
        RegisterArmorMaterial(0.50,  1, 0  , 1 , 1.0, "#FFFFFF", "default");

        RegisterArmorMaterial(1.83, 25, 0.2, 14, 0.5, "#EAEE57", "ingotGold"); //vanilla values (minus Weight)
        RegisterArmorMaterial(1.50, 30, 0  , 10, 0.7, "#E0D495", "ingotNickel");
        RegisterArmorMaterial(2.00, 23, 0.1, 16, 0.4, "#A5BCC3", "ingotSilver");
        RegisterArmorMaterial(2.50,  9, 0  , 30, 1.0, "#D8D8D8", "ingotIron"); //vanilla value
        RegisterArmorMaterial(3.60,  3, 1  , 5 , 1.5, "#ABABCF", "ingotLead");
        RegisterArmorMaterial(2.70, 10, 0  , 28, 0.6, "#FFB262", "ingotCopper");
        RegisterArmorMaterial(2.90, 10, 0  , 23, 1.0, "#CFD7D7", "ingotTin");
        RegisterArmorMaterial(3.00, 11, 0  , 30, 1.0, "#FC5D2D", "ingotBronze");
        RegisterArmorMaterial(3.80, 10, 0  , 40, 1.1, "#898989", "ingotSteel");
        RegisterArmorMaterial(3.33, 10, 0  , 66, 1.0, "#8CF4E2", "gemDiamond"); // vanilla values
        RegisterArmorMaterial(3.50, 18,-0.3, 9 , 2.0, "#FFFF0F", "ingotRefinedGlowstone"); // actually speeds up the player
        RegisterArmorMaterial(4.10, 40, 0  , 50, 2.0, "#1E0059", "ingotRefinedObsidian");
        RegisterArmorMaterial(4.10, 15, 2  , 70, 1.3, "#A97DE0", "ingotManyullyn"); //TODO undo weight nerf (testing)
        RegisterArmorMaterial(4.00, 20,-0.8, 50, 1.8, "#F48A00", "ingotArdite");
        RegisterArmorMaterial(4.00, 25, 0.1, 60, 1.2, "#2376DD", "ingotCobalt");
        RegisterArmorMaterial(0.30,  3, 0  , 5 , 5.0, "#CD8B7D", "ingotMeatRaw");
        RegisterArmorMaterial(0.50,  5, 0  , 8 , 5.0, "#6E3F23", "ingotMeat");
        RegisterArmorMaterial(2.70, 30, 0  , 23, 1.4, "#F0D467", "ingotAluminiumBrass");
        RegisterArmorMaterial(3.00, 10,-0.1, 35, 1.3, "#E39BD3", "ingotAlumite");
        RegisterArmorMaterial(2.40, 12, 0  , 25, 0.4, "#C5C5C5", "ingotAluminum");
        RegisterArmorMaterial(3.50, 35, 0  , 45, 0.2, "#F0F589", "ingotElectrumFlux"); // balancing against redstone arsenal
        RegisterArmorMaterial(3.00, 12, 0  , 40, 0.1, "#FF9F1A", "ingotSignalum");
        RegisterArmorMaterial(2.80, 14, 0  , 35, 0.8, "#DDE2E0", "ingotInvar");
        RegisterArmorMaterial(4.50, 20,-0.1, 50, 1.7, "#107272", "ingotEnderium"); // TODO add teleport?
        RegisterArmorMaterial(0.90, 20,-1  , 20, 3.5, "#D5BF6D", "ingotLumium");
        RegisterArmorMaterial(2.80, 35, 0.3, 15, 0.2, "#FDF488", "ingotElectrum");
        RegisterArmorMaterial(0.50,  5, 0  , 80, 3.0, "#8F60D4", "ingotObsidian");
        RegisterArmorMaterial(2.00, 50,-0.1, 10, 2.0, "#88D8FF", "ingotMithril"); //mana infused ore
        RegisterArmorMaterial(2.50, 23, 0  , 10, 0.2, "#C7EEFF", "ingotPlatinum");
        RegisterArmorMaterial(2.60, 25, 0  , 30, 1.5, "#7A6AAE", "ingotThaumium");
        RegisterArmorMaterial(1.30,  9,0.05, 40, 1.3, "#F0A8A4", "ingotPigIron");
        RegisterArmorMaterial(4.00, 25, 0  , 50, 4.0, "#200D36", "ingotVoid");
        RegisterArmorMaterial(1.00, 25, 0  , 500,0.01,"#737372", "ingotUnstable"); // the irony, unstable ingots form the most stable(durable) armor
        RegisterArmorMaterial(1.00,  5, 0.3, 21, 3.0, "#D9DB5C", "ingotYellorium");
        RegisterArmorMaterial(2.00,  7, 0.2, 18, 3.0, "#4642D6", "ingotBlutonium");
        RegisterArmorMaterial(0.70,  4, 0.4, 19, 3.0, "#5CAFDB", "ingotCyanite");
        RegisterArmorMaterial(2.30, 10, 0  , 1 , 0.9, "#8095A9", "ingotOsmium");
        RegisterArmorMaterial(3.20, 28,-0.3, 60, 2.5, "#FACAFC", "ingotElvenElementium");
        RegisterArmorMaterial(3.50, 25, 0  , 70, 3.0, "#90E764", "ingotTerrasteel"); // TODO Health Increase
        RegisterArmorMaterial(2.70, 18, 0  , 45, 1.5, "#CAEAFD", "ingotManasteel");


        ArmorMaterialRegistry.get("ingotVoid").addTrait(TraitNames.DAMAGE_MAGIC,50);
        ArmorMaterialRegistry.get("ingotVoid").addTrait(TraitNames.DAMAGE_VOID,1000);

        ArmorMaterialRegistry.get("ingotLumium").addTrait(TraitNames.DAMAGE_FALL, 50);
        ArmorMaterialRegistry.get("ingotMithril").addTrait(TraitNames.DAMAGE_MAGIC, 30);
        ArmorMaterialRegistry.get("ingotThaumium").addTrait(TraitNames.DAMAGE_MAGIC, 25);

        ArmorMaterialRegistry.get("ingotYellorium").addTrait(TraitNames.DAMAGE_WITHER, 20);
        ArmorMaterialRegistry.get("ingotBlutonium").addTrait(TraitNames.DAMAGE_WITHER, 10);
        ArmorMaterialRegistry.get("ingotCyanite").addTrait(TraitNames.DAMAGE_WITHER, 40);


        //trait Weight for potion effects is a dice roll every 20 ticks (Weight <= dice.roll(2000))
        //aka Weight of 1 with 1 ingot will trigger ever 33.33 minutes
        ArmorMaterialRegistry.get("ingotMeat").addTrait(TraitNames.POTION_EFFECT_SATURATION, 2);
        ArmorMaterialRegistry.get("ingotPigIron").addTrait(TraitNames.POTION_EFFECT_SATURATION, 6);
        ArmorMaterialRegistry.get("ingotMeatRaw").addTrait(TraitNames.POTION_EFFECT_HUNGER,5);
        ArmorMaterialRegistry.get("ingotMeatRaw").addTrait(TraitNames.POTION_EFFECT_SATURATION,15);
        ArmorMaterialRegistry.get("ingotMeatRaw").addTrait(TraitNames.DAMAGE_STARVE, 5);


    }
	
	public static void RegisterItems(){
		GameRegistry.registerItem(BasicHelmet, "BasicHelmet");
		GameRegistry.registerItem(BasicChestplate, "BasicChestplate");
		GameRegistry.registerItem(BasicLeggings, "BasicLeggings");
		GameRegistry.registerItem(BasicBoots, "BasicBoots");
        GameRegistry.registerItem(AlloyBinder, "AlloyBinder");
        GameRegistry.registerItem(Alloy, "Alloy");
        GameRegistry.registerItem(Thruster, "Thruster");
        GameRegistry.registerItem(ThrusterCasing, "ThrusterCasing");
        GameRegistry.registerItem(ThrusterRSEngine, "ThrusterRSEngine");
        GameRegistry.registerItem(BasicCapacitor, "BasicCapacitor");
        GameRegistry.registerItem(StepAssistModule, "StepAssistModule");
        GameRegistry.registerItem(SprintBooster, "SprintBooster");
        GameRegistry.registerItem(FlyBooster, "FlyBooster");
        GameRegistry.registerItem(SwimBooster, "SwimBooster");
        GameRegistry.registerItem(NightVision, "NightVision");
        GameRegistry.registerItem(RocketThruster, "RocketThruster");
	}
	
	public static void DefineItems(){
        //doesn't matter what properties we use, the return functions are overridden
		BasicHelmet = new ItemBasicArmor(ArmorMaterial.IRON, 0).setUnlocalizedName("BasicHelmet").setTextureName("ITA:BasicHelmet");
		BasicChestplate = new ItemBasicArmor(ArmorMaterial.IRON, 1).setUnlocalizedName("BasicChestplate").setTextureName("ITA:BasicChestplate");
		BasicLeggings = new ItemBasicArmor(ArmorMaterial.IRON, 2).setUnlocalizedName("BasicLeggings").setTextureName("ITA:BasicLeggings");
		BasicBoots= new ItemBasicArmor(ArmorMaterial.IRON, 3).setUnlocalizedName("BasicBoots").setTextureName("ITA:BasicBoots");
        AlloyBinder = new ItemAlloyBinder().setUnlocalizedName("AlloyBinder").setTextureName("ITA:AlloyBinder");
        Alloy = new ItemAlloy().setUnlocalizedName("Alloy").setTextureName("ITA:Alloy");
        Thruster = new ItemThruster().setUnlocalizedName("Thruster").setTextureName("ITA:Thruster");
        ThrusterCasing = new ItemThrusterCasing().setUnlocalizedName("ThrusterCasing").setTextureName("ITA:ThrusterCasing");
        ThrusterRSEngine = new ItemThrusterRSEngine().setUnlocalizedName("ThrusterRSEngine").setTextureName("ITA:ThrusterRSEngine");
        BasicCapacitor = new ItemCapacitor().setUnlocalizedName("BasicCapacitor").setTextureName("ITA:Capacitor");
        StepAssistModule = new ItemStepAssist().setUnlocalizedName("StepAssist").setTextureName("ITA:StepAssist");
        SprintBooster = new ItemSprintBooster().setUnlocalizedName("SprintBooster").setTextureName("ITA:SprintBooster");
        FlyBooster = new ItemFlightBooster().setUnlocalizedName("FlightBooster").setTextureName("ITA:FlyBooster");
        SwimBooster = new ItemSwimBooster().setUnlocalizedName("Swim Booster").setTextureName("ITA:SwimBooster");
        NightVision = new ItemNightVision().setUnlocalizedName("Night Vision").setTextureName("ITA:NightVision");
        RocketThruster = new ItemRocketThruster().setUnlocalizedName("Rocket Thruster").setTextureName("ITA:RocketThruster");
	}

    public static void RegisterRecipes() {
        //Register These here as new ItemBasicArmor() is called several times
        GameRegistry.addRecipe(new ArmorRecipe());
        GameRegistry.addRecipe(new AmorDyeRecipe());


        ItemStack iron = new ItemStack(Items.iron_ingot);
        ItemStack rsBlock = new ItemStack(Item.getItemFromBlock(Blocks.redstone_block));
        ItemStack piston = new ItemStack(Item.getItemFromBlock(Blocks.piston));
        ItemStack rs = new ItemStack(Items.redstone);
        ItemStack slime = new ItemStack(Items.slime_ball);
        ItemStack Glowstone = new ItemStack(Items.glowstone_dust);
        ItemStack Diamond = new ItemStack(Items.diamond);
        ItemStack Lapis = new ItemStack(Items.dye);
        Lapis.setItemDamage(4);
        ItemStack Lime = new ItemStack(Items.dye);
        Lime.setItemDamage(10);
        ItemStack Glass = new ItemStack(Item.getItemFromBlock(Blocks.glass));

        ItemStack sa = new ItemStack(ITA.StepAssistModule);
        CompoundMaterialProperties cmp = new CompoundMaterialProperties();
        cmp.zeroAllValues();
        cmp.Name = "Step Assist Module";
        cmp.ColorHex = "#D8D8D8"; //make it look like iron
        sa.stackSize = 1;
        sa.stackTagCompound = cmp.getTagCompound();
        GameRegistry.addShapedRecipe(sa, "s", "r", "p", 's', slime, 'r', rs, 'p', piston);


        sa = new ItemStack(ITA.AlloyBinder);
        sa.stackSize = 5;
        GameRegistry.addShapedRecipe(sa, "ili", "gdg", "ili", 'i', iron, 'l', Lapis, 'g', Glowstone, 'd', Diamond);
        GameRegistry.addShapedRecipe(sa,"igi", "ldl", "igi", 'i', iron, 'l', Lapis, 'g',Glowstone, 'd', Diamond);

        sa = new ItemStack(ITA.SprintBooster);
        cmp.zeroAllValues();
        cmp.Name = "Sprint Boost Module";
        cmp.ColorHex = "#D8D8D8";
        cmp.Resistance = 2;
        sa.stackSize = 1;
        sa.stackTagCompound = cmp.getTagCompound();
        GameRegistry.addShapedRecipe(sa, "iii", "rrr", "iii", 'i', iron, 'r', rsBlock);

        sa = new ItemStack(ITA.FlyBooster);
        cmp.zeroAllValues();
        cmp.Name = "Flight Boost Module";
        cmp.ColorHex = "#D8D8D8";
        cmp.Resistance = 2;
        sa.stackSize = 1;
        sa.stackTagCompound = cmp.getTagCompound();
        GameRegistry.addShapedRecipe(sa, "ii ", "rri", "ii ", 'i', iron, 'r', rsBlock);

        sa = new ItemStack(ITA.FlyBooster);
        cmp.zeroAllValues();
        cmp.Name = "Swim Boost Module";
        cmp.ColorHex = "#D8D8D8";
        cmp.Resistance = 2;
        sa.stackSize = 1;
        sa.stackTagCompound = cmp.getTagCompound();
        GameRegistry.addShapedRecipe(sa, "ii ", "lli", "ii ", 'i', iron, 'l', Lapis);

        sa = new ItemStack(ITA.NightVision);
        cmp.zeroAllValues();
        cmp.Name = "Night Vision Module";
        cmp.ColorHex = "#D8D8D8";
        cmp.Resistance = 1;
        sa.stackSize = 1;
        sa.stackTagCompound = cmp.getTagCompound();
        GameRegistry.addShapedRecipe(sa, "iii","gig","l l", 'i', iron, 'g', Glass, 'l', Lime);

        sa = new ItemStack(ITA.RocketThruster);
        cmp.zeroAllValues();
        cmp.Name = "Rocket Thruster";
        cmp.ColorHex = "#8D8D8D";
        cmp.Resistance = 1;
        sa.stackSize = 1;
        sa.stackTagCompound = cmp.getTagCompound();
        GameRegistry.addShapedRecipe(sa, " i ", "iri", "iri", 'i', iron, 'r', rsBlock);
    }
}
