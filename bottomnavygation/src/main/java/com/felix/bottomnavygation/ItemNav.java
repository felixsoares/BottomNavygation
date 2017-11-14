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
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.util.Random;

/**
 * Created by user on 07/11/2017.
 */

public class ItemNav extends LinearLayout {

    private int profileActive;
    private int profileInactive;
    private int imageIcon;
    private int imageIconActive;
    private String titulo;
    private String pathImageProfile;

    private int colorInactive;
    private int colorActive;

    private Boolean isProfile = false;

    private CircularImageView circularImageView;
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
        this.titulo = titulo;
        this.imageIconActive = imageIconActive;

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

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                Util.convertDpToPixel(Util.VALUE_SIZE, getContext()),
                Util.convertDpToPixel(Util.VALUE_SIZE, getContext())
        );

        int id = new Random().nextInt(10);

        this.imageView = new ImageView(getContext());
        this.imageView.setId(id);
        this.imageView.setPadding(5, 5, 5, 5);
        this.imageView.setLayoutParams(param);

        this.circularImageView = new CircularImageView(getContext());
        this.circularImageView.setId(id);
        this.circularImageView.setPadding(5, 5, 5, 5);
        this.circularImageView.setLayoutParams(param);
        this.circularImageView.setBorderWidth(4);
        this.circularImageView.setVisibility(GONE);

        setIconInImageView(imageIcon);

        if (this.colorInactive != 0) {
            this.imageView.setColorFilter(ContextCompat.getColor(getContext(), this.colorInactive), android.graphics.PorterDuff.Mode.MULTIPLY);
            this.circularImageView.setBorderColor(ContextCompat.getColor(getContext(), this.colorInactive));
        }

        this.relativeLayout.addView(this.imageView);
        this.relativeLayout.addView(this.circularImageView);

        addView(relativeLayout);
    }

    private void fileToImageView() {
        if (this.isProfile && this.pathImageProfile != null && !this.pathImageProfile.trim().equals("")) {
            updatePathImageProfile(pathImageProfile);
        }
    }

    private void setIconInImageView(int icon) {
        this.imageView.setVisibility(VISIBLE);
        this.imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), icon));
        this.circularImageView.setVisibility(GONE);
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

                    if(this.colorInactive != 0){
                        deselect();
                    }

                    this.imageView.setVisibility(GONE);
                    this.circularImageView.setVisibility(VISIBLE);
                    this.circularImageView.setImageBitmap(myBitmap);
                }
            } else {
                this.isProfile = false;
                setIconInImageView(imageIcon);
            }
        } else {
            this.isProfile = false;
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

    public ItemNav addProfileColorInative(int colorInative) {
        this.profileInactive = colorInative;
        return this;
    }

    public ItemNav addProfileColorAtive(int colorActive) {
        this.profileActive = colorActive;
        return this;
    }

    public boolean isProfile() {
        return isProfile;
    }

    public ItemNav isProfileItem() {
        this.isProfile = true;
        return this;
    }

    public ItemNav setPathImageProfile(String path) {
        this.isProfile = true;
        this.pathImageProfile = path;
        fileToImageView();
        return this;
    }

    public void deselect() {
        if (isProfile() && pathImageProfile != null && !pathImageProfile.trim().equals("")) {
            selectInactiveColorProfile();
        } else {
            if (imageIconActive != 0) {
                setIconInImageView(imageIcon);
            }

            if (this.colorInactive != 0) {
                this.imageView.setColorFilter(ContextCompat.getColor(getContext(), this.colorInactive), android.graphics.PorterDuff.Mode.SRC_IN);
                this.circularImageView.setBorderColor(ContextCompat.getColor(getContext(), this.colorInactive));

                if (this.titulo != null && !this.titulo.equals("")) {
                    this.textView.setTextColor(ContextCompat.getColor(getContext(), this.colorInactive));
                }
            } else {
                this.imageView.setColorFilter(null);
                this.circularImageView.setBorderColor(0);

                if (this.titulo != null && !this.titulo.equals("")) {
                    this.textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                }
            }
        }

        post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                        Util.convertDpToPixel(Util.VALUE_SIZE, getContext()),
                        Util.convertDpToPixel(Util.VALUE_SIZE, getContext())
                );

                imageView.setLayoutParams(param);
                circularImageView.setLayoutParams(param);
            }
        });
    }

    public void select() {

        if (isProfile() && pathImageProfile != null && !pathImageProfile.trim().equals("")) {
            selectActiveColorProfile();
        } else {
            if (this.imageIconActive != 0) {
                setIconInImageView(this.imageIconActive);
            } else {
                selectColor();
            }
        }

        doBounceAnimation();
    }

    public void selectInactiveColorProfile() {
        if (this.profileInactive != 0) {
            this.circularImageView.setBorderColor(ContextCompat.getColor(getContext(), this.profileInactive));
        }
    }

    public void selectActiveColorProfile() {
        if (this.profileActive != 0) {
            this.circularImageView.setBorderColor(ContextCompat.getColor(getContext(), this.profileActive));
        }
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
                RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                        Util.convertDpToPixel(Util.VALUE_SIZE_ACTIVE, getContext()),
                        Util.convertDpToPixel(Util.VALUE_SIZE_ACTIVE, getContext())
                );
                imageView.setLayoutParams(param);
                circularImageView.setLayoutParams(param);
            }
        });
    }

}
