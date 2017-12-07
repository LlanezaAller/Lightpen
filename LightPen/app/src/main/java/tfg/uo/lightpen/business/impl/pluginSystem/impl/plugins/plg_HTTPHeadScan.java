package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins;

import java.net.URL;
import java.util.ArrayList;

import tfg.uo.lightpen.R;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.impl.HttpHeadScanImpl;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 2/5/17.
 */

public class plg_HTTPHeadScan implements Plugin {
    private static final String TAG = "HTTPHeadScan";
    private ContextData ctxD;

    @Override
    public ArrayList<Error> run(URL dir) {
        HttpHeadScanImpl httpi = new HttpHeadScanImpl(dir, ctxD);
        return httpi.getPentest();
    }

    @Override
    public String showDescription() {
        return ctxD.getContext().getString(R.string.httphs_description);
    }

    @Override
    public String showName() {
        return ctxD.getContext().getString(R.string.httphs_name);
    }

    @Override
    public Double showVersion() {
        return Double.parseDouble(ctxD.getContext().getString(R.string.httphs_version));
    }

    @Override
    public String printErrors(ArrayList<Error> errores) {
        String ans = "<div class=\"plugin-result\">";
        ans += "<h1 class=\"plugin-title\">"+ showName() +"</h1>\n";
        ans += "<p class=\"plugin-description\">"+showDescription()+"</p>\n";
        if(!errores.isEmpty()){
            ans += "<div class=\"plugin-result-table-wrapper\">\n";
            ans += "<table id='headers-scan-table' class='error-table'>\n";
            ans += "<tr>\n";
            ans += "<th>" + ctxD.getContext()
                    .getString(R.string.errors_suite_Description) +"</th>\n";
            ans += "<th>" + ctxD.getContext()
                    .getString(R.string.errors_suite_RiskLevel) + "</th>\n";
            ans += "</tr>\n";
            for (Error e : errores){
                ans += "<tr>\n";
                ans += e.printError();
                ans += "</tr>\n";
            }
            ans += "</table>\n";
            ans += "</div>\n"; //plugin-result-table-wrapper

        }else{
            ans += "<div class=\"headers-result-wrapper\">\n";
            ans += "<p>" + ctxD.getContext()
                    .getString(R.string.errors_suite_noErrors) + "</p>";
            ans += "</div>\n";
        }

        ans += "</div>\n"; // plugin-result

        return ans;
    }

    @Override
    public void setContextData(ContextData ctxD) {this.ctxD = ctxD;    }
}
