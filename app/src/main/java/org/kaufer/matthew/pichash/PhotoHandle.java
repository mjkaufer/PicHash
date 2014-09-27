//package org.kaufer.matthew.pichash;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.hardware.Camera;
//import android.widget.ImageView;
//import java.security.*;
//
///**
// * Created by 2016mkaufer on 9/22/2014.
// */
//public class PhotoHandle implements Camera.PictureCallback {
//    Context context;
//    Main main;
//    private byte[] lastData;
//    public PhotoHandle(Context applicationContext) {
//        context = applicationContext;
//        //context.getResources().
//        main = new Main();
//    }
//
//
//    @Override
//    public void onPictureTaken(byte[] bytes, Camera camera) {
//        lastData = bytes;
////        main.img(bytes);
//
//        System.out.println(bytes);
//
////
////        ImageView image = (ImageView) main.getImg();
////        Bitmap bMap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
////        image.setImageBitmap(bMap);
//    }
//
//    public byte[] getData(){
//        return lastData;
//    }
//
//    public String byteArrayToHex(byte[] a) {
//        StringBuilder sb = new StringBuilder(a.length * 2);
//        for(byte b: a)
//            sb.append(String.format("%02x", b & 0xff));
//        return sb.toString();
//    }
//}
