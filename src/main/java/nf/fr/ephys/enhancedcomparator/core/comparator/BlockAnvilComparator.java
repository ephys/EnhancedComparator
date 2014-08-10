package nf.fr.ephys.enhancedcomparator.core.comparator;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import nf.fr.ephys.enhancedcomparator.api.IComparatorHandler;

public class BlockAnvilComparator implements IComparatorHandler {
	@Override
	public int getComparatorInput(Block block, World world, int x, int y, int z, int side) {
		return block.damageDropped(world.getBlockMetadata(x, y, z));
	}
}
