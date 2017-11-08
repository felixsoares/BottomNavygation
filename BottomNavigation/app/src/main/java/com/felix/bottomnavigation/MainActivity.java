package com.felix.bottomnavigation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.felix.bottomnavygation.BadgeIndicator;
import com.felix.bottomnavygation.BottomNav;
import com.felix.bottomnavygation.ItemNav;

public class MainActivity extends AppCompatActivity {

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BadgeIndicator badgeIndicator = new BadgeIndicator(this, android.R.color.holo_red_dark, android.R.color.white);

        final BottomNav bottomNav = findViewById(R.id.bottomNav);
        bottomNav.addItemNav(new ItemNav(this, R.drawable.feed, R.drawable.feed_sel).addBadgeIndicator(badgeIndicator));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.explore, R.drawable.explore_sel));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.atividades, R.drawable.atividades_sel));
        bottomNav.addItemNav(new ItemNav(this, R.drawable.perfil, R.drawable.perfil_sel).setPathImageProfile(""));
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
                bottomNav.updateImageProfile("https://cdn.hrpayrollsystems.net/wp-content/uploads/2013/03/phr-sphr-businessman-thinking.jpg");
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
}
