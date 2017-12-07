package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins;

import java.net.URL;
import java.util.ArrayList;

import tfg.uo.lightpen.R;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.impl.PortScanImpl;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.impl.UrlScanImpl;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 22/4/17.
 * Plugin realizado para el analisis del fichero robots.txt
 */
public class plg_PortScan implements Plugin{

    private static final String TAG = "HTTPProductScan";
    private ContextData ctxD;

    @Override
    public ArrayList<Error> run(URL dir) {
        PortScanImpl ri = new PortScanImpl(dir, ctxD);
        return ri.getPentest();
    }

    @Override
    public String showDescription() {

        return ctxD.getContext().getString(R.string.PortScan_description);
    }

    @Override
    public String showName() {
        return ctxD.getContext().getString(R.string.PortScan_name);
    }

    @Override
    public Double showVersion() {
        return Double.parseDouble(ctxD.getContext().getString(R.string.PortScan_version));
    }


    @Override
    public String printErrors(ArrayList<Error> errores) {
        String ans = "<div class=\"plugin-result\">";
                    ans += "<h1 class=\"plugin-title\">"+ showName() +"</h1>\n";
                    ans += "<p class=\"plugin-description\">"+showDescription()+"</p>\n";
        if(!errores.isEmpty()){
                        ans += "<div class=\"plugin-result-table-wrapper\">\n";
                            ans += "<table id='httpProduct-scan-table' class='error-table'>\n";
                                ans += "<tr>\n";
                                        ans += "<th>" + ctxD.getContext()
                                                .getString(R.string.errors_suite_Description) +"</th>\n";
                                ans += "</tr>\n";
                            for (Error e : errores){
                                ans += "<tr>\n";
                                    ans += e.printError();
                                ans += "</tr>\n";
                            }
                            ans += "</table>\n";
                        ans += "</div>\n"; //plugin-result-table-wrapper

        }else{
            ans += "<div class=\"plugin-result-wrapper\">\n";
                ans += "<p>" + ctxD.getContext()
                        .getString(R.string.PortScan_noPorts) + "</p>";
            ans += "</div>\n";
        }

        ans += "</div>\n"; // plugin-result

        return ans;
    }

    @Override
    public void setContextData(ContextData ctxD) {
        this.ctxD = ctxD;
    }
}
