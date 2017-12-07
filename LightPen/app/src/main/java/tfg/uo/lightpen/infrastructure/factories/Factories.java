package tfg.uo.lightpen.infrastructure.factories;

import tfg.uo.lightpen.business.BusinessFactory;
import tfg.uo.lightpen.business.impl.SimpleBusinessFactory;

/**
 * Created by Iñigo on 27/01/2017.
 */

public class Factories {

    /**
     * Fachada de la capa del controlador
     */
    public static BusinessFactory business = new SimpleBusinessFactory();


}
