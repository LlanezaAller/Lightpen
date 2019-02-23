package tfg.uo.lightpen.business.impl.htmlBuilder.impl;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import tfg.uo.lightpen.business.impl.htmlBuilder.HtmlBuilder;
import tfg.uo.lightpen.business.impl.htmlBuilder.impl.nameTools.NameTool;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 20/2/17.
 */
public class HtmlBuilderImpl implements HtmlBuilder {

    private static final String TAG = "HTMLBuilder";
    private ContextData ctxD;
    private ConcurrentHashMap<Plugin, ArrayList<Error>> errores;
    private ArrayList<Plugin> plugins;




    public HtmlBuilderImpl(ContextData ct , ConcurrentHashMap<Plugin, ArrayList<Error>> er){
        setContextData(ct);
        setErrores(er);
        setPlugins(new ArrayList<>(er.keySet()));
    }

    /**
     * Metodo que crea el fichero donde se almacenaran los resultados de las pruebas
    */
    @Override
    public File processOutput(String url){

        String date= DateFormat
                .format("EEE dd/MM/yyyy - hh:mm:ss aaa", System.currentTimeMillis()).toString();
        String filename = new NameTool(ctxD).createTitle(url);
        String fileTitle = "Lightpen pentest for: "+ url;
        FileOutputStream outputStream;

        File f = createExternalStorageFile(ctxD, "results/"+filename); //creamos el nuevo fichero

        try {

            outputStream =
                    new FileOutputStream(f);

            String results = buildPentestResult();

            //Comienza la escritura del HTML
            String response = writeHTML(fileTitle, date.toString(), results);

            outputStream.write(response.getBytes());

            outputStream.close();

        } catch (IOException e){
            Log.e(TAG, "processInput: Error en el acceso/escritura al fichero"+e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "processInput: Error durante la interacción con el fichero"+e.getMessage());
        } finally{
            Log.d(TAG, "processInput: Finalizada tarea de escritura de fichero");
        }
        return f;
    }

    /**
     * Metodo que genera y copia el contenido del resultado con los errores
     * @return template html
     */
    private String writeHTML(String filename, String date, String result){
        String body = "";
        try {
            InputStream is = ctxD.getContext().getAssets().open("PentestResult.html");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = "";
            while ((line = reader.readLine()) != null){
                if(line.contains("{title}"))
                    body += filename;
                else if(line.contains("{date}"))
                    body += date;
                else if(line.contains("{pentest}"))
                    body += result;
                else
                    body += line;
                body += "\n";
            }

            reader.close();
        } catch (IOException e) {
            Log.e(TAG, "writeHtml: No ha podido accederse al fichero con el template", e);
        }
        return body;
    }


    /**
     * Metodo que itera sobre los elementos resultantes del analisis
     * @return
     */
    private String buildPentestResult() {
        String report ="";
        Enumeration<Plugin> plugins = errores.keys();
        Plugin pl;
        while(plugins.hasMoreElements()){
            pl = plugins.nextElement();
            report += resolvePluginReport(pl, errores.get(pl))+"\n";
        }
        return report;
    }

    /**
     * Metodo que itera sobre los elementos de un solo analisis
     * @param errors
     * @return
     */
    private String resolvePluginReport(Plugin pl, ArrayList<Error> errors) {
        String ans = pl.printErrors(errors);


        return ans;
    }


    /**
     * Metodo que extra el nombre de una url y crea un fichero temporal con ese nombre
     * @param contextData contextData donde se almacena el contexto de la app
     * @param url Direccion del fichero
     * @return fichero creado en almacenamiento temporal
     */
    public File createTempFile(ContextData contextData, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File
                    .createTempFile(fileName, null, contextData.getContext().getCacheDir());
        } catch (IOException e) {
            Log.e(TAG, "createTempFile: Error durante la creacion del fichero", e);
        }
        return file;
    }

    /**
     * Metodo de creacion de fichero en la memoria
     * @param contextData contextData donde se almacena el contexto de la app
     * @param url Direccion del fichero
     * @return fichero creado en almacenamiento externo
     */
    public File createExternalStorageFile(ContextData contextData, String url) {
        File file = null;
        try {
            if (!isExternalStorageWritable())
                throw new IllegalAccessException("No se ha podido acceder a la memoria");
            String dir = Factories
                    .business
                    .createConfigReaderFactory()
                    .createConfigReader()
                    .run(getContextData().getContext(), "results_path");
            File fileDir = getAlbumStorageDir("/lightpen/pentest");
            String fileName = Uri.parse(url).getLastPathSegment();
            file = new File(fileDir, fileName);

            //Hacemos que el documento este listo inmediatamente
            MediaScannerConnection.scanFile(contextData.getContext()
                    , new String[]{file.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });

        } catch (IllegalAccessException e) {
            Log.e(TAG, "createExternalStorageFile: Error durante el acceso del fichero", e);
        }
        return file;
    }

    /**
     * Obtiene el directorio de la carpeta deseada
     * @param albumName nombre de la carpeta a buscar
     * @return carpeta con nombre albumName
     */
    public File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "getAlbumStorageDir: directorio no creado");
        }
        return file;
    }


    //region Comprobar almacenamientos
    /**
     * Comprobacion del almacenamiento externo
     * @return true: se puede escribir y leer
     */
    public boolean isExternalStorageReady(){
        return isExternalStorageReadable() && isExternalStorageWritable();
    }

    /**
     * Metodo para comprobar que el almacenamiento externo permite esritura y lectura
     * @return
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Metodo para comprobar que el almacenamiento externo permite al menos lectura
     * @return
     */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }



    //endregion

    //region Setters & Getters
    private void setContextData(ContextData ct){
        this.ctxD = ct; }

    private ContextData getContextData(){
        return this.ctxD; }

    private void setErrores(ConcurrentHashMap<Plugin, ArrayList<Error>> er ){

        this.errores = er;
    }

    private ConcurrentHashMap<Plugin, ArrayList<Error>> getErrores(){
        return errores;
    }

    private void setPlugins(ArrayList<Plugin> p){
        this.plugins = p;
    }
    private ArrayList<Plugin> getPlugins(){
        return plugins;
    }
    //endregion
}
