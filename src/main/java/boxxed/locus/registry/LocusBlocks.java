package boxxed.locus.registry;

import boxxed.locus.Locus;
import boxxed.locus.content.blockEntities.TestBE;
import boxxed.locus.content.blocks.HorDirBlock;
import boxxed.locus.content.blocks.TestBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Function;

import static boxxed.locus.Locus.REGISTRATE;

public class LocusBlocks {
    public static final BlockEntry<TestBlock> TEST_BLOCK = REGISTRATE.block("test_block", TestBlock::new)
            .simpleItem()
            .register();

    public static void init() {}
}
