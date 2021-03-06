package com.slimeflow.slimeengin.deadpool;

import com.slimeflow.slimeengin.SlimePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by x9litch on 19/03/2016. - slimeflow.com
 */
public class DeadPoolListener implements Listener
{
    @EventHandler
    void onPlayerJoin(PlayerJoinEvent evt)
    {
        SlimePlayer sp = SlimePlayer.Manager().getSlimePlayer(evt.getPlayer().getUniqueId());

        if (sp != null)
        {
            sp.updateDeadPool();
        }
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent evt)
    {
        Player source = evt.getEntity();
        SlimePlayer sPlayer = SlimePlayer.Manager().getSlimePlayer(source.getUniqueId());

        if(sPlayer == null)
            return;

        if (sPlayer.hasDeadPool())
        {
            double amount = sPlayer.getDeadPool().burn();
            evt.setDeathMessage("DeadPool reward for " + source.getDisplayName() + " goes to " + source.getKiller().getDisplayName());
            //TODO: Reward Killer...
        }
    }
}
