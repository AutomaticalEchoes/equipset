package com.AutomaticalEchoes.equipset.common.network;

import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;

public final class UpdateSetName implements IMessage, IMessageHandler<UpdateSetName,IMessage> {
    private int suitNum;
    private String suitName;

    public UpdateSetName() {
    }

    public UpdateSetName(int suitNum, String suitName) {
        this.suitNum = suitNum;
        this.suitName = suitName;
    }

    public int suitNum() {
        return suitNum;
    }

    public String suitName() {
        return suitName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        UpdateSetName that = (UpdateSetName) obj;
        return this.suitNum == that.suitNum &&
                Objects.equals(this.suitName, that.suitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suitNum, suitName);
    }

    @Override
    public String toString() {
        return "UpdateSetName[" +
                "suitNum=" + suitNum + ", " +
                "suitName=" + suitName + ']';
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.suitNum = buf.readInt();
        this.suitName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.suitNum);
        ByteBufUtils.writeUTF8String(buf,this.suitName);
    }

    @Override
    public IMessage onMessage(UpdateSetName msg, MessageContext ctx) {
        ((IPlayerInterface) ctx.getServerHandler().player).updateSetName(msg.suitNum, msg.suitName);
        return null;
    }
}
