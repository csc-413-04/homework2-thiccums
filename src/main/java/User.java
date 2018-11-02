package main.java;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class User{
    @Id
    private ObjectId id;
    private String username;
    private String password;

    // just some getters and setters

    public User(){
    }
    public User(String username, String password){
        super();
        this.username = username;
        this.password = password;
    }
    public String getUser() {
        return username;
    }
    public void setUser(String username) {
        this.username = username;
    }
    public String getPass() {
        return password;
    }
    public void setPass(String password) {
        this.password = password;
    }
}

