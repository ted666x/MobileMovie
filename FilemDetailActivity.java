package id.ac.amikom.myfilm.ahmadtedi.filemku;

import android.database.sqlite.SQLiteDatabase;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FilemDetailActivity extends AppCompatActivity {

    // Youtube Player
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private YouTubePlayer youTubePlayer;

    private TextView txtjudul;
    private TextView txtRingkasan;
    private  TextView txttayang;
    private ImageView imgPoster;
    DataHelper dbHelper;
    private Button favorite;
    private String image;
    private ImageButton notification;
    private String trailerurl;
    private int trailerkey;
    private String trailers = null;
    private String keyYoutube;


//    public static String YOUTUBE_API_KEY = "7c3a6ded1e48da7786f7f66275cb0035";
    public static final String DEVELOPER_KEY = "AIzaSyAbr3EPpoTxH5HyiD4sYHVtQ9Tpko8OrmY";
    public static final String video = "CqhpNxI8qYw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filemdetailactivity);
        dbHelper = new DataHelper(this);
        Filem  f = (Filem) getIntent().getSerializableExtra("M");
        txtjudul=(TextView) findViewById(R.id.txtDetailJudul);
        txtjudul.setText(f.getTxtJudul());
        txtRingkasan= findViewById(R.id.txtRingkasanAll);
        txtRingkasan=(TextView)findViewById(R.id.txtRingkasanAll);
        txtRingkasan.setText(f.getTxtRingkasan());
        txttayang=(TextView) findViewById(R.id.txtdatatayang);
        txttayang.setText(f.gettayang());
        trailerkey = f.getId();
        trailerurl = "https://api.themoviedb.org/3/movie/" +trailerkey+ "/videos?api_key=801b1e0446fb600018c5926549d8b856";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(trailerurl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(),
                        "Tidak dapat terhubung server", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                try{
                    JSONObject objData = new JSONObject(responseData);
                    final JSONArray arrayResults = objData.getJSONArray("results");
                    final JSONObject objMovie = new JSONObject(arrayResults.get(0).toString());
                    final String key = objMovie.getString("key");

                    FilemDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            keyYoutube = key;
                            youTubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.youtube_player_fragment);

                            if (youTubePlayerFragment == null)
                                return;

                            youTubePlayerFragment.initialize(DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {

                                @Override
                                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                                                    boolean wasRestored) {
                                    if (!wasRestored) {
                                        youTubePlayer = player;

                                        //set the player style default
                                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                                        //cue the 1st video by default
                                        youTubePlayer.cueVideo(keyYoutube);
                                    }
                                }

                                @Override
                                public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {

                                    //print or show error if initialization failed
                                    Log.e(video, "Youtube Player View initialization failed");
                                }

                            });

                        }
                    });

                }catch (JSONException e){
                    e.printStackTrace();
                }


            }
        });


        //init youtebe player
//        initializeYoutubePlayer();

        image = f.getImgPoster();

        notification = (ImageButton) findViewById(R.id.notif);

        imgPoster=(ImageView) findViewById(R.id.coverFilem);
        Glide.with(getApplicationContext())
                .load(f.getImgPoster()).override(600,600).into(imgPoster);

        Log.d("",f.getImgPoster());
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("insert into favorit(judul, tgl, ringkasan, image) values('" +
                        txtjudul.getText().toString() + "','" +
                        txttayang.getText().toString() + "','" +
                        txtRingkasan.getText().toString() + "','" +
                        image + "')");
                String toast = getString(R.string.toastberhasil);
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
                showNotif();
            }
        });

    }



    private void showNotif() {
        NotificationManager notificationManager;

        Intent mIntent = new Intent(this, FilemDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("fromnotif", "notif");
        mIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this , FavoriteActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setColor(getResources().getColor(R.color.colorPrimary));
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.favorite_i)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.favorite_i))
                .setTicker("Filem Favorit di tambahkan ")
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("XXI Lite")
                .setContentText("Filem Favorit telah di tambahkan ");


        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(115, builder.build());

    }
}
