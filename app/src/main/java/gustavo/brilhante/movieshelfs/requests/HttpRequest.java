package gustavo.brilhante.movieshelfs.requests;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import gustavo.brilhante.movieshelfs.models.Argument;

/**
 * Created by Gustavo on 05/02/17.
 */

public class HttpRequest {

    public static String getRequest(String url, ArrayList<Argument> parameters){
        HttpResponse response = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpGet;
        StringBuilder builder = new StringBuilder();

        String jsonResult = "";

        try {
            String urlFinal="";
            url += url.endsWith("?") ? "" : "?";
            for(int i=0; i< parameters.size();i++){
                String chave = parameters.get(i).getKey();
                if (i == 0) {
                    url = url + chave+"="+parameters.get(i).getValue();
                } else {
                    url = url +"&"+chave+"="+parameters.get(i).getValue();
                }
            }
            urlFinal = url;

            httpGet = new HttpPost(urlFinal);

            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-Type", "application/json");

            response = httpclient.execute(httpGet);

            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200 || statusCode==201)
            {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;

                while((line = reader.readLine()) != null)
                {
                    builder.append(line);
                }
                jsonResult = builder.toString();
            }else{
                String couldntFind = "ERROR - " + statusCode;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("exception", e.getMessage());
        }

        return jsonResult;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
