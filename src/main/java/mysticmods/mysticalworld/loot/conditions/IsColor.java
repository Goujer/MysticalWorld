package mysticmods.mysticalworld.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mysticmods.mysticalworld.entity.SproutEntity;
import mysticmods.mysticalworld.init.ModLoot;
import net.minecraft.entity.Entity;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;

public class IsColor implements ILootCondition {
  private final boolean inverse;
  private final int variant;

  public IsColor(boolean inverseIn, String color) {
    this.inverse = inverseIn;
    this.variant = SproutEntity.StringToVariant(color);
  }

  @Override
  public boolean test(LootContext lootContext) {
    int variant;
    Entity looted = lootContext.getParamOrNull(LootParameters.THIS_ENTITY);
    if (looted instanceof SproutEntity) {
      SproutEntity sprout = (SproutEntity) looted;
      variant = sprout.getEntityData().get(SproutEntity.variant);
    } else {
      variant = 0;
    }

    boolean flag = variant == this.variant;

    return flag == !this.inverse;
  }

  @Override
  public LootConditionType getType() {
    return ModLoot.IS_COLOR;
  }

  public static class Serializer implements ILootSerializer<IsColor> {
    @Override
    public void serialize(JsonObject json, IsColor value, JsonSerializationContext context) {
      json.addProperty("inverse", value.inverse);
      json.addProperty("color", SproutEntity.VariantToString(value.variant));
    }

    @Override
    public IsColor deserialize(JsonObject json, JsonDeserializationContext context) {
      return new IsColor(JSONUtils.getAsBoolean(json, "inverse", false), JSONUtils.getAsString(json, "color", "green"));
    }
  }

  public static ILootCondition.IBuilder builder(String variant) {
    return () -> new IsColor(false, variant);
  }
}

