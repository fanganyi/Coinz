package com.example.fanganyi.coinz;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        //set bankvalue text view
        TextView valueView = findViewById(R.id.bankvalue);
        valueView.setText("GOLD IN BANK: "+String.valueOf(User.getBankvalue()));

        //set rates text view
        TextView ratesView = findViewById(R.id.rates);
        ratesView.setText("CURRENCY RATES OF THE DAY: "+
                            "\nDOLR: "+Rates.getDOLR()+
                            "\nSHIL: "+Rates.getSHIL()+
                            "\nPENY: "+Rates.getPENY()+
                            "\nQUID: "+Rates.getQUID());

    }
}
