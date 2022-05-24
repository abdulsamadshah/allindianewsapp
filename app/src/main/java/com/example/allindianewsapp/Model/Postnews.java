package com.example.allindianewsapp.Model;

public class Postnews {
   private String postId;
   private String postnewimage;
   private String postedby;
   private String newstitle;
   private String newsdescriptiondata;
   private String postedAt;


    public Postnews(String newsdescriptiondata) {
        this.newsdescriptiondata = newsdescriptiondata;
    }

    public Postnews(String postId, String postnewimage, String postedby, String newstitle, String postedAt) {
        this.postId = postId;
        this.postnewimage = postnewimage;
        this.postedby = postedby;
        this.newstitle = newstitle;
        this.postedAt = postedAt;
    }

    public Postnews() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostnewimage() {
        return postnewimage;
    }

    public void setPostnewimage(String postnewimage) {
        this.postnewimage = postnewimage;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getNewsdescriptiondata() {
        return newsdescriptiondata;
    }

    public void setNewsdescriptiondata(String newsdescriptiondata) {
        this.newsdescriptiondata = newsdescriptiondata;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }
}
