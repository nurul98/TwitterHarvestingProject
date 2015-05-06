import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class tweetharv41 {
    static final String consumerKey="n27ja5wXhcVxQ7KrP8wVEcYsJ";
    static final String consumerSecret="O0d4yYwrjhtp3Dlcc8yOXup8JR3d8tcWAVtyiSRaUwjR0yAROf";
    static final String twitterToken="575227480-hXM3BCEMDvQRQfqEyMIbT5JYhfykw7OHdGEOERwY";
    static final String twitterSecret="gZFQUEkbnFpbjL3V972tIy2tWO2Tpvl44ojzuHICJFZmW";
    public static void main(String[] args) {
        for(int i=0;i<args.length;i++){
            System.out.println(args[i].toString());

        }
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
                Session s = new Session("nrozaidi:cutetedd@115.146.86.120",5984);
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

        double lat1 = 40.9144773;
        double longitude1 =  -74.2590899;
        double lat2 = 40.9152556;
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
