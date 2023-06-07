package com.example.corsa.recyclerViewBoard;

import java.util.Comparator;

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
        if (sub != 0)
            return sub;
        else if(this.username.equals(o.username))
            return 1;
        else
            return -1;
    }
}
