package boxxed.locus.registry;

import boxxed.locus.space.Planet;
import boxxed.locus.space.PlanetarySystem;
import boxxed.locus.space.Star;
import boxxed.locus.util.LocusUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DataPackRegistryEvent;

import static boxxed.locus.Locus.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LocusSpaceDynamicRegistry {
    public static final ResourceKey<Registry<PlanetarySystem>> PLANETARY_SYSTEM_KEY = ResourceKey.createRegistryKey(LocusUtil.path("planetary_systems/systems"));
    public static final ResourceKey<Registry<Planet>> PLANET_KEY = ResourceKey.createRegistryKey(LocusUtil.path("planetary_systems/planets"));
    public static final ResourceKey<Registry<Star>> STAR_KEY = ResourceKey.createRegistryKey(LocusUtil.path("planetary_systems/stars"));

    @SubscribeEvent
    public static void registerDataPackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                PLANET_KEY,
                Planet.CODEC,
                Planet.CODEC
        );
        event.dataPackRegistry(
                STAR_KEY,
                Star.CODEC,
                Star.CODEC
        );
        event.dataPackRegistry(
                PLANETARY_SYSTEM_KEY,
                PlanetarySystem.CODEC,
                PlanetarySystem.CODEC
        );
    }
}
