package boxxed.locus;

import boxxed.locus.client.AtmosphereHandler;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostProcessingManager;
import foundry.veil.forge.event.ForgeVeilRendererEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.BiConsumer;

import static boxxed.locus.Locus.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
@SuppressWarnings({"unchecked"})
public class LocusClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
    }

    @SubscribeEvent
    public static void onVeilRenderer(ForgeVeilRendererEvent event) {
        if (VeilRenderSystem.renderer() == null) {
            Locus.LOGGER.info("veilrendersystwm is null");
            return;}
        PostProcessingManager manager = VeilRenderSystem.renderer().getPostProcessingManager();
        manager.add(Locus.path("atmosphere"));
        //AtmosphereHandler.update();
    }

    //@SubscribeEvent
    //public static void onAddReloadListener(BiConsumer<ResourceLocation, PreparableReloadListener> consumer) {
        //consumer.accept(Locus.path("planet_renderers"));
    //}

}
