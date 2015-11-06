package io.jkn.deeep.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by n3tr on 1/5/2015 AD.
 */
public class ShotImageDO implements Parcelable {
    @SerializedName("hidpi")    private String hidpiUrl;
    @SerializedName("normal")   private String normalUrl;
    @SerializedName("teaser")   private String teaserUrl;

    public String getHidpiUrl() {
        return hidpiUrl;
    }

    public void setHidpiUrl(String hidpiUrl) {
        this.hidpiUrl = hidpiUrl;
    }

    public String getNormalUrl() {
        return normalUrl;
    }

    public void setNormalUrl(String normalUrl) {
        this.normalUrl = normalUrl;
    }

    public String getTeaserUrl() {
        return teaserUrl;
    }

    public void setTeaserUrl(String teaserUrl) {
        this.teaserUrl = teaserUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hidpiUrl);
        dest.writeString(this.normalUrl);
        dest.writeString(this.teaserUrl);
    }

    public ShotImageDO() {
    }

    private ShotImageDO(Parcel in) {
        this.hidpiUrl = in.readString();
        this.normalUrl = in.readString();
        this.teaserUrl = in.readString();
    }

    public static final Parcelable.Creator<ShotImageDO> CREATOR = new Parcelable.Creator<ShotImageDO>() {
        public ShotImageDO createFromParcel(Parcel source) {
            return new ShotImageDO(source);
        }

        public ShotImageDO[] newArray(int size) {
            return new ShotImageDO[size];
        }
    };
}
