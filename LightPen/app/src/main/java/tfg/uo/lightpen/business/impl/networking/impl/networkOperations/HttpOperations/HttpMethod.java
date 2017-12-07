package tfg.uo.lightpen.business.impl.networking.impl.networkOperations.HttpOperations;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;

import tfg.uo.lightpen.business.impl.networking.impl.networkOperations.NetworkOperation;

/**
 * Created by Iñigo Llaneza Aller on 3/5/17.
 */

public class HttpMethod extends NetworkOperation {

    private static final String TAG = "HTTPMethodSOBTAINER";
    private HttpURLConnection con;
    private HashMap<URL, String> response = new HashMap<>();
    private int timeout;

    public HttpMethod(){}

    @Override
    public void run() { getHttpMethodFromURL(con, getCon().getURL(), getTimeout()); }


    public void getHttpMethodFromURL(HttpURLConnection con, URL url, int timeout){
        try{
            if (con.getResponseCode() == 200) { //codigo correcto
                response.put(url, con.getRequestMethod());
            }
        }catch (SocketTimeoutException e){
            Log.e(TAG, " getHttpMethodFromURL: ", e);
        }catch (UnknownHostException e){
            Log.e(TAG, " getHttpMethodFromURL: ", e);
        }catch (Exception e) {
            Log.e(TAG, " getHttpMethodFromURL: ", e);
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

    public HashMap<URL, String> getResponse() {
        return response;
    }

    public void setResponse(HashMap<URL, String> response) {
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
