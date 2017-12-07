package tfg.uo.lightpen.business.impl;

import tfg.uo.lightpen.business.BusinessFactory;
import tfg.uo.lightpen.business.impl.configReader.ConfigReaderFactory;
import tfg.uo.lightpen.business.impl.configReader.impl.SimpleConfigReaderFactory;
import tfg.uo.lightpen.business.impl.htmlBuilder.HtmlBuilderFactory;
import tfg.uo.lightpen.business.impl.htmlBuilder.impl.SimpleHtmlBuilderFactory;
import tfg.uo.lightpen.business.impl.networking.NetworkingFactory;
import tfg.uo.lightpen.business.impl.networking.impl.SimpleNetworkingFactory;
import tfg.uo.lightpen.business.impl.pluginSystem.PluginsFactory;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.SimplePluginFactory;

/**
 * Created by Iñigo on 27/01/2017.
 */

public class SimpleBusinessFactory implements BusinessFactory {

    @Override
    public PluginsFactory createPluginFactory() {
        return new SimplePluginFactory(); }

    @Override
    public HtmlBuilderFactory createHtmlBuilderFactory() {
        return new SimpleHtmlBuilderFactory(); }

    @Override
    public NetworkingFactory createNetworkingFactory() {
        return new SimpleNetworkingFactory(); }

    @Override
    public ConfigReaderFactory createConfigReaderFactory() {
        return new SimpleConfigReaderFactory(); }
}
