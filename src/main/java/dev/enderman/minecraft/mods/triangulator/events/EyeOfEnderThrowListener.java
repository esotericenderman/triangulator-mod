package dev.enderman.minecraft.mods.triangulator.events;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import dev.enderman.minecraft.mods.triangulator.Triangulator;
import org.jetbrains.annotations.NotNull;

public class EyeOfEnderThrowListener {

    private final Triangulator triangulator;

    public EyeOfEnderThrowListener(Triangulator triangulator) {
        this.triangulator = triangulator;
    }

    public void registerListener() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            Triangulator.LOGGER.debug("Player {} used item in hand {} in world {}.", player, hand, world);

            ItemStack item = player.getStackInHand(hand);

            Triangulator.LOGGER.debug("item = {}", item);

            Item itemType = item.getItem();

            Triangulator.LOGGER.debug("itemType = {}", itemType);

            if (itemType == Items.ENDER_EYE) {
                Triangulator.LOGGER.debug("The item is in an eye of ender");
                onEyeOfEnderThrow(item, player);
            }

            return TypedActionResult.pass(item);
        });
    }

    private void onEyeOfEnderThrow(ItemStack eyeOfEnderItem, @NotNull PlayerEntity player) {
        Triangulator.LOGGER.debug("{} threw an eye of ender.", player.getName());

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        Vec3d location = new Vec3d(x, y, z);

        Triangulator.LOGGER.debug("{}'s coordinates: {}.", player.getName(), location);

        triangulator.startingPositionEyeMap.put(location, null);
    }
}
