package nf.fr.ephys.enhancedcomparator.core.comparator;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.world.World;
import nf.fr.ephys.enhancedcomparator.api.IComparatorHandler;

public class BlockNoteHandler implements IComparatorHandler {
	@Override
	public int getComparatorInput(Block block, World world, int x, int y, int z, int side) {
		TileEntityNote note = (TileEntityNote) world.getTileEntity(x, y, z);

		if (note == null) return 0;

		// 25 notes, 15 redstone levels
		return (int) (note.note / 25F * 15);
	}
}