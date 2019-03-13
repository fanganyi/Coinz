package com.example.fanganyi.coinz;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class FriendsActivity extends ListActivity {
    private static String selectedCoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView lstview=(ListView)findViewById(R.id.friendListView);

        //if user has no friends, pop a message
        if(User.getS_friends().isEmpty()){
            Toast.makeText(this, R.string.no_friends, Toast.LENGTH_LONG).show();

        }else {
            //initial the input string list content
            String[] content = new String[User.getS_friends().size()];
            //initial index of content
            int i=0;
            // for each friend, get the id and save in content
            for(User u: User.getS_friends().values()){
                content[i] = u.getid();
                i++;
            }

            //listview the content with helper function FriendsAdapter
            setListAdapter(new FriendsAdapter(this, content));

        }
    }

    public void sendTo(View view){
        if(selectedCoin!=null){
            //get tag of the clicked button
            Button b = (Button) view;
            String userid = (String)b.getTag();
            //send the coin to this friend

            Toast.makeText(this, "you just send out a coin", Toast.LENGTH_LONG).show();
            //reset selected coin, preventing duplication.
            setSelectedCoin(null);
        }else {

            Toast.makeText(this, "please select a coin you want to send out.", Toast.LENGTH_LONG).show();
        }
        }

    public static void setSelectedCoin(String id){
        selectedCoin=id;
    }

}
