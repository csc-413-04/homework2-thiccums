package main.java;
import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class User {
    private String username;
    private String password;
    private HashSet<User> friends;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        friends = new HashSet<User>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashSet<User> getFriends() {
        return friends;
    }

    public void setFriends(HashSet<User> friends) {
        this.friends = friends;
    }

    public void addfriend(User user ){

    }

    public String allfriends(){
        return friends.toString();

    }

    public boolean passwordVal(String pw){

        if( pw== this.password){
            return true;
        }
        else{
            return false;
        }
    }
}


