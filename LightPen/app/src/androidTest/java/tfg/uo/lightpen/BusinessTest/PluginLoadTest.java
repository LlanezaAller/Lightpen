package tfg.uo.lightpen.BusinessTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.List;

import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;
import tfg.uo.lightpen.business.impl.pluginSystem.PluginLoader;
import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Iñigo Llaneza Aller on 1/3/17.
 */
public class PluginLoadTest {

    @Test
    public void pluginRead() throws Exception {
        // Contexto de la aplicacion
        Context ctx ;
        Context appContext = InstrumentationRegistry.getTargetContext();

        ContextData ctxD = new ContextData();
        ctxD.setContext(appContext);

        PluginLoader pl = Factories.business.createPluginFactory().createPluginLoader(ctxD);
        List<Plugin> pls = pl.pluginLoad();
        assertTrue(pls.size()>0);
    }

    @Test
    public void classFornameTest() {
        try {
            Class newPlugin =
                    Class.forName("tfg.uo.lightpen.business.impl" +
                            ".pluginSystem.impl.plugins.plg_Robots");
            assertTrue(true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}