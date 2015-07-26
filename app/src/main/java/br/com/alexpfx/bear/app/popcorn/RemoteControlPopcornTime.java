package br.com.alexpfx.bear.app.popcorn;

import android.util.Base64;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexandre on 26/07/15.
 */
public class RemoteControlPopcornTime {


    public void connect() {

        try {

            Map<String, String> headers = new HashMap<>();
            String auth = Base64.encodeToString("popcorn:popcorn".getBytes(), 0);
            headers.put("Authorization", "Basic " + auth);
            JsonRpcClient client = new JsonRpcHttpClient(new URL("http://192.168.25.119:8008"), headers);

            final ObjectMapper objectMapper = client.getObjectMapper();

            System.out.println(objectMapper);


            client.setRequestListener(new JsonRpcClient.RequestListener() {
                @Override
                public void onBeforeRequestSent(JsonRpcClient client, ObjectNode request) {
                    System.out.println(request);
                }

                @Override
                public void onBeforeResponseProcessed(JsonRpcClient client, ObjectNode response) {
                    System.out.println(client);
                }
            });

            System.out.println(client);


        } catch (MalformedURLException e) {


        }


    }


}
