package fr.pokepixel.pokewiki.gui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.FlagType;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.LineType;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.config.*;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Gender;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import fr.pokepixel.pokewiki.Pokewiki;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.ConfigCategory;

import java.util.List;

import static fr.pokepixel.pokewiki.Pokewiki.customSpawnPokemonInfoListInfo;
import static fr.pokepixel.pokewiki.config.ChatColor.translateAlternateColorCodes;
import static fr.pokepixel.pokewiki.config.Lang.*;
import static fr.pokepixel.pokewiki.config.Utils.canSpawnCustom;
import static fr.pokepixel.pokewiki.gui.ChoiceForm.getSpawnInfoList;
import static fr.pokepixel.pokewiki.gui.ChoiceForm.openChoiceFormGUI;
import static fr.pokepixel.pokewiki.gui.DisplayEvo.displayEvoGUI;
import static fr.pokepixel.pokewiki.gui.DisplaySpawn.displaySpawnGUI;
import static fr.pokepixel.pokewiki.info.SimpleInfo.*;

public class DisplayInfo {


    public static void displayInfoGUI(EntityPlayerMP player, Pokemon pokemon){
        ConfigCategory langgeneral = Pokewiki.lang.getCategory(CATEGORY_GENERAL_LANG);
        ConfigCategory langevo = Pokewiki.lang.getCategory(CATEGORY_EVO_LANG);
        ConfigCategory langspawn = Pokewiki.lang.getCategory(CATEGORY_SPAWN_LANG);
        boolean hasForm =  pokemon.getSpecies().getPossibleForms(false).size()>1;
        List<String> spriteForm = Lists.newArrayList();
        if (hasForm){
            spriteForm.add(translateAlternateColorCodes('&',langgeneral.get("backtoformselection").getString()));
        }

        String formname = "";
        if (!pokemon.getFormEnum().getFormSuffix().isEmpty() && !pokemon.getFormEnum().getFormSuffix().equalsIgnoreCase("-normal")){
            formname = pokemon.getFormEnum().getLocalizedName();
        }else{
            if (pokemon.getFormEnum().getUnlocalizedName().contains("battle_bond")){
                formname = pokemon.getFormEnum().getLocalizedName();
            }
        }

        Button pokesprite = GooeyButton.builder()
                .title("§6"+pokemon.getLocalizedName() + " "  + formname)
                .display(ItemPixelmonSprite.getPhoto(pokemon))
                .lore(spriteForm)
                .onClick(buttonAction -> {
                    if (hasForm){
                        openChoiceFormGUI(buttonAction.getPlayer(),pokemon.getSpecies());
                    }
                })
                .build();

        Button redglass = GooeyButton.builder()
                .display(new ItemStack(Blocks.STAINED_GLASS_PANE,1,14))
                .build();

        Button blackglass = GooeyButton.builder()
                .display(new ItemStack(Blocks.STAINED_GLASS_PANE,1,15))
                .build();

        Button whiteglass = GooeyButton.builder()
                .display(new ItemStack(Blocks.STAINED_GLASS_PANE))
                .build();

        Button type = GooeyButton.builder()
                .hideFlags(FlagType.All)
                .display(new ItemStack(PixelmonItemsBadges.marshBadge))
                .title(translateAlternateColorCodes('&',langgeneral.get("pokemontype").getString()))
                .lore(Lists.newArrayList(getType(pokemon),getEggGroup(pokemon.getBaseStats(),langgeneral),getEggSteps(pokemon,langgeneral)))
                .build();

        long baserate = (Math.round(pokemon.getBaseStats().getCatchRate() / 255.0D * 100.0D));
        double femalepercent = (100 - pokemon.getBaseStats().getMalePercent());
        //translateAlternateColorCodes('&',lang.get("backtoformselection").getString())
        List<String> ballLore = Lists.newArrayList();
        ballLore.add(translateAlternateColorCodes('&',langgeneral.get("baserate").getString().replaceFirst("%baserate%",String.valueOf(baserate))));
        if (pokemon.getGender().equals(Gender.None)){
            ballLore.add(translateAlternateColorCodes('&',langgeneral.get("genderless").getString()));
        }else{
            ballLore.add(translateAlternateColorCodes('&',langgeneral.get("malepercent").getString().replaceFirst("%malepercent",String.valueOf(pokemon.getBaseStats().getMalePercent()))));
            ballLore.add(translateAlternateColorCodes('&',langgeneral.get("femalepercent").getString().replaceFirst("%femalepercent",String.valueOf(femalepercent))));
        }
        Button catchrate = GooeyButton.builder()
                .hideFlags(FlagType.All)
                .display(new ItemStack(PixelmonItemsPokeballs.pokeBall))
                .title(translateAlternateColorCodes('&',langgeneral.get("catchrate").getString()))
                .lore(ballLore)
                .build();

        boolean hasSpawn = !getSpawnInfoList(pokemon).isEmpty() || canSpawnCustom(pokemon);
        List<String> loreSpawn = Lists.newArrayList();
        if (hasSpawn){
            loreSpawn.add(translateAlternateColorCodes('&',langgeneral.get("lorespawninfo1").getString()));
        }else{
            loreSpawn.add(translateAlternateColorCodes('&',langgeneral.get("lorespawninfo2").getString()));
        }

        Button spawninfo = GooeyButton.builder()
                .display(new ItemStack(Blocks.SAPLING))
                .title(translateAlternateColorCodes('&',langgeneral.get("spawninfos").getString()))
                .lore(loreSpawn)
                .onClick(buttonAction -> {
                    if (hasSpawn){
                        displaySpawnGUI(buttonAction.getPlayer(),pokemon,langspawn);
                    }
                })
                .build();

        String evotitle = pokemon.getBaseStats().getEvolutions().size()>0 ? translateAlternateColorCodes('&',langgeneral.get("evo").getString()) : translateAlternateColorCodes('&',langgeneral.get("noevo").getString());

        Button evoinfo = GooeyButton.builder()
                .display(new ItemStack(PixelmonItemsHeld.upGrade))
                .hideFlags(FlagType.All)
                .title(evotitle)
                .onClick(buttonAction -> {
                    if (pokemon.getBaseStats().getEvolutions().size()>0){
                        displayEvoGUI(buttonAction.getPlayer(),pokemon,langevo);
                    }
                })
                .build();

        Button abilityinfo = GooeyButton.builder()
                .hideFlags(FlagType.All)
                .display(new ItemStack(PixelmonItems.abilityCapsule))
                .title(translateAlternateColorCodes('&',langgeneral.get("ability").getString()))
                .lore(Lists.newArrayList(getAbilityForPoke(pokemon,langgeneral)))
                .build();

        Button breedinfo = GooeyButton.builder()
                .hideFlags(FlagType.All)
                .display(new ItemStack(PixelmonItems.ranchUpgrade))
                .title(translateAlternateColorCodes('&',langgeneral.get("breed").getString()))
                .lore(Lists.newArrayList(getBreeding(pokemon.getBaseStats())))
                .build();

        Button dropinfo = GooeyButton.builder()
                .display(new ItemStack((Items.DIAMOND)))
                .title(translateAlternateColorCodes('&',langgeneral.get("drops").getString()))
                .lore(Lists.newArrayList(getDrops(pokemon.getSpecies())))
                .build();

        Button movebylevelinfo = GooeyButton.builder()
                .display(new ItemStack((PixelmonItemsTMs.gen1TM)))
                .hideFlags(FlagType.All)
                .title(translateAlternateColorCodes('&',langgeneral.get("movesbylevel").getString()))
                .lore(Lists.newArrayList(getMovesByLevel(pokemon.getSpecies())))
                .build();

        Button typeeffectiveness = GooeyButton.builder()
                .display(new ItemStack((PixelmonItemsBadges.rumbleBadge)))
                .hideFlags(FlagType.All)
                .title(translateAlternateColorCodes('&',langgeneral.get("typeeffectiveness").getString()))
                .lore(Lists.newArrayList(getTypeEffectiveness(pokemon)))
                .build();

        Button basestats = GooeyButton.builder()
                .display(new ItemStack((PixelmonItemsHeld.weaknessPolicy)))
                .hideFlags(FlagType.All)
                .title(translateAlternateColorCodes('&',langgeneral.get("basestats").getString()))
                .lore(Lists.newArrayList(getBaseStats(pokemon.getBaseStats())))
                .build();

        Button evyield = GooeyButton.builder()
                .display(new ItemStack((PixelmonItemsHeld.powerWeight)))
                .hideFlags(FlagType.All)
                .title(translateAlternateColorCodes('&',langgeneral.get("evyield").getString()))
                .lore(Lists.newArrayList(getEVYield(pokemon.getBaseStats())))
                .build();

        Button movebytutor = GooeyButton.builder()
                .display(new ItemStack((PixelmonItemsTMs.gen1TM)))
                .hideFlags(FlagType.All)
                .title(translateAlternateColorCodes('&',langgeneral.get("tutormoves").getString()))
                .lore(Lists.newArrayList(getTutorMoves(pokemon.getBaseStats())))
                .build();

        Button tmhmmoves = GooeyButton.builder()
                .display(new ItemStack((PixelmonItemsTMs.gen1TM)))
                .hideFlags(FlagType.All)
                .title(translateAlternateColorCodes('&',langgeneral.get("tmhmmoves").getString()))
                .lore(Lists.newArrayList(getTMHMMoves(pokemon.getBaseStats())))
                .build();

        Button trmoves = GooeyButton.builder()
                .display(new ItemStack((PixelmonItemsTMs.gen1TM)))
                .hideFlags(FlagType.All)
                .title(translateAlternateColorCodes('&',langgeneral.get("trmoves").getString()))
                .lore(Lists.newArrayList(getTRMoves(pokemon.getBaseStats())))
                .build();

        Button eggmoves = GooeyButton.builder()
                .display(new ItemStack((PixelmonItemsTMs.gen1TM)))
                .hideFlags(FlagType.All)
                .title(translateAlternateColorCodes('&',langgeneral.get("eggmoves").getString()))
                .lore(Lists.newArrayList(getBreedingMoves(pokemon.getBaseStats())))
                .build();


        ChestTemplate template = ChestTemplate.builder(5)
                .rectangle(0,0,2,9,redglass)
                .line(LineType.HORIZONTAL,2,0,9,blackglass)
                .rectangle(3,0,2,9,whiteglass)
                .set(1,3,type)
                .set(1,4,evoinfo)
                .set(1,5,spawninfo)
                .set(1,6,catchrate)
                .set(1,7,abilityinfo)
                .set(2,1,pokesprite)
                .set(2,3,breedinfo)
                .set(2,4,dropinfo)
                .set(2,5,evyield)
                .set(2,6,typeeffectiveness)
                .set(2,7,basestats)
                .set(3,3,movebylevelinfo)
                .set(3,4,movebytutor)
                .set(3,5,tmhmmoves)
                .set(3,6,trmoves)
                .set(3,7,eggmoves)
                .build();

        LinkedPage page = LinkedPage.builder()
                .template(template)
                .title("§eWiki")
                .build();
        UIManager.openUIForcefully(player, page);

    }

}
