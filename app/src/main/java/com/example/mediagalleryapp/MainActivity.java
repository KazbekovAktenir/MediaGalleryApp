package com.example.mediagalleryapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private GalleryFragment galleryFragment;
    private PlayerFragment playerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создаем фрагменты
        galleryFragment = new GalleryFragment();
        playerFragment = new PlayerFragment();

        // Проверяем ориентацию экрана
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Горизонтальная ориентация - показываем оба фрагмента
            setupLandscapeMode();
        } else {
            // Вертикальная ориентация - показываем один фрагмент с навигацией
            setupPortraitMode();
        }
    }

    private void setupPortraitMode() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.VISIBLE);
            bottomNav.setOnItemSelectedListener(item -> {
                Fragment selected = null;
                int id = item.getItemId();

                if (id == R.id.nav_gallery) {
                    selected = galleryFragment;
                } else if (id == R.id.nav_player) {
                    selected = playerFragment;
                }

                if (selected != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, selected)
                            .commit();
                    return true;
                }
                return false;
            });

            // первый экран по умолчанию
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, galleryFragment)
                    .commit();
        }
    }

    private void setupLandscapeMode() {
        // Скрываем навигацию в горизонтальной ориентации
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.GONE);
        }

        // Очищаем контейнер и добавляем оба фрагмента
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, galleryFragment)
                .add(R.id.fragment_container, playerFragment)
                .commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        
        // Обрабатываем изменение ориентации без пересоздания активности
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setupLandscapeMode();
        } else {
            setupPortraitMode();
        }
    }
}
