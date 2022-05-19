package fr.pokepixel.pokewiki.config;

import fr.pokepixel.pokewiki.Pokewiki;
import fr.pokepixel.pokewiki.info.CustomInfo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class GsonUtils {

    public static void writeJson(CustomInfo info){
        try (PrintWriter writer = new PrintWriter(Pokewiki.customspawninfo,"UTF-8")) {
            Pokewiki.gson.toJson(info, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static CustomInfo readJson(){
        try (Reader reader = new InputStreamReader(new FileInputStream(Pokewiki.customspawninfo), StandardCharsets.UTF_8)) {
            CustomInfo data = Pokewiki.gson.fromJson(reader, CustomInfo.class);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HashMap<String, List<CustomInfo.CustomSpawnPokemonInfo>> getAllCustom(){
        CustomInfo data = readJson();
        assert data != null;
        return data.getCustomPokemonList();
    }




}
