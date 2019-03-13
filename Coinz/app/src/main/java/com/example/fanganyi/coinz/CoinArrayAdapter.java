package com.example.fanganyi.coinz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CoinArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] content;

    public CoinArrayAdapter(Context context,  String[] content){
        super(context, R.layout.coinrow, content);
        this.context=context;
        this.content=content;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //format of each row
        View rowView = inflater.inflate(R.layout.coinrow, parent, false);
        TextView textview = (TextView) rowView.findViewById(R.id.value);
        Button send = (Button) rowView.findViewById(R.id.bt);
        Button bank = (Button) rowView.findViewById(R.id.bankin);

        // Set text to each TextView of ListView item
        //set tag to buttons
        textview.setText(content[position].substring(0,6));
        send.setTag(content[position].substring(7));
        bank.setTag(content[position].substring(7));
        return rowView;

    }




}
