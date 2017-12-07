package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors;

import tfg.uo.lightpen.business.impl.pluginSystem.impl.Plugin;

/**
 * Created by Iñigo Llaneza Aller on 6/3/17.
 * Clase basica de error, definiremos aqui detalles basicos como la version, el
 * plugin que lo ha generado
 */

public abstract class Error {

    private String description;
    private Double version;
    private Double risk;

    public Error(){}

    public Error(String description, Double version, Double risk){
        setDescription(description);
        setVersion(version);
        setRisk(risk);
    }

    public abstract String printError();

    //region Getters & Setters

    public void setVersion(Double v){
        this.version = v;
    }

    public Double getVersion(){
        return this.version;
    }

    public void setRisk(Double d){ this.risk = d;}

    public Double getRisk(){ return this.risk; }

    public void setDescription (String description) { this.description = description; }

    public String getDescription(){ return this.description; }
    //endregion
}
