package mysticmods.mysticalworld.loot.modifiers;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mysticmods.mysticalworld.config.ConfigManager;
import mysticmods.mysticalworld.init.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class SilkwormEggModifier extends LootModifier {
  protected SilkwormEggModifier(LootItemCondition[] conditionsIn) {
    super(conditionsIn);
  }

  @NotNull
  @Override
  protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
    if (ConfigManager.SILKWORM_CONFIG.getLeafDropsEnabled()) {
      if (context.getLevel().random.nextInt(ConfigManager.SILKWORM_CONFIG.getLeafDropChance()) == 0) {
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        if (tool != null && !tool.is(Tags.Items.SHEARS)) {
          generatedLoot.add(new ItemStack(ModItems.SILKWORM_EGG.get()));
        }
      }
    }
    return generatedLoot;
  }

  @Override
  public Codec<? extends IGlobalLootModifier> codec() {
    return null;
  }
}
