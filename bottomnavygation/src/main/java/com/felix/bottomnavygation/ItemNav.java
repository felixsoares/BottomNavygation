package com.felix.bottomnavygation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.felix.bottomnavygation.Util.RoundedImageView;
import com.felix.bottomnavygation.Util.Util;

import java.io.File;
import java.util.Random;

/**
 * Created by user on 07/11/2017.
 */

public class ItemNav extends LinearLayout {

    private int imageIcon;
    private int imageIconActive;
    private String titulo;
    private String pathImageProfile;

    private int colorInactive;
    private int colorActive;

    private Boolean isProfile = false;

    private ImageView imageView;
    private RelativeLayout relativeLayout;
    private TextView textView;

    public ItemNav(Context context, @NonNull int imagem, String titulo) {
        super(context);
        init(imagem, titulo, 0);
    }

    public ItemNav(Context context, @NonNull int imagem, int imageIconActive, String titulo) {
        super(context);
        init(imagem, titulo, imageIconActive);
    }

    public ItemNav(Context context, @NonNull int imagem, int imageIconActive) {
        super(context);
        init(imagem, "", imageIconActive);
    }

    public ItemNav(Context context, @NonNull int imagem) {
        super(context);
        init(imagem, "", 0);
    }

    private void init(int imagem, String titulo, int imageIconActive) {
        this.imageIcon = imagem;
        this.imageIconActive = imageIconActive;
        this.titulo = titulo;

        addComponent();
    }

    private void addComponent() {
        configureRootLayout();
        configureImageView();
        configureTextView();
    }

    private void configureRootLayout() {
        setOrientation(LinearLayout.VERTICAL);

        LayoutParams param = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                1.0f
        );

        setLayoutParams(param);
        setGravity(Gravity.CENTER);
    }

    private void configureTextView() {
        if (this.titulo != null && !this.titulo.trim().equals("")) {
            this.textView = new TextView(getContext());
            this.textView.setGravity(Gravity.CENTER);
            this.textView.setTextSize(10);
            this.textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            this.textView.setText(this.titulo);

            addView(this.textView);
        }
    }

    private void configureImageView() {
        this.relativeLayout = new RelativeLayout(getContext());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        this.relativeLayout.setLayoutParams(layoutParams);

        this.imageView = new ImageView(getContext());
        this.imageView.setId(new Random().nextInt(10));
        this.imageView.setPadding(5,5,5,5);
        this.imageView.setLayoutParams(new RelativeLayout.LayoutParams(Util.convertDpToPixel(Util.VALUE_SIZE, getContext()), Util.convertDpToPixel(Util.VALUE_SIZE, getContext())));

        setIconInImageView(imageIcon);

        if (this.colorInactive != 0) {
            this.imageView.setColorFilter(ContextCompat.getColor(getContext(), this.colorInactive), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        this.relativeLayout.addView(this.imageView);

        addView(relativeLayout);
    }

    private void fileToImageView() {
        if (this.isProfile && this.pathImageProfile != null && !this.pathImageProfile.trim().equals("")) {
            updatePathImageProfile(pathImageProfile);
        }
    }

    private void setIconInImageView(int icon) {
        this.imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
    }

    // ***** INTERACTIONS ***** \\

    public ItemNav addBadgeIndicator(BadgeIndicator badgeIndicator) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_RIGHT, this.imageView.getId());
        params.addRule(RelativeLayout.ALIGN_TOP, this.imageView.getId());

        RelativeLayout layoutBadge = new RelativeLayout(getContext());
        layoutBadge.setLayoutParams(params);

        layoutBadge.addView(badgeIndicator);

        this.relativeLayout.addView(layoutBadge);
        return this;
    }

    public void updatePathImageProfile(String pathImage) {
        this.pathImageProfile = pathImage;

        if (pathImage != null && !pathImage.equals("")) {
            File imgFile = new File(pathImage);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(pathImage);
                if (myBitmap != null) {
                    Resources res = getResources();
                    RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, myBitmap);
                    dr.setCornerRadius(Math.max(myBitmap.getWidth(), myBitmap.getHeight()) / 2.0f);

                    RoundedImageView roundView = new RoundedImageView(getContext());
                    roundView.setImageDrawable(dr);
                    roundView.setLayoutParams(this.imageView.getLayoutParams());
                    roundView.setId(this.imageView.getId());
                    roundView.setPadding(
                            this.imageView.getPaddingLeft(), this.imageView.getPaddingTop(),
                            this.imageView.getPaddingRight(),this.imageView.getPaddingBottom()
                    );

                    removeView(this.imageView);

                    this.imageView = roundView;

                    addView(imageView);
                }
            } else {
                setIconInImageView(imageIcon);
            }
        } else {
            setIconInImageView(imageIcon);
        }
    }

    public ItemNav addColorInative(int colorInative) {
        this.colorInactive = colorInative;
        return this;
    }

    public ItemNav addColorAtive(int colorActive) {
        this.colorActive = colorActive;
        return this;
    }

    public boolean isProfile() {
        return isProfile;
    }

    public ItemNav setPathImageProfile(String path) {
        this.isProfile = true;
        this.pathImageProfile = path;
        fileToImageView();
        return this;
    }

    public void deselect() {

        if(imageIconActive != 0){
            setIconInImageView(imageIcon);
        }

        if (this.colorInactive != 0) {
            this.imageView.setColorFilter(ContextCompat.getColor(getContext(), this.colorInactive), android.graphics.PorterDuff.Mode.SRC_IN);

            if (this.titulo != null && !this.titulo.equals("")) {
                this.textView.setTextColor(ContextCompat.getColor(getContext(), this.colorInactive));
            }
        } else {
            this.imageView.setColorFilter(null);

            if (this.titulo != null && !this.titulo.equals("")) {
                this.textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
            }
        }

        post(new Runnable() {
            @Override
            public void run() {
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(Util.convertDpToPixel(Util.VALUE_SIZE, getContext()), Util.convertDpToPixel(Util.VALUE_SIZE, getContext())));
            }
        });
    }

    public void select() {

        if (dontHaveProfilePick()) {
            selectColor();
        }

        if(this.imageIconActive != 0 && dontHaveProfilePick()){
            setIconInImageView(this.imageIconActive);
        }

        doBounceAnimation();
    }

    private boolean dontHaveProfilePick(){
        return this.pathImageProfile == null || (this.pathImageProfile != null && this.pathImageProfile.trim().equals(""));
    }

    public void selectColor() {
        if (this.colorActive != 0) {
            this.imageView.setColorFilter(ContextCompat.getColor(getContext(), this.colorActive), android.graphics.PorterDuff.Mode.SRC_IN);

            if (this.titulo != null && !this.titulo.equals("")) {
                this.textView.setTextColor(ContextCompat.getColor(getContext(), this.colorActive));
            }
        }
    }

    private void doBounceAnimation() {
        post(new Runnable() {
            @Override
            public void run() {
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(Util.convertDpToPixel(Util.VALUE_SIZE_ACTIVE, getContext()), Util.convertDpToPixel(Util.VALUE_SIZE_ACTIVE, getContext())));
            }
        });
    }

}
