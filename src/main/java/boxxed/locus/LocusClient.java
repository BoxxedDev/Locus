package boxxed.locus;

import boxxed.locus.client.SpaceDimensionEffects;
import boxxed.locus.dimension.LocusDimensionTypes;
import boxxed.locus.editor.PlanetEditor;
import boxxed.locus.util.LocusUtil;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostProcessingManager;
import foundry.veil.forge.event.ForgeVeilRendererEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static boxxed.locus.Locus.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
@SuppressWarnings({"unchecked"})
public class LocusClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {

    }

    @SubscribeEvent
    public static void onRegisterDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(LocusDimensionTypes.SPACE_EFFECTS, new SpaceDimensionEffects());
    }

    @SubscribeEvent
    public static void onVeilForgeRenderer(ForgeVeilRendererEvent event) {
        event.getRenderer().getEditorManager().add(new PlanetEditor());
        if (VeilRenderSystem.renderer() == null) {
            Locus.LOGGER.info("veilrendersystwm is null");
            return;}
        PostProcessingManager manager = VeilRenderSystem.renderer().getPostProcessingManager();
        manager.add(LocusUtil.path("atmosphere"));
        //AtmosphereHandler.update();
    }


    //@SubscribeEvent
    //public static void onAddReloadListener(BiConsumer<ResourceLocation, PreparableReloadListener> consumer) {
        //consumer.accept(Locus.path("planet_renderers"));
    //}

}
