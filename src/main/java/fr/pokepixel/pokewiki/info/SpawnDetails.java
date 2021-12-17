package fr.pokepixel.pokewiki.info;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.pokemon.SpawnInfoPokemon;
import com.pixelmonmod.pixelmon.api.world.WeatherType;
import com.pixelmonmod.pixelmon.api.world.WorldTime;
import fr.pokepixel.pokewiki.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static fr.pokepixel.pokewiki.config.ChatColor.translateAlternateColorCodes;

public class SpawnDetails {


    public static String createPokeDetails(SpawnInfoPokemon spawnInfo, ConfigCategory langspawn){
        String txt = "";
        if(spawnInfo.locationTypes.size()>0){
            txt = txt.concat(translateAlternateColorCodes('&',langspawn.get("typeoflocation").getString().replaceFirst("%spawnlocation%",String.join(", ",spawnInfo.stringLocationTypes))) + "\n");
        }
        int minlevel = spawnInfo.getPokemonSpec().level != null ? spawnInfo.getPokemonSpec().level : spawnInfo.minLevel;
        int maxlevel = spawnInfo.getPokemonSpec().level != null ? spawnInfo.getPokemonSpec().level : spawnInfo.maxLevel;
        txt = txt.concat(translateAlternateColorCodes('&',langspawn.get("minlevel").getString().replaceFirst("%minlevel%",String.valueOf(minlevel))) + "\n");
        txt = txt.concat(translateAlternateColorCodes('&',langspawn.get("maxlevel").getString().replaceFirst("%maxlevel%",String.valueOf(maxlevel))) + "\n");
        //ReflectionHelper.
        //spawnInfo.species.getBaseStats().getType1().
        if (spawnInfo.heldItems != null){
            List<String> itemName = Lists.newArrayList();
            spawnInfo.heldItems.forEach(jsonItemStack -> {
                itemName.add(TextFormatting.DARK_AQUA+jsonItemStack.getItemStack().getDisplayName());
            });
            txt = txt.concat(translateAlternateColorCodes('&',langspawn.get("helditems").getString().replaceFirst("%helditems%",String.join(TextFormatting.YELLOW+", ",itemName))) + "\n");
        }

        if (spawnInfo.condition != null){
            //Time
            if (spawnInfo.condition.times != null && !spawnInfo.condition.times.isEmpty()){
                txt = txt.concat(translateAlternateColorCodes('&',langspawn.get("times").getString().replaceFirst("%times%",getTimeSpawns(spawnInfo))) +"\n");
            }
            //Weather
            if (spawnInfo.condition.weathers != null && !spawnInfo.condition.weathers.isEmpty()){
                txt = txt.concat(translateAlternateColorCodes('&',langspawn.get("weathers").getString().replaceFirst("%weathers%",getWeatherSpawns(spawnInfo))) +"\n");
            }
            //Biomes
            if (spawnInfo.condition.biomes != null && !spawnInfo.condition.biomes.isEmpty()){
                txt = txt.concat(translateAlternateColorCodes('&',langspawn.get("biomes").getString().replaceFirst("%biomes%",getBiomeSpawns(spawnInfo))) +"\n");
            }
            //Nearby Blocks
            if (spawnInfo.condition.cachedNeededNearbyBlocks != null && !spawnInfo.condition.cachedNeededNearbyBlocks.isEmpty()){
                txt = txt.concat(translateAlternateColorCodes('&',langspawn.get("nearbyblocks").getString().replaceFirst("%nearbyblocks%",getNearbyBlocksSpawns(spawnInfo))) +"\n");
            }
        }
        /*ForgeRegistries.BLOCKS.forEach(block -> {
            if (block.getLocalizedName().endsWith(".name")){
                System.out.println(new ItemStack(block).getDisplayName());
            }else{
                System.out.println(block.getLocalizedName());
            }
        });*/
        //Rarity
        txt = txt.concat(translateAlternateColorCodes('&',langspawn.get("rarity").getString().replaceFirst("%rarity%",String.valueOf(spawnInfo.rarity))) +"\n");
        return txt;
    }

    public static String getBiomeSpawns(SpawnInfoPokemon info){
        ArrayList<Biome> allBiomes = new ArrayList<>();
        for (Biome biome : GameRegistry.findRegistry(Biome.class)) {
            allBiomes.add(biome);
        }
        if (info.condition != null && info.condition.biomes != null && !info.condition.biomes.isEmpty()) {
            allBiomes.removeIf(biome -> !info.condition.biomes.contains(biome));
        }
        if (info.anticondition != null && info.anticondition.biomes != null && !info.anticondition.biomes.isEmpty()) {
            allBiomes.removeIf(biome -> info.anticondition.biomes.contains(biome));
        }
        if (info.compositeCondition != null) {
            if (info.compositeCondition.conditions != null) {
                info.compositeCondition.conditions.forEach(condition -> {
                    if (condition.biomes != null && !condition.biomes.isEmpty()) {
                        allBiomes.removeIf(biome -> !condition.biomes.contains(biome));
                    }
                });
            }
            if (info.compositeCondition.anticonditions != null) {
                info.compositeCondition.anticonditions.forEach(anticondition -> {
                    if (anticondition.biomes != null && anticondition.biomes.isEmpty()) {
                        allBiomes.removeIf(biome -> anticondition.biomes.contains(biome));
                    }
                });
            }
        }
        Set<Biome> avail = new HashSet<>(allBiomes);
        ArrayList<String> biomeNames = new ArrayList<>();
        for (Biome biome : avail) {
            String biomeName = ReflectionHelper.getBiomeName(biome).orElse("???");
            biomeNames.add(TextFormatting.DARK_AQUA+biomeName);
        }
        return String.join(TextFormatting.YELLOW+", ",biomeNames);
    }

