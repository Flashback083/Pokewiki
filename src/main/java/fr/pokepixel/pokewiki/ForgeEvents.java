package fr.pokepixel.pokewiki;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static fr.pokepixel.pokewiki.config.Config.CATEGORY_GENERAL;
import static fr.pokepixel.pokewiki.gui.ChoiceForm.openChoiceFormGUI;
import static fr.pokepixel.pokewiki.gui.DisplayInfo.displayInfoGUI;

public class ForgeEvents {

    @SubscribeEvent
    public void onShiftRightClick(PlayerInteractEvent.EntityInteract event){
        // add config boolean check
        if (!Pokewiki.config.getCategory(CATEGORY_GENERAL).get("allowRightClickOpenWiki").getBoolean()) return;
        if (event.getTarget() instanceof EntityPixelmon && event.getEntityPlayer().isSneaking() && event.getEntityPlayer().inventory.getCurrentItem().isEmpty()){
            EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
            EntityPixelmon pixelmon = (EntityPixelmon) event.getTarget();
            if (pixelmon.getSpecies().getPossibleForms(false).size()>1){
                openChoiceFormGUI(player,pixelmon.getSpecies());
            }else{
                displayInfoGUI(player, Pixelmon.pokemonFactory.create(pixelmon.getSpecies()));
            }
        }
    }


}
