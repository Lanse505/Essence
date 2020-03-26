package com.teamacronymcoders.essence.utils.network;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.utils.config.EssenceGeneralConfig;
import com.teamacronymcoders.essence.utils.network.message.PacketItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

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

    private static final SimpleChannel handler = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Essence.MODID, "networker"))
        .clientAcceptedVersions(getProtocolVersion()::equals)
        .serverAcceptedVersions(getProtocolVersion()::equals)
        .networkProtocolVersion(PacketHandler::getProtocolVersion)
        .simpleChannel();

    private int index = 0;

    private static String getProtocolVersion() {
        return Essence.instance == null ? "999.999.999" : Essence.instance.versionNumber;
    }

    /**
     * Encodes an Object[] of data into a DataOutputStream.
     *
     * @param dataValues - an Object[] of data to encode
     * @param output     - the output stream to write to
     */
    public static void encode(Object[] dataValues, PacketBuffer output) {
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
                output.writeString((String) data);
            } else if (data instanceof UUID) {
                output.writeUniqueId((UUID) data);
            } else if (data instanceof Direction) {
                output.writeInt(((Direction) data).ordinal());
            } else if (data instanceof ItemStack) {
                output.writeItemStack((ItemStack) data);
            } else if (data instanceof FluidStack) {
                output.writeFluidStack((FluidStack) data);
            } else if (data instanceof CompoundNBT) {
                output.writeCompoundTag((CompoundNBT) data);
            } else if (data instanceof ResourceLocation) {
                output.writeResourceLocation((ResourceLocation) data);
            } else if (data instanceof Enum) {
                output.writeEnumValue((Enum<?>) data);
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

    public static String readString(PacketBuffer buffer) {
        //TODO: Re-evaluate, this method is currently used because buffer.readString() is clientside only, so it mimics its behaviour so that servers don't crash
        return buffer.readString(Short.MAX_VALUE);
    }

    public static void log(String log) {
        if (EssenceGeneralConfig.getInstance().getEnableDebugLogging().get()) {
            Essence.LOGGER.info(log);
        }
    }

    public static PlayerEntity getPlayer(Supplier<NetworkEvent.Context> context) {
        return Essence.proxy.getPlayer(context);
    }

    public void init() {
        registerMessage(PacketItemStack.class, PacketItemStack::encode, PacketItemStack::decode, PacketItemStack::handle);
    }

    private <MSG> void registerMessage(Class<MSG> type, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> consumer) {
        registerMessage(index++, type, encoder, decoder, consumer);
    }

    public <MSG> void registerMessage(int id, Class<MSG> type, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> consumer) {
        handler.registerMessage(id, type, encoder, decoder, consumer);
    }

    /**
     * Send this message to the specified player.
     *
     * @param message - the message to send
     * @param player  - the player to send it to
     */
    public <MSG> void sendTo(MSG message, ServerPlayerEntity player) {
        handler.sendTo(message, player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
    }

    /**
     * Send this message to everyone connected to the server.
     *
     * @param message - message to send
     */
    public <MSG> void sendToAll(MSG message) {
        handler.send(PacketDistributor.ALL.noArg(), message);
    }

    /**
     * Send this message to everyone within the supplied dimension.
     *
     * @param message   - the message to send
     * @param dimension - the dimension to target
     */
    public <MSG> void sendToDimension(MSG message, DimensionType dimension) {
        handler.send(PacketDistributor.DIMENSION.with(() -> dimension), message);
    }

    /**
     * Send this message to the server.
     *
     * @param message - the message to send
     */
    public <MSG> void sendToServer(MSG message) {
        handler.sendToServer(message);
    }

    public <MSG> void sendToAllTracking(MSG message, TileEntity tile) {
        sendToAllTracking(message, tile.getWorld(), tile.getPos());
    }

    public <MSG> void sendToAllTracking(MSG message, World world, BlockPos pos) {
        if (world instanceof ServerWorld) {
            //If we have a ServerWorld just directly figure out the ChunkPos so as to not require looking up the chunk
            // This provides a decent performance boost over using the packet distributor
            ((ServerWorld) world).getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false).forEach(p -> sendTo(message, p));
        } else {
            //Otherwise fallback to entities tracking the chunk if some mod did something odd and our world is not a ServerWorld
            handler.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunk(pos.getX() >> 4, pos.getZ() >> 4)), message);
        }
    }

}
