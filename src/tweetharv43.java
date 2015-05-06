import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class tweetharv43 {
    static final String consumerKey = "p3t9PeOEUNe8dZVKSk6Co4OnP";
    static final String consumerSecret = "GCwDmmEZ37ZdJcFZO2swcWEQmsh2edXVuFylTPlBdB2tAIwTwg";
    static final String twitterToken = "267498394-1IQQ25epR858GvLXxYcK1pT9sAhrg7DFinJkEb4d";
    static final String twitterSecret = "Nt1t4T6fJQzyf2neoPTad9qkmajwllHgcPU8gqCsGlJyi";

    public static void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
                .setDebugEnabled(true)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(twitterToken)
                .setOAuthAccessTokenSecret(twitterSecret)
                .setJSONStoreEnabled(true);


        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();


        RawStreamListener listener = new RawStreamListener() {
            @Override
            public void onMessage(String rawJSON) {

                //System.out.println(rawJSON);

                net.sf.json.JSONObject myjson = net.sf.json.JSONObject.fromObject(rawJSON);
                Session s = new Session("nrozaidi:cutetedd@115.146.86.120", 5984);
                Database db = s.getDatabase("tweetharv");
                Document doc = new Document(myjson);
                db.saveDocument(doc, myjson.get("id_str").toString());
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        FilterQuery fq = new FilterQuery();


        double lat1 = 40.913699;
        double longitude1 = -74.2590899;
        double lat2 = 40.9144773;
        double longitude2 = -73.979681;
        twitterStream.addListener(listener);
        double[][] bb = {{longitude1, lat1}, {longitude2, lat2}};
        // fq.track(keywords);
        fq.locations(bb);
        twitterStream.filter(fq);
        //twitterStream.addListener(listener);
        //twitterStream.sample();

    }

    private static boolean isNumericalArgument(String argument) {
        String args[] = argument.split(",");
        boolean isNumericalArgument = true;
        for (String arg : args) {
            try {
                Integer.parseInt(arg);
            } catch (NumberFormatException nfe) {
                isNumericalArgument = false;
                break;
            }
        }
        return isNumericalArgument;
    }
}