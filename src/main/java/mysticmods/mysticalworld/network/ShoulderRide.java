package mysticmods.mysticalworld.network;


import mysticmods.mysticalworld.MysticalWorld;
import mysticmods.mysticalworld.api.Capabilities;
import mysticmods.mysticalworld.api.IPlayerShoulderCapability;
import mysticmods.mysticalworld.capability.PlayerShoulderCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ShoulderRide {
  private CompoundNBT tag;
  private UUID id;

  public ShoulderRide(PacketBuffer buffer) {
    long uuid1 = buffer.readLong();
    long uuid2 = buffer.readLong();
    id = new UUID(uuid1, uuid2);
    tag = buffer.readNbt();
  }

  public ShoulderRide(PlayerEntity player, IPlayerShoulderCapability cap) {
    this.tag = cap.writeNBT();
    this.id = player.getUUID();
  }

  public CompoundNBT getTag() {
    return tag;
  }

  public UUID getId() {
    return id;
  }

  public void encode(PacketBuffer buf) {
    buf.writeLong(id.getMostSignificantBits());
    buf.writeLong(id.getLeastSignificantBits());
    buf.writeNbt(tag);
  }

  public void handle(Supplier<NetworkEvent.Context> context) {
    context.get().enqueueWork(() -> handle(this, context));
  }

  @OnlyIn(Dist.CLIENT)
  private static void handle(ShoulderRide message, Supplier<NetworkEvent.Context> context) {
    PlayerEntity target = Minecraft.getInstance().player;
    World world = target.level;
    if (!target.getUUID().equals(message.getId())) {
      target = world.getPlayerByUUID(message.getId());
    }

    if (target == null) {
      return;
    }

    final PlayerEntity player = target;

    target.getCapability(Capabilities.SHOULDER_CAPABILITY).ifPresent((cap) -> {
      cap.readNBT(message.getTag());
      try {
        PlayerShoulderCapability.setRightShoulder.invokeExact(player, cap.generateShoulderNBT());
      } catch (Throwable throwable) {
        MysticalWorld.LOG.error("Unable to fake player having a shoulder entity", throwable);
      }
    });

    context.get().setPacketHandled(true);
  }
}

