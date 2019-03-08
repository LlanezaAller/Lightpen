package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins;

import java.net.URL;
import java.util.ArrayList;

import tfg.uo.lightpen.R;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.model.ContextData;

public class Telemetry implements Plugin {
    @Override
    public ArrayList<Error> run(URL dir) {
        return null;
    }

    @Override
    public String showDescription() {
        return "telemetry";
    }

    @Override
    public String showName() {
        return "telemetry";
    }

    @Override
    public Double showVersion() {
        return 1.0;
    }

    @Override
    public String printErrors(ArrayList<Error> errores) {
        String ans = "<div class=\"plugin-result\">";
        ans += "<h1 class=\"plugin-title\">"+ showName() +"</h1>\n";
        ans += "<p class=\"plugin-description\">"+showDescription()+"</p>\n";
        if(!errores.isEmpty()){
            ans += "<div class=\"plugin-result-table-wrapper\">\n";
            ans += "<div> TIME CPU MEM </div>";
            ans += "<div>\n";


            for(Error err : errores){
                ans += err.getDescription() + "\n";
            }
            ans += "</div>\n";
            ans += "</div>\n"; //plugin-result-table-wrapper

        }else{
            ans += "<div class=\"plugin-result-wrapper\">\n";
            ans += "<p>" + ctxD.getContext()
                    .getString(R.string.errors_suite_noErrors) + "</p>";
            ans += "</div>\n";
        }

        ans += "</div>\n"; // plugin-result

        return ans;
    }

    private ContextData ctxD;

    @Override
    public void setContextData(ContextData ctxD) {
        this.ctxD = ctxD;
    }
}
