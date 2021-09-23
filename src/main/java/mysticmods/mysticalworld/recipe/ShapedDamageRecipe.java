package mysticmods.mysticalworld.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

@SuppressWarnings("NullableProblems")
public class ShapedDamageRecipe extends ShapedRecipe implements IDamageRecipe {
  private Ingredient damageItem;
  private Ingredient damageIngredient;
  private int damageAmount;

  private ShapedDamageRecipe(ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn, Ingredient damageItem, int damageAmount) {
    super(idIn, groupIn, recipeWidthIn, recipeHeightIn, recipeItemsIn, recipeOutputIn);
    this.damageItem = damageItem;
    this.damageAmount = damageAmount;
    this.damageIngredient = createDamageIngredient(damageItem);
  }

  @Override
  public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
    return getRemainingItems(inv, damageIngredient, damageAmount);
  }

  public static ShapedDamageRecipe create(ShapedRecipe recipe, Ingredient damageItem, int damageAmount) {
    return new ShapedDamageRecipe(recipe.getId(), recipe.getGroup(), recipe.getRecipeWidth(), recipe.getRecipeHeight(), recipe.getIngredients(), recipe.getResultItem(), damageItem, damageAmount);
  }

  public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ShapedDamageRecipe> {

    @Override
    public ShapedDamageRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
      ShapedRecipe result = IRecipeSerializer.SHAPED_RECIPE.fromJson(recipeId, json);
      Ingredient damageItem = Ingredient.fromJson(json.get(IDamageRecipe.TAG));
      int damageAmount = JSONUtils.getAsInt(json, IDamageRecipe.DAMAGE, -1);
      if (damageAmount == -1) {
        throw new JsonSyntaxException("Invalid damage_amount for ShapedDamageRecipe.");
      }
      return ShapedDamageRecipe.create(result, damageItem, damageAmount);
    }

    @Override
    public ShapedDamageRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
      ShapedRecipe result = IRecipeSerializer.SHAPED_RECIPE.fromNetwork(recipeId, buffer);
      Ingredient damageItem = Ingredient.fromNetwork(buffer);
      int damageAmount = buffer.readInt();
      return ShapedDamageRecipe.create(result, damageItem, damageAmount);
    }

    @Override
    public void toNetwork(PacketBuffer buffer, ShapedDamageRecipe recipe) {
      IRecipeSerializer.SHAPED_RECIPE.toNetwork(buffer, recipe);
      recipe.damageItem.toNetwork(buffer);
      buffer.writeInt(recipe.damageAmount);
    }
  }
}
