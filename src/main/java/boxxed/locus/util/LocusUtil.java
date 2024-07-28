package boxxed.locus.util;

import boxxed.locus.Locus;
import net.minecraft.resources.ResourceLocation;

public class LocusUtil {
    public static ResourceLocation path(String path) {
        return new ResourceLocation(Locus.MODID, path);
    }
}
