package nf.fr.ephys.enhancedcomparator.api;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public interface IComparatorHandler {
	public int getComparatorInput(Block block, World world, int x, int y, int z, int side);
}