    public static String getTimeSpawns(SpawnInfoPokemon info){
        ArrayList<WorldTime> allTimes = Lists.newArrayList(WorldTime.values());
        if (info.condition != null && info.condition.times != null && !info.condition.times.isEmpty()) {
            allTimes.removeIf(time -> !info.condition.times.contains(time));
        }
        if (info.anticondition != null && info.anticondition.times != null && !info.anticondition.times.isEmpty()) {
            allTimes.removeIf(time -> info.anticondition.times.contains(time));
        }
        if (info.compositeCondition != null) {
            if (info.compositeCondition.conditions != null) {
                info.compositeCondition.conditions.forEach(condition -> {
                    if (condition.times != null && !condition.times.isEmpty()) {
                        allTimes.removeIf(time -> !condition.times.contains(time));
                    }
                });
            }
            if (info.compositeCondition.anticonditions != null) {
                info.compositeCondition.anticonditions.forEach(anticondition -> {
                    if (anticondition.times != null && anticondition.times.isEmpty()) {
                        allTimes.removeIf(time -> anticondition.times.contains(time));
                    }
                });
            }
        }
        Set<WorldTime> avail = new HashSet<>(allTimes);
        ArrayList<String> worldTimeNames = new ArrayList<>();
        for (WorldTime times : avail) {
            String worldTimeName = times.getLocalizedName();
            worldTimeNames.add(TextFormatting.DARK_AQUA+worldTimeName);
        }
        return String.join(TextFormatting.YELLOW+", ",worldTimeNames);
    }


    public static String getWeatherSpawns(SpawnInfoPokemon info){
        ArrayList<WeatherType> allWeathers = Lists.newArrayList(WeatherType.values());
        if (info.condition != null && info.condition.weathers != null && !info.condition.weathers.isEmpty()) {
            allWeathers.removeIf(weather -> !info.condition.weathers.contains(weather));
        }
        if (info.anticondition != null && info.anticondition.weathers != null && !info.anticondition.weathers.isEmpty()) {
            allWeathers.removeIf(weather -> info.anticondition.weathers.contains(weather));
        }
        if (info.compositeCondition != null) {
            if (info.compositeCondition.conditions != null) {
                info.compositeCondition.conditions.forEach(condition -> {
                    if (condition.weathers != null && !condition.weathers.isEmpty()) {
                        allWeathers.removeIf(weather -> !condition.weathers.contains(weather));
                    }
                });
            }
            if (info.compositeCondition.anticonditions != null) {
                info.compositeCondition.anticonditions.forEach(anticondition -> {
                    if (anticondition.weathers != null && anticondition.weathers.isEmpty()) {
                        allWeathers.removeIf(weather -> anticondition.weathers.contains(weather));
                    }
                });
            }
        }
        Set<WeatherType> avail = new HashSet<>(allWeathers);
        ArrayList<String> weatherNames = new ArrayList<>();
        for (WeatherType weathers : avail) {
            String weatherName = weathers.name();
            weatherNames.add(TextFormatting.DARK_AQUA+weatherName);
        }
        return String.join(TextFormatting.YELLOW+", ",weatherNames);
    }

    public static String getNearbyBlocksSpawns(SpawnInfoPokemon info){
        ArrayList<Block> allBlocks = Lists.newArrayList(ForgeRegistries.BLOCKS);
        if (info.condition != null && info.condition.cachedNeededNearbyBlocks != null && !info.condition.cachedNeededNearbyBlocks.isEmpty()) {
            allBlocks.removeIf(blocks -> !info.condition.cachedNeededNearbyBlocks.contains(blocks));
        }
        if (info.anticondition != null && info.anticondition.cachedNeededNearbyBlocks != null && !info.anticondition.cachedNeededNearbyBlocks.isEmpty()) {
            allBlocks.removeIf(block -> info.anticondition.cachedNeededNearbyBlocks.contains(block));
        }
        if (info.compositeCondition != null) {
            if (info.compositeCondition.conditions != null) {
                info.compositeCondition.conditions.forEach(condition -> {
                    if (condition.cachedNeededNearbyBlocks != null && !condition.cachedNeededNearbyBlocks.isEmpty()) {
                        allBlocks.removeIf(block -> !condition.cachedNeededNearbyBlocks.contains(block));
                    }
                });
            }
            if (info.compositeCondition.anticonditions != null) {
                info.compositeCondition.anticonditions.forEach(anticondition -> {
                    if (anticondition.cachedNeededNearbyBlocks != null && anticondition.cachedNeededNearbyBlocks.isEmpty()) {
                        allBlocks.removeIf(block -> anticondition.cachedNeededNearbyBlocks.contains(block));
                    }
                });
            }
        }
        Set<Block> avail = new HashSet<>(allBlocks);
        ArrayList<String> blocksName = new ArrayList<>();
        for (Block block : avail) {
            /*if (block.getLocalizedName().endsWith(".name")){
                blocksName.add(TextFormatting.DARK_AQUA+new ItemStack(block).getDisplayName());
            }else{

            }*/
            blocksName.add(TextFormatting.DARK_AQUA+block.getLocalizedName());
        }
        return String.join(TextFormatting.YELLOW+", ",blocksName);
    }

}
