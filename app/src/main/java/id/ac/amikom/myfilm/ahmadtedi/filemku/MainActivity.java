package id.ac.amikom.myfilm.ahmadtedi.filemku;

import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<ArrayList<Filem>> {


        private RecyclerView recyclerView;
        private ArrayList<Filem> list;
        DataHelper dbcenter;
        private String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=801b1e0446fb600018c5926549d8b856";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            recyclerView = findViewById(R.id.mainRecycler);
            recyclerView.setHasFixedSize(true);
            getSupportLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<ArrayList<Filem>>)this).forceLoad();
        }

        private void showRecyclerCardView(){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            CardFilemAdapter CardFilemAdapter = new CardFilemAdapter(this);
            CardFilemAdapter.setMovies(list);
            recyclerView.setAdapter(CardFilemAdapter);
        }

        @Override
        public Loader<ArrayList<Filem>> onCreateLoader(int i, Bundle bundle) {
            return new DataFilem(this, URL);
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Filem>> loader, ArrayList<Filem> filem) {
            this.list = filem;
            Log.d("LIST : ", String.valueOf(this.list.size()));
            if(this.list != null){
                showRecyclerCardView();
            }else{
                setContentView(R.layout.filemdetailactivity);
            }
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Filem>> loader) {
            this.list = null;
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void pindah (View view)
    {
     Intent intent = new Intent(MainActivity.this,FilemDetailActivity.class );
     startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.gantibahasa:
                    Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                    startActivity(intent);
                break;

            case R.id.keluar:
                finish();
                break;
            case R.id.intentfavorit:
                Intent intentfavorite = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intentfavorite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
