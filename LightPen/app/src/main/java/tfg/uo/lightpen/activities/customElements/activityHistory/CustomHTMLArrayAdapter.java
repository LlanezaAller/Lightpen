package tfg.uo.lightpen.activities.customElements.activityHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tfg.uo.lightpen.R;
import tfg.uo.lightpen.infrastructure.factories.Factories;
import tfg.uo.lightpen.model.ContextData;

/**
 * Created by Iñigo Llaneza Aller on 16/4/17.
 */

public class CustomHTMLArrayAdapter extends ArrayAdapter<HTMLRow> implements
        View.OnClickListener {

    private LayoutInflater layoutInflater;

    public CustomHTMLArrayAdapter(Context context, List<HTMLRow> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // holder pattern
        CustomHTMLArrayAdapter.Holder holder = null;

        if (convertView == null) {
            holder = new CustomHTMLArrayAdapter.Holder();

            convertView = layoutInflater.inflate(R.layout.listview_html, parent, false);

            holder.setTextViewTitle((TextView) convertView
                    .findViewById(R.id.textViewTitle));

            convertView.setTag(holder);

        } else {
            holder = (CustomHTMLArrayAdapter.Holder) convertView.getTag();
        }

        final HTMLRow row = getItem(position);
        String fileName =
                Factories
                        .business
                        .createHtmlBuilderFactory()
                        .parseNameFile(new ContextData(getContext()),row.getFile().getName());
        holder.getTextViewTitle().setText(fileName);//row.getFile().getName());

        return convertView;
    }


    @Override
    public void onClick(View v) {}

    static class Holder
    {
        TextView textViewTitle;

        //region Setters & Getters
        public TextView getTextViewTitle()
        {
            return textViewTitle;
        }

        public void setTextViewTitle(TextView textViewTitle)

        {
            this.textViewTitle = textViewTitle;
        }
        //endregion
    }
}
