package dev.enderman.minecraft.mods.triangulator;

import net.fabricmc.api.ClientModInitializer;

public class TriangulatorClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Triangulator.LOGGER.info("[Triangulator]: Loading client mod.");
    }
}
