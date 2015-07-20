package io.github.rypofalem.mushroommanipulatormadness;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class BlockTransform {
	Material original;
	String key;
	MaterialWithData replacement;
	
	BlockTransform(Material original, MaterialWithData replace, String key){
		this.original = original;
		this.replacement = replace;
		this.key = key;
	}
	
	public boolean attemptReplaceBlock(Block block, ItemStack heldItem){
		if(block.getType() == heldItem.getType() && block.getType() == original){
			if(heldItem.hasItemMeta() && heldItem.getItemMeta().hasDisplayName() && heldItem.getItemMeta().getDisplayName().equalsIgnoreCase(key)){
				return replacement.getBlockStateWithData(block).update(true);
			}
		}
		return false;
	}
}
