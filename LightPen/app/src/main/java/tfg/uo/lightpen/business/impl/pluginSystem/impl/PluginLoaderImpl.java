package tfg.uo.lightpen.business.impl.pluginSystem.impl;


import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import dalvik.system.DexFile;
import tfg.uo.lightpen.business.impl.pluginSystem.PluginLoader;
import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo
 */
public class PluginLoaderImpl implements PluginLoader {

    private static final String TAG = "PluginLoader";
    private ContextData ct;
    private String PATH;
    private String prefix;

    public PluginLoaderImpl(ContextData ct){

        setContextData(ct);
        setPath(Factories
                .business
                .createConfigReaderFactory()
                .createConfigReader()
                .run(getContextData().getContext(), "plugin_path"));
        Log.d(TAG, "PluginLoader: Cargada ruta de plugins: "+getPATH());
        setPluginPrefix(Factories
                .business
                .createConfigReaderFactory()
                .createConfigReader()
                .run(getContextData().getContext(), "plugin_prefix"));
        Log.d(TAG, "PluginLoader: Cargado sufijo de plugins: "+ getPluginPrefix());
    }

    /**
     * Metodo que cargara los plugins y devolvera una lista con los que se encuentran listos
     * para realizar el analisis
     * @return Lista de Plugins listos
     */
    @Override
    public ArrayList<Plugin> pluginLoad(){
        Log.d(TAG, "PluginLoad: Comenzamos la carga de plugins");
        ArrayList<String> pluginNames = getClassesOfPackage(getPATH());
        Log.d(TAG, "PluginLoad: Se han encontrado "+ pluginNames.size()+ " plugins");
        ArrayList<Plugin> plugins = new ArrayList<>();

        Class newPlugin;
        try{
            for(String p : pluginNames) {
                //Se crean las clases segun los nombres encontrados
                newPlugin = Class.forName(getPATH()+"."+p);
                //Comprobamos que implementen la interfaz Plugin
                if(newPlugin.newInstance() instanceof Plugin)
                    plugins.add((Plugin) newPlugin.newInstance());
            }

        }catch(ClassNotFoundException e) {
            Log.e(TAG, "pluginLoad: No ha podido encontrarse la clase\n"+e.getMessage());
        } catch (InstantiationException e) {
            Log.e(TAG, "pluginLoad: No se ha podido instanciar la clase\n"+e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(TAG, "pluginLoad: No se ha podido acceder a la clase\n"+e.getMessage());
        }

        return plugins;
    }

    /**
     * Metodo que obtiene las clases de un paquete del proyecto
     * @param packageName nombre del paquete
     * @return Array con los nombres de los diferentes ficheros
     */
    private ArrayList<String> getClassesOfPackage(String packageName) {
        ArrayList<String> classes = new ArrayList<String>();
        try {
            String packageCodePath = ct.getContext().getPackageCodePath();
            DexFile df = new DexFile(packageCodePath);
            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); ) {
                //Vamos obeniendo los nombres de las clases
                String className = iter.nextElement();
                if (className.contains(packageName)) {
                    if(className.contains(getPluginPrefix()))
                        classes.add(
                                className.substring(
                                        className.lastIndexOf(".") + 1, className.length()));
                }
            }
        } catch (IOException e) {
            Log.e(TAG,"getClassesOfPackage: Error de Carga de clases\t"+e.getMessage());
        }

        return classes;
    }


    //region Setters & Getters
    private ContextData getContextData(){
        return this.ct;
    }
    private void setContextData(ContextData ct){
        this.ct = ct;
    }
    private String getPATH(){ return this.PATH;}
    private void setPath(String PATH){ this.PATH = PATH;}
    private void setPluginPrefix(String prefix){this.prefix = prefix;}
    private String getPluginPrefix(){return this.prefix;}
    //endregion

}
