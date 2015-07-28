package br.com.alexpfx.bear.app.popcorn;

import android.util.Base64;
import android.widget.Toast;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.ConnectionConfigurator;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by alexandre on 26/07/15.
 */
public class RemoteControlPopcornTime {

    public static final LinkedBlockingDeque<Runnable> WORK_QUEUE = new LinkedBlockingDeque<Runnable>();
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 100, TimeUnit.SECONDS, WORK_QUEUE);
    ;



    private String commands[] = new String[]{"ping", "toggleplaying", "togglemute", "togglefullscreen", "togglefavorite", "toggletab", "togglewatched", "togglequality", "showlist", "movieslist", "animelist", "showwatchlist", "showfavorites", "showabout", "showsettings", "clearsearch", "enter", "back", "watchtrailer", "setsubtitle"};

    private Random random = new Random();
    private Runnable connect = new Runnable() {
        @Override
        public void run() {
            Map<String, String> headers = new HashMap<>();
            final String auth = Base64.encodeToString("popcorn:popcorn".getBytes(), 0);
            headers.put("Authorization", "Basic " + auth);
            try {
                String cmd = commands [random.nextInt(commands.length - 1)];
                System.out.println(cmd);
                JSONRPC2Session s = new JSONRPC2Session(new URL("http://192.168.25.119:8008"));
                s.setConnectionConfigurator(new MyConnectionConfigurator(auth));
                s.getOptions().setRequestContentType("application/json");

                JSONRPC2Request r = new JSONRPC2Request(cmd, new HashMap<String, Object>(), 1);

                final JSONRPC2Response response = s.send(r);
                if (response != null && response.getError() == null) {
                    System.out.println(response.getResult());
                }
            } catch (MalformedURLException e) {


            } catch (JSONRPC2SessionException e) {
                e.printStackTrace();
            }
        }
    };

    public void connect() {
        threadPoolExecutor.execute(connect);

    }

    private static class MyConnectionConfigurator implements ConnectionConfigurator {
        private final String auth;

        public MyConnectionConfigurator(String auth) {
            this.auth = auth;
        }

        @Override
        public void configure(HttpURLConnection connection) {
            connection.addRequestProperty("Authorization", "Basic " + auth);
        }
    }
}