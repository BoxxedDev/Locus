package boxxed.locus.space;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public record Star(Component name, Vec3 position, float radius, Vec3 color) {
    public static final Codec<Star> CODEC = RecordCodecBuilder.create(starInstance ->
            starInstance.group(
                    ExtraCodecs.COMPONENT.fieldOf("name").forGetter(Star::name),
                    Vec3.CODEC.fieldOf("position").forGetter(Star::position),
                    Codec.FLOAT.fieldOf("radius").forGetter(Star::radius),
                    Vec3.CODEC.fieldOf("color").forGetter(Star::color)
            ).apply(starInstance, Star::new)
    );
}
