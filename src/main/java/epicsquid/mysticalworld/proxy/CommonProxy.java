package epicsquid.mysticalworld.proxy;

import epicsquid.mysticallib.util.Util;
import epicsquid.mysticalworld.MysticalWorld;
import epicsquid.mysticalworld.config.ConfigManager;
import epicsquid.mysticalworld.events.LeafHandler;
import epicsquid.mysticalworld.init.ModEntities;
import epicsquid.mysticalworld.init.ModItems;
import epicsquid.mysticalworld.integration.endercore.EndercoreHarvest;
import epicsquid.mysticalworld.integration.harvest.HarvestIntegration;
import epicsquid.mysticalworld.integration.patchouli.api.ConfigKeys;
import epicsquid.mysticalworld.integration.thaumcraft.AspectRegistry;
import epicsquid.mysticalworld.integration.thaumcraft.ThaumcraftInit;
import epicsquid.mysticalworld.loot.conditions.*;
import epicsquid.mysticalworld.world.OreGenerator;
import epicsquid.mysticalworld.world.StructureGenerator;
import epicsquid.mysticalworld.world.WorldGeneratorTrees;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
  public static ResourceLocation BARROW = new ResourceLocation(MysticalWorld.MODID, "barrow");
  public static ResourceLocation HUT = new ResourceLocation(MysticalWorld.MODID, "hut");

  private StructureGenerator hutGenerator;
  private StructureGenerator barrowGenerator;
  private WorldGeneratorTrees treeGenerator;

  public void preInit(FMLPreInitializationEvent event) {
    GameRegistry.registerWorldGenerator(new OreGenerator(), 1);
    ModEntities.registerLootTables();
    LootTableList.register(new ResourceLocation(MysticalWorld.MODID, "chests/hut"));
    if (Loader.isModLoaded("thaumcraft")) {
      ThaumcraftInit.init();
    }
  }

  public void init(FMLInitializationEvent event) {
    ModItems.registerOredict();
    if (Loader.isModLoaded("endercore")) {
      EndercoreHarvest.init();
    }
    LootConditionManager.registerCondition(new HasHorns.Serializer());
    LootConditionManager.registerCondition(new IsLava.Serializer());
    LootConditionManager.registerCondition(new IsObsidian.Serializer());
    LootConditionManager.registerCondition(new IsEnder.Serializer());
    LootConditionManager.registerCondition(new IsMature.Serializer());
    LootConditionManager.registerCondition(new WasRitual.Serializer());
    if (ConfigManager.BarrowDistance != -1) {
      GameRegistry.registerWorldGenerator(barrowGenerator = new StructureGenerator(BARROW, 10, () -> {
        switch (Util.rand.nextInt(6)) {
          case 0:
          case 1:
            return EntitySkeleton.class;
          default:
            return EntityZombie.class;
        }
      }, ConfigManager.BarrowDistance), 400);
    }

    if (ConfigManager.HutDistance != -1) {
      GameRegistry.registerWorldGenerator(hutGenerator = new StructureGenerator(HUT, 6, () -> {
        if (Util.rand.nextInt(4) == 0) {
          return EntityWitch.class;
        }
        return EntityZombie.class;
      }, ConfigManager.HutDistance), 400);
    }

    GameRegistry.registerWorldGenerator(treeGenerator = new WorldGeneratorTrees(), 400);
    ConfigKeys.init();

    // Leaping potions!
    PotionHelper.addMix(PotionTypes.AWKWARD, ModItems.antlers, PotionTypes.LEAPING);
  }

  public void postInit(FMLPostInitializationEvent event) {
/*    ItemCharm.ALLOWED_ENCHANTS.addAll(Arrays.asList(Enchantments.PROTECTION,
        Enchantments.FIRE_PROTECTION,
        Enchantments.FEATHER_FALLING,
        Enchantments.BLAST_PROTECTION,
        Enchantments.PROJECTILE_PROTECTION,
        Enchantments.RESPIRATION,
        Enchantments.AQUA_AFFINITY,
        Enchantments.THORNS,
        Enchantments.DEPTH_STRIDER,
        Enchantments.FROST_WALKER,
        Enchantments.BINDING_CURSE,
        Enchantments.SHARPNESS,
        Enchantments.SMITE,
        Enchantments.BANE_OF_ARTHROPODS,
        Enchantments.KNOCKBACK,
        Enchantments.FIRE_ASPECT,
        Enchantments.LOOTING,
        Enchantments.SWEEPING,
        Enchantments.EFFICIENCY,
        Enchantments.SILK_TOUCH,
        Enchantments.UNBREAKING,
        Enchantments.FORTUNE,
        Enchantments.POWER,
        Enchantments.PUNCH,
        Enchantments.FLAME,
        Enchantments.INFINITY,
        Enchantments.LUCK_OF_THE_SEA,
        Enchantments.LURE,
        Enchantments.MENDING,
        Enchantments.VANISHING_CURSE));*/
  }

  public void loadComplete(FMLLoadCompleteEvent event) {
    if (Loader.isModLoaded("harvest")) {
      HarvestIntegration.init();
    }
    LeafHandler.getLeafBlocks();
  }

  public void serverStarted(FMLServerStartedEvent event) {
  }
}
