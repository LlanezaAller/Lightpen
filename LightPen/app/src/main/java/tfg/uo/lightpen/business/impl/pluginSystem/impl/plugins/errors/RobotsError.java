package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors;


/**
 * Created by Iñigo Llaneza Aller on 25/4/17.
 */

public class RobotsError extends Error {

    private final static Double version = 0.001;

    public RobotsError(String description , Double risk){super( description , version, risk);}

    @Override
    public String printError() {

        String err =  "<td> "+getDescription()+ " </td>\n";
        err+= "<td class='risk-level'> "+getRisk()+ " </td>\n";

        return err;
    }
}
