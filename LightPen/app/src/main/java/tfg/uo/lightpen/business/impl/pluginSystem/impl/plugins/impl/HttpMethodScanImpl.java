package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import tfg.uo.lightpen.R;
import tfg.uo.lightpen.business.impl.networking.impl.networkOperations.HttpOperations.HttpMethod;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.HttpMethodsError;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.riskRating.OwaspRiskRating;
import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 9/5/17.
 */

public class HttpMethodScanImpl {

    private static final String TAG = "HttpMethodScan_Impl";
    private int maxUrlDeep;
    private ArrayList<Error> errors = new ArrayList<>();
    private HashMap<URL, String> methods = new HashMap<>();
    private URL url;
    private ContextData ctxD;



    public HttpMethodScanImpl(URL url, ContextData ctxD){
        setUrl(url);
        setContextData(ctxD);
    }

    public ArrayList<Error> getPentest() {
        HttpMethod httpMth = new HttpMethod();
        Factories
                .business
                .createNetworkingFactory()
                .createNetworking(ctxD)
                    .crawl(url, httpMth.maxURLDeep(ctxD), httpMth);
        //-1 indica que alcancaremos todos los elementos de la web
        setMethods(httpMth.getResponse());
        analyzeMethods();
        return getErrors();
    }

    private void analyzeMethods() {
        Set<URL> keys = getMethods().keySet();
        for(URL u : keys){
            add(assertMethod(getMethods().get(u)));
        }
    }

    private HttpMethodsError assertMethod(String mth){
        HttpMethodsError err = null;
        switch (mth){
            case "PUT":
                err =  new HttpMethodsError(getContextData()
                        .getContext()
                        .getString(R.string.httphs_method_put),
                        new OwaspRiskRating(9, 8, 7, 9).getRiskLevel());
                break;
            case "DELETE":
                err =  new HttpMethodsError(getContextData()
                        .getContext()
                        .getString(R.string.httphs_method_delete),
                        new OwaspRiskRating(9, 9, 9, 9).getRiskLevel());
                break;
            case "CONNECT":
                err =  new HttpMethodsError(getContextData()
                        .getContext()
                        .getString(R.string.httphs_method_connect),
                        new OwaspRiskRating(9, 4, 3, 9).getRiskLevel());
                break;
            case "TRACE":
                err =  new HttpMethodsError(getContextData()
                        .getContext()
                        .getString(R.string.httphs_method_trace),
                        new OwaspRiskRating(9, 8, 6, 9).getRiskLevel());
                break;
            case "POST":
                break;
            case "GET":
                break;
            case "OPTIONS":
                break;
            case "HEAD":
                break;
            default:
                err =  assertWebdav(mth);
                break;
        }
        return err;
    }

    private HttpMethodsError assertWebdav(String mth){
        HttpMethodsError err = null;
        if(mth.contains("PROPFIND")){
        }else if(mth.contains("PROPPATCH")){
        }else if(mth.contains("MKCOL")){
        }else if(mth.contains("COPY")){
        }else if(mth.contains("MOVE")){
        }else if(mth.contains("LOCK")){
        }else if(mth.contains("UNLOCK")){
        }else{
            err =  new HttpMethodsError(mth+": "+ getContextData()
                    .getContext()
                    .getString(R.string.httphs_method_custom),
                    new OwaspRiskRating(9, 3, 4, 9).getRiskLevel());
        }
        return err;
    }


    //region Getters & Setters
    public void setUrl(URL url){
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
    private HashMap<URL, String> getMethods() {
        return methods;
    }
    private void setMethods(HashMap<URL, String> methods) {
        this.methods = methods;
    }
    private void add(HttpMethodsError httpMethodsError) {
        if(httpMethodsError != null)
            getErrors().add(httpMethodsError);
    }
    //endregion
}
