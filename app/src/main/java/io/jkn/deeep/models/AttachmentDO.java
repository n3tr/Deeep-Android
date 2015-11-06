package io.jkn.deeep.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by n3tr on 1/10/2015 AD.
 */
public class AttachmentDO implements Parcelable {

    private int id;

    @SerializedName("url")
    private String attachmentUrl;

    @SerializedName("thumbnail_url")
    private String thumbnailUrl;

    @SerializedName("size")
    private double attachmentSize;

    @SerializedName("content_type")
    private String contentType;

    @SerializedName("views_count")
    private int viewsCount;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("updated_at")
    private Date updatedAt;

    public int getId() {
        return id;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public double getAttachmentSize() {
        return attachmentSize;
    }

    public String getContentType() {
        return contentType;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.attachmentUrl);
        dest.writeString(this.thumbnailUrl);
        dest.writeDouble(this.attachmentSize);
        dest.writeString(this.contentType);
        dest.writeInt(this.viewsCount);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
    }

    public AttachmentDO() {
    }

    private AttachmentDO(Parcel in) {
        this.id = in.readInt();
        this.attachmentUrl = in.readString();
        this.thumbnailUrl = in.readString();
        this.attachmentSize = in.readDouble();
        this.contentType = in.readString();
        this.viewsCount = in.readInt();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
    }

    public static final Parcelable.Creator<AttachmentDO> CREATOR = new Parcelable.Creator<AttachmentDO>() {
        public AttachmentDO createFromParcel(Parcel source) {
            return new AttachmentDO(source);
        }

        public AttachmentDO[] newArray(int size) {
            return new AttachmentDO[size];
        }
    };
}
