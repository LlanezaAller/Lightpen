package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tfg.uo.lightpen.R;
import tfg.uo.lightpen.business.impl.networking.impl.networkOperations.HttpOperations.Headers;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.HttpHSError;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.riskRating.OwaspRiskRating;
import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 2/5/17.
 */

public class HttpHeadScanImpl {

    private static final String TAG = "HttpHeadScan_Impl";
    private ArrayList<Error> errors = new ArrayList<>();
    private HashMap<String, HttpHSError> policies = new HashMap<>();
    private URL url;
    private ContextData ctxD;



    public HttpHeadScanImpl(URL url, ContextData ctxD){
        setUrl(url);
        setContextData(ctxD);
        createPolicyMap();
    }

    public ArrayList<Error> getPentest() {
        Headers res = new Headers();
        Factories
                .business
                .createNetworkingFactory()
                .createNetworking(ctxD)
                    .crawl(getUrl(), 0, res);
        analyzeHeaders(res.getResponse());
        createReport();
        return getErrors();
    }
    private void createReport(){
        Iterator errorsI = policies.keySet().iterator();
        while(errorsI.hasNext()){
            getErrors().add(policies.get(errorsI.next()));
        }
        createPolicyMap();
    }

    public void analyzeHeaders(HashMap<URL, Map<String, List<String>>> ans){
        Iterator keys = ans.keySet().iterator();
        Map<String, List<String>> headers;
        while(keys.hasNext()){
            headers = ans.get(keys.next());
            assertHeadersFromURL(headers);
        }
    }

    private void assertHeadersFromURL(Map<String, List<String>> urlHeaders){
        Iterator keys = urlHeaders.keySet().iterator();
        int status = 0;
        String header;
        while(keys.hasNext()){
            if(status++ == 0) {
                keys.next();
            }
            header = keys.next().toString();
            if(policies.containsKey(header))
                policies.remove(header);
            if(header.equals("Server"))
                assertServerHeader(urlHeaders.get(header));
        }
    }

    private void assertServerHeader(List<String> serverInfo){
        for(String s : serverInfo){
            getErrors().add(
                    new HttpHSError(getContextData()
                    .getContext().getString(R.string.hhs_policy_server_info)
                            +"\n"+ s,
                    new OwaspRiskRating(9, 3, 6, 1).getRiskLevel()));
        }
    }



    private void createPolicyMap(){
        policies.put("X-Content-Type-Options",
                new HttpHSError(getContextData()
                        .getContext()
                        .getString(R.string.hhs_policy_X_Content_Type_Options),
                new OwaspRiskRating(8, 4, 4, 3).getRiskLevel()));
        policies.put("Strict-Transport-Security",
                new HttpHSError(
                getContextData()
                        .getContext()
                        .getString(R.string.hhs_policy_Strict_Transport_Security),
                        new OwaspRiskRating(8, 4, 7, 9).getRiskLevel()));
        policies.put("X-XSS-Protection",
                new HttpHSError(
                getContextData()
                        .getContext()
                        .getString(R.string.hhs_policy_X_XSS_Protection),
                        new OwaspRiskRating(8, 4, 7, 9).getRiskLevel()));
        policies.put("X-Frame-Options",
                new HttpHSError(
                getContextData()
                        .getContext()
                        .getString(R.string.hhs_policy_X_Frame_Options),
                new OwaspRiskRating(8, 3, 4, 3).getRiskLevel()));
        policies.put("Referrer-Policy",
                new HttpHSError(
                        getContextData().getContext().getString(R.string.hhs_policy_Referrer_Policy),
                        new OwaspRiskRating(8, 4, 5, 7).getRiskLevel()));
        policies.put("Public-Key-Pins",
                new HttpHSError(
                        getContextData().getContext().getString(R.string.hhs_policy_Public_Key_Pins),
                        new OwaspRiskRating(6, 2, 3, 7).getRiskLevel()));
    }

    //region Getters & Setters
    private void setUrl(URL url){
        this.url = url;
    }
    private URL getUrl(){
        return url;
    }
    private void setErrors(ArrayList<Error> errores){
        this.errors = errores;
    }
    private ArrayList<Error> getErrors(){
        return errors;
    }
    private ContextData getContextData() {
        return ctxD;
    }
    private void setContextData(ContextData ctxD) {
        this.ctxD = ctxD;
    }
    //endregion
}
