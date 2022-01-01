package com.teamacronymcoders.essence.common.util.network;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.common.util.config.EssenceGeneralConfig;
import com.teamacronymcoders.essence.common.util.network.message.server.PacketItemStack;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Credit for most of this code goes to Mekanism.
 */
public class PacketHandler {

    private static final SimpleChannel handler = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Essence.MOD_ID, "networker"))
            .clientAcceptedVersions(getProtocolVersion()::equals)
            .serverAcceptedVersions(getProtocolVersion()::equals)
            .networkProtocolVersion(PacketHandler::getProtocolVersion)
            .simpleChannel();

    private int index = 0;

    private static String getProtocolVersion() {
        return Essence.instance == null ? "999.999.999" : Essence.versionNumber;
    }

    /**
     * Encodes an Object[] of data into a DataOutputStream.
     *
     * @param dataValues - an Object[] of data to encode
     * @param output     - the output stream to write to
     */
    public static void encode(Object[] dataValues, FriendlyByteBuf output) {
        for (Object data : dataValues) {
            if (data instanceof Byte) {
                output.writeByte((Byte) data);
            } else if (data instanceof Integer) {
                output.writeInt((Integer) data);
            } else if (data instanceof Short) {
                output.writeShort((Short) data);
            } else if (data instanceof Long) {
                output.writeLong((Long) data);
            } else if (data instanceof Boolean) {
                output.writeBoolean((Boolean) data);
            } else if (data instanceof Double) {
                output.writeDouble((Double) data);
            } else if (data instanceof Float) {
                output.writeFloat((Float) data);
            } else if (data instanceof String) {
                output.writeUtf((String) data);
            } else if (data instanceof UUID) {
                output.writeUUID((UUID) data);
            } else if (data instanceof Direction) {
                output.writeInt(((Direction) data).ordinal());
            } else if (data instanceof ItemStack) {
                output.writeItemStack((ItemStack) data, false);
            } else if (data instanceof FluidStack) {
                output.writeFluidStack((FluidStack) data);
            } else if (data instanceof CompoundTag) {
                output.writeNbt((CompoundTag) data);
            } else if (data instanceof ResourceLocation) {
                output.writeResourceLocation((ResourceLocation) data);
            } else if (data instanceof Enum) {
                output.writeEnum((Enum<?>) data);
            } else if (data instanceof int[]) {
                for (int i : (int[]) data) {
                    output.writeInt(i);
                }
            } else if (data instanceof byte[]) {
                for (byte b : (byte[]) data) {
                    output.writeByte(b);
                }
            } else if (data instanceof List) {
                encode(((List<?>) data).toArray(), output);
            } else {
                throw new RuntimeException("Un-encodable data passed to encode(): " + data + ", full data: " + Arrays.toString(dataValues));
            }
        }
    }

    public static void log(String log) {
        if (EssenceGeneralConfig.getInstance().getEnableDebugLogging().get()) {
            Essence.LOGGER.info(log);
        }
    }

    public static Player getPlayer(Supplier<NetworkEvent.Context> context) {
        return Essence.proxy.getPlayer(context);
    }

    public void init() {
        registerMessage(PacketItemStack.class, PacketItemStack::encode, PacketItemStack::decode, PacketItemStack::handle);
    }

    private <MSG> void registerMessage(Class<MSG> type, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> consumer) {
        registerMessage(index++, type, encoder, decoder, consumer);
    }

    public <MSG> void registerMessage(int id, Class<MSG> type, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> consumer) {
        handler.registerMessage(id, type, encoder, decoder, consumer);
    }

    /**
     * Send this message to the specified player.
     *
     * @param message - the message to send
     * @param player  - the player to send it to
     */
    public <MSG> void sendTo(MSG message, ServerPlayer player) {
        handler.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }

    /**
     * Send this message to the server.
     *
     * @param message - the message to send
     */
    public <MSG> void sendToServer(MSG message) {
        handler.sendToServer(message);
    }

}
