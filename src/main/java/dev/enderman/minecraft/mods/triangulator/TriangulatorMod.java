package dev.enderman.minecraft.mods.triangulator;

import java.util.ArrayList;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.math.Vec3d;
import dev.enderman.minecraft.mods.triangulator.events.EyeOfEnderBreakListener;
import dev.enderman.minecraft.mods.triangulator.events.EyeOfEnderSpawnListener;
import dev.enderman.minecraft.mods.triangulator.events.EyeOfEnderThrowListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class TriangulatorMod implements ModInitializer {

	public static final String MOD_ID = "triangulator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private final HashMap<Vec3d, EyeOfEnderEntity> startingPositionEyeMap = new HashMap<>();
	private final ArrayList<Line> eyeOfEnderDirections = new ArrayList<>();

	public HashMap<Vec3d, EyeOfEnderEntity> getStartingPositionEyeMap() {
		return startingPositionEyeMap;
	}

	public ArrayList<Line> getEyeOfEnderDirections() {
		return eyeOfEnderDirections;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("[Triangulator]: Initialising Triangulator mod.");

		new EyeOfEnderThrowListener(this);
		new EyeOfEnderSpawnListener(this);
		new EyeOfEnderBreakListener(this);
	}
}
