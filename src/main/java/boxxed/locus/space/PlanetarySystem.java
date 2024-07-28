package boxxed.locus.space;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

import java.util.List;

public record PlanetarySystem(Component name, ResourceLocation star, List<ResourceLocation> planets) {
    public static final Codec<PlanetarySystem> CODEC = RecordCodecBuilder.create(planetarySystemInstance ->
            planetarySystemInstance.group(
                    ExtraCodecs.COMPONENT.fieldOf("name").forGetter(PlanetarySystem::name),
                    ResourceLocation.CODEC.fieldOf("star").forGetter(PlanetarySystem::star),
                    ResourceLocation.CODEC.listOf().fieldOf("planets").forGetter(PlanetarySystem::planets)
            ).apply(planetarySystemInstance, PlanetarySystem::new)
    );
}
