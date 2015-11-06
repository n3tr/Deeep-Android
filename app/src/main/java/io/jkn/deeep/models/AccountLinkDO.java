package io.jkn.deeep.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by n3tr on 1/3/2015 AD.
 */
public class AccountLinkDO implements Parcelable {
    @SerializedName("web")
    String web;

    @SerializedName("twitter")
    String twitter;

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.web);
        dest.writeString(this.twitter);
    }

    public AccountLinkDO() {
    }

    private AccountLinkDO(Parcel in) {
        this.web = in.readString();
        this.twitter = in.readString();
    }

    public static final Parcelable.Creator<AccountLinkDO> CREATOR = new Parcelable.Creator<AccountLinkDO>() {
        public AccountLinkDO createFromParcel(Parcel source) {
            return new AccountLinkDO(source);
        }

        public AccountLinkDO[] newArray(int size) {
            return new AccountLinkDO[size];
        }
    };
}
