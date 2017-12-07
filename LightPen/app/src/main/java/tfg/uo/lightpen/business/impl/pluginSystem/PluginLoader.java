package tfg.uo.lightpen.business.impl.pluginSystem;


import java.util.ArrayList;

import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;

/**
 * Created by Iñigo on 27/01/2017.
 * TODO Clase que carga los plugins que esten preparados para ejecutar
 */
public interface PluginLoader{

    /**
     * Metodo que cargara los plugins y devolvera una lista con los que se encuentran listos
     * para realizar el analisis
     * @return Lista de Plugins listos
     */
    public ArrayList<Plugin> pluginLoad();

}
