package id.ac.amikom.myfilm.ahmadtedi.filemku;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FavoriteDetail extends AppCompatActivity {

    protected Cursor cursor;
    private TextView txtJudul;
    private TextView txtRingkasan;
    private  TextView txtTanggal;
    DataHelper dbHelper;
    private ImageView image;
    private TextView no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_detail);

        dbHelper = new DataHelper(this);
        no = (TextView) findViewById(R.id.no);
        txtJudul = (TextView) findViewById(R.id.txtJudul);
        txtTanggal = (TextView) findViewById(R.id.txtTgl);
        txtRingkasan = (TextView) findViewById(R.id.txtRingkasan);
        image = (ImageView) findViewById(R.id.coverFilem);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM favorit WHERE no = '" +
                getIntent().getStringExtra("no") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            no.setText(cursor.getString(0).toString());

            Glide.with(getApplicationContext())
                    .load(cursor.getString(4)).override(350,350).into(image);

            txtJudul.setText(cursor.getString(1).toString());
            txtTanggal.setText(cursor.getString(2).toString());
            txtRingkasan.setText(cursor.getString(3).toString());
        }

    }
}
