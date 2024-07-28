package boxxed.locus.client;

import boxxed.locus.Locus;
import boxxed.locus.util.LocusUtil;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostProcessingManager;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import foundry.veil.api.resource.VeilResourceManager;
import foundry.veil.platform.VeilEventPlatform;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class AtmosphereHandler {
    private static final ResourceLocation CUSTOM_POST_PIPELINE = LocusUtil.path("atmosphere");
    private static final ResourceLocation ATMOSPHERE_SHADER = LocusUtil.path("atmosphere");

    public static void update() {
        VeilEventPlatform.INSTANCE.preVeilPostProcessing((pipelineName, pipeline, context) -> {
            if (CUSTOM_POST_PIPELINE.equals(pipelineName)) {
                ShaderProgram shader = context.getShader(ATMOSPHERE_SHADER);
                if (shader != null) {
                    shader.setVector("dirToSun", (float) Math.sin(Util.getEpochMillis()/1000f)*3,0,(float) Math.sin(Util.getEpochMillis()/1000f)*3);
                }
            }
        });
    }
}
