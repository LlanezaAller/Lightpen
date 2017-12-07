package tfg.uo.lightpen.business;

import tfg.uo.lightpen.business.impl.configReader.ConfigReaderFactory;
import tfg.uo.lightpen.business.impl.htmlBuilder.HtmlBuilderFactory;
import tfg.uo.lightpen.business.impl.networking.NetworkingFactory;
import tfg.uo.lightpen.business.impl.pluginSystem.PluginsFactory;

/**
 * Created by Iñigo on 27/01/2017.
 * Clase de acceso a la logica
 */
public interface BusinessFactory {

    public PluginsFactory createPluginFactory();

    public HtmlBuilderFactory createHtmlBuilderFactory();

    public NetworkingFactory createNetworkingFactory();

    public ConfigReaderFactory createConfigReaderFactory();
}
