package boxxed.locus.editor;

import foundry.veil.api.client.editor.SingleWindowEditor;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.shader.definition.ShaderPreDefinitions;
import imgui.ImGui;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.FolderRepositorySource;

public class PlanetEditor extends SingleWindowEditor {

    @Override
    protected void renderComponents() {
        ShaderPreDefinitions definitions = VeilRenderSystem.renderer().getShaderDefinitions();

        if (ImGui.beginTabBar("Planet Editor")) {
            for (Planets planet : Planets.values()) {
                if (ImGui.beginTabItem(planet.name)) {
                    WavelengthRGB(definitions, planet.name, planet.WLR, planet.WLG, planet.WLB);
                }
            }

            ImGui.endTabBar();
        }
    }

    @Override
    public String  getDisplayName() {
        return "Locus Planet Editor";
    }

    public void WavelengthRGB(ShaderPreDefinitions definitions, String name, int[] WLR, int[] WLG, int[] WLB) {
        if (ImGui.dragInt("Red Wavelength", WLR, 1, 400, Integer.MAX_VALUE)) {
            definitions.define("WAVELENGTH_RED_" + name.toUpperCase(), String.valueOf(WLR[0]));
        }
        if (ImGui.dragInt("Green Wavelength", WLG, 1, 400, Integer.MAX_VALUE)) {
            definitions.define("WAVELENGTH_GREEN_" + name.toUpperCase(), String.valueOf(WLG[0]));
        }
        if (ImGui.dragInt("Blue Wavelength", WLB, 1, 400, Integer.MAX_VALUE)) {
            definitions.define("WAVELENGTH_BLUE_" + name.toUpperCase(), String.valueOf(WLB[0]));
        }

        ImGui.endTabItem();
    }

    private enum Planets {
        EARTH("Earth");

        private final String name;
        private final int[] WLR = new int[]{700};
        private final int[] WLG = new int[]{530};
        private final int[] WLB = new int[]{440};

        Planets(String name) {
            this.name = name;
        }
    }
}
