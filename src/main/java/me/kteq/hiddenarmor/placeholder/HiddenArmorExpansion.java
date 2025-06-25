package me.kteq.hiddenarmor.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.kteq.hiddenarmor.HiddenArmor;
import me.kteq.hiddenarmor.manager.HiddenArmorManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * PlaceholderAPI expansion for HiddenArmor.
 * 
 * Usage: %hiddenarmor_<placeholder>%
 * Each placeholder returns "Enabled" or "Disabled" for the given player.
 *
 * Available placeholders:
 *   %hiddenarmor_armorvisibility% - Armor visibility toggle status
 *   %hiddenarmor_armorhidden%     - Whether armor is currently hidden (considers game mode and invisibility)
 */
public class HiddenArmorExpansion extends PlaceholderExpansion {
    
    private final HiddenArmor plugin;
    
    public HiddenArmorExpansion(HiddenArmor plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public @NotNull String getIdentifier() {
        return "hiddenarmor";
    }

    @Override
    public @NotNull String getAuthor() {
        return "kteq";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, @NotNull String identifier) {
        if (offlinePlayer == null) return "";
        Player player = offlinePlayer.getPlayer();
        if (player == null) return "";
        
        HiddenArmorManager manager = plugin.getHiddenArmorManager();
        
        return switch (identifier.toLowerCase()) {
            case "armorvisibility" -> boolToString(manager.isEnabled(player));
            case "armorhidden" -> boolToString(manager.isArmorHidden(player));
            default -> null;
        };
    }

    private String boolToString(boolean enabled) {
        return enabled ? "Enabled" : "Disabled";
    }
} 