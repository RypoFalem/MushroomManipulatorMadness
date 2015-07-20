package io.github.rypofalem.mushroommanipulatormadness;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MushroomManipulatorMadnessPlugin extends JavaPlugin implements Listener{

	String stalkName = "stalk";
	String poreName = "pore";
	ArrayList<BlockTransform> blockTransforms;

	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		blockTransforms = new ArrayList<BlockTransform>();
		loadConfig();
	}

	@SuppressWarnings("deprecation")
	private void loadConfig() {
		this.saveDefaultConfig();
		HashSet<String> blockSet = (HashSet<String>) getConfig().getKeys(false);
		for(String uniqueName : blockSet){
			uniqueName = uniqueName.trim();
			if(!getConfig().isString(uniqueName +".name")){
				getServer().getLogger().info(uniqueName +".name" + "is missing or invalid and couldn't be loaded");
				continue;
			}
			if(!getConfig().isInt(uniqueName +".fromblocktype")){
				getServer().getLogger().info(uniqueName +".fromblocktype" + "is missing or invalid and couldn't be loaded");
				continue;
			}
			if(!getConfig().isInt(uniqueName +".toblocktype")){
				getServer().getLogger().info(uniqueName +".toblocktype" + "is missing or invalid and couldn't be loaded");
				continue;
			}

			String name = getConfig().getString(uniqueName +".name");
			int fromBlockType = getConfig().getInt(uniqueName +".fromblocktype");
			int toBlockType = getConfig().getInt(uniqueName +".toblocktype");
			Material from = Material.getMaterial(fromBlockType);
			MaterialWithData to;
			if(getConfig().isInt(uniqueName +".toblockdata")){
				byte toBlockData = (byte)getConfig().getInt(uniqueName +".toblockdata");
				to = new MaterialWithData(Material.getMaterial(toBlockType), toBlockData);
			}else{
				if(getConfig().isBoolean(uniqueName +".useplacedblockdata")){
					to = new MaterialWithData(Material.getMaterial(toBlockType), getConfig().getBoolean(uniqueName +".useplacedblockdata"));
				}else{
					getServer().getLogger().info(uniqueName +".toblockdata" + "is missing or invalid and " + uniqueName + ".useplacedblockdata is not set to true.");
					continue;
				}
			}
			BlockTransform bt = new BlockTransform(from, to, name);
			blockTransforms.add(bt);
		}
	}

	@EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void afterMushroomPlace(BlockPlaceEvent e){
		if(e.getItemInHand() != null && e.getBlock() != null){
			for(BlockTransform transform : blockTransforms){
				transform.attemptReplaceBlock(e.getBlock(), e.getItemInHand());
			}
		}
	}
}