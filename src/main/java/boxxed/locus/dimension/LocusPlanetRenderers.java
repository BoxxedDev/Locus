package boxxed.locus.dimension;

import boxxed.locus.client.SpaceDimensionEffects;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class LocusPlanetRenderers extends SimpleJsonResourceReloadListener {
    public LocusPlanetRenderers() {
        super(new Gson(), "planet_renderers");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager man, ProfilerFiller profiler) {
        Map<ResourceKey<Level>, SpaceDimensionEffects> effects = new HashMap<>();
        object.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "planets");
            effects.put(LocusDimensions.MOON_LEVEL, new SpaceDimensionEffects());
        });
    }
}
