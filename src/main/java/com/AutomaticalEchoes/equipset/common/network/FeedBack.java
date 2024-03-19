package com.AutomaticalEchoes.equipset.common.network;

import com.AutomaticalEchoes.equipset.api.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;

public final class FeedBack implements IMessage, IMessageHandler<FeedBack,IMessage> {
    private int code;
    private int num;

    public FeedBack() {
    }

    public FeedBack(int code) {
        super();
        this.code = code;
    }
    public FeedBack(int code, Integer num) {
        super();
        this.code = code;
        this.num = num;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        FeedBack that = (FeedBack) obj;
        return Objects.equals(this.code, that.code) && Objects.equals(this.num, that.num);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.code = buf.readInt();
        this.num = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(code);
        buf.writeInt(num);
    }

    @Override
    public IMessage onMessage(FeedBack message, MessageContext ctx) {
        Minecraft.getMinecraft().player.inventory.markDirty();
        Minecraft.getMinecraft().ingameGUI.setOverlayMessage(Utils.BuildComponent(message.code, num),false);
        return null;
    }
}
