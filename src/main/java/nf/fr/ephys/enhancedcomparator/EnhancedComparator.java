package nf.fr.ephys.enhancedcomparator;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemStack;
import nf.fr.ephys.cookiecore.helpers.RegistryHelper;
import nf.fr.ephys.enhancedcomparator.api.IComparatorHandler;
import nf.fr.ephys.enhancedcomparator.block.BlockComparator;
import nf.fr.ephys.enhancedcomparator.core.comparator.BlockAnvilComparator;
import nf.fr.ephys.enhancedcomparator.core.comparator.BlockNoteHandler;
import nf.fr.ephys.enhancedcomparator.core.comparator.ComparatorOverrideRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "enhancedcomparator", version = "0.0.2", dependencies = "required-after:cookiecore@[1.3.0,)")
public class EnhancedComparator {
	public static Logger LOGGER = LogManager.getLogger("enhancedcomparator");

	@EventHandler
	public void init(FMLInitializationEvent event) {
		Block comparator_powered = new BlockComparator(true).setHardness(0.0F).setStepSound(Block.soundTypeWood).setBlockName("comparator").setBlockTextureName("comparator_on");
		Block comparator_unpowered = new BlockComparator(false).setHardness(0.0F).setLightLevel(0.625F).setStepSound(Block.soundTypeWood).setBlockName("comparator").setBlockTextureName("comparator_off");

		RegistryHelper.overwriteBlock("minecraft:powered_comparator", comparator_powered);
		RegistryHelper.overwriteBlock("minecraft:unpowered_comparator", comparator_unpowered);
		RegistryHelper.overwriteReedBlock((ItemReed) Items.comparator, comparator_unpowered);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		IComparatorHandler anvilComparator = new BlockAnvilComparator();
		ComparatorOverrideRegistry.addOverride(Blocks.noteblock, new BlockNoteHandler());
		ComparatorOverrideRegistry.addOverride(Blocks.anvil, anvilComparator);

		ItemStack rcAnvil = RegistryHelper.getItemStack("Railcraft:tile.railcraft.anvil");
		if (rcAnvil != null && rcAnvil.getItem() instanceof ItemBlock) {
			Block steelAnvil = ((ItemBlock) rcAnvil.getItem()).field_150939_a;

			ComparatorOverrideRegistry.addOverride(steelAnvil, anvilComparator);
		} else {
			LOGGER.info("Failed to load Railcraft's steel anvil. No support for you I guess :(");
		}
	}
}