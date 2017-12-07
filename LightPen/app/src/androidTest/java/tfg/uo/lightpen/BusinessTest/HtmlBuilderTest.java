package tfg.uo.lightpen.BusinessTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import tfg.uo.lightpen.business.impl.htmlBuilder.HtmlBuilder;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.RobotsError;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.plg_Robots;
import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Iñigo Llaneza Aller on 1/3/17.
 */
public class HtmlBuilderTest {

    @Test
    public void htmlBuildTest() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        ConcurrentHashMap<Plugin, ArrayList<Error>> result = new ConcurrentHashMap<>();


        ArrayList<Error> errores = new ArrayList<>();
        errores.add(new RobotsError("testError", 3.0));

        plg_Robots robots = new plg_Robots();
        robots.setContextData(new ContextData(appContext));
        result.put(robots
                , errores);

        ContextData ctxD = new ContextData();
        ctxD.setContext(appContext);

        HtmlBuilder htmlB = Factories
                .business
                .createHtmlBuilderFactory()
                .createHtmlBuilder(ctxD, result);
        File ans = htmlB.processOutput("ingenieriainformatica.uniovi.es");
        assertNotNull(ans);
    }
}