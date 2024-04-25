package boxxed.locus;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.VeilRenderer;
import foundry.veil.api.client.render.post.PostProcessingManager;
import foundry.veil.forge.event.ForgeVeilRendererEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static boxxed.locus.Locus.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LocusClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
    }

    @SubscribeEvent
    public static void onVeilRenderer(ForgeVeilRendererEvent event) {
        if (VeilRenderSystem.renderer() == null) return;
        PostProcessingManager manager = VeilRenderSystem.renderer().getPostProcessingManager();
        manager.add(new ResourceLocation(MODID, "inverted"));
    }
}
