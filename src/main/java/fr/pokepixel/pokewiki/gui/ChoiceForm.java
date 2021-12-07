package fr.pokepixel.pokewiki.gui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.FlagType;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.spawning.SpawnInfo;
import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.pokemon.SpawnInfoPokemon;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.forms.IEnumForm;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static fr.pokepixel.pokewiki.gui.DisplayInfo.displayInfoGUI;

public class ChoiceForm {

    public static void openChoiceFormGUI(EntityPlayerMP player, EnumSpecies specie){

        Button glass = GooeyButton.builder()
                .display(new ItemStack(Blocks.STAINED_GLASS_PANE,1,15))
                .build();

        LinkedPageButton previous = LinkedPageButton.builder()
                .display(new ItemStack(PixelmonItems.LtradeHolderLeft))
                .hideFlags(FlagType.All)
                .title("§e<----")
                .linkType(LinkType.Previous)
                .build();

        LinkedPageButton next = LinkedPageButton.builder()
                .display(new ItemStack(PixelmonItems.tradeHolderRight))
                .hideFlags(FlagType.All)
                .title("§e---->")
                .linkType(LinkType.Next)
                .build();


        PlaceholderButton placeholder = new PlaceholderButton();

        ChestTemplate template = ChestTemplate.builder(4)
                //.line(LineType.HORIZONTAL,5,0,9,glass)
                .rectangle(0, 2, 2, 5, placeholder)
                .fill(glass)
                .set(3,3,previous)
                .set(3,5,next)
                .build();

        LinkedPage.Builder page = LinkedPage.builder()
                .title("");

        //Make this offthread
        LinkedPage firstPage = PaginationHelper.createPagesFromPlaceholders(template, createButtonFromList(specie), page);
        UIManager.openUIForcefully(player, firstPage);

    }

    public static List<Button> createButtonFromList(EnumSpecies species) {
        List<Button> list = Lists.newArrayList();
        for (IEnumForm possibleForm : species.getPossibleForms(false)) {
            Pokemon pokemon = Pixelmon.pokemonFactory.create(species);
            pokemon.setForm(possibleForm);
            String formname = "";
            if (!pokemon.getFormEnum().getFormSuffix().isEmpty() && !pokemon.getFormEnum().getFormSuffix().equalsIgnoreCase("-normal")){
                formname = possibleForm.getLocalizedName();
            }
            list.add(GooeyButton.builder()
                    .display(ItemPixelmonSprite.getPhoto(pokemon))
                    .title("§a"+pokemon.getLocalizedName()+ " " + formname)
                    .onClick(buttonAction -> {
                        displayInfoGUI(buttonAction.getPlayer(),pokemon);
                    })
                    .build());
        }
        /*ArrayList<SpawnSet> setinfos = Lists.newArrayList();
        setinfos.addAll(PixelmonSpawning.standard);
        setinfos.addAll(PixelmonSpawning.legendaries);
        for (SpawnSet set : setinfos) {
            for (SpawnInfo info : set.spawnInfos) {
                if (info instanceof SpawnInfoPokemon) {
                    SpawnInfoPokemon spawnInfoPokemon = (SpawnInfoPokemon) info;
                    if (spawnInfoPokemon.getPokemonSpec().name.equalsIgnoreCase(species.getPokemonName())){
                        Pokemon pokemon = Pixelmon.pokemonFactory.create(spawnInfoPokemon.getPokemonSpec());
                        String form = "";
                        if (pokemon.getForm()>0){
                            form = " " + form.concat(pokemon.getSpecies().getFormEnum(pokemon.getForm()).getLocalizedName());
                        }
                        list.add(GooeyButton.builder()
                                .display(ItemPixelmonSprite.getPhoto(Pixelmon.pokemonFactory.create(spawnInfoPokemon.getPokemonSpec())))
                                .title("§a"+pokemon.getSpecies().getPokemonName()+form)
                                .onClick(buttonAction -> {
                                    displayInfoGUI(buttonAction.getPlayer(),spawnInfoPokemon);
                                })
                                .build());
                    }
                }
            }
        }*/
        return list;
    }

    public static List<SpawnInfoPokemon> getSpawnInfoList(Pokemon pokemon){
        List<SpawnInfoPokemon> spawnInfoPokemonList = Lists.newArrayList();
        ArrayList<SpawnSet> setinfos = Lists.newArrayList();
        setinfos.addAll(PixelmonSpawning.standard);
        setinfos.addAll(PixelmonSpawning.legendaries);
        for (SpawnSet set : setinfos) {
            for (SpawnInfo info : set.spawnInfos) {
                if (info instanceof SpawnInfoPokemon) {
                    SpawnInfoPokemon spawnInfoPokemon = (SpawnInfoPokemon) info;
                    if (spawnInfoPokemon.getPokemonSpec().name.equalsIgnoreCase(pokemon.getSpecies().getPokemonName())){
                        if (spawnInfoPokemon.getPokemonSpec().matches(pokemon)){
                            if (spawnInfoPokemon.getPokemonSpec().form == null){
                                if (pokemon.getForm()<1){
                                    spawnInfoPokemonList.add(spawnInfoPokemon);
                                }
                            }else{
                                spawnInfoPokemonList.add(spawnInfoPokemon);
                            }
                        }
                    }
                }
            }
        }
        return spawnInfoPokemonList;
    }


}
