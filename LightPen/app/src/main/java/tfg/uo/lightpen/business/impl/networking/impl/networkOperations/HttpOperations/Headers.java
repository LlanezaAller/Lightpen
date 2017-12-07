package tfg.uo.lightpen.business.impl.networking.impl.networkOperations.HttpOperations;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tfg.uo.lightpen.business.impl.networking.impl.networkOperations.NetworkOperation;

/**
 * Created by Iñigo Llaneza Aller on 3/5/17.
 */

public class Headers extends NetworkOperation {

    private static final String TAG = "HEADERSOBTAINER";
    private HttpURLConnection con;
    private HashMap<URL, Map<String, List<String>>> response = new HashMap<>();
    private int timeout;

    public Headers(){}

    @Override
    public void run() {
        getHeadersFromURL(con, getCon().getURL(), getTimeout());
    }


    public void getHeadersFromURL(HttpURLConnection con, URL url, int timeout){
        try{
            if (con.getResponseCode() == 200) { //codigo correcto
                response.put(url, con.getHeaderFields());
            }
        }catch (SocketTimeoutException e){
            Log.e(TAG, " getHeadersFromURL: ", e);
        }catch (UnknownHostException e){
            Log.e(TAG, " getHeadersFromURL: ", e);
        }catch (Exception e) {
            Log.e(TAG, " getHeadersFromURL: ", e);
        }
        finally {
            con.disconnect();
        }
    }

    //region Setters & Getters

    public HttpURLConnection getCon() {
        return con;
    }

    public void setCon(HttpURLConnection con) {
        this.con = con;
    }

    public HashMap<URL, Map<String, List<String>>> getResponse() {
        return response;
    }

    public void setResponse(HashMap<URL, Map<String, List<String>>> response) {
        this.response = response;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    //endregion
}
