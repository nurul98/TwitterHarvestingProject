/**
 * Created by LoVeYiLa98 on 5/6/2015.
 */

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;
//import org.apache.http.impl.client.DefaultHttpClient;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

import static twitter4j.TwitterObjectFactory.getRawJSON;

public class serachHarv01 {
    static final String consumerKey="n27ja5wXhcVxQ7KrP8wVEcYsJ";
    static final String consumerSecret="O0d4yYwrjhtp3Dlcc8yOXup8JR3d8tcWAVtyiSRaUwjR0yAROf";
    static final String twitterToken="575227480-hXM3BCEMDvQRQfqEyMIbT5JYhfykw7OHdGEOERwY";
    static final String twitterSecret="gZFQUEkbnFpbjL3V972tIy2tWO2Tpvl44ojzuHICJFZmW";
    public static void main(String[] args) throws Exception{

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
                .setDebugEnabled(true)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(twitterToken)
                .setOAuthAccessTokenSecret(twitterSecret)
                .setJSONStoreEnabled(true);

        Twitter twitter =new TwitterFactory(cb.build()).getInstance();

        Query query = new Query();
        GeoLocation ge=new GeoLocation(40.9144773, -74.1193855);
        query.setGeoCode(ge,50, Query.Unit.km);
        query.setLang("en");

        query.since("2015-05-01");
        query.until("2015-05-31");

        QueryResult result;
        do {

            result = twitter.search(query);
            List<Status> tweets = result.getTweets();
            for (Status tweet : tweets) {
                try {
                    String rawJSON = getRawJSON(tweet);
                    net.sf.json.JSONObject myjson = net.sf.json.JSONObject.fromObject(rawJSON);
                    Session s = new Session("nrozaidi:cutetedd@localhost",5984);
                    Database db = s.getDatabase("tweetharv");
                    Document doc = new Document(myjson);

                    db.saveDocument(doc, myjson.get("id_str").toString());
                } catch (Exception te) {
                    te.printStackTrace();
                    System.out.println("Failed to search tweets: " + te.getMessage());
                }
            }
        } while ((query = result.nextQuery()) != null);
        System.exit(0);

    }

}
