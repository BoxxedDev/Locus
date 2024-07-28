package boxxed.locus.space;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.Vec3;

public record Planet(int id, Component name, Vec3 position, float radius, float orbitRadius, ResourceLocation texture, Atmosphere atmosphere) {
    public static final Codec<Planet> CODEC = RecordCodecBuilder.create(planetInstance ->
            planetInstance.group(
                    Codec.INT.fieldOf("id").forGetter(Planet::id),
                    ExtraCodecs.COMPONENT.fieldOf("name").forGetter(Planet::name),
                    Vec3.CODEC.fieldOf("position").forGetter(Planet::position),
                    Codec.FLOAT.fieldOf("radius").forGetter(Planet::radius),
                    Codec.FLOAT.fieldOf("orbit_radius").forGetter(Planet::orbitRadius),
                    ResourceLocation.CODEC.fieldOf("planet_texture").forGetter(Planet::texture),
                    Atmosphere.CODEC.fieldOf("atmosphere").forGetter(Planet::atmosphere)
            ).apply(planetInstance, Planet::new)
    );
}
