package com.trcx.ita.client;

import com.trcx.ita.common.ITA;
import com.trcx.ita.common.Main;
import com.trcx.ita.common.properties.ITAArmorProperties;
import com.trcx.ita.common.network.PacketKey;
import com.trcx.ita.common.traits.TraitNames;
import com.trcx.ita.common.utility.KeySync;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

/**
 * Created by JPiquette on 11/13/2014.
 */
public class ITAKeybindings {

    private static final String KB_CATEGORY = "Infinitely Tweakable Armor";


    //since this is on the client we don't need to track the username
    private static Boolean FLY = false;
    private static Boolean HOVER = false;
    private static Boolean DESCEND = false;
    private static Boolean SPRINTACC = false;
    private static Boolean NIGHTVISION = false;
    private static Boolean ROCKETING = false;

    public static KeyBinding KB_HOVER = new KeyBinding("Hover", Keyboard.KEY_M, KB_CATEGORY);
    public static KeyBinding KB_NIGHTVISION = new KeyBinding("Night Vision", Keyboard.KEY_N, KB_CATEGORY);
    public static KeyBinding KB_FLY = FMLClientHandler.instance().getClient().gameSettings.keyBindJump;
    public static KeyBinding KB_DESCEND = FMLClientHandler.instance().getClient().gameSettings.keyBindSneak;
    //TODO make config option for "alternate controls"
    public static KeyBinding KB_ALT_DESCEND = new KeyBinding("Descend", Keyboard.KEY_NONE, KB_CATEGORY);
    public static KeyBinding KB_ALT_FLY = new KeyBinding("Fly", Keyboard.KEY_NONE, KB_CATEGORY);
    public static KeyBinding KB_SPRINT_ACC = new KeyBinding("Super Sprint", Keyboard.KEY_LCONTROL, KB_CATEGORY);
    public static KeyBinding KB_ROCKET = new KeyBinding("Rocket", Keyboard.KEY_LMENU, KB_CATEGORY);

    public ITAKeybindings(){
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        ClientRegistry.registerKeyBinding(ITAKeybindings.KB_ALT_FLY);
        ClientRegistry.registerKeyBinding(ITAKeybindings.KB_ALT_DESCEND);
        ClientRegistry.registerKeyBinding(ITAKeybindings.KB_HOVER);
        ClientRegistry.registerKeyBinding(ITAKeybindings.KB_SPRINT_ACC);
        ClientRegistry.registerKeyBinding(ITAKeybindings.KB_ROCKET);
    }

    @SubscribeEvent
    public void HandleKeyPress(InputEvent.KeyInputEvent event) {
        boolean hasJetpack = false;
        boolean hasSprint = false;
        boolean hasNightVision = false;
        boolean hasRocket = false;
        for (int i=0; i<4; i++){
            ItemStack is = Minecraft.getMinecraft().thePlayer.getCurrentArmor(i);
            if (is != null && (is.getItem() == ITA.BasicHelmet || is.getItem() == ITA.BasicChestplate ||
                    is.getItem() == ITA.BasicLeggings || is.getItem() == ITA.BasicBoots)){
                ITAArmorProperties props = new ITAArmorProperties(is);
                if (props.Traits.containsKey(TraitNames.ABILITY_BASIC_FLIGHT))
                    hasJetpack = true;
                if (props.Traits.containsKey(TraitNames.ABILITY_BASIC_STEPASSIST))
                    hasSprint = true;
                if (props.Traits.containsKey(TraitNames.POTION_EFFECT_NIGHTVISION))
                    hasNightVision = true;
                if (props.Traits.containsKey(TraitNames.ABILITY_BASIC_ROCKET))
                    hasRocket = true;
            }
        }
        if (hasJetpack) {
            if (FLY != (KB_FLY.getIsKeyPressed() || KB_ALT_FLY.getIsKeyPressed())) {
                FLY = (KB_FLY.getIsKeyPressed() || KB_ALT_FLY.getIsKeyPressed());
                KeySync.setKey(Minecraft.getMinecraft().thePlayer.getDisplayName(), KeySync.FLY, FLY);
                Main.Network.sendToServer(new PacketKey.KeyMessage(KeySync.FLY, FLY));
            } else if (DESCEND != (KB_DESCEND.getIsKeyPressed() || KB_ALT_DESCEND.getIsKeyPressed())) {
                DESCEND = (KB_DESCEND.getIsKeyPressed() || KB_ALT_DESCEND.getIsKeyPressed());
                KeySync.setKey(Minecraft.getMinecraft().thePlayer.getDisplayName(), KeySync.DESCEND, DESCEND);
                Main.Network.sendToServer(new PacketKey.KeyMessage(KeySync.DESCEND, DESCEND));
            } else if (KB_HOVER.isPressed()) {
                HOVER = !HOVER;
                String msg = "Hover mode is now " + EnumChatFormatting.RED + "[OFF]";
                if (HOVER)
                    msg = "Hover mode is now " + EnumChatFormatting.ITALIC + EnumChatFormatting.GREEN + "[ON]";
                KeySync.setKey(Minecraft.getMinecraft().thePlayer.getDisplayName(), KeySync.HOVER, HOVER);
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
                Main.Network.sendToServer(new PacketKey.KeyMessage(KeySync.HOVER, HOVER));
            }
        }
        if (hasNightVision){
            if (KB_NIGHTVISION.isPressed()){
                NIGHTVISION = !NIGHTVISION;
                String msg = "Toggled Night Vision " + EnumChatFormatting.RED + "[OFF]";
                if (NIGHTVISION)
                    msg = "Toggled Night Vision " + EnumChatFormatting.GREEN + "[ON]";
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
                KeySync.setKey(Minecraft.getMinecraft().thePlayer.getDisplayName(), KeySync.NIGHTVISION, NIGHTVISION);
                Main.Network.sendToServer(new PacketKey.KeyMessage(KeySync.NIGHTVISION, NIGHTVISION));
            }
        }
        if (hasSprint){
            if (KB_SPRINT_ACC.getIsKeyPressed() != SPRINTACC){
                SPRINTACC = KB_SPRINT_ACC.getIsKeyPressed();
                KeySync.setKey(Minecraft.getMinecraft().thePlayer.getDisplayName(), KeySync.SPRINTACC, SPRINTACC);
                Main.Network.sendToServer(new PacketKey.KeyMessage(KeySync.SPRINTACC, SPRINTACC));
            }
        }
        if (hasRocket){
            if (KB_ROCKET.getIsKeyPressed() != ROCKETING){
                ROCKETING = KB_ROCKET.getIsKeyPressed();
                KeySync.setKey(Minecraft.getMinecraft().thePlayer.getDisplayName(), KeySync.ROCKET, ROCKETING);
                Main.Network.sendToServer(new PacketKey.KeyMessage(KeySync.ROCKET, ROCKETING));
            }
        }
    }

}
