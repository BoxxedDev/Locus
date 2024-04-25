package boxxed.locus.registry;

import boxxed.locus.Locus;
import boxxed.locus.asikufdjalksfj;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;

public class LocusBlocks {
    public static final BlockEntry<asikufdjalksfj> WOWZA = Locus.REGISTRATE.block("wowza", asikufdjalksfj::new)
            .simpleItem()
            .register();

    public static void register() {}
}
