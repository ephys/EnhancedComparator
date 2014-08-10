package nf.fr.ephys.enhancedcomparator.core.comparator;

import net.minecraft.block.Block;
import nf.fr.ephys.enhancedcomparator.api.IComparatorHandler;

import java.util.HashMap;
import java.util.Map;

public class ComparatorOverrideRegistry {
	private static Map<Block, IComparatorHandler> overrides = new HashMap<>();

	public static boolean addOverride(Block block, IComparatorHandler handler) {
		if (overrides.containsKey(block)) {
			return false;
		}

		overrides.put(block, handler);

		return true;
	}

	public static IComparatorHandler getOverride(Block block) {
		return overrides.get(block);
	}
}