package dev.enderman.minecraft.mods.triangulator.events;

import dev.enderman.minecraft.mods.triangulator.TriangulatorMod;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class EyeOfEnderSpawnListener {

    private final TriangulatorMod mod;

    public EyeOfEnderSpawnListener(TriangulatorMod mod) {
        this.mod = mod;

        registerListener();
    }

    public void registerListener() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            TriangulatorMod.LOGGER.debug("Entity {} has been loaded in {}.", entity, world);;

            if (entity instanceof EyeOfEnderEntity eyeOfEnder) {
                TriangulatorMod.LOGGER.info("Entity is an eye of ender entity.");
                onEyeOfEnderSpawn(eyeOfEnder);
            }
        });
    }

    private void onEyeOfEnderSpawn(@NotNull EyeOfEnderEntity eyeOfEnder) {
        double x = eyeOfEnder.getX();
        double y = eyeOfEnder.getY();
        double z = eyeOfEnder.getZ();

        Vec3d position = new Vec3d(x, y, z);

        TriangulatorMod.LOGGER.info("Eye of ender spawned at {}, {}, {}.", x, y, z);

        TriangulatorMod.LOGGER.info("Comparing location of eye of ender to known throwing locations.");

        for (Vec3d vector : mod.getStartingPositionEyeMap().keySet()) {
            TriangulatorMod.LOGGER.info("Comparing {} to {}", position, vector);

            if (vector.x == position.x && vector.z == position.z) {
                TriangulatorMod.LOGGER.info("Location vectors are equivalent, setting data for the eye of ender.");

                mod.getStartingPositionEyeMap().put(vector, eyeOfEnder);

                TriangulatorMod.LOGGER.info("{}: {}", vector, mod.getStartingPositionEyeMap().get(vector));
            }
        }
    }
}
