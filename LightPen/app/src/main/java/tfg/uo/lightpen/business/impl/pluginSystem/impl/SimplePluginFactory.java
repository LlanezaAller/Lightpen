package tfg.uo.lightpen.business.impl.pluginSystem.impl;

import tfg.uo.lightpen.business.impl.pluginSystem.PluginLoader;
import tfg.uo.lightpen.business.impl.pluginSystem.PluginsFactory;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo on 27/01/2017.
 */

public class SimplePluginFactory implements PluginsFactory {


    @Override
    public PluginLoader createPluginLoader(ContextData ctxD) {
        return new PluginLoaderImpl(ctxD);
    }

}
