package tfg.uo.lightpen.business.impl.configReader.impl;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import tfg.uo.lightpen.R;
import tfg.uo.lightpen.business.impl.configReader.ConfigReader;

/**
 * Created by Iñigo Llaneza Aller on 1/3/17.
 * Clase que carga datos del fichero de configuracion
 */

public class ConfigReaderImpl implements ConfigReader {
    private static final String TAG = "ConfigHelper";

    /**
     * Metodo de carga de datos
     * @param ct contexto de la aplicacion
     * @param nombre nombre del parametro a cargar
     * @return valor del parametro solicitado
     */
    @Override
    public  String run(Context ct, String nombre) {
        Resources resources = ct.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(nombre);
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "No ha podido cargarse el fichero: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "No ha podido abrirse el fichero");
        }
        return null;
    }

    @Override
    public String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        Boolean firstLine = true;
        while ((line = reader.readLine()) != null) {
            if(firstLine){
                sb.append(line);
                firstLine = false;
            } else {
                sb.append("\n").append(line);
            }
        }
        reader.close();
        return sb.toString();
    }

    @Override
    public String getStringFromFile (String filePath) throws IOException {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        try {
            String ret = convertStreamToString(fin);
            fin.close();
            return ret;
        }finally {
            fin.close();
        }
    }
}
