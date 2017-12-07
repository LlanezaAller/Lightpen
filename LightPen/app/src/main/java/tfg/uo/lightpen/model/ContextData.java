package tfg.uo.lightpen.model;

import android.content.Context;
import android.widget.ProgressBar;

/**
 * Created by Iñigo Llaneza Aller on 22/2/17.
 * Clase que guardara los datos necesarios del contexto para el resto de las capas
 */

public class ContextData {

    Context context;
    ProgressBar pBar;

    public ContextData(){}

    /**
     * Constructor
     * @param ct contexto de la aplicacion
     */
    public ContextData(Context ct){
        setContext(ct);
    }

    public ContextData(Context ct, ProgressBar pb){
        setContext(ct);
        setProgressBar(pb);
    }

    //region Getters & Setters
    public void setContext (Context ct){
        this.context = ct;
    }

    public Context getContext (){
        return this.context;
    }

    public ProgressBar getProgressBar(){ return pBar; }

    public void setProgressBar(ProgressBar pb ){ this.pBar = pb; }
    //endregion
}
