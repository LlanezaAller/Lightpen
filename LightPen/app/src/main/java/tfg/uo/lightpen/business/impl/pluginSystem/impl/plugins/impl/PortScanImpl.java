package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.impl;

import java.net.URL;
import java.util.ArrayList;

import tfg.uo.lightpen.business.impl.networking.impl.networkOperations.SocketOperations.Ports;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.PortScanError;
import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 22/4/17.
 * Plugin realizado para el analisis del fichero robots.txt
 */
public class PortScanImpl {

    private static final String TAG = "HTTPPRODUCTSCAN_IMPL";
    ArrayList<Error> errors = new ArrayList<>();
    URL url;
    private ContextData ctxD;

    public PortScanImpl(URL url, ContextData ctxD){
        setUrl(url);
        setContextData(ctxD);
    }

    public ArrayList<Error> getPentest(){
        Ports port = new Ports();

        Factories
                .business
                .createNetworkingFactory()
                .createNetworking(ctxD)
                    .crawl(getUrl(), port.maxThreads(ctxD), port, true);
        for(String s : port.getResponse()){
            getErrors().add(new PortScanError(s));
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
