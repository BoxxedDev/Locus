package boxxed.locus.space;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Atmosphere(
        float size, float falloff, float density, float redWavelength, float greenWavelength, float blueWavelength, float brightness, float densityMul
) {
    public static final Codec<Atmosphere> CODEC = RecordCodecBuilder.create(atmosphereInstance ->
            atmosphereInstance.group(
                    Codec.FLOAT.fieldOf("size").forGetter(Atmosphere::size),
                    Codec.FLOAT.fieldOf("falloff").forGetter(Atmosphere::falloff),
                    Codec.FLOAT.fieldOf("density").forGetter(Atmosphere::density),
                    Codec.FLOAT.fieldOf("red_wavelength").forGetter(Atmosphere::redWavelength),
                    Codec.FLOAT.fieldOf("green_wavelength").forGetter(Atmosphere::greenWavelength),
                    Codec.FLOAT.fieldOf("blue_wavelength").forGetter(Atmosphere::blueWavelength),
                    Codec.FLOAT.fieldOf("brightness").forGetter(Atmosphere::brightness),
                    Codec.FLOAT.fieldOf("density_multiplier").forGetter(Atmosphere::densityMul)
            ).apply(atmosphereInstance, Atmosphere::new)
    );
}
