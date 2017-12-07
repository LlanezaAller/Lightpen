package tfg.uo.lightpen.business.impl.pluginSystem;

import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo on 27/01/2017.
 */

public interface PluginsFactory {


    public PluginLoader createPluginLoader(ContextData ctxD);

}
