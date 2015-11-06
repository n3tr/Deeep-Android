package io.jkn.deeep.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.net.URI;
import java.util.Date;

/**
 * Created by n3tr on 1/5/2015 AD.
 */
public class ShotDO implements Parcelable {
    private int id;
    private String title;
    private String description;
    private int width;
    private int height;
    private String[] tags;
    private AccountDO user;
    private AccountDO team;
    @SerializedName("images") private ShotImageDO shotImage;
    @SerializedName("views_count") private int viewsCount;
    @SerializedName("likes_count") private int likesCount;
    @SerializedName("comments_count") private int commentsCount;
    @SerializedName("attachments_count") private int attachmentsCount;
    @SerializedName("buckets_count") private int bucketsCount;
    @SerializedName("created_at") private Date createdAt;
    @SerializedName("updated_at") private Date updatedAt;
    @SerializedName("html_url") private String htmlUrl;
    @SerializedName("rebound_source_url") private String reboundSourceUrl;
    @SerializedName("rebound_source_id") private int reboundSourceId;

    private ShotDO reboundSourceShot;

    public int getReboundSourceId() {
        if (reboundSourceId != 0)
            return reboundSourceId;

        if (reboundSourceUrl == null)
            return 0;

        String[] part = reboundSourceUrl.split("/");
        if (part == null || part.length <= 0) {
            return 0;
        }

        int reboundsId = Integer.valueOf(part[part.length-1]);
        return reboundsId;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ShotImageDO getShotImage() {
        return shotImage;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getAttachmentsCount() {
        return attachmentsCount;
    }

    public int getBucketsCount() {
        return bucketsCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String[] getTags() {
        return tags;
    }

    public AccountDO getUser() {
        return user;
    }

    public AccountDO getTeam() {
        return team;
    }

    public String getReboundSourceUrl() {
        return reboundSourceUrl;
    }

    public ShotDO getReboundSourceShot() {
        return reboundSourceShot;
    }

    public void setReboundSourceShot(ShotDO reboundSourceShot) {
        this.reboundSourceShot = reboundSourceShot;
    }

    public ShotDO() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeStringArray(this.tags);
        dest.writeParcelable(this.user, 0);
        dest.writeParcelable(this.team, 0);
        dest.writeParcelable(this.shotImage, 0);
        dest.writeInt(this.viewsCount);
        dest.writeInt(this.likesCount);
        dest.writeInt(this.commentsCount);
        dest.writeInt(this.attachmentsCount);
        dest.writeInt(this.bucketsCount);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
        dest.writeString(this.htmlUrl);
        dest.writeString(this.reboundSourceUrl);
        dest.writeInt(this.reboundSourceId);
        dest.writeParcelable(this.reboundSourceShot, 0);
    }

    private ShotDO(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.tags = in.createStringArray();
        this.user = in.readParcelable(AccountDO.class.getClassLoader());
        this.team = in.readParcelable(AccountDO.class.getClassLoader());
        this.shotImage = in.readParcelable(ShotImageDO.class.getClassLoader());
        this.viewsCount = in.readInt();
        this.likesCount = in.readInt();
        this.commentsCount = in.readInt();
        this.attachmentsCount = in.readInt();
        this.bucketsCount = in.readInt();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        this.htmlUrl = in.readString();
        this.reboundSourceUrl = in.readString();
        this.reboundSourceId = in.readInt();
        this.reboundSourceShot = in.readParcelable(ShotDO.class.getClassLoader());
    }

    public static final Creator<ShotDO> CREATOR = new Creator<ShotDO>() {
        public ShotDO createFromParcel(Parcel source) {
            return new ShotDO(source);
        }

        public ShotDO[] newArray(int size) {
            return new ShotDO[size];
        }
    };
}
