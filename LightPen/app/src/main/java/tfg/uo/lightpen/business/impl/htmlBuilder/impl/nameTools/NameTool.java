package tfg.uo.lightpen.business.impl.htmlBuilder.impl.nameTools;

import android.text.format.DateFormat;

import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 13/6/17.
 */

public class NameTool {
    ContextData ctxD;

    public NameTool(ContextData ctxD){
       setContextData(ctxD);
    }

    /**
     * Crea un fichero dada una ruta
     * @param url ruta
     * @return nombre del fichero
     */
    public String createTitle(String url){
        String date= DateFormat
                .format("EEE dd-MM-yyyy _ hh-mm-ss aaa", System.currentTimeMillis()).toString();
        String dateCode = DateFormat.format("ddMMyyyyhhmmss", System.currentTimeMillis()).toString();
        String filename = date+" Lightpen "+url+"_"+dateCode+".html";

        return filename;
    }

    /**
     * Devuelve una cadena de texto legible dado un nombre de fichero
     * @param title nombre del fichero
     * @return Nombre legible
     */
    public String parseTitle(String title){
        String ans;
        String[] sections = title.split("Lightpen");
        if(sections.length>1){
            return  "[" + sections[0].replace('_',' ') + "] " + sections[1].split("_")[0];
        }
        return title;
    }


    //region Setters && Getters

    public ContextData setContextData() {
        return ctxD;
    }

    public void setContextData(ContextData ctxD) {
        this.ctxD = ctxD;
    }

    //endregion
}
