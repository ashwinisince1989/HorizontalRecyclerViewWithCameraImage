package com.techespo.imagecaptureandstorerecyclerview.imgComponent.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.techespo.imagecaptureandstorerecyclerview.R;
import com.techespo.imagecaptureandstorerecyclerview.imgComponent.model.ImgModel;

import java.util.List;

public class ImgRecyclerViewAdapter extends RecyclerView.Adapter<ImgRecyclerViewAdapter.MyViewHolder> {

    public interface ImageCaptureOptionLister {
        void captureImage(int pos, String imageName);
        void viewImage(int pos, String imageName,Uri imagePath);
    }

    private ImageCaptureOptionLister imageCaptureListener;
    List<ImgModel> _data;
    Context _c;

    public ImgRecyclerViewAdapter(List<ImgModel> getData, Context context, ImageCaptureOptionLister imageCaptureListener) {
        _data = getData;
        _c = context;
        this.imageCaptureListener = imageCaptureListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.img_list_item_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        // Set data in listView
        final ImgModel dataSet = (ImgModel) _data.get(position);

        dataSet.setListItemPosition(position);

        if (!dataSet.isHaveImage()) {
            Bitmap icon = BitmapFactory.decodeResource(_c.getResources(), R.mipmap.ic_launcher);
            myViewHolder.imageView.setImageBitmap(icon);
        } else {
            myViewHolder.imageView.setImageURI(dataSet.getImageSrc());
        }
        myViewHolder.imageName.setText(dataSet.getSubtext());
        if (dataSet.isStatus()) {
            myViewHolder.clickImage.setVisibility(View.VISIBLE);
            myViewHolder.removeImage.setVisibility(View.GONE);
        } else {
            myViewHolder.removeImage.setVisibility(View.VISIBLE);
            myViewHolder.clickImage.setVisibility(View.GONE);
        }

        myViewHolder.clickImage.setFocusable(false);
        myViewHolder.removeImage.setFocusable(false);


        myViewHolder.clickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call parent method of activity to click image
                imageCaptureListener.captureImage(dataSet.getListItemPosition(), dataSet.getLabel() + "" + dataSet.getSubtext());
            }
        });
        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call parent method of activity to click image
                imageCaptureListener.viewImage(dataSet.getListItemPosition(), dataSet.getLabel() + "" + dataSet.getSubtext(), dataSet.getImageSrc());
            }
        });
        myViewHolder.removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSet.setStatus(true);
                dataSet.setHaveImage(false);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView imageName;
        ImageButton clickImage, removeImage;

        public MyViewHolder(View view) {
            super(view);
            clickImage = (ImageButton) view.findViewById(R.id.capture);
            removeImage = (ImageButton) view.findViewById(R.id.cancel);
            imageName = (TextView) view.findViewById(R.id.txt_img_name);
            imageView = (ImageView) view.findViewById(R.id.imgPrv);
        }
    }

    /**
     * @param position Get position of of object
     * @param imageSrc set image in imageView
     */
    public void setImageInItem(int position, Bitmap imageSrc, Uri imagePath) {
        ImgModel dataSet = (ImgModel) _data.get(position);
        dataSet.setImage(imageSrc);
        dataSet.setStatus(false);
        dataSet.setHaveImage(true);
        dataSet.setImageSrc(imagePath);
        notifyDataSetChanged();
    }
}