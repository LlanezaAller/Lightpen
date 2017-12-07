package tfg.uo.lightpen.business.impl.networking.impl.networkOperations;

import android.content.SharedPreferences;

import java.net.HttpURLConnection;

import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 3/5/17.
 */

public abstract class NetworkOperation {

    /**
     * Metodo base para ejecución de las operaciones de red
     */
    public abstract void run();

    /**
     * Configura la conexión
     * @param con
     */
    public abstract void setCon(HttpURLConnection con);

    /**
     * Obtiene la profundidad máxima para la navegación del crawler
     * @param ctxD
     * @return maxima profundidad de anvegacion
     */
    public int maxURLDeep(ContextData ctxD){
        SharedPreferences settings = ctxD.getContext().getSharedPreferences("config.File", 0);

        return settings.getInt("maxUrlDeep",
                Integer.parseInt(
                        Factories
                                .business
                                .createConfigReaderFactory()
                                .createConfigReader()
                                .run(ctxD.getContext(), "maxUrlDeep")));
    }

    /**
     * Obtiene el número máximo de hilos para las operaciones de escaneo de puertos
     * @param ctxD
     * @return numero maximo de hilos
     */
    public int maxThreads(ContextData ctxD){
        SharedPreferences settings = ctxD.getContext().getSharedPreferences("config.File", 0);

        return settings.getInt("maxThreads",
                Integer.parseInt(
                        Factories
                                .business
                                .createConfigReaderFactory()
                                .createConfigReader()
                                .run(ctxD.getContext(), "maxThreads")));
    }

}
