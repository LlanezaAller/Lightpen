package tfg.uo.lightpen.business.impl.networking.impl.networkOperations.HttpOperations;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import tfg.uo.lightpen.business.impl.networking.impl.networkOperations.NetworkOperation;

/**
 * Created by Iñigo Llaneza Aller on 3/5/17.
 */

public class Resources extends NetworkOperation {

    private static final String TAG = "RESOURCEOBTAINER";
    private HttpURLConnection con;
    private HashMap<URL, String> response = new HashMap<>();

    @Override
    public void run() {
        try {
            response.put(con.getURL(), readStream(con.getInputStream()));
        } catch (IOException e) {
            Log.e(TAG, "run: Fallo con el fichero", e);
        }
    }

    public Resources(HttpURLConnection con){
        setCon(con);
    }

    public Resources(){}

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line+"\n");
            }
        } catch (IOException e) {
            Log.e(TAG, "readStream: Fallo con el fichero", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "readStream: Problema cerrando el fichero", e);
                }
            }
        }
        return response.toString();
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
    //endregion
}
