package dev.enderman.minecraft.mods.triangulator.utility;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatUtil {
    public static void sendMessage(String message) {
        try (MinecraftClient minecraftClient = MinecraftClient.getInstance()) {
            minecraftClient.inGameHud.getChatHud().addMessage(Text.of(message));
        }
    }
}
