package tfg.uo.lightpen.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import tfg.uo.lightpen.R;
import tfg.uo.lightpen.infrastructure.factories.Factories;

public class ActivityStart extends BasicActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        loadSetupData();
    }

    /**
     * Metodo que carga la activity donde se realizaran las pruebas
     * @param view
     */
    public void penTestStart(View view){
        Intent actividad = new Intent(getApplicationContext(), ActivityPentest.class);
        startActivity(actividad);
    }

    /**
     * Metodo que carga la activity donde se visualizaran las pruebas anteriores
     * @param view
     */
    public void historyStart(View view){
        Intent actividad = new Intent(getApplicationContext(), ActivityHistory.class);
        startActivity(actividad);
    }

    /**
     * Metodo de configuracion inicial
     * @param view
     */
    public void setUp(View view){

        FrameLayout fragment = (FrameLayout) findViewById(R.id.fragmentMenuInicio);

        if(fragment.getVisibility() == View.VISIBLE) {
            fragment.setVisibility(View.INVISIBLE);

            FrameLayout fragmentSetup = (FrameLayout) findViewById(R.id.fragmentMenuOpciones);
            fragmentSetup.setVisibility(View.VISIBLE);
        }else{
            fragment.setVisibility(View.VISIBLE);

            FrameLayout fragmentSetup = (FrameLayout) findViewById(R.id.fragmentMenuOpciones);
            fragmentSetup.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Metodo de cierre de aplicacion
     */
    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityStart.this);
        builder.setTitle(getString(R.string.appStartCloseDialogTitle));

        String positiveText = getString(android.R.string.yes);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    });

        String negativeText = getString(android.R.string.no);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    /**
     * Metodo de carga de datos para la configuración
     */
    private void loadSetupData() {

        DiscreteSeekBar threads = (DiscreteSeekBar) findViewById(R.id.setupSeekBar_Threads);
        DiscreteSeekBar deep = (DiscreteSeekBar) findViewById(R.id.setupSeekBar_UrlDeep);

        int maxThreads = Integer.parseInt(Factories
                .business
                .createConfigReaderFactory()
                .createConfigReader()
                .run(getApplicationContext(), "maxThreads"));
        int maxUrlDeep = Integer.parseInt(Factories
                .business
                .createConfigReaderFactory()
                .createConfigReader()
                .run(getApplicationContext(), "maxUrlDeep"));

        SharedPreferences settings = getBaseContext().getSharedPreferences("config.File", 0);

        if(settings.getInt("maxThreads", -1) == -1) {
            SharedPreferences.Editor editor =
                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

            editor.putInt("maxThreads", maxThreads);
            editor.putInt("maxUrlDeep", maxUrlDeep);
            editor.commit();
        }
        if(settings.getString("locale", "default").equals("default")){
            setLocale("en");
        }

        threads.setMax(maxThreads);
        deep.setMax(maxUrlDeep);

        threadsSeekBarListenerCreation(threads);

        deepSeekBarListenerCreation(deep);

        threads.setProgress(settings.getInt("maxThreads", 1));
        deep.setProgress(settings.getInt("maxUrlDeep", 1));

        modifyLanguage(settings.getString("locale","en"));
    }

        private void languageButtonsSetUp(String lang){
            Button btn_locale_en = (Button) findViewById(R.id.setupButton_English);
            Button btn_locale_es = (Button) findViewById(R.id.setupButton_Spanish);
            switch(lang){
                case "en":
                    btn_locale_en.setActivated(false);
                    btn_locale_es.setActivated(true);
                    break;
                case "es":
                    btn_locale_en.setActivated(true);
                    btn_locale_es.setActivated(false);
                    break;
                default:
                    btn_locale_en.setActivated(false);
                    btn_locale_es.setActivated(true);
                    break;
            }
    }

    private void deepSeekBarListenerCreation(DiscreteSeekBar deep) {
        deep.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {

            SharedPreferences.Editor editor =
                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int progress, boolean b) {
                if(progress==0)
                    progress++;
                editor.putInt("maxUrlDeep", progress);
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                if(seekBar.getProgress()==0)
                    seekBar.setProgress(1);
                showMessage(getBaseContext(), getString(R.string.start_msg_start_seekBar),
                        Gravity.BOTTOM,getMsgTime());
                //showMessage(getBaseContext(),
                //        getString(R.string.start_msg_valueStablishedOnURL)+" "+seekBar.getProgress(),
                //        Gravity.BOTTOM,getMsgTime());
            }
        });
    }

    private void threadsSeekBarListenerCreation(DiscreteSeekBar threads) {


        threads.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {

            SharedPreferences.Editor editor =
                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int progress, boolean fromUser) {
                if(progress==0)
                    progress++;
                editor.putInt("maxThreads", progress);
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                if(seekBar.getProgress()==0)
                    seekBar.setProgress(1);
                //showMessage(getBaseContext(),
                //        getString(R.string.start_msg_valueStablishedOnThreads)+" "+seekBar.getProgress(),
                //        Gravity.BOTTOM,getMsgTime());
                showMessage(getBaseContext(), getString(R.string.start_msg_start_seekBar),
                        Gravity.BOTTOM,getMsgTime());
            }
        });
    }

    public void setEnglish(View view){
        modifyLanguage("en");
    }
    public void setSpanish(View view){
        modifyLanguage("es");
    }

    public void modifyLanguage(String lang){
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        if(!lang.equals(conf.locale.getLanguage())) {
            setLocale(lang);
            languageButtonsSetUp(lang);
            Intent refresh = new Intent(this, ActivityStart.class);
            startActivity(refresh);
            finish();
        }
    }




}
