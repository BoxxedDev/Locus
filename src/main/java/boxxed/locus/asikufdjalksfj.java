package boxxed.locus;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostProcessingManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class asikufdjalksfj extends Block {
    private ResourceLocation ATMOSPHERE_SHADER_LOCATION = new ResourceLocation(Locus.MODID, "pinwheel/shaders/post/atmosphere.json");

    public asikufdjalksfj(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
        PostProcessingManager man = VeilRenderSystem.renderer().getPostProcessingManager();

        man.add(ATMOSPHERE_SHADER_LOCATION);

        super.onPlace(p_60566_, p_60567_, p_60568_, p_60569_, p_60570_);
    }

    @Override
    public void onRemove(BlockState p_60515_, Level p_60516_, BlockPos p_60517_, BlockState p_60518_, boolean p_60519_) {
        PostProcessingManager man = VeilRenderSystem.renderer().getPostProcessingManager();

        man.remove(ATMOSPHERE_SHADER_LOCATION);

        super.onRemove(p_60515_, p_60516_, p_60517_, p_60518_, p_60519_);
    }
}
