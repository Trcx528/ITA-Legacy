package com.trcx.ita;

import com.trcx.ita.client.ITAKeybindings;
import com.trcx.ita.client.JetPackOverlay;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        new JetPackOverlay();
    }

    @Override
    public void registerKeybindings() {
        new ITAKeybindings();
    }
}
