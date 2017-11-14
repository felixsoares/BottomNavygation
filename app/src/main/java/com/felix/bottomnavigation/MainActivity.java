package com.felix.bottomnavigation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.felix.bottomnavygation.BadgeIndicator;
import com.felix.bottomnavygation.BottomNav;
import com.felix.bottomnavygation.ItemNav;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    public static final int GALLERY_REQUEST = 2000;
    public static final int IMAGE_REQUEST = 2500;

    private int count = 0;

    private BottomNav bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BadgeIndicator badgeIndicator = new BadgeIndicator(this, android.R.color.holo_red_dark, android.R.color.white);

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.addItemNav(new ItemNav(this, R.drawable.feed, R.drawable.feed_sel).addBadgeIndicator(badgeIndicator));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.explore, R.drawable.explore_sel));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.atividades).addColorAtive(android.R.color.holo_blue_bright));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.perfil, R.drawable.perfil_sel).isProfileItem().addProfileColorAtive(android.R.color.holo_red_dark).addProfileColorInative(android.R.color.black));
        bottomNav.setTabSelectedListener(listener);
        bottomNav.build();

        Button btnAddBadge = (Button) findViewById(R.id.btnAddBadge);
        btnAddBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count += 1;
                badgeIndicator.updateCount(count);
            }
        });

        Button btnRemoveBadge = (Button) findViewById(R.id.btnRemoveBadge);
        btnRemoveBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count >= 0) {
                    count -= 1;
                    badgeIndicator.updateCount(count);
                }
            }
        });

        Button btnVisible = (Button) findViewById(R.id.btnVisible);
        btnVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNav.setVisibility(View.VISIBLE);
            }
        });

        Button btnInvisible = (Button) findViewById(R.id.btnInvisible);
        btnInvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNav.setVisibility(View.GONE);
            }
        });

        Button btnAddPhoto = (Button) findViewById(R.id.btnAddPhoto);
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askGalleryPermition();
            }
        });

        Button btnRemovePhoto = (Button) findViewById(R.id.btnRemovePhoto);
        btnRemovePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNav.updateImageProfile("");
            }
        });

        final EditText editSelect = findViewById(R.id.editSelect);

        Button btnSelect = (Button) findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editSelect.getText().toString().equals("")) {
                    bottomNav.selectTab(Integer.parseInt(editSelect.getText().toString()));
                }
            }
        });
    }

    BottomNav.OnTabSelectedListener listener = new BottomNav.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {
            Toast.makeText(MainActivity.this, "Click posicao " + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTabLongSelected(int position) {
            Toast.makeText(MainActivity.this, "Long posicao " + position, Toast.LENGTH_SHORT).show();
        }
    };

    private void askGalleryPermition() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, GALLERY_REQUEST);
        } else {
            callGalleryIntent();
        }
    }

    public void callGalleryIntent() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case GALLERY_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callGalleryIntent();
                } else {
                    Toast.makeText(this, "The app was not allowed to permissions.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
            try {
                Uri selectedImage = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                String path = saveProfilePhoto(bitmap);
                bottomNav.updateImageProfile(path);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private String saveProfilePhoto(Bitmap bitmap) {
        try {
            File file = new File(getExternalFilesDir("profile"), "profile_image.png");

            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
