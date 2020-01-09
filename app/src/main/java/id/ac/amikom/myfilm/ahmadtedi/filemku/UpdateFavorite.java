package id.ac.amikom.myfilm.ahmadtedi.filemku;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class UpdateFavorite extends AppCompatActivity {

    protected Cursor cursor;
    DataHelper dbHelper;
    Button btnBack, btnUpdate;
    EditText txtJudul, txtTanggal, txtRingkasan;
    ImageView image;
    TextView no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_favorite);

        dbHelper = new DataHelper(this);
        no = (TextView) findViewById(R.id.no);
        txtJudul = (EditText) findViewById(R.id.txtJudul);
        txtTanggal = (EditText) findViewById(R.id.txtTgl);
        txtRingkasan = (EditText) findViewById(R.id.txtRingkasan);
        image=(ImageView) findViewById(R.id.coverFilem);

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
        btnBack = (Button) findViewById(R.id.btnBack);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("update favorit set judul ='"+
                    txtJudul.getText().toString() +"', tgl='" +
                    txtTanggal.getText().toString() +"', ringkasan='" +
                    txtRingkasan.getText().toString() +"' where no='" +
                    no.getText().toString()+"'");

                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                FavoriteActivity.fa.RefreshList();
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
}
