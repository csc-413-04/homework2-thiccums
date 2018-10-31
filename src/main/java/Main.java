package main.java;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
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

      // /login?username=<username>&password=<pass>
      get("/login", (req, res) -> {
          String username = req.queryParams("username");
          String password = req.queryParams("password");



          Document search = users.find(eq("username", username)).first();
          String token = search.get("_id").toString();

          long temp = new Date().getTime();
          Timestamp stamp = new Timestamp(temp);
          long time = stamp.getTime();
          Date date = new Date(time);



          if (search != null) {
              String userPassword = search.getString(("password"));


              System.out.print(password + " " + userPassword);
              if (password.equalsIgnoreCase(userPassword)) {
                  Document authorize = new Document("token", token).append("user", username).append("date", date);
                  auth.insertOne(authorize);
                  return "Token: " + token;
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
          ArrayList friends = new ArrayList();

          Document newUser = new Document("username", username).append("password", password).append("friends", friends);
          users.insertOne(newUser);
          return "okay";



      });

      // /addfriend?token=<token>&friend=<friendsuserid>
      get("/addfriend", (req, res) -> {
          String token = req.queryParams("token");
          String friend = req.queryParams("friend");

          Document searchToken = auth.find(eq("token", token )).first();
          Document searchUser = users.find(eq("username", friend)).first();

          if(searchToken != null && searchUser != null){

              String user = searchToken.getString("user");
              Document tokenUser = users.find(eq("username", user)).first();
              Bson document = new Document ("username", tokenUser.get("username"));
              Bson add = new Document("friends", friend);
              Bson updateDoc = new Document("$push", add);
              users.updateOne(document, updateDoc);

              return "okay";
          }
          return "failed_authentication";
      });

      get("/friends", (req, res) -> {
          String token = req.queryParams("token");
          Document searchToken = auth.find(eq("token", token)).first();
          String user = searchToken.getString("user");
          Document tokenUser = users.find(eq("username", user)).first();

          if(tokenUser != null){
             return "List of " + tokenUser.getString("username")+"'s friends: " + tokenUser.get("friends").toString();
          }
          else{
              return "failed_authentication";
          }



      });


    }



}
