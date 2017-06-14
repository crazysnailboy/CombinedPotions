package net.crazysnailboy.mods.compot.item.crafting;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import net.crazysnailboy.mods.compot.CombinedPotions;
import net.crazysnailboy.mods.compot.common.config.ModConfiguration;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;


@SuppressWarnings("deprecation")
public class RecipeCombinedPotions2 implements IRecipe
{

	private static Item[] VALID_ITEMS = null;


	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		ItemStack tempStack = ItemStack.EMPTY;
		if (VALID_ITEMS == null) buildValidItemsArray();

		int potionEffects = 0;
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty())
			{
				Item item = stack.getItem();
				if (!ArrayUtils.contains(VALID_ITEMS, item)) return false;

				if (tempStack.isEmpty())
				{
					tempStack = stack.copy();
				}
				else
				{
					if (!canCombineItems(tempStack, stack)) return false;
				}

				if (ModConfiguration.maxPotionEffects >= 0)
				{
					potionEffects += getEffectsFromStack(stack).size();
					if (potionEffects > ModConfiguration.maxPotionEffects) return false;
				}
			}
		}

		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack outputStack = ItemStack.EMPTY;
		if (countSlotsNotEmpty(inv) <= 1) return outputStack;

		Collection<PotionEffect> effects = new ArrayList<PotionEffect>();

		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty())
			{
				if (outputStack.isEmpty()) outputStack = getOutputStack(stack);
				if (outputStack.isEmpty()) return outputStack;

				effects.addAll(getEffectsFromStack(stack));
			}
		}

		outputStack = PotionUtils.appendEffects(outputStack, effects);
		outputStack.setStackDisplayName(I18n.translateToLocal("item.combined_" + outputStack.getItem().getUnlocalizedName().substring(5) + ".name"));

		return outputStack;
	}

	@Override
	public int getRecipeSize()
	{
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return ItemStack.EMPTY;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
	{
		return NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
	}


	private static int countSlotsNotEmpty(IInventory inventory)
	{
		int result = 0;
		for (int i = 0; i < inventory.getSizeInventory(); i++)
		{
			if (!inventory.getStackInSlot(i).isEmpty()) result++;
		}
		return result;
	}

	private static void buildValidItemsArray()
	{
		List<Item> valid_items = new ArrayList<Item>();

		valid_items.add(Items.POTIONITEM);
		valid_items.add(Items.SPLASH_POTION);
		valid_items.add(Items.LINGERING_POTION);
		valid_items.add(Items.TIPPED_ARROW);

		if (Loader.isModLoaded("potioncore"))
		{
			valid_items.add(Item.getByNameOrId("potioncore:custom_potion"));
			valid_items.add(Item.getByNameOrId("potioncore:custom_arrow"));
		}

		VALID_ITEMS = valid_items.toArray(new Item[valid_items.size()]);
	}

	private boolean canCombineItems(ItemStack stackA, ItemStack stackB)
	{
		if (ItemStack.areItemsEqual(stackA, stackB)) return true;

		if (stackA.getItem() == Items.POTIONITEM && stackB.getItem() == Item.getByNameOrId("potioncore:custom_potion") && stackB.getMetadata() == 0) return true;
		if (stackA.getItem() == Items.SPLASH_POTION && stackB.getItem() == Item.getByNameOrId("potioncore:custom_potion") && stackB.getMetadata() == 1) return true;
		if (stackA.getItem() == Items.LINGERING_POTION && stackB.getItem() == Item.getByNameOrId("potioncore:custom_potion") && stackB.getMetadata() == 2) return true;

		if (stackB.getItem() == Items.POTIONITEM && stackA.getItem() == Item.getByNameOrId("potioncore:custom_potion") && stackA.getMetadata() == 0) return true;
		if (stackB.getItem() == Items.SPLASH_POTION && stackA.getItem() == Item.getByNameOrId("potioncore:custom_potion") && stackA.getMetadata() == 1) return true;
		if (stackB.getItem() == Items.LINGERING_POTION && stackA.getItem() == Item.getByNameOrId("potioncore:custom_potion") && stackA.getMetadata() == 2) return true;

		if (stackA.getItem() == Items.TIPPED_ARROW && stackB.getItem() == Item.getByNameOrId("potioncore:custom_arrow")) return true;
		if (stackB.getItem() == Items.TIPPED_ARROW && stackA.getItem() == Item.getByNameOrId("potioncore:custom_arrow")) return true;

		return false;
	}

	private ItemStack getOutputStack(ItemStack inputStack)
	{
		Item item = inputStack.getItem();

		if (item == Items.POTIONITEM || (item == Item.getByNameOrId("potioncore:custom_potion") && inputStack.getMetadata() == 0))
		{
			return new ItemStack(Items.POTIONITEM, 1, 0);
		}
		else if (item == Items.SPLASH_POTION || (item == Item.getByNameOrId("potioncore:custom_potion") && inputStack.getMetadata() == 1))
		{
			return new ItemStack(Items.SPLASH_POTION, 1, 0);
		}
		else if (item == Items.LINGERING_POTION || (item == Item.getByNameOrId("potioncore:custom_potion") && inputStack.getMetadata() == 2))
		{
			return new ItemStack(Items.LINGERING_POTION, 1, 0);
		}
		else if (item == Items.TIPPED_ARROW || item == Item.getByNameOrId("potioncore:custom_arrow"))
		{
			return new ItemStack(Items.TIPPED_ARROW, 1, 0);
		}

		else return ItemStack.EMPTY;
	}

	@SuppressWarnings("unchecked")
	private List<PotionEffect> getEffectsFromStack(ItemStack stack)
	{
		try
		{
			Method method = Class.forName("com.tmtravlr.potioncore.PotionCoreHelper").getMethod("getEffectsFromStack", ItemStack.class);
			return (List<PotionEffect>)method.invoke(null, stack);
		}
		catch (Exception ex)
		{
			CombinedPotions.LOGGER.catching(ex);
			return new ArrayList<PotionEffect>();
		}
	}

}
