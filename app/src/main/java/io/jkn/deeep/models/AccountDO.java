package io.jkn.deeep.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by n3tr on 1/3/2015 AD.
 */
public class AccountDO implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("avatar_url")
    private String avatar_url;

    @SerializedName("bio")
    private String bio;

    @SerializedName("location")
    private String location;

    @SerializedName("link")
    private AccountLinkDO links;

    @SerializedName("buckets_count")
    private int bucketsCount;

    @SerializedName("followers_count")
    private int followersCount;

    @SerializedName("followings_count")
    private int followingsCount;

    @SerializedName("likes_count")
    private int likesCount;

    @SerializedName("projects_count")
    private int projectsCount;

    @SerializedName("shots_count")
    private int shotsCount;

    @SerializedName("teams_count")
    private int teamCount;

    @SerializedName("type")
    private String type;

    @SerializedName("pro")
    private boolean pro;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("updated_at")
    private Date updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public AccountLinkDO getLinks() {
        return links;
    }

    public void setLinks(AccountLinkDO links) {
        this.links = links;
    }

    public int getBucketsCount() {
        return bucketsCount;
    }

    public void setBucketsCount(int bucketsCount) {
        this.bucketsCount = bucketsCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(int followingsCount) {
        this.followingsCount = followingsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getProjectsCount() {
        return projectsCount;
    }

    public void setProjectsCount(int projectsCount) {
        this.projectsCount = projectsCount;
    }

    public int getShotsCount() {
        return shotsCount;
    }

    public void setShotsCount(int shotsCount) {
        this.shotsCount = shotsCount;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPro() {
        return pro;
    }

    public void setPro(boolean pro) {
        this.pro = pro;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AccountDO() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.htmlUrl);
        dest.writeString(this.avatar_url);
        dest.writeString(this.bio);
        dest.writeString(this.location);
        dest.writeParcelable(this.links, 0);
        dest.writeInt(this.bucketsCount);
        dest.writeInt(this.followersCount);
        dest.writeInt(this.followingsCount);
        dest.writeInt(this.likesCount);
        dest.writeInt(this.projectsCount);
        dest.writeInt(this.shotsCount);
        dest.writeInt(this.teamCount);
        dest.writeString(this.type);
        dest.writeByte(pro ? (byte) 1 : (byte) 0);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
    }

    private AccountDO(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.htmlUrl = in.readString();
        this.avatar_url = in.readString();
        this.bio = in.readString();
        this.location = in.readString();
        this.links = in.readParcelable(AccountLinkDO.class.getClassLoader());
        this.bucketsCount = in.readInt();
        this.followersCount = in.readInt();
        this.followingsCount = in.readInt();
        this.likesCount = in.readInt();
        this.projectsCount = in.readInt();
        this.shotsCount = in.readInt();
        this.teamCount = in.readInt();
        this.type = in.readString();
        this.pro = in.readByte() != 0;
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
    }

    public static final Creator<AccountDO> CREATOR = new Creator<AccountDO>() {
        public AccountDO createFromParcel(Parcel source) {
            return new AccountDO(source);
        }

        public AccountDO[] newArray(int size) {
            return new AccountDO[size];
        }
    };
}
