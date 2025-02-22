package com.dudu.sieve.items;

import net.minecraft.CreativeTabs;
import net.minecraft.IconRegister;
import net.minecraft.Item;
import net.minecraft.ItemStack;

public class ItemMesh extends Item {

	public ItemMesh(int id) {
		super(id,"sieve:mesh");
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "mesh";
	}
	
	@Override
	public String getUnlocalizedName(ItemStack item)
	{
		return "mesh";
	}
	
	@Override
	public void registerIcons(IconRegister register)
	{
		this.itemIcon = register.registerIcon("sieve:mesh");
	}
}
