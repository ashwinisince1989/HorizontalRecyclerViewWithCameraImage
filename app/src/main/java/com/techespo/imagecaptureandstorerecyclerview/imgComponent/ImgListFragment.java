package com.techespo.imagecaptureandstorerecyclerview.imgComponent;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.techespo.imagecaptureandstorerecyclerview.BuildConfig;
import com.techespo.imagecaptureandstorerecyclerview.R;
import com.techespo.imagecaptureandstorerecyclerview.imgComponent.adapter.ImgRecyclerViewAdapter;
import com.techespo.imagecaptureandstorerecyclerview.imgComponent.model.ImgModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class ImgListFragment extends Fragment implements ImgRecyclerViewAdapter.ImageCaptureOptionLister {
    private static int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public Uri fileUri;
    String[] perms = {"android.permission.FINE_LOCATION", "android.permission.CAMERA"};

    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    //private String mParam1;
    // private String mParam2;

    //private OnFragmentInteractionListener mListener;
    private final int PERMISSION_ALL = 501;
    private final String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public ImgListFragment() {
        // Required empty public constructor
    }

    public static ImgListFragment newInstance(String param1, String param2) {
        ImgListFragment fragment = new ImgListFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getArguments() != null) {
        //    mParam1 = getArguments().getString(ARG_PARAM1);
        //    mParam2 = getArguments().getString(ARG_PARAM2);
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_img_list, container, false);

        //listView = (ListView) v.findViewById(R.id.captureList);
        img_preview = (ImageView) v.findViewById(R.id.img_preview);
        getSets = new ArrayList<ImgModel>();
        imageFor = getResources().getStringArray(R.array.imageFor);
        for (int i = 0; i < 3; i++) {

            ImgModel inflate = new ImgModel();
            // Global Values
            inflate.setUid(String.valueOf(i));
            inflate.setLabel("Image");
            inflate.setHaveImage(false);
            inflate.setSubtext(imageFor[i]);
            inflate.setStatus(true);

            getSets.add(inflate);
        }
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.img_recycler_view);
        customImageAdapter = new ImgRecyclerViewAdapter(getSets, getActivity(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(customImageAdapter);
        if (!hasPermissions(getContext(), PERMISSIONS)) {
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        }
        return v;
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public void onButtonPressed(Uri uri) {
        //if (mListener != null) {
        //    mListener.onFragmentInteraction(uri);
        //}
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if (context instanceof OnFragmentInteractionListener) {
           // mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    ///////////////////////////////////////////////////////////////
    /////////////////Camera Image Capture Code////////////////////
    /////////////////////////////////////////////////////////////
    ImgRecyclerViewAdapter customImageAdapter;
    ArrayList<ImgModel> getSets;
    ImageView img_preview;
    int position;
    String imageTempName;
    String[] imageFor;

    /**
     * Capture Image and save into database
     */
    @Override
    public void captureImage(int pos, String imageName) {

        if (!hasPermissions(getContext(), PERMISSIONS)) {
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        } else {
            position = pos;
            imageTempName = imageName;
            try {
                final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
                File newdir = new File(dir);
                if (!newdir.exists()) {
                    newdir.mkdir();
                }
                String file = dir + System.currentTimeMillis() + ".jpg";
                File newfile = new File(file);
                newfile.createNewFile();
                fileUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID, newfile);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void viewImage(int pos, String imageName, Uri imagePath) {
        Intent intent = new Intent(getActivity(), ImageZoomActivity.class);
        intent.putExtra("image_path", imagePath.toString());
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case 200:
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                img_preview.setImageURI(fileUri);
                customImageAdapter.setImageInItem(position, null, fileUri);
            }
        }
    }

   /* public Uri getImageUri(Context inContext, Bitmap inImage, String imageName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, imageName, null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Bitmap convertSrcToBitmap(String imageSrc) {
        Bitmap myBitmap = null;
        File imgFile = new File(imageSrc);
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return myBitmap;
    }*/
}
