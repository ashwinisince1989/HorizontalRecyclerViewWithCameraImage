package com.techespo.imagecaptureandstorerecyclerview.imgComponent.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class ImgModel {

    String label;
    Bitmap image;
    Uri imageSrc;
    private String uid;
    boolean haveImage;
    String subtext;
    boolean status;
    int listItemPosition;

    public ImgModel() {

    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getListItemPosition() {
        return listItemPosition;
    }

    public void setListItemPosition(int listItemPosition) {
        this.listItemPosition = listItemPosition;
    }

    public boolean isHaveImage() {
        return haveImage;
    }

    public void setHaveImage(boolean haveImage) {
        this.haveImage = haveImage;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Uri getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(Uri imageSrc) {
        this.imageSrc = imageSrc;
    }
}
