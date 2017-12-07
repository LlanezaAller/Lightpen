package tfg.uo.lightpen.activities.customElements.activityHistory;

import java.io.File;

import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;

/**
 * Created by Iñigo Llaneza Aller on 16/4/17.
 */

public class HTMLRow {

    private File file;

    public HTMLRow(File fl){
        setFile(fl);
    }

    //region Getters & Setters
    public void setFile(File fl){
        this.file = fl;
    }
    public File getFile(){
        return this.file;
    }
    //endregion

}
