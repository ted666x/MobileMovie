package id.ac.amikom.myfilm.ahmadtedi.filemku;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataFilem extends AsyncTaskLoader<ArrayList<Filem>> {
    private String URL;
    private String filems = null;

    public DataFilem(Context context, String URL) {
        super(context);
        this.URL = URL;
    }

    @Override
    public ArrayList<Filem> loadInBackground() {
        final ArrayList<Filem> list = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL)
                .build();
        try {
            Response response = client.newCall(request).execute();

            filems = response.body().string();

            try{
                JSONObject objData = new JSONObject(filems);
                final JSONArray arrayResults = objData.getJSONArray("results");
                if(arrayResults != null) {
                    for (int i = 0; i < arrayResults.length(); i++) {
                        JSONObject objMovie = new JSONObject(arrayResults.get(i).toString());
                        String judul = objMovie.getString("title");
                        String ringkasan = objMovie.getString("overview");
                        String tayang = objMovie.getString("release_date");
                        String imgPoster = "http://image.tmdb.org/t/p/original" + objMovie.getString("poster_path");
                        int id = objMovie.getInt("id");
                        list.add(new Filem(id,judul,ringkasan, tayang, imgPoster));
                    }
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


}
