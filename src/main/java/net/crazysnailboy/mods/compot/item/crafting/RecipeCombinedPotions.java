package net.crazysnailboy.mods.compot.item.crafting;

import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class RecipeCombinedPotions implements IRecipe
{

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn)
	{
		ItemStack tempStack = null;

		for ( int i = 0 ; i < inv.getSizeInventory() ; i++ )
		{
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null)
			{

				Item item = stack.getItem();
				if (item != Items.TIPPED_ARROW && item != Items.POTIONITEM && item != Items.SPLASH_POTION && item != Items.LINGERING_POTION) return false;

				if (tempStack == null)
				{
					tempStack = stack.copy();
				}
				else
				{
					if (!ItemStack.areItemsEqual(tempStack, stack)) return false;
				}

			}
		}

		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack outputStack = null;
		if (countSlotsNotEmpty(inv) <= 1) return outputStack;

		Collection<PotionEffect> effects = new ArrayList<PotionEffect>();

		for ( int i = 0 ; i < inv.getSizeInventory() ; i++ )
		{
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null)
			{
				if (outputStack == null) outputStack = new ItemStack(stack.getItem(), 1, 0);
				effects.addAll(PotionUtils.getEffectsFromStack(stack));
				PotionUtils.getPotionFromItem(stack);
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
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv)
	{
		return new ItemStack[9];
	}


	private static int countSlotsNotEmpty(IInventory inventory)
	{
		int result = 0;
		for ( int i = 0 ; i < inventory.getSizeInventory() ; i++ )
		{
			if (inventory.getStackInSlot(i) != null) result++;
		}
		return result;
	}

}
