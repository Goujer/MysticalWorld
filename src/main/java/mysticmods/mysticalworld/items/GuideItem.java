package mysticmods.mysticalworld.items;

import mysticmods.mysticalworld.MysticalWorld;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.patchouli.common.base.PatchouliSounds;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;
import vazkii.patchouli.common.network.NetworkHandler;
import vazkii.patchouli.common.network.message.MessageOpenBookGui;

import java.util.List;

@SuppressWarnings("NullableProblems")
public class GuideItem extends Item {
  private static final ResourceLocation ENCYCLOPEDIA = new ResourceLocation(MysticalWorld.MODID, "world_guide");

  public GuideItem(Item.Properties properties) {
    super(properties);
  }

  public static Book getBook() {
    return BookRegistry.INSTANCE.books.get(ENCYCLOPEDIA);
  }

  @Override
  public ITextComponent getName(ItemStack stack) {
    Book book = getBook();
    return (book != null ? new TranslationTextComponent(book.name) : super.getName(stack));
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);
    Book book = getBook();
    if (book != null && book.contents != null) {
      tooltip.add(book.getSubtitle().withStyle(TextFormatting.GRAY));
    }
  }

  @Override
  public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
    ItemStack stack = playerIn.getItemInHand(handIn);
    Book book = getBook();
    if (book == null) {
      return ActionResult.fail(stack);
    } else {
      if (playerIn instanceof ServerPlayerEntity) {
        NetworkHandler.sendToPlayer(new MessageOpenBookGui(book.id, null), (ServerPlayerEntity) playerIn);
        SoundEvent sfx = PatchouliSounds.getSound(book.openSound, PatchouliSounds.book_open);
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), sfx, SoundCategory.PLAYERS, 1.0F, (float) (0.7D + Math.random() * 0.4D));
      }

      return ActionResult.success(stack);
    }
  }
}
