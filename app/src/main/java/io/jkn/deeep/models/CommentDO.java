package io.jkn.deeep.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by n3tr on 1/10/2015 AD.
 */
public class CommentDO implements Parcelable {

    private int id;
    private String body;
    @SerializedName("likes_count")
    private int likeCounts;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("updated_at")
    private Date updatedAt;
    private AccountDO user;

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public int getLikeCounts() {
        return likeCounts;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public AccountDO getUser() {
        return user;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.body);
        dest.writeInt(this.likeCounts);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
        dest.writeParcelable(this.user, 0);
    }

    public CommentDO() {
    }

    private CommentDO(Parcel in) {
        this.id = in.readInt();
        this.body = in.readString();
        this.likeCounts = in.readInt();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        this.user = in.readParcelable(AccountDO.class.getClassLoader());
    }

    public static final Parcelable.Creator<CommentDO> CREATOR = new Parcelable.Creator<CommentDO>() {
        public CommentDO createFromParcel(Parcel source) {
            return new CommentDO(source);
        }

        public CommentDO[] newArray(int size) {
            return new CommentDO[size];
        }
    };
}

