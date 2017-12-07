package tfg.uo.lightpen.business.impl.htmlBuilder;



import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo on 27/01/2017.
 */

public interface HtmlBuilderFactory {

    public HtmlBuilder createHtmlBuilder(ContextData ctxD,
                                         ConcurrentHashMap<Plugin, ArrayList<Error>> err);

    public String parseNameFile(ContextData ctxD, String fileName);

}
