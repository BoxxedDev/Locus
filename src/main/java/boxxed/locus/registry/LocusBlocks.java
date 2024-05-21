package boxxed.locus.registry;

import boxxed.locus.content.blocks.TestBlock;
import com.tterrag.registrate.util.entry.BlockEntry;

import static boxxed.locus.Locus.REGISTRATE;

public class LocusBlocks {
    public static final BlockEntry<TestBlock> TEST_BLOCK = REGISTRATE.block("test_block", TestBlock::new)
            .simpleItem()
            .register();

    public static void init() {}
}
