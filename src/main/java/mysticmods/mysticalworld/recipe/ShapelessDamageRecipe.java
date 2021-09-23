package mysticmods.mysticalworld.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ShapelessDamageRecipe extends ShapelessRecipe implements IDamageRecipe {
  private Ingredient damageItem;
  private Ingredient damageIngredient;
  private int damageAmount;

  public ShapelessDamageRecipe(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn, Ingredient damageItem, int damageAmount) {
    super(idIn, groupIn, recipeOutputIn, recipeItemsIn);
    this.damageItem = damageItem;
    this.damageAmount = damageAmount;
    this.damageIngredient = createDamageIngredient(damageItem);
  }

  @Override
  public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
    return getRemainingItems(inv, damageIngredient, damageAmount);
  }

  public static ShapelessDamageRecipe create(ShapelessRecipe recipe, Ingredient damageItem, int damageAmount) {
    return new ShapelessDamageRecipe(recipe.getId(), recipe.getGroup(), recipe.getResultItem(), recipe.getIngredients(), damageItem, damageAmount);
  }

  public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ShapelessDamageRecipe> {

    @Override
    public ShapelessDamageRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
      ShapelessRecipe result = IRecipeSerializer.SHAPELESS_RECIPE.fromJson(recipeId, json);
      Ingredient damageItem = Ingredient.fromJson(json.get(TAG));
      int damageAmount = JSONUtils.getAsInt(json, DAMAGE, -1);
      if (damageAmount == -1) {
        throw new JsonSyntaxException("Invalid damage_amount for ShapelessDamageRecipe.");
      }
      return ShapelessDamageRecipe.create(result, damageItem, damageAmount);
    }

    @Override
    public ShapelessDamageRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
      ShapelessRecipe result = IRecipeSerializer.SHAPELESS_RECIPE.fromNetwork(recipeId, buffer);
      Ingredient damageItem = Ingredient.fromNetwork(buffer);
      int damageAmount = buffer.readInt();
      return ShapelessDamageRecipe.create(result, damageItem, damageAmount);
    }

    @Override
    public void toNetwork(PacketBuffer buffer, ShapelessDamageRecipe recipe) {
      IRecipeSerializer.SHAPELESS_RECIPE.toNetwork(buffer, recipe);
      recipe.damageItem.toNetwork(buffer);
      buffer.writeInt(recipe.damageAmount);
    }
  }
}
