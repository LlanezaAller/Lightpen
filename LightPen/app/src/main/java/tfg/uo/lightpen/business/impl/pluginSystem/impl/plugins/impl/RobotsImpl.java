package tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.impl;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import tfg.uo.lightpen.R;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.Error;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.RobotsError;
import tfg.uo.lightpen.business.impl.pluginSystem.impl.plugins.errors.riskRating.OwaspRiskRating;
import tfg.uo.lightpen.business.impl.networking.impl.networkOperations.HttpOperations.Resources;
import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 22/4/17.
 * Plugin realizado para el analisis del fichero robots.txt
 */
public class RobotsImpl{

    private static final String TAG = "ROBOTS_IMPL";
    ArrayList<Error> errors = new ArrayList<>();
    URL url;
    private ContextData ctxD;

    public RobotsImpl(URL url, ContextData ctxD){
        setUrl(url);
        setContextData(ctxD);
    }

    public ArrayList<Error> getPentest(){
        String robots = getRobots();
        if(robots != null)
            analize(robots);
        return getErrors();
    }

    private String getRobots(){
        String parsedUrl = getUrl().getHost();
        String robots = "";

        if(getUrl().toString().contains("https"))
            parsedUrl = "https://" + parsedUrl;
        else
            parsedUrl = "http://" + parsedUrl;

        try {
            URL robotsDir = new URL(parsedUrl+"/robots.txt");
            Resources rs = new Resources();
            Factories.business
                    .createNetworkingFactory()
                    .createNetworking(ctxD)
                        .crawl(robotsDir, 0, rs);
            robots = rs.getResponse().get(robotsDir);
        } catch (MalformedURLException e) {
            Log.e(TAG, "getRobots:: ",e);
        }
        return robots;
    }

    private void analize(String robots) {
        String[] lines = robots.split("\\n");
        for(int i = 0; i< lines.length ; i++){
            check(lines[i], i+1);
        }
    }

    private final static String adminPathPattern = ".*/admin/.*"; // .../admin/...
    private final static String fileExtPattern = ".*\\.[a-zA-Z0-9]+$"; // example.csv
    private final static String fileExtNPatternNoHtml = ".*\\.((.?)htm(l?))+$"; // example.html
    private final static String fileInfoPhpPattern = ".*/info\\.(php)+$"; // info.php
    private final static String fileUpdatePhpPattern = ".*/update\\.(php)+$"; // update.php
    private final static String passwordPattern = ".*/password/*$"; // .../password/...
    private final static String installFilePattern = ".*/INSTALL\\.*$"; // INSTALL.xxx.yyy
    private final static String installationPathPattern = ".*/installation/.*$";
                            // .../installation/...
    private final static String profilesPathPattern = ".*/profiles/.*$"; //.../profiles/...
    private final static String apiPathPattern = ".*/api/.*"; // .../api/...
    private final static String testPathPattern = ".*/test/.*"; // .../test/...



    private void check(String text, int line) {
        String description = text +"  :found in line "+line+".";
        Error rob = null;
        if(text.matches(adminPathPattern))
            getErrors().add(new RobotsError(description +
                    getContextData().getContext().getString(R.string.rs_vulnerability_admin),
                    new OwaspRiskRating(9, 3, 6, 1).getRiskLevel()));
        if(text.matches(fileExtPattern)) {
            if (text.matches(fileInfoPhpPattern))
                getErrors().add(new RobotsError(description +
                        getContextData().getContext().getString(R.string.rs_vulnerability_infoPhP),
                        new OwaspRiskRating(9, 7, 6, 9).getRiskLevel()));
            else if (text.matches(fileUpdatePhpPattern))
                getErrors().add(new RobotsError(description +
                        getContextData().getContext().getString(R.string.rs_vulnerability_updatePhP),
                        new OwaspRiskRating(9, 4, 6, 9).getRiskLevel()));
            else if (!text.matches(fileExtNPatternNoHtml))
                getErrors().add(new RobotsError(description +
                        getContextData().getContext().getString(R.string.rs_vulnerability_fileNoHtml),
                        new OwaspRiskRating(9, 1, 5, 9).getRiskLevel()));
        }if(text.matches(passwordPattern))
            getErrors().add(new RobotsError(description +
                    getContextData().getContext().getString(R.string.rs_vulnerability_password),
                    new OwaspRiskRating(9, 3, 6, 9).getRiskLevel()));
        if(text.matches(installFilePattern))
            getErrors().add(new RobotsError(description +
                    getContextData().getContext()
                            .getString(R.string.rs_vulnerability_installationFile),
                    new OwaspRiskRating(9, 7, 6, 9).getRiskLevel()));
        if(text.matches(installationPathPattern))
            getErrors().add(new RobotsError(description +
                    getContextData().getContext()
                            .getString(R.string.rs_vulnerability_installationPath),
                    new OwaspRiskRating(9, 4, 6, 9).getRiskLevel()));
        if(text.matches(profilesPathPattern))
            getErrors().add(new RobotsError(description +
                    getContextData().getContext()
                            .getString(R.string.rs_vulnerability_profilesPath),
                    new OwaspRiskRating(9, 2, 6, 9).getRiskLevel()));
        if(text.matches(apiPathPattern))
            getErrors().add(new RobotsError(description +
                    getContextData().getContext()
                            .getString(R.string.rs_vulnerability_apiPath),
                    new OwaspRiskRating(9, 2, 6, 9).getRiskLevel()));
        if(text.matches(testPathPattern))
            getErrors().add(new RobotsError(description +
                    getContextData().getContext()
                            .getString(R.string.rs_vulnerability_testPath),
                    new OwaspRiskRating(9, 8, 8, 9).getRiskLevel()));

    }

    //region Getters & Setters
    private void setUrl(URL url){
        this.url = url;
    }
    private URL getUrl(){
        return url;
    }
    private void setErrors(ArrayList<Error> errores){
        this.errors = errores;
    }
    private ArrayList<Error> getErrors(){
        return errors;
    }
    public ContextData getContextData() {
        return ctxD;
    }

    public void setContextData(ContextData ctxD) {
        this.ctxD = ctxD;
    }
    //endregion
}
