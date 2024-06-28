package boxxed.locus.editor;

import foundry.veil.api.client.editor.SingleWindowEditor;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.shader.definition.ShaderPreDefinitions;
import imgui.ImGui;

public class PlanetEditor extends SingleWindowEditor {


    @Override
    protected void renderComponents() {
        ShaderPreDefinitions definitions = VeilRenderSystem.renderer().getShaderDefinitions();

        if (ImGui.beginTabBar("Planet Editor")) {

        }
    }

    @Override
    public String getDisplayName() {
        return "Locus Planet Editor";
    }
}
