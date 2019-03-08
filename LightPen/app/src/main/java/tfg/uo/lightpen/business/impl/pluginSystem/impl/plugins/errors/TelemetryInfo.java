package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors;

public class TelemetryInfo extends Error {

    public TelemetryInfo(String msg){
        this.setDescription(msg);
        this.setVersion(1.0);
        this.setRisk(0.0);
    }

    @Override
    public String printError() {
        return getDescription();
    }
}
