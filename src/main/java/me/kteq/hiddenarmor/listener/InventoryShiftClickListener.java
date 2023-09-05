package me.kteq.hiddenarmor.listener;

import me.kteq.hiddenarmor.HiddenArmor;
import me.kteq.hiddenarmor.handler.ArmorPacketHandler;
import me.kteq.hiddenarmor.manager.HiddenArmorManager;
import me.kteq.hiddenarmor.util.EventUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryShiftClickListener implements Listener {
    HiddenArmor plugin;
    HiddenArmorManager hiddenArmorManager;

    public InventoryShiftClickListener(HiddenArmor plugin){
        this.plugin = plugin;
        this.hiddenArmorManager = plugin.getHiddenArmorManager();
        EventUtil.register(this, plugin);
    }

    @EventHandler
    public void onShiftClickArmor(InventoryClickEvent event){
        if(!hiddenArmorManager.isArmorHidden((Player) event.getWhoClicked())) return;
        if(!(event.getClickedInventory() instanceof PlayerInventory)) return;
        if(!event.isShiftClick()) return;

        Player player = (Player) event.getWhoClicked();
        PlayerInventory inv = player.getInventory();
        ItemStack armor = event.getCurrentItem();

        if(player == null) return;
        if(inv == null) return;
        if(armor == null) return;

        if((armor.getType().toString().endsWith("_HELMET") && inv.getHelmet() == null) ||
                ((armor.getType().toString().endsWith("_CHESTPLATE") || armor.getType().equals(Material.ELYTRA)) && inv.getChestplate() == null) ||
                (armor.getType().toString().endsWith("_LEGGINGS") && inv.getLeggings() == null) ||
                (armor.getType().toString().endsWith("_BOOTS") && inv.getBoots() == null)){
            plugin.foliaLib.getImpl().runAtEntityLater(player, () -> ArmorPacketHandler.getInstance().updateSelf(player),1);
        }
    }
}
