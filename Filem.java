package id.ac.amikom.myfilm.ahmadtedi.filemku;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Filem implements Serializable,Parcelable {

    private int id;
    private String txtJudul;
    private String txtRingkasan;
    private String tayang;
    private String imgPoster;



    public Filem(int id, String judul, String Ringkasan, String tayang, String imgPoster){
        this.id = id;
        this.txtJudul = judul;
        this.txtRingkasan = Ringkasan;
        this.tayang = tayang;
        this.imgPoster = imgPoster;
    }
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getImgPoster() {
        return imgPoster;
    }

    public void setImgPoster(String imgPoster) {
        this.imgPoster = imgPoster;
    }

    public String getTxtJudul() {
        return txtJudul;
    }

    public void setTxtJudul(String txtJudul) {
        this.txtJudul = txtJudul;
    }

    public String getTxtRingkasan() {
        return txtRingkasan;
    }

    public void setTxtRingkasan(String txtRingkasan) {
        this.txtRingkasan = txtRingkasan;
    }

    public String gettayang() {
        return tayang;
    }

    public void settayang(String tayang) {
        this.tayang = tayang;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.txtJudul);
        dest.writeString(this.txtRingkasan);
        dest.writeString(this.tayang);
        dest.writeString(this.imgPoster);
    }

    protected Filem(Parcel in) {
        this.id = in.readInt();
        this.txtJudul = in.readString();
        this.txtRingkasan = in.readString();
        this.tayang = in.readString();
        this.imgPoster = in.readString();
    }

    public static final Parcelable.Creator<Filem> CREATOR = new Parcelable.Creator<Filem>() {
        @Override
        public Filem createFromParcel(Parcel source) {
            return new Filem(source);
        }

        @Override
        public Filem[] newArray(int size) {
            return new Filem[size];
        }
    };
}
