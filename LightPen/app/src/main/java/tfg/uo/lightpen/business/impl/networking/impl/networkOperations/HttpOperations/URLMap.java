package tfg.uo.lightpen.business.impl.networking.impl.networkOperations.HttpOperations;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import tfg.uo.lightpen.business.impl.networking.impl.networkOperations.NetworkOperation;

/**
 * Created by Iñigo Llaneza Aller on 3/5/17.
 */

public class URLMap extends NetworkOperation {

    private static final String TAG = "URLMap";
    private HttpURLConnection con;
    private ArrayList<URL> response = new ArrayList<>();

    @Override
    public void run() {
        //Se utiliza el crawler de NetworkingImpl
    }

    public URLMap(HttpURLConnection con){
        setCon(con);
    }

    public URLMap(){}




    //region Setters & Getters

    public HttpURLConnection getCon() {
        return con;
    }

    public void setCon(HttpURLConnection con) {
        this.con = con;
    }

    public ArrayList<URL> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<URL> response) {
        this.response = response;
    }
    //endregion
}
