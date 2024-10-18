package dev.enderman.minecraft.mods.triangulator.events;

import dev.enderman.minecraft.mods.triangulator.TriangulatorMod;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.math.Vec3d;
import dev.enderman.minecraft.mods.triangulator.Line;
import dev.enderman.minecraft.mods.triangulator.Line.SameLineException;
import dev.enderman.minecraft.mods.triangulator.Line.ZeroVectorException;
import dev.enderman.minecraft.mods.triangulator.utility.MapUtility;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.Map;

public class EyeOfEnderBreakListener {

    private final TriangulatorMod mod;

    public EyeOfEnderBreakListener(TriangulatorMod mod) {
        this.mod = mod;

        registerListener();
    }

    public void registerListener() {
        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            TriangulatorMod.LOGGER.debug("Entity {} unloaded in world {}.", entity, world);
            if (entity instanceof EyeOfEnderEntity eyeOfEnder) {
                TriangulatorMod.LOGGER.debug("Entity is an eye of ender entity.");
                onEyeOfEnderSpawn(eyeOfEnder);
            }
        });
    }

    private void onEyeOfEnderSpawn(@NotNull EyeOfEnderEntity eyeOfEnder) {
        double x = eyeOfEnder.getX();
        double y = eyeOfEnder.getY();
        double z = eyeOfEnder.getZ();

        Vec3d endPosition = new Vec3d(x, y, z);

        Map<EyeOfEnderEntity, Vec3d> eyePositionMap = MapUtility.inverMap(mod.getStartingPositionEyeMap());
        Vec3d startingPosition = null;

        for (Map.Entry<EyeOfEnderEntity, Vec3d> entry : eyePositionMap.entrySet()) {
            EyeOfEnderEntity eye = entry.getKey();

            if (eye.getX() == eyeOfEnder.getX() && eye.getY() == eyeOfEnder.getY()) {
                startingPosition = entry.getValue();
                break;
            }
        }

        if (startingPosition == null) {
            return;
        }

        TriangulatorMod.LOGGER.debug("Eye of ender broken at {}.", endPosition);
        TriangulatorMod.LOGGER.debug("Its starting position was {}.", startingPosition);

        Vec3d difference = endPosition.subtract(startingPosition);

        Vector2d startingPoint = new Vector2d(startingPosition.x, startingPosition.z);
        Vector2d directionVector = new Vector2d(difference.x, difference.z);

        Line eyeOfEnderDirection;

        try {
            eyeOfEnderDirection = new Line(startingPoint, directionVector);
        } catch (ZeroVectorException exception) {
            exception.printStackTrace();
            return;
        }

        TriangulatorMod.LOGGER.info("The line l of the path of the eye of ender is defined as follows: l = {}", eyeOfEnderDirection);

        ArrayList<Line> eyeOfEnderDirections = mod.getEyeOfEnderDirections();

        if (!eyeOfEnderDirections.contains(eyeOfEnderDirection)) {
            for (Line direction : eyeOfEnderDirections) {
                Vector2d intersection;

                try {
                    intersection = direction.getIntersectionPoint(eyeOfEnderDirection);
                } catch (SameLineException exception) {
                    continue;
                }

                if (intersection == null) {
                    continue;
                }

                TriangulatorMod.LOGGER.info("Intersection of the two lines {} & {} found at {}", eyeOfEnderDirection, direction, intersection);
            }

            mod.getEyeOfEnderDirections().add(eyeOfEnderDirection);
        }
    }
}
