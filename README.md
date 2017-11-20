[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-BottomNavygation-green.svg?style=flat )]( https://android-arsenal.com/details/1/6463 )
[![Release]( https://img.shields.io/badge/Release-v1.8.6-blue.svg?style=flat )]( https://jitpack.io/#felixsoares/BottomNavygation/ )

# BottomNavygation

Bottom Navigation based on Bottom Navigation View from Android

![](https://image.ibb.co/i0zMk6/img1.jpg) ![](https://image.ibb.co/fu2wJR/img2.jpg) ![](https://image.ibb.co/bSEXsm/img3.jpg)

### Getting Started

Add it in your root build.gradle (Project module)

```gradle
allprojects {
   repositories {
        ...
        maven { url 'https://jitpack.io' }
   }
}
```

Add the dependency in build.gradle (App module)

```gradle
dependencies {
	compile 'com.github.felixsoares:BottomNavygation:1.8.6'
}
```

## Usage example

In layout file

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.felix.bottomnavygation.BottomNav
        android:id="@+id/bottomNav"
        android:background="@color/gray"
        android:layout_alignParentBottom="true"
        android:layout_width="match_parent"
        android:layout_height="55dp"/>

</RelativeLayout>
```

In Activity or Fragment

```java
BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(this, R.mipmap.explore, "Explore").addColorAtive(R.color.colorAccent));
bottomNav.addItemNav(new ItemNav(this, R.mipmap.atividades).addColorAtive(R.color.colorAccent));
bottomNav.build();
```

### Documentation

1) Support click and longClick listeners (just in case if ItemNav is profile item).

```java
bottomNav.setTabSelectedListener(listener);

BottomNav.OnTabSelectedListener listener = new BottomNav.OnTabSelectedListener() {
    @Override
    public void onTabSelected(int position) {
        Toast.makeText(MainActivity.this, "Click position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabLongSelected(int position) {
        Toast.makeText(MainActivity.this, "Long position " + position, Toast.LENGTH_SHORT).show();
    }
};
```

2) Support to add badge in ItemNav.

```java
BadgeIndicator badgeIndicator = new BadgeIndicator(this, android.R.color.holo_red_dark, android.R.color.white);

BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(this, R.mipmap.feed).addColorAtive(R.color.colorAccent).addBadgeIndicator(badgeIndicator));
bottomNav.build();
```

3) Update badge.

```java
badgeIndicator.updateCount(count);
```

4) Support to add profile photo int ItemNav.

```java
BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(this, R.mipmap.perfil).addColorAtive(R.color.colorAccent).setPathImageProfile(YOUR_IMAGE_PATH));
bottomNav.build();
```

5) Support to ative and inative colors in tab.

```java
BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(this, R.mipmap.explore).addColorAtive(R.color.colorAccent).addColorInative(R.color.colorPrimary));
bottomNav.build();
```

6) Support to select especific Tab.

```java
BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(this, R.mipmap.explore));
bottomNav.build();

bottomNav.selectTab(0);
```

7) Support to change icon when is ative.

```java
BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(this, R.mipmap.explore, R.mipmap.explore_ative));
bottomNav.build();
```

8) Support to change color of border from profile photo when is ative or inative.

```java
BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(contexto, R.drawable.explore, R.drawable.explore_sel).isProfileItem().addProfileColorAtive(R.color.verdepadrao).addProfileColorInative(R.color.preto));
bottomNav.build();
```

MIT License

Copyright (c) 2017 Felix Soares

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
