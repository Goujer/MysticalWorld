package mysticmods.mysticalworld.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

import java.util.Random;

@SuppressWarnings("deprecation")
public class PetrifiedFlowerBlock extends FlowerBlock {
  public PetrifiedFlowerBlock(AbstractBlock.Properties propertiesIn) {
    super(Effects.JUMP, 50, propertiesIn);
  }

  @Override
  protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) {
    return state.is(Tags.Blocks.STONE) || state.is(Blocks.GRAVEL) || state.is(Tags.Blocks.ORES);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    VoxelShape voxelshape = this.getShape(stateIn, worldIn, pos, ISelectionContext.empty());
    Vector3d vector3d = voxelshape.bounds().getCenter();
    double d0 = (double) pos.getX() + vector3d.x;
    double d1 = (double) pos.getZ() + vector3d.z;

    for (int i = 0; i < 3; ++i) {
      if (rand.nextBoolean()) {
        worldIn.addParticle(ParticleTypes.ASH, d0 + rand.nextDouble() / 5.0D, (double) pos.getY() + (0.5D - rand.nextDouble()), d1 + rand.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
      }
    }

  }

  @Override
  public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
    if (!worldIn.isClientSide) {
      if (entityIn instanceof LivingEntity) {
        LivingEntity livingentity = (LivingEntity) entityIn;
        livingentity.addEffect(new EffectInstance(Effects.JUMP, 20, 2));
      }
    }
  }
}
