////set User.getS_wallet() to MainActivity.getCoins() when testing.
//

package com.example.fanganyi.coinz;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class WalletCoins extends ListActivity {

    private final String tag = "WalletCoins";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView lstview=(ListView)findViewById(R.id.coinListView);


        //if user has no friends, pop a test
        if(User.getS_wallet().isEmpty()){
            Toast.makeText(this, "You have no coins in your wallet.", Toast.LENGTH_LONG).show();

        }else {
            //initial the input string list content
            @NonNull String[] content = new String[User.getS_wallet().size()];
            //initial index of content
            int i=0;
            // for all the coins in wallet, get the information need as content
            for(@NonNull Coin c: User.getS_wallet().values()){
                content[i] = c.getSymbol()+" "+c.getCurrancy()+" "+c.getCoinID();
                i++;
            }
            //a log message to check content
            Log.d(tag, content[0].substring(6)+"\n"+content[0]);
            //listview the content with helper function CoinArrayAdapter
            setListAdapter(new CoinArrayAdapter(this, content));

        }
    }

    public void sendOut(View view){
        //get tag of the clicked button
        Button b = (Button) view;
        @NonNull String id = (String)b.getTag();
        //call FriendsActivity if user has friend
        if(User.getS_wallet().size()>0) {
            //remove coin from wallet
            User.removeCoin(User.getS_wallet().get(id));
            //parse coin id to FriendActivity
            FriendsActivity.setSelectedCoin(id);
            Intent open = new Intent(this, FriendsActivity.class);
            startActivity(open);
        }else{
            //if user have no friend, pop up a message.
            Toast.makeText(this, "cannot send this coin since you have no friend.", Toast.LENGTH_LONG).show();

        }
    }

    public void bankIn(View view){
        //get tag of the clicked button
        Button b = (Button) view;
        @NonNull String id = (String)b.getTag();
        //and relative gold value in bank
        User.addBankvalue(User.getS_wallet().get(id));
        //add this coin to banked coin list
        User.addBankedcoin(User.getS_wallet().get(id));
        //remove coin from wallet
        User.removeCoin(User.getS_wallet().get(id));
        Toast.makeText(this, "you just bank in a coin", Toast.LENGTH_LONG).show();

    }

}
