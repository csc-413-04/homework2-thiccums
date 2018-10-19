package midterm2018;
import com.google.gson.Gson;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        String question1and2 = "GET /add?a=3&b=4 HTTP/1.1\n"
            + "Host: localhost:1298\n"
            + "Connection: keep-alive\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36\n"
            + "Accept: image/webp,image/apng,image/*,*/*;q=0.8\n"
            + "Referer: http://localhost:1298/\n"
            + "Accept-Encoding: gzip, deflate, br\n"
            + "Accept-Language: en-US,en;q=0.9,es;q=0.8\n";

        String question3 = "{\n"
            + "    “task” : “inc”,\n"
            + "    “num” : 3\n"
            + "}\n";

        String question4and5 = "To opt out, you’ll need to enter the Settings menu by clicking the three vertical dots, all the way in the upper right corner of the browser. From there, you’ll need to enter the Advanced settings at the very bottom and find the “Allow Chrome sign in” toggle, then turn it to off. Doing so lets you sign into Google services like Gmail and Maps without signing into the Chrome browser itself.";


        System.out.println(question1and2);
        System.out.println(question3);
        System.out.println(question4and5);

        // print each answer at the end

        // Question 1
        // Return the Host
        String host_string[] = question1and2.split("http://");
        host_string = host_string[1].split("/");
        System.out.println("Question 1: " + host_string[0]);





        // Question 2
        // return sum of a and b
        String sum[] = question1and2.split("add\\?a=");
        sum = sum [1].split("&b=");
        String a = sum [0];
        sum = sum[1].split(" ");
        String b = sum[0];
        int ans = (Integer.parseInt(a) + Integer.parseInt(b));
        System.out.println("Question 2: " + ans);



        // Question 3
        // convert to java object, increment num, convert back to json and return

        /*
        j_obj j = new j_obj();
        Gson gson = new Gson();
        gson.fromJson(question3, j);
        j.num += 1;
        Object i = j;
        String q3ans = gson.toJson(i);
        System.out.println("Question 3: " + q3ans);
        */






        // Question 4
        // return  number of unique words

        HashMap  <String, Integer> wcHashMap = new HashMap<>();
        String q4[] = question4and5.split(" ");

        for(int i = 0; i < q4.length; i++) {
            int countWord = 0;
            if (!wcHashMap.containsKey(q4[i])) {
                wcHashMap.put(q4[i], 1);
            } else {
                countWord = wcHashMap.get(q4[i]) + 1;
                wcHashMap.remove(q4[i]);
                wcHashMap.put(q4[i], countWord);
            }
        }
        System.out.println("Question 4: " + wcHashMap.size());


        // Question 5
        // return 2nd most common word




        class j_obj {
            String task;
            int num;


        }
    }
}
