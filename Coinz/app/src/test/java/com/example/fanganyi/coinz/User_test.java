package com.example.fanganyi.coinz;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
public class User_test {
    private static final Coin coin1 = new Coin(-3.187067851289144, 55.94583163791626,
            "700d-b6f2-da88-a5dc-6002-cabe",9.54859807530815, "PENY","9");
    private static final Coin coin2 = new Coin(-3.1868301923640945, 55.943010536823124,
            "c72a-eaaf-ba19-170e-7b03-974b", 0.5901685593209194, "DOLR", "0");
    private static final Coin coin3 = new Coin(-3.1920692692309576, 55.94345615342773,
            "3be4-6ec8-728f-01c0-1c76-3156", 4.504042669740328, "SHIL", "4");
    private static final Coin coin4 = new Coin(-3.1864827000440195, 55.9429233986976,
            "e97c-cf81-353b-1780-f6dc-1913", 3.1596871075784163, "QUID", "3");
    private static final Coin coin5 = new Coin(-3.18891986593543, 55.945371035610975,
            "6379-38cd-7830-d8de-e089-00af", 4.324394843951783, "SHIL", "4");
    private static final Coin coin6 = new Coin(-3.1893803187184475, 55.94394805676217,
            "c84b-6084-620c-9798-963e-46ce", 4.539296874164929, "SHIL", "4");

    private HashMap<String, Coin> wallet = new HashMap<String, Coin>();
    private HashMap<String, User> friends = new HashMap<String, User>();
    private ArrayList<Coin> bankedCoin = new ArrayList<>();

    private User friend = new User("meinitial");
    private User me;


    public void setUser(){
        wallet.put(coin1.getCoinID(), coin1);
        wallet.put(coin2.getCoinID(), coin2);
        wallet.put(coin3.getCoinID(), coin3);
        wallet.put(coin4.getCoinID(), coin4);
        friends.put(friend.getid(), friend);
        bankedCoin.add(coin5);
        me = new User("me", 4.9,wallet,friends,bankedCoin);
        User.setUser(me);

    }

    @Test
    public void testgetBankvalue(){
        setUser();
        assertEquals(4.9,User.getBankvalue(),0);
    }

    @Test
    public void testaddBankvalue(){
        setUser();
        Rates.setDOLR(9.0);
        Rates.setPENY(13.0);
        Rates.setQUID(11.0);
        Rates.setSHIL(7.0);

        User.addBankvalue(coin1);
        assertEquals(4.9+coin1.getValue()*Rates.getPENY(), User.getBankvalue(),0);
    }

    @Test
    public void testaddCoin(){
        setUser();
        assertEquals(false, User.getS_wallet().containsKey(coin6.getCoinID()));
        assertEquals(false,User.getS_wallet().containsValue(coin6));
        User.addCoin(coin6);
        assertEquals(true, User.getS_wallet().containsKey(coin6.getCoinID()));
        assertEquals(true,User.getS_wallet().containsValue(coin6));
    }

    @Test
    public void testremoveCoin(){
        setUser();
        User.removeCoin(coin1);
        assertEquals(false, User.getS_wallet().containsKey(coin1.getCoinID()));
        assertEquals(false,User.getS_wallet().containsValue(coin1));
        assertEquals(true, User.getS_wallet().containsKey(coin2.getCoinID()));
        assertEquals(true,User.getS_wallet().containsValue(coin2));

    }

    @Test
    public void testgetId(){
        setUser();
        assertEquals("me", User.getId());
    }

    @Test
    public void testgetS_friends(){
        setUser();
        assertEquals(friends, User.getS_friends());
    }

    @Test
    public void testaddfriend(){
        setUser();
        User.addfriend(me);
        assertEquals(true , User.getS_friends().containsValue(me));
        assertEquals(true, User.getS_friends().containsKey("me"));
    }

    @Test
    public void testgetS_wallet(){
        setUser();
        assertEquals(wallet, User.getS_wallet());
    }

    @Test
    public void testaddBankedcoin(){
        setUser();
        User.addBankedcoin(coin6);
        assertEquals(true, User.getS_bankedCoin().contains(coin6));
    }

    @Test
    public void testgetS_bankedCoin(){
        setUser();
        bankedCoin.add(coin6);
        assertEquals(bankedCoin,User.getS_bankedCoin());
    }

    @Test
    public void testgetid(){
        setUser();
        assertEquals("me", me.getid());
    }

}
