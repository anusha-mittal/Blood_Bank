package com.example.blood_bank.DataModels;

//public class RequestDataModel {
//    private String message;
//    private String imageUrl;  //for uploading user's image
//
//    public RequestDataModel(String message, String imageUrl) {
//        this.message = message;
//        this.imageUrl = imageUrl;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//}

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestDataModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("number")
    @Expose
    private String number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}