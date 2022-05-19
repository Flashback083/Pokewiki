package fr.pokepixel.pokewiki.config;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import fr.pokepixel.pokewiki.info.CustomInfo;

import static fr.pokepixel.pokewiki.Pokewiki.customSpawnPokemonInfoListInfo;

public class Utils {

    public static boolean canSpawnCustom(Pokemon pokemon){
        if (!customSpawnPokemonInfoListInfo.containsKey(pokemon.getSpecies().getPokemonName())){
            return false;
        }
        for (CustomInfo.CustomSpawnPokemonInfo customSpawnPokemonInfo : customSpawnPokemonInfoListInfo.get(pokemon.getSpecies().getPokemonName())) {
            PokemonSpec spec = new PokemonSpec(customSpawnPokemonInfo.getSpec());
            if (spec.form != null){
                if (spec.form <= 0 && pokemon.getForm()<=0) {
                    pokemon.setForm(spec.form);
                }
            }
            if (spec.matches(pokemon)){
                return true;
            }
        }
        return false;
    }


}
