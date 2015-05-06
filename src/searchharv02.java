

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;
//import org.apache.http.impl.client.DefaultHttpClient;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

import static twitter4j.TwitterObjectFactory.getRawJSON;

public class searchharv02 {
    static final String consumerKey="gYeVADTHjujHs1d4CZAsxiJMR";//my 01
    static final String consumerSecret="uT4gmYLDehZGM5alH5aF3zfZRl6LJGmk9yHVrNL65b8y5qs6Cu";
    static final String twitterToken="287646389-ZN1Mlg3IEfsG4S9C61Hw6Paq3QSAdog6i90XPQAH";
    static final String twitterSecret="dv1GUG3rCTroo3wvns69ZvObQTerurVK2tlWd6bBYyJbM";
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
        GeoLocation ge=new GeoLocation(40.9144773, -73.8399766);
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
                    Session s = new Session("nrozaidi:cutetedd@115.146.86.120",5984);
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
