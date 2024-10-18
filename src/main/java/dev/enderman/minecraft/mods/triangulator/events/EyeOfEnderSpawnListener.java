package dev.enderman.minecraft.mods.triangulator.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.math.Vec3d;
import dev.enderman.minecraft.mods.triangulator.Triangulator;
import org.jetbrains.annotations.NotNull;

public class EyeOfEnderSpawnListener {

    private final Triangulator triangulator;

    public EyeOfEnderSpawnListener(Triangulator triangulator) {
        this.triangulator = triangulator;
    }

    public void registerListener() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            Triangulator.LOGGER.debug("Entity {} has been loaded in {}.", entity, world);;

            if (entity instanceof EyeOfEnderEntity eyeOfEnder) {
                Triangulator.LOGGER.debug("Entity is an eye of ender entity.");
                onEyeOfEnderSpawn(eyeOfEnder);
            }
        });
    }

    private void onEyeOfEnderSpawn(@NotNull EyeOfEnderEntity eyeOfEnder) {
        double x = eyeOfEnder.getX();
        double y = eyeOfEnder.getY();
        double z = eyeOfEnder.getZ();

        Vec3d position = new Vec3d(x, y, z);

        Triangulator.LOGGER.debug("Eye of ender spawned at {}, {}, {}.", x, y, z);

        Triangulator.LOGGER.debug("Comparing location of eye of ender to known throwing locations.");

        for (Vec3d vector : triangulator.startingPositionEyeMap.keySet()) {
            Triangulator.LOGGER.debug("Comparing {} to {}", position, vector);

            if (vector.x == position.x && vector.z == position.z) {
                Triangulator.LOGGER.debug("Location vectors are equivalent, setting data for the eye of ender.");

                triangulator.startingPositionEyeMap.put(vector, eyeOfEnder);

                Triangulator.LOGGER.debug("{}: {}", vector, triangulator.startingPositionEyeMap.get(vector));
            }
        }
    }
}