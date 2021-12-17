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
import ca.landonjw.gooeylibs2.api.template.LineType;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.config.PixelmonItemsHeld;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.ConfigCategory;

import java.util.List;

import static fr.pokepixel.pokewiki.config.ChatColor.translateAlternateColorCodes;
import static fr.pokepixel.pokewiki.gui.DisplayInfo.displayInfoGUI;
import static fr.pokepixel.pokewiki.info.SimpleInfo.getInfoEvo;

public class DisplayEvo {

    public static void displayEvoGUI(EntityPlayerMP player, Pokemon pokemon, ConfigCategory langevo){

        Button ejectbutton = GooeyButton.builder()
                .title(translateAlternateColorCodes('&',langevo.get("back2").getString()))
                .display(new ItemStack(PixelmonItemsHeld.ejectButton))
                .hideFlags(FlagType.All)
                .onClick(buttonAction -> {
                    displayInfoGUI(buttonAction.getPlayer(),pokemon);
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

        ChestTemplate template = ChestTemplate.builder(5)
                .rectangle(0,0,2,9,redglass)
                .line(LineType.HORIZONTAL,2,0,9,blackglass)
                .rectangle(3,0,2,9,whiteglass)
                .rectangle(1, 4, 3, 3, placeholder)
                .set(2,1,ejectbutton)
                .set(4,4,previous)
                .set(4,6,next)
                .build();

        LinkedPage.Builder page = LinkedPage.builder()
                .title("Evolution Info");

        //Make this offthread
        LinkedPage firstPage = PaginationHelper.createPagesFromPlaceholders(template, getEvoInfos(pokemon,langevo), page);
        UIManager.openUIForcefully(player, firstPage);

    }


    public static List<Button> getEvoInfos(Pokemon pokemon, ConfigCategory langevo){
        List<Button> buttonList = Lists.newArrayList();
        LinkedHashMultimap<Pokemon, String> listevo = getInfoEvo(pokemon, langevo);
        String name = pokemon.getLocalizedName();
        listevo.forEach((poketoevo, evo) -> {
            ItemStack item = ItemPixelmonSprite.getPhoto(poketoevo);
            buttonList.add(GooeyButton.builder()
                    .display(item)
                    .title("§a"+name)
                    .onClick(buttonAction -> {
                        displayInfoGUI(buttonAction.getPlayer(),poketoevo);
                    })
                    .lore(Lists.newArrayList(evo))
                    .build());
        });
        return buttonList;
    }


}
