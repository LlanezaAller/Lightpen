package tfg.uo.lightpen.business.impl.configReader;

import java.io.IOException;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import tfg.uo.lightpen.R;

/**
 * Created by Iñigo Llaneza Aller on 1/3/17.
 * Clase que carga datos del fichero de configuracion
 */

public interface ConfigReader {

    /**
     * Metodo de carga de datos
     * @param ct contexto de la aplicacion
     * @param nombre nombre del parametro a cargar
     * @return valor del parametro solicitado
     */
    public  String run(Context ct, String nombre) ;

    public  String convertStreamToString(InputStream is) throws IOException;

    public  String getStringFromFile (String filePath) throws IOException ;
}
