package mysticmods.mysticalworld.network;

import mysticmods.mysticalworld.MysticalWorld;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import noobanidus.libs.noobutil.network.PacketHandler;

public class Networking extends PacketHandler {
  public static Networking INSTANCE = new Networking();

  public Networking() {
    super(MysticalWorld.MODID);
  }

  @Override
  public void registerMessages() {
    registerMessage(ShoulderRide.class, ShoulderRide::encode, ShoulderRide::new, ShoulderRide::handle);
  }

  public static void sendTo(Object msg, ServerPlayer player) {
    INSTANCE.sendToInternal(msg, player);
  }

  public static void sendToServer(Object msg) {
    INSTANCE.sendToServerInternal(msg);
  }

  public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message) {
    INSTANCE.sendInternal(target, message);
  }
}