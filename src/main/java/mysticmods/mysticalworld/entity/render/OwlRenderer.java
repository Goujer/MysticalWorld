package mysticmods.mysticalworld.entity.render;

import mysticmods.mysticalworld.MysticalWorld;
import mysticmods.mysticalworld.entity.OwlEntity;
import mysticmods.mysticalworld.entity.model.ModelHolder;
import mysticmods.mysticalworld.entity.model.OwlModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class OwlRenderer extends MobRenderer<OwlEntity, OwlModel> {

  public OwlRenderer(EntityRendererManager p_i50961_1_, OwlModel p_i50961_2_, float p_i50961_3_) {
    super(p_i50961_1_, p_i50961_2_, p_i50961_3_);
  }

  public static class Factory implements IRenderFactory<OwlEntity> {

    @Override
    @Nonnull
    public OwlRenderer createRenderFor(@Nonnull EntityRendererManager manager) {
      return new OwlRenderer(manager, ModelHolder.owlModel, 0.25f);
    }
  }

  @Override
  @Nonnull
  public ResourceLocation getTextureLocation(@Nonnull OwlEntity entity) {
    return new ResourceLocation(MysticalWorld.MODID + ":textures/entity/owl.png");
  }
}
