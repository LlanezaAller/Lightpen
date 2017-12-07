package tfg.uo.lightpen.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import tfg.uo.lightpen.infrastructure.factories.Factories;


public class BasicActivity extends AppCompatActivity {

    private int msgTime;
    public static final String PREFS_NAME = "config.File";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ocultarMenuNavegacion();
        setMsgTime(Integer.parseInt(Factories
                .business
                .createConfigReaderFactory()
                .createConfigReader()
                .run(getApplicationContext(), "msgTime")));
        SharedPreferences settings = getBaseContext().getSharedPreferences(PREFS_NAME, 0);
        setLocale(settings.getString("locale","en"));
    }

    /**
     * Metodo que oculta la barra inferior de navegacion en android
     */
    @TargetApi(19)
    private void hideVirtualButtons() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void ocultarMenuNavegacion() {
        // Ocultar botones navegacion
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            hideVirtualButtons();
        else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * Metodo que lanza mensajes
     * @param c contexto
     * @param msg mensaje
     * @param duration tiempo del mensaje
     */
    public void showMessage(Context c, String msg, int duration, int gravity){
        CharSequence text = msg;

        Toast toast = Toast.makeText(c, text, duration);
        //toast.setGravity(gravity, 0,0);
        toast.show();
    }

    /**
     * Metodo que devuelve el recurso de color necesario
     * @param id
     * @return
     */
    public int getColorId(int id){
        return getResources().getColor(id);
    }

    /**
     * Metodo que oculta el teclado
     * @param activity activity donde te encuentras || this
     */
    public void hideKeyboard(Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Metodo que cambia el idioma
     * @param lang codigo del idioma
     */
    public void setLocale(String lang) {
        //SharedPreferences settings = getBaseContext().getSharedPreferences("config.File", 0);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        if(!lang.equals(conf.locale.getLanguage())) {
            createLocale(lang);
            Locale myLocale = new Locale(lang);
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
    }

    /**
     * Metodo que crea la variable de idioma
     * @param lang
     */
    public void createLocale(String lang){
        SharedPreferences.Editor editor =
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("locale", lang);
        editor.commit();
    }


    //region Validaciones de la vista

    /**
     * Metodo que comprueba la validez de una URL
     * @param url url en formato string P.Ej. https://www.uniovi.es
     * @return Url valida
     */
    public static URL validURL(String url){
        URL ur;
        try {
            ur = new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
        return ur;
    }

    //endregion

    //region Setter & Getter
    public void setMsgTime(int time){
        this.msgTime = time;
    }
    public int getMsgTime(){
        return msgTime;
    }
    //endregion
}
