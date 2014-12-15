package com.trcx.ita.common.network;

import com.trcx.ita.common.utility.KeySync;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class PacketKey implements IMessageHandler<PacketKey.KeyMessage, IMessage>
{
    @Override
    public IMessage onMessage(KeyMessage message, MessageContext context) 
    {
        String playerName = context.getServerHandler().playerEntity.getDisplayName();
        KeySync.setKey(playerName, message.key, message.state);
        return null;
    }
    
    public static class KeyMessage implements IMessage
    {
        public int key;
        public Boolean state;

        public KeyMessage(){} //Required, do no remove

        public KeyMessage(int key, boolean state)
        {
            this.key = key;
            this.state = state;
        }
    
        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeInt(this.key);
            buf.writeBoolean(state);
        }
    
        @Override
        public void fromBytes(ByteBuf dataStream)
        {
            this.key = dataStream.readInt();
            this.state = dataStream.readBoolean();
        }
    }
}
