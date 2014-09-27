package org.kaufer.matthew.pichash;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;


public class Main extends Activity {

    private Camera camera;
    private int cid = 0;
    private BigDecimal max = null;
    private boolean safeToTakePicture = false;

    final protected static char[] hexArray = "0123456789abcdef".toCharArray();//for hash stuff

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton b = (ImageButton)findViewById(R.id.imageButton);
        camera = Camera.open();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.startPreview();
                camera.takePicture(null, null, new Camera.PictureCallback(){

                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {

                        Bitmap bMap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        b.setImageBitmap(bMap);
                        //above code displays img
                        try {
                            MessageDigest md = MessageDigest.getInstance("SHA-1");
                            byte[] digest = md.digest(bytes);

                            String s = hashToHex(digest);
                            System.out.println(s);
                            System.out.println("Hash: " + s + "\nRandom Number: " + getOdds(s));
//                            setText("Hash: " + s + "\nRandom Number: " + getOdds(s));

                            TextView h = (TextView)findViewById(R.id.hash);
                            h.setText("Hash: " + s);

                            TextView r = (TextView)findViewById(R.id.num);
                            r.setText("Rand:" + getOdds(s));

                        } catch (Exception e){
                            System.out.println("Phone can't hash, yo");
                            TextView h = (TextView)findViewById(R.id.hash);
                            h.setText("Phone can't hash, yo");
                        }


                    }
                });
                camera.stopPreview();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static String hashToHex(byte[] bytes) {//turn byte array to hash
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public String getOdds(String hash){
        if(max == null){
            max = makeMax(hash);
        }

        BigDecimal numerator = new BigDecimal(new BigInteger(hash, 16));

        return numerator.divide(max).toPlainString();
    }

    public BigDecimal makeMax(String hash){//will make a max relative to the hash
        String s = "1";
        for(int i = 0; i < hash.length(); i++)
            s+="0";
        return new BigDecimal(new BigInteger(s, 16));
    }
}
