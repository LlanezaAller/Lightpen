package tfg.uo.lightpen.business.impl.htmlBuilder.impl;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import tfg.uo.lightpen.business.impl.htmlBuilder.HtmlBuilderFactory;
import tfg.uo.lightpen.business.impl.htmlBuilder.HtmlBuilder;
import tfg.uo.lightpen.business.impl.htmlBuilder.impl.nameTools.NameTool;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 20/2/17.
 */

public class SimpleHtmlBuilderFactory implements HtmlBuilderFactory {


    @Override
    public HtmlBuilder createHtmlBuilder(ContextData ctxD,
                                         ConcurrentHashMap<Plugin, ArrayList<Error>> err) {
        return new HtmlBuilderImpl(ctxD, err);
    }

    @Override
    public String parseNameFile(ContextData ctxD, String fileName) {
        return new NameTool(ctxD).parseTitle(fileName);
    }
}
