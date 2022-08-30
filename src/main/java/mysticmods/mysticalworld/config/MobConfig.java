package mysticmods.mysticalworld.config;

import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class MobConfig extends AbstractConfig {

  protected String name;
  protected int chance;
  protected int min;
  protected int max;
  protected List<String> biomes;
  protected TagKey<Biome> restriction;

  protected ForgeConfigSpec.IntValue configChance;
  protected ForgeConfigSpec.IntValue configMin;
  protected ForgeConfigSpec.IntValue configMax;
  protected ForgeConfigSpec.ConfigValue<String> configBiomes;

  public MobConfig(String name, int chance, int min, int max, List<String> biomes) {
    this(name, chance, min, max, biomes, BiomeTags.IS_OVERWORLD);
  }

  public MobCategory getClassification() {
    return MobCategory.CREATURE;
  }

  public MobConfig(String name, int chance, int min, int max, List<String> biomes, TagKey<Biome> restriction) {
    super();
    this.name = name;
    this.chance = chance;
    this.min = min;
    this.max = max;
    this.biomes = biomes;
    this.restriction = restriction;
  }

  public TagKey<Biome> getRestriction() {
    return restriction;
  }

  public int getChance() {
    return configChance.get();
  }

  public int getMin() {
    return configMin.get();
  }

  public int getMax() {
    return configMax.get();
  }

  public boolean shouldRegister() {
    return getChance() > 0;
  }

  protected void preApply(ForgeConfigSpec.Builder builder) {
  }

  protected void doApply(ForgeConfigSpec.Builder builder) {
    builder.comment(name + " spawn config.").push(name + "_spawn");
    configChance = builder.comment("Chance to spawn (set to 0 to disable).").defineInRange("spawnChance", chance, 0, 256);
    configMin = builder.comment("Min to spawn in a group.").defineInRange("min", min, 0, 256);
    configMax = builder.comment("Max to spawn in a group.").defineInRange("max", max, 0, 256);

    StringBuilder sb = new StringBuilder();
    biomes.forEach(biome -> {
      sb.append(biome);
      sb.append(",");
    });

    configBiomes = builder.comment("List of biome types to spawn.").define("biomes", sb.toString());
  }

  protected void postApply(ForgeConfigSpec.Builder builder) {
    builder.pop();
  }

  @Override
  public void apply(ForgeConfigSpec.Builder builder) {
    preApply(builder);
    doApply(builder);
    postApply(builder);
  }

  @Override
  public void reset() {
  }
}
