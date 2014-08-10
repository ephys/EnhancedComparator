package nf.fr.ephys.enhancedcomparator.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import nf.fr.ephys.enhancedcomparator.EnhancedComparator;
import nf.fr.ephys.enhancedcomparator.api.IComparatorHandler;
import nf.fr.ephys.enhancedcomparator.core.comparator.ComparatorOverrideRegistry;

public class BlockComparator extends BlockRedstoneComparator {
	public BlockComparator(boolean isPowered) {
		super(isPowered);

		disableStats();
	}

	protected int getInputStrength(World world, int x, int y, int z, int side) {
		int orientation = getDirection(side);
		int behindX = x + Direction.offsetX[orientation];
		int behindZ = z + Direction.offsetZ[orientation];

		Integer override = getBlockOverride(world, behindX, y, behindZ, Direction.rotateOpposite[orientation]);
		if (override != null) return override;

		Block block = world.getBlock(behindX, y, behindZ);
		int strength = getUncomparableInputStrength(world, x, y, z, side);
		if (strength < 15 && block.isNormalCube()) {
			behindX += Direction.offsetX[orientation];
			behindZ += Direction.offsetZ[orientation];

			override = getBlockOverride(world, behindX, y, behindZ, Direction.rotateOpposite[orientation]);
			if (override != null) return override;
		}

		return strength;
	}

	public static int getUncomparableInputStrength(World world, int x, int y, int z, int side) {
		int i1 = getDirection(side);
		int j1 = x + Direction.offsetX[i1];
		int k1 = z + Direction.offsetZ[i1];
		int l1 = world.getIndirectPowerLevelTo(j1, y, k1, Direction.directionToFacing[i1]);
		return l1 >= 15 ? l1 : Math.max(l1, world.getBlock(j1, y, k1) == Blocks.redstone_wire ? world.getBlockMetadata(j1, y, k1) : 0);
	}

	public static Integer getBlockOverride(World world, int x, int y, int z, int side) {
		Block block = world.getBlock(x, y, z);

		if (block.hasComparatorInputOverride())
			return block.getComparatorInputOverride(world, x, y, z, side);

		IComparatorHandler handler = ComparatorOverrideRegistry.getOverride(block);

		if (handler != null)
			return handler.getComparatorInput(block, world, x, y, z, side);

		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof IInventory)
			return Container.calcRedstoneFromInventory((IInventory) te);

		return null;
	}
}
