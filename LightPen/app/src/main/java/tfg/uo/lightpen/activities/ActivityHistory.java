package tfg.uo.lightpen.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import tfg.uo.lightpen.R;
import tfg.uo.lightpen.activities.customElements.activityHistory.CustomHTMLArrayAdapter;
import tfg.uo.lightpen.activities.customElements.activityHistory.HTMLRow;
import tfg.uo.lightpen.infrastructure.factories.Factories;

public class ActivityHistory extends BasicActivity {

    private static final String TAG = "ActivityHistory";
    final ArrayList<HTMLRow> htmlRows = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        loadHtmlResults();



        ListView list = (ListView) findViewById(R.id.activityHistory_listHTML);
        list.setAdapter(new CustomHTMLArrayAdapter(this, htmlRows));



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                Intent intent  = new Intent(ActivityHistory.this, ActivityPentestView.class);
                intent.putExtra("pentest", htmlRows.get(position).getFile() );
                intent.putExtra("nav", "history");

                startActivity(intent);
                finish();

            }
                @SuppressWarnings("unused")
                public void onClick(View v){
                };
            });}


    private void loadHtmlResults() {

        String path = Factories
                .business
                .createConfigReaderFactory()
                .createConfigReader()
                .run(getApplicationContext(), "results_path");

        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), path);
        File[] results = file.listFiles();

        if(results != null && results.length>0) {
            for (int i = 0; i < results.length; i++)
                htmlRows.add(new HTMLRow(results[i]));

        }else{
            showMessage(getBaseContext(),
                    getResources().getString(R.string.history_noResults),
                    Gravity.BOTTOM,getMsgTime());
        }
    }

    public void back(View view){
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
