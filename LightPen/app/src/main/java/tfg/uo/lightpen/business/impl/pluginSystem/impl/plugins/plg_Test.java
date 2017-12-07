package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins;

import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;

import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.TestError;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 28/2/17.
 * Clase de prueba de plugins
 */
public class plg_Test implements Plugin {

    private static final String TAG = "plg_Test";
    private ContextData ctxD;


    @Override
    public ArrayList<Error> run(URL dir) {
        ArrayList<Error> test = new ArrayList<>();

        Error t = new TestError(this, showVersion());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        test.add(t);

        return test;
    }

    @Override
    public String showDescription() {
        return "Plugin de prueba";
    }

    @Override
    public String showName() {
        return "testPlugin";
    }

    @Override
    public Double showVersion() { return 0.001; }

    @Override
    public String printErrors(ArrayList<Error> errores) {
        return "";
    }

    @Override
    public void setContextData(ContextData ctxD) {
        this.ctxD = ctxD;
    }


}
