package tfg.uo.lightpen.business.impl.pluginSystem.impl;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 31/1/17.
 * Interfaz que deben implementar todos los Plugins que deseen incluirse en la aplicacion
 */
public interface Plugin {

    /**
     * Metodo que comienza la ejecucion del plugin
     * @param dir direccion objetivo
     * @return elemento JSON con los resultados de las pruebas
     */
    public ArrayList<Error> run(URL dir);

    /**
     * Metodo que devuelve una breve descripcion del plugin
     * @return descripcion del plugin
     */
    public String showDescription();

    /**
     * Metodo que devuelve el nombre del Plugin
     * @return nombre del plugin
     */
    public String showName();

    /**
     * Metodo que devuelve la version del plugin
     * @return version del plugin
     */
    public Double showVersion();


    /**
     * Metodo que permite al plugin decidir como sera su salida
     * @param errores Errores detectados por este plugin
     * @return codigo html de su propio analisis
     */
    public String printErrors(ArrayList<Error> errores);

    /**
     * Metodo que permitira al plugin acceder al contexto para las operaciones que lo necesiten
     */
    public void setContextData(ContextData ctxD);
}
