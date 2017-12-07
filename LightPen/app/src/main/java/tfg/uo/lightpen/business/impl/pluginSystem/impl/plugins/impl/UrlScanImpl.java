package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.impl;

import java.net.URL;
import java.util.ArrayList;

import tfg.uo.lightpen.business.impl.networking.Networking;
import tfg.uo.lightpen.business.impl.networking.impl.networkOperations.HttpOperations.URLMap;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.UrlScanError;
import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 22/4/17.
 * Plugin realizado para el analisis del fichero robots.txt
 */
public class UrlScanImpl {

    private static final String TAG = "HTTPPRODUCTSCAN_IMPL";
    private int maxUrlDeep;
    ArrayList<Error> errors = new ArrayList<>();
    URL url;
    private ContextData ctxD;

    public UrlScanImpl(URL url, ContextData ctxD){
        setUrl(url);
        setContextData(ctxD);
    }

    public ArrayList<Error> getPentest(){
        Networking net = Factories.business.createNetworkingFactory().createNetworking(ctxD);
        URLMap uM = new URLMap();
        net.crawl(getUrl(), uM.maxURLDeep(ctxD), uM);

        for(URL url : net.getUrlTree()){
            errors.add(new UrlScanError(url.toString()));
        }

        return getErrors();
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
    public ContextData getContextData() {
        return ctxD;
    }
    public void setContextData(ContextData ctxD) {
        this.ctxD = ctxD;
    }
    //endregion
}
