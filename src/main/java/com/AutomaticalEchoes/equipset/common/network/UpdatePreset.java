package com.AutomaticalEchoes.equipset.common.network;

import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;

public final class UpdatePreset implements IMessage, IMessageHandler<UpdatePreset,IMessage> {
    private int targetNum;
    private int cases;

    public UpdatePreset() {
    }

    public UpdatePreset(int targetNum, int cases) {
        this.targetNum = targetNum;
        this.cases = cases;
    }

    public int targetNum() {
        return targetNum;
    }

    public int cases() {
        return cases;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        UpdatePreset that = (UpdatePreset) obj;
        return this.targetNum == that.targetNum &&
                this.cases == that.cases;
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetNum, cases);
    }

    @Override
    public String toString() {
        return "UpdatePreset[" +
                "targetNum=" + targetNum + ", " +
                "cases=" + cases + ']';
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.targetNum = buf.readInt();
        this.cases = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(targetNum);
        buf.writeInt(cases);
    }

    @Override
    public IMessage onMessage(UpdatePreset message, MessageContext ctx) {
        IPlayerInterface player = (IPlayerInterface) ctx.getServerHandler().player;
        player.updateSet(message.targetNum, message.cases);
        return null;
    }
}
