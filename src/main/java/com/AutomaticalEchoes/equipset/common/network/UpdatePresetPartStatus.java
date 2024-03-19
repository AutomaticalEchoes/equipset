package com.AutomaticalEchoes.equipset.common.network;

import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;

public final class UpdatePresetPartStatus implements IMessage, IMessageHandler<UpdatePresetPartStatus,IMessage> {
    private int targetNum;
    private String partName;
    private boolean enable;

    public UpdatePresetPartStatus() {
    }

    public UpdatePresetPartStatus(int targetNum, String partName, boolean enable) {
        this.targetNum = targetNum;
        this.partName = partName;
        this.enable = enable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        UpdatePresetPartStatus that = (UpdatePresetPartStatus) obj;
        return this.targetNum == that.targetNum &&
                Objects.equals(this.partName, that.partName) &&
                this.enable == that.enable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetNum, partName, enable);
    }

    @Override
    public String toString() {
        return "UpdatePresetPartStatus[" +
                "targetNum=" + targetNum + ", " +
                "partName=" + partName + ", " +
                "enable=" + enable + ']';
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.targetNum = buf.readInt();
        this.partName = ByteBufUtils.readUTF8String(buf);
        this.enable = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(targetNum);
        ByteBufUtils.writeUTF8String(buf,partName);
        buf.writeBoolean(enable);
    }

    @Override
    public IMessage onMessage(UpdatePresetPartStatus msg, MessageContext ctx) {
        IPlayerInterface player = (IPlayerInterface) ctx.getServerHandler().player;
        player.updatePartStatus(msg.targetNum, msg.partName, msg.enable);
        return null;
    }
}
