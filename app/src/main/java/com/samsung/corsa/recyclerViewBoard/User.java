package com.samsung.corsa.recyclerViewBoard;

import android.util.Log;

public class User implements Comparable<User> {
    String rating;
    String username;

    public User(String rating, String username) {
        this.rating = rating;
        this.username = username;
    }

    @Override
    public int compareTo(User o) {
        int sub = Integer.parseInt(this.rating) - Integer.parseInt(o.rating);
        Log.i("TAG", this.rating);
        if (sub != 0)
            return sub;
        else if(this.username.equals(o.username))
            return 1;
        else
            return -1;
    }
}
