package com.slimeflow.slimeengin;

import com.slimeflow.slimeengin.commands.DebugCommands;
import com.slimeflow.slimeengin.database.ICommitter;
import com.slimeflow.slimeengin.deadpool.DeadPoolListener;
import com.slimeflow.slimeengin.deadpool.DeadPoolTable;
import com.slimeflow.slimeengin.warper.Warper;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by x9litch on 19/03/2016. - slimeflow.com
 */
public class SlimeEngin extends JavaPlugin
{
    private static SlimeEngin m_instance;

    private SqlCommitter m_Committer;
    private SlimePlayerManager m_slimePlayerManager;
    private Warper m_Warper;
    private boolean m_WarperEnable = true;
    private DeadPoolTable m_deadPoolTable;
    private boolean m_deadPoolEnable = true;

    @Override
    public void onLoad()
    {
        if (m_instance != this)
            m_instance = this;

        m_Committer = new SqlCommitter();
        m_Committer.start();

        m_slimePlayerManager = new SlimePlayerManager();
    }

    @Override
    public void onEnable()
    {
        PluginManager pMan = getServer().getPluginManager();

        //REQUIRED MODULE
        initSlimeManager(pMan);

        //OPTIONAL MODULE
        initWarper(pMan);
        initDeadPoolTable(pMan);
    }

    @Override
    public void onDisable()
    {
        m_slimePlayerManager.clear();
    }

    //region -> Init methods

    private void initSlimeManager(PluginManager pm)
    {
        getLogger().info("Enabling SlimeManager ...");
        SlimePlayerListener spl = new SlimePlayerListener();
        pm.registerEvents(spl, this);
        getCommand("debug").setExecutor(new DebugCommands());
    }

    private void initWarper(PluginManager pm)
    {
        if (m_WarperEnable)
        {
            getLogger().info("Enabling Warper Module ...");
            m_Warper = new Warper();
        }
        else
        {
            getLogger().info("Warper Module not enable.");
        }
    }

    private void initDeadPoolTable(PluginManager pm)
    {
        if (m_deadPoolEnable)
        {
            getLogger().info("Enabling DeadPool Module ...");
            m_deadPoolTable = new DeadPoolTable();
            DeadPoolListener dpl = new DeadPoolListener();
            pm.registerEvents(dpl, this);
        }
        else
        {
            getLogger().info("DeadPool Module not enable.");
        }
    }

    //endregion

    //region -> Static accesses

    public static SlimeEngin Main()
    {
        return m_instance;
    }

    public static ICommitter getCommitter()
    {
        return m_instance.m_Committer;
    }

    public static SlimePlayerManager getSlimeManager()
    {
        return m_instance.m_slimePlayerManager;
    }

    public static Warper getWarper()
    {
        return m_instance.m_Warper;
    }

    public static boolean isWarperEnable()
    {
        return m_instance.m_WarperEnable;
    }

    public static DeadPoolTable getDeadPools()
    {
        return m_instance.m_deadPoolTable;
    }

    public static boolean isDeadPoolEnable()
    {
        return m_instance.m_deadPoolEnable;
    }

    //endregion
}
