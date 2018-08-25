package com.john.www.abu.PostStuffs.models;



import com.google.firebase.firestore.Exclude;
import com.john.www.abu.DatabaseStuffs.sqlUsers.User;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserDatabase;
import com.john.www.abu.DatabaseStuffs.sqlUsers.UserForPost;

import java.io.Serializable;

/**
 * Created by brad on 2017/02/05.
 */

public class PostModel implements Serializable {
    private UserForPost user;
    private String postText;
    private String postImageUrl;
    private String thumbIamgeUri;
    private String postId;
    private String type;
    private long numLikes;
    private long numComments;
    private long timeCreated;
    private String userid;

    public PostModel() {
    }

    public PostModel(UserForPost user, String postText, String postImageUrl, String postId, long numLikes, long numComments, long timeCreated) {

        this.user = user;
        this.postText = postText;
        this.postImageUrl = postImageUrl;
        this.postId = postId;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.timeCreated = timeCreated;
    }

    @Exclude
    public UserForPost getUser() {

        return user;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbIamgeUri() {
        return thumbIamgeUri;
    }

    public void setThumbIamgeUri(String thumbIamgeUri) {
        this.thumbIamgeUri = thumbIamgeUri;
    }

    public void setUser(UserForPost user) {
        this.user = user;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public long getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(long numLikes) {
        this.numLikes = numLikes;
    }

    public long getNumComments() {
        return numComments;
    }

    public void setNumComments(long numComments) {
        this.numComments = numComments;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }
}
