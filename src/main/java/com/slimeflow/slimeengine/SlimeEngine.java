package com.slimeflow.slimeengine;

import com.slimeflow.slimeengine.deadpool.DeadPoolListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by x9litch on 19/03/2016. - slimeflow.com
 */
public class SlimeEngine extends JavaPlugin
{
    private static SlimeEngine m_instance;

    private SlimePlayerManager m_slimePlayerManager;

    @Override
    public void onLoad()
    {
        if (m_instance != this)
            m_instance = this;

        m_slimePlayerManager = new SlimePlayerManager();
    }

    @Override
    public void onEnable()
    {
        PluginManager pMan = getServer().getPluginManager();

        //SlimePlayerListener
        SlimePlayerListener spl = new SlimePlayerListener();
        pMan.registerEvents(spl, this);

        //DeadPoolListener
        DeadPoolListener dpl = new DeadPoolListener();
        pMan.registerEvents(dpl, this);
    }

    @Override
    public void onDisable()
    {
        m_slimePlayerManager.clear();
    }

    public static SlimeEngine Main()
    {
        return m_instance;
    }

    public static SlimePlayerManager getSlimeManager()
    {
        return m_instance.m_slimePlayerManager;
    }

}
