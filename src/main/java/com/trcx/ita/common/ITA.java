package com.trcx.ita.common;

import com.trcx.ita.common.properties.*;
import com.trcx.ita.common.item.ItemBasicArmor;
import com.trcx.ita.common.recipes.AlloyRecipe;
import com.trcx.ita.common.recipes.AmorDyeRecipe;
import com.trcx.ita.common.recipes.ArmorRecipe;
import com.trcx.ita.common.item.*;
import com.trcx.ita.common.traits.BaseTrait;
import com.trcx.ita.common.traits.BasicFlightTrait;
import com.trcx.ita.common.traits.GenericPotionEffect;
import com.trcx.ita.common.traits.GenericSpecialProtection;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
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


        RegisterTrait("damageFall","Fall Protection", null, fallProtection);
        RegisterTrait("damageMagic", "Magical Protection", null,magicProtection);
        RegisterTrait("damageVoid", "Void Protection", null,voidProtection);
        RegisterTrait("damageWither", "Wither Protection", null,witherProtection);
        RegisterTrait("damageStarve", "Starvation Negation", null,starveProtection);
        RegisterTrait("effectSaturation", "Saturating!", saturationEffect, null);
        RegisterTrait("effectHunger", "Always Hungry", hungerEffect, null);
        RegisterTrait(new BasicFlightTrait("basicFlight"));
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


        ArmorMaterialRegistry.get("ingotVoid").addTrait("damageMagic",50);
        ArmorMaterialRegistry.get("ingotVoid").addTrait("damageVoid",1000);

        ArmorMaterialRegistry.get("ingotLumium").addTrait("damageFall", 50);
        ArmorMaterialRegistry.get("ingotMithril").addTrait("damageMagic", 30);
        ArmorMaterialRegistry.get("ingotThaumium").addTrait("damageMagic", 25);

        ArmorMaterialRegistry.get("ingotYellorium").addTrait("damageWither", 20);
        ArmorMaterialRegistry.get("ingotBlutonium").addTrait("damageWither", 10);
        ArmorMaterialRegistry.get("ingotCyanite").addTrait("damageWither", 40);


        //trait Weight for potion effects is a dice roll every 20 ticks (Weight <= dice.roll(2000))
        //aka Weight of 1 with 1 ingot will trigger ever 33.33 minutes
        ArmorMaterialRegistry.get("ingotMeat").addTrait("effectSaturation", 2);
        ArmorMaterialRegistry.get("ingotPigIron").addTrait("effectSaturation", 6);
        ArmorMaterialRegistry.get("ingotMeatRaw").addTrait("effectHunger",5);
        ArmorMaterialRegistry.get("ingotMeatRaw").addTrait("effectSaturation",15);
        ArmorMaterialRegistry.get("ingotMeatRaw").addTrait("damageStarve", 5);


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
	}

    public static void RegisterRecipes() {
        GameRegistry.addRecipe(new ArmorRecipe());
        GameRegistry.addRecipe(new AlloyRecipe());
        GameRegistry.addRecipe(new AmorDyeRecipe());
    }
}
