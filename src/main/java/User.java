package main.java;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class User{
    private static final String USER_SESSION_ID = "sessionId";
    @Id
    
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
	public String getUsername() {
		return username;
	}
	public void setUsename(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
    }
    
    
}
