package fr.pokepixel.pokewiki.info;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.pokemon.SpawnInfoPokemon;
import fr.pokepixel.pokewiki.ReflectionHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpawnDetails {


    public static String createPokeDetails(SpawnInfoPokemon spawnInfo){
        String txt = "";
        //Pokemon pokemon = Pixelmon.pokemonFactory.create(spawnInfo.getPokemonSpec());
        //String form = "";
        /*if (pokemon.getForm()>0){
            form = " " + form.concat(pokemon.getSpecies().getFormEnum(pokemon.getForm()).getLocalizedName());
            //txt.appendText("Form: " + pokemon.getLocalizedName()+ " " + pokemon.getSpecies().getFormEnum(pokemon.getForm()).getLocalizedName()+"\n");
        }*/
        /*if (spawnInfo.getPokemonSpec().extraSpecs != null){
            for (SpecValue<?> extraSpec : spawnInfo.getPokemonSpec().extraSpecs) {
                System.out.println("Test spec " + extraSpec.key +" and " + extraSpec.value);
            }
        }
        if (spawnInfo.specs != null){
            for (PokemonSpec spec : spawnInfo.specs) {
                System.out.println("Test spec 04 " + spec);
            }
        }
        System.out.println("Test spec 02" + spawnInfo.getPokemonSpec());*/
        txt = txt.concat("§eType of spawn location: §6" + String.join(", ",spawnInfo.stringLocationTypes) + "\n");
        txt = txt.concat("§eMinimum level: §6" + spawnInfo.minLevel + "\n");
        txt = txt.concat("§eMaximum level: §6" + spawnInfo.maxLevel + "\n");
        //ReflectionHelper.
        //spawnInfo.species.getBaseStats().getType1().
        if (spawnInfo.heldItems != null){
            List<String> itemName = Lists.newArrayList();
            spawnInfo.heldItems.forEach(jsonItemStack -> {
                itemName.add(TextFormatting.DARK_AQUA+jsonItemStack.getItemStack().getDisplayName());
            });
            txt = txt.concat("§eHeldItems: §6" + String.join(TextFormatting.YELLOW+", ",itemName)+"\n");
        }
        //Biomes
        txt = txt.concat("§eBiomes: " + getBiomeSpawns(spawnInfo)+"\n");
        //Rarity
        txt = txt.concat("§eRarity: §6" + spawnInfo.rarity);
        //TextComponentString pokeHover = new TextComponentString(TextFormatting.DARK_AQUA+pokemon.getLocalizedName()+form);
        //pokeHover.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,txt));
        //oui.getStyle(). setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/pokearena register confirm"));
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


    /*public static String getWorldSpawn(SpawnInfoPokemon info){
        ArrayList<Integer> allDims = new ArrayList<>();
        for (WorldServer world : getServer().worlds) {
            allDims.add(world.provider.getDimension());
        }
        if (info.condition != null && info.condition.dimensions != null && !info.condition.dimensions.isEmpty()) {
            allDims.removeIf(id -> !info.condition.dimensions.contains(id));
        }
        if (info.anticondition != null && info.anticondition.dimensions != null && !info.anticondition.dimensions.isEmpty()) {
            allDims.removeIf(id -> info.anticondition.dimensions.contains(id));
        }
        if (info.compositeCondition != null) {
            if (info.compositeCondition.conditions != null) {
                info.compositeCondition.conditions.forEach(condition -> {
                    if (condition.dimensions != null && !condition.dimensions.isEmpty()) {
                        allDims.removeIf(id -> !condition.dimensions.contains(id));
                    }
                });
            }
            if (info.compositeCondition.anticonditions != null) {
                info.compositeCondition.anticonditions.forEach(anticondition -> {
                    if (anticondition.dimensions != null && anticondition.dimensions.isEmpty()) {
                        allDims.removeIf(id -> anticondition.dimensions.contains(id));
                    }
                });
            }
        }
        Set<Integer> avail = new HashSet<>(allDims);
        ArrayList<String> dimNames = new ArrayList<>();
        for (Integer id : avail) {
            dimNames.add(TextFormatting.DARK_AQUA+getServer().getWorld(id).getWorldInfo().getWorldName());
        }
        return String.join(TextFormatting.YELLOW+", ",dimNames);
    }*/

}
