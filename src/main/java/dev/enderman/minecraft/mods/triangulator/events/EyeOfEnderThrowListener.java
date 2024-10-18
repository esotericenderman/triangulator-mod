package dev.enderman.minecraft.mods.triangulator.events;

import dev.enderman.minecraft.mods.triangulator.TriangulatorMod;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class EyeOfEnderThrowListener {

    private final TriangulatorMod mod;

    public EyeOfEnderThrowListener(TriangulatorMod mod) {
        this.mod = mod;

        registerListener();
    }

    public void registerListener() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            TriangulatorMod.LOGGER.debug("Player {} used item in hand {} in world {}.", player, hand, world);

            ItemStack item = player.getStackInHand(hand);

            TriangulatorMod.LOGGER.debug("item = {}", item);

            Item itemType = item.getItem();

            TriangulatorMod.LOGGER.debug("itemType = {}", itemType);

            if (itemType == Items.ENDER_EYE) {
                TriangulatorMod.LOGGER.debug("The item is in an eye of ender");
                onEyeOfEnderThrow(item, player);
            }

            return TypedActionResult.pass(item);
        });
    }

    private void onEyeOfEnderThrow(ItemStack eyeOfEnderItem, @NotNull PlayerEntity player) {
        TriangulatorMod.LOGGER.debug("{} threw an eye of ender.", player.getName());

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        Vec3d location = new Vec3d(x, y, z);

        TriangulatorMod.LOGGER.debug("{}'s coordinates: {}.", player.getName(), location);

        mod.startingPositionEyeMap.put(location, null);
    }
}
