package mysticmods.mysticalworld.world.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.placement.IPlacementConfig;

import java.util.Set;
import java.util.stream.Collectors;

public class DimensionCountRangeConfig implements IPlacementConfig {
  public static final Codec<DimensionCountRangeConfig> CODEC = RecordCodecBuilder.create((codec) -> codec.group(
      Codec.INT.fieldOf("count").forGetter((config) -> config.count),
      Codec.INT.fieldOf("bottomOffset").forGetter((config) -> config.bottomOffset),
      Codec.INT.fieldOf("topOffset").forGetter((config) -> config.topOffset),
      Codec.INT.fieldOf("maximum").forGetter((config) -> config.maximum),
      ResourceLocation.CODEC.listOf().fieldOf("dimensions").forGetter(o -> o.dimensions.stream().map(RegistryKey::location).collect(Collectors.toList()))).apply(codec, (c, b, t, m, r) -> new DimensionCountRangeConfig(c, b, t, m, r.stream().map(o -> RegistryKey.create(Registry.DIMENSION_REGISTRY, o)).collect(Collectors.toSet()))));

  public final Set<RegistryKey<World>> dimensions;
  public final int count;
  public final int bottomOffset;
  public final int topOffset;
  public final int maximum;

  public DimensionCountRangeConfig(int count, int bottomOffset, int topOffset, int maximum, Set<RegistryKey<World>> dimensions) {
    this.dimensions = dimensions;
    this.count = count;
    this.bottomOffset = bottomOffset;
    this.topOffset = topOffset;
    this.maximum = maximum;
  }
}