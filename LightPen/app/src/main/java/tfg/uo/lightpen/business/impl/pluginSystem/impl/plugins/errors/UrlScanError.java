package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors;


/**
 * Created by Iñigo Llaneza Aller on 2/5/17.
 */

public class UrlScanError extends Error{

    private final static Double version = 0.001;

    public UrlScanError(String description){
        super( description , version, 0.0);
    }

    @Override
    public String printError() {

        String err =  "<td> "+getDescription()+ " </td>\n";

        return err;
    }
}
