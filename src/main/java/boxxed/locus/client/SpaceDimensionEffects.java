package boxxed.locus.client;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpaceDimensionEffects extends DimensionSpecialEffects {
    public SpaceDimensionEffects() {
        super(Float.NaN, false, SkyType.NORMAL, true, false);
    }

    public Vec3 getBrightnessDependentFogColor(Vec3 p_108908_, float p_108909_) {
        return p_108908_.multiply((p_108909_ * 0.94F + 0.06F), (p_108909_ * 0.94F + 0.06F), (p_108909_ * 0.91F + 0.09F));
    }

    public boolean isFoggyAt(int p_108905_, int p_108906_) {
        return false;
    }
}
