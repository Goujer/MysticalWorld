package epicsquid.mysticalworld.entity.model;

import epicsquid.mysticalworld.entity.EnderminiEntity;
import net.minecraft.client.renderer.entity.model.EndermanModel;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class ModelHolder implements ISelectiveResourceReloadListener {

  public static FoxModel foxModel;
  public static FrogModel frogModel;
  public static BeetleModel beetleModel;
  public static DeerModel deerModel;
  public static SproutModel sproutModel;
  public static EnderminiModel<EnderminiEntity> enderminiModel;

  public static void init() {
    foxModel = new FoxModel();
    frogModel = new FrogModel();
    beetleModel = new BeetleModel();
    deerModel = new DeerModel();
    sproutModel = new SproutModel();
    enderminiModel = new EnderminiModel<>();
  }

  @Override
  public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {
  }

  @Override
  public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
    // TODO make this work selectively
    init();
  }
}
