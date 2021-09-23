package mysticmods.mysticalworld.items.copper;

import com.google.common.collect.Multimap;
import mysticmods.mysticalworld.items.ModifiedArmorItem;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import noobanidus.libs.noobutil.material.MaterialType;

import net.minecraft.item.Item.Properties;

public class CopperArmorItem extends ModifiedArmorItem implements ICopperItem {
  public CopperArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
    super(materialIn, slot, builder);
  }

  @Override
  public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
    Multimap<Attribute, AttributeModifier> map = super.getDefaultAttributeModifiers(equipmentSlot);

    if (this.slot == equipmentSlot) {
      map.put(Attributes.MAX_HEALTH, getOrCreateModifier(Attributes.MAX_HEALTH, () -> new AttributeModifier(MaterialType.ARMOR_MODIFIERS[slot.getIndex()], "Healthiness", 1f, AttributeModifier.Operation.ADDITION)));
    }

    return map;
  }
}
