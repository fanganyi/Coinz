package com.example.fanganyi.coinz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class FriendsAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] content;

    public FriendsAdapter(Context context, String[] content) {
        super(context, R.layout.coinrow, content);
        this.context = context;
        this.content = content;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //format of each row
        View rowView = inflater.inflate(R.layout.simplerow, parent, false);
        TextView textview = (TextView) rowView.findViewById(R.id.rowTextView);
        Button sendto = (Button) rowView.findViewById(R.id.sendto);

        // Set text to each TextView of ListView item
        textview.setText(content[position]);
        sendto.setTag(content[position]);
        return rowView;
    }
}


