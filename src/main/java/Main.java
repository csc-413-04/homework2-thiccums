package main.java;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
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
        MongoCollection <Document> auth = db.getCollection("auth");


        System.out.println("Databse: " + db.getName());
        System.out.println("Collection: " + users.getNamespace());
        System.out.println("Collection: " + auth.getNamespace());



      port(1234);
      // calling get will make your app start listening for the GET path with the /hello endpoint
      get("/hello", (req, res) -> "Hello World");

      get("/login", (req, res) -> {
          String username = req.queryParams("username");
          String password = req.queryParams("password");



          Document search = users.find(eq("username", username)).first();
          long temp = new Date().getTime();
          Timestamp stamp = new Timestamp(temp);
          long time = stamp.getTime();
          Date date = new Date(time);



          if (search != null) {
              String userPassword = search.getString(("password"));


              System.out.print(password + " " + userPassword);
              if (password.equalsIgnoreCase(userPassword)) {
                  Object token = search.get("_id");
                  Document authorize = new Document("token", token).append("user", username).append("time", date);
                  auth.insertOne(authorize);
                  return "Token: " + date;
              } else {
                  return "password_incorrect";
              }
          } else {
              return "user_not_found";

          }



      });

      // /newuser?username=user&password=pw
      get("/newuser", (req, res) -> {

          String username = req.queryParams("username");
          String password = req.queryParams("password");

          Document newUser = new Document("username", username).append("password", password);
          users.insertOne(newUser);
          return "okay";



      });

      get("/addfriend", (req, res) -> {
          String token = req.queryParams("token");
          ObjectId searchToken = new ObjectId(token);
          Document search = users.find(eq("", searchToken )).first();
          if(search != null){
              return "okay";
          }
          return "failed_authentication";
      });

      get("/friends", (req, res) -> {
          HashSet<String> allFriends = new HashSet<String>();
          String token = req.queryParams("token");
          ObjectId searchToken = new ObjectId(token);
          Document search = users.find(eq("_id", searchToken)).first();
          

            return "listoffriendsuserid";
      });


    }



}
