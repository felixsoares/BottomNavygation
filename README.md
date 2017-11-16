# BottomNavygation

Bottom Navigation based on Bottom Navigation View from Android

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
	compile 'com.github.felixsoares:BottomNavygation:1.8.2'
}
```

## Usage example

In your layout file

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

In your Activity or Fragment

```java
BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(this, R.mipmap.explore, "Explore").addColorAtive(R.color.colorAccent));
bottomNav.addItemNav(new ItemNav(this, R.mipmap.atividades).addColorAtive(R.color.colorAccent));
bottomNav.build();
```

### Documentation

1) Add listeners

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

2) Add badge, create a BadgeIndicator and put in ItemNav.

```java
BadgeIndicator badgeIndicator = new BadgeIndicator(this, android.R.color.holo_red_dark, android.R.color.white);

BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(this, R.mipmap.feed).addColorAtive(R.color.colorAccent).addBadgeIndicator(badgeIndicator));
bottomNav.build();
```

3) Update badge

```java
badgeIndicator.updateCount(count);
```

4) With you want to add profile photo int ItemNav

```java
BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(this, R.mipmap.perfil).addColorAtive(R.color.colorAccent).setPathImageProfile(YOUR_IMAGE_PATH));
bottomNav.build();
```

5) Ative and Inative color tab

```java
BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(this, R.mipmap.explore).addColorAtive(R.color.colorAccent).addColorInative(R.color.colorPrimary));
bottomNav.build();
```

6) Select especific Tab

```java
BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(this, R.mipmap.explore));
bottomNav.build();

bottomNav.selectTab(0);
```

7) Icon tab Active (change icon when select)

```java
BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(this, R.mipmap.explore, R.mipmap.explore_ative));
bottomNav.build();
```

8) Profile icon Active / Inative (change icon when select)

```java
BottomNav bottomNav = findViewById(R.id.bottomNav);
bottomNav.addItemNav(new ItemNav(contexto, R.drawable.explore, R.drawable.explore_sel).isProfileItem().addProfileColorAtive(R.color.verdepadrao).addProfileColorInative(R.color.preto));
bottomNav.build();
```
