package io.github.rypofalem.mushroommanipulatormadness;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class MaterialWithData {
	Material material;
	byte data;
	boolean usePlacedBlockData = false;
	
	MaterialWithData(Material blockMaterial, byte blockData){
		this.material = blockMaterial;
		this.data = blockData;
	}
	
	public MaterialWithData(Material blockMaterial, boolean usePlacedBlockData) {
		this.material = blockMaterial;
		this.usePlacedBlockData = usePlacedBlockData;
	}
	
	@SuppressWarnings("deprecation")
	public BlockState getBlockStateWithData(Block block){
		BlockState blockState = block.getState();
		byte startData = blockState.getRawData();
		blockState.setType(material);
		if(usePlacedBlockData){
			blockState.setRawData(startData);
		}else{
			blockState.setRawData(data);
		}
		return blockState;
	}
}
