package de.redfox.steckbrief.manager.blindness;

import de.redfox.steckbrief.utils.ReflectionSession;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

class CustomGenerator extends ChunkGenerator {
    public World world;

    public CustomGenerator() {
        WorldCreator a = new WorldCreator("test");
        a.environment(World.Environment.THE_END);
        a.generateStructures(false);

        new ReflectionSession(a).setField("generator", this);
        world = Bukkit.createWorld(a);
    }

    @Override
    public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull ChunkGenerator.ChunkData chunkData) {
        chunkData.setRegion(x, worldInfo.getMinHeight(), z, x + 16, worldInfo.getMaxHeight(), z + 16, Material.AIR);
        super.generateBedrock(worldInfo, random, x, z, chunkData);
    }

    @Override
    public void generateCaves(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull ChunkGenerator.ChunkData chunkData) {
        chunkData.setRegion(x, worldInfo.getMinHeight(), z, x + 16, worldInfo.getMaxHeight(), z + 16, Material.AIR);
        super.generateCaves(worldInfo, random, x, z, chunkData);
    }

    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull ChunkGenerator.ChunkData chunkData) {
        chunkData.setRegion(x, worldInfo.getMinHeight(), z, x + 16, worldInfo.getMaxHeight(), z + 16, Material.AIR);
        super.generateNoise(worldInfo, random, x, z, chunkData);
    }

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull ChunkGenerator.ChunkData chunkData) {
        chunkData.setRegion(x, worldInfo.getMinHeight(), z, x + 16, worldInfo.getMaxHeight(), z + 16, Material.AIR);
        super.generateSurface(worldInfo, random, x, z, chunkData);
    }
}
