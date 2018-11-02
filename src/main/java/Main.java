package main.java;
import java.util.ArrayList;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.conversions.Bson;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        staticFiles.externalLocation("public");
        // http://sparkjava.com/documentation
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        MongoDatabase db = mongoClient.getDatabase("REST2");
        MongoCollection<Document> users = db.getCollection("users");
        MongoCollection<Document> auth = db.getCollection("auth");

        port(1234);
        // calling get will make your app start listening for the GET path with the /hello endpoint
        get("/hello", (req, res) -> "Hello World");

        get("/login", (req, res) -> {
            String username = req.queryParams("username");
            String password = req.queryParams("password");

            Document lookup = users.find(eq("username", username)).first();
            String token = lookup.get("_id").toString();

            long temp = new Date().getTime();
            Timestamp stamp = new Timestamp(temp);
            long t1 = stamp.getTime();
            Date t2 = new Date(time);

            String userPassword = lookup.getString(("password"));
            if (password.equalsIgnoreCase(userPassword)) {
                Document loginAuth = new Document("token", token).append("user", username).append("timestamp", t2);
                auth.insertOne(loginAuth);
                return "Token: " + token;
            }
            return "login_failed";
        });
        ///login?username=<username>&password=<wrongpassword>
        //login_failed
        ///login?username=<username>&password=<pass>
        //return custom hash or token


        get("/newuser", (req, res) -> {

            String username = req.queryParams("username");
            String password = req.queryParams("password");
            ArrayList friends = new ArrayList();

            Document newUser = new Document("username", username).append("password", password).append("friends", friends);
            users.insertOne(newUser);
            return "okay";
        });
        ///newuser?username=<username>&password=<pass>
        //okay
        ///newuser?username=<anotheruser>&password=<pass>
        //okay


        get("/addfriend", (req, res) -> {
            String token = req.queryParams("token");
            String friend = req.queryParams("friend");
            Document lookupFriend = users.find(eq("username", friend)).first();
            Document lookupToken = auth.find(eq("token", token )).first();

            if(lookupToken != null && lookupFriend != null){
                String user = lookupToken.getString("user");
                Document tokenUser = users.find(eq("username", user)).first();
                Bson doc = new Document ("username", tokenUser.get("username"));
                Bson add = new Document("friends", friend);
                Bson updateDoc = new Document("$push", add);
                users.updateOne(doc, updateDoc);
                return "okay";
            }
            return "failed_authentication";
        });
        ///addfriend?token=<badtoken>&friend=<freindsuserid>
        //failed_authentication
        ///addfriend?token=<token>&friend=<freindsuserid>
        //okay


        get("/friends", (req, res) -> {
            String token = req.queryParams("token");
            Document lookupToken = auth.find(eq("token", token)).first();
            String user = lookupToken.getString("user");
            Document tokenUser = users.find(eq("username", user)).first();

            if(tokenUser != null) return tokenUser.get("friends").toString();

            return "failed_authentication";
        });
        ///friends?token=<token>
        //<otherfriendsid>
    }
}
