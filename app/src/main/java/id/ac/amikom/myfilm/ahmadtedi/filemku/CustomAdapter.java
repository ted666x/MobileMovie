package id.ac.amikom.myfilm.ahmadtedi.filemku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by JamesAndrew on 12/31/2018.
 */

public class CustomAdapter extends BaseAdapter {
    private Context context;
    String no[];
    String judul[];
    String ringkasan[];
    String image[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] no, String[] judul, String[] ringkasan, String[] image) {
        this.context = applicationContext;
        this.no = no;
        this.judul = judul;
        this.ringkasan = ringkasan;
        this.image = image;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return judul.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_row_favorite, null);
        TextView vno = (TextView)           view.findViewById(R.id.no);
        TextView vjudul = (TextView)           view.findViewById(R.id.judul);
        TextView vringkasan = (TextView) view.findViewById(R.id.ringkasan);
        ImageView vimage = (ImageView) view.findViewById(R.id.image);
        vno.setText(no[i]);
        vjudul.setText(judul[i]);
        vringkasan.setText(ringkasan[i]);
        Glide.with(context)
                .load(image[i])
                .override(350, 350)
                .into(vimage);
        return view;
    }
}
