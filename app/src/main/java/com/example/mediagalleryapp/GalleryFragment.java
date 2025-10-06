package com.example.mediagalleryapp;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class GalleryFragment extends Fragment {

    private ImageView imageView;
    private TextView textCaption;
    private TextView textImageInfo;
    private int currentIndex = 0;
    private int[] images;
    private String[] captions;
    private Random random;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);

        imageView = v.findViewById(R.id.imageView);
        textCaption = v.findViewById(R.id.textCaption);
        textImageInfo = v.findViewById(R.id.textImageInfo);
        Button btnPrev = v.findViewById(R.id.btnPrev);
        Button btnNext = v.findViewById(R.id.btnNext);
        Button btnRandom = v.findViewById(R.id.btnRandom);

        // Инициализация Random
        random = new Random();

        // Загружаем массив изображений из arrays.xml
        TypedArray ta = getResources().obtainTypedArray(R.array.gallery_images);
        images = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            images[i] = ta.getResourceId(i, 0);
        }
        ta.recycle();

        // Загружаем массив подписей
        captions = getResources().getStringArray(R.array.gallery_captions);

        // Показываем начальную картинку и подпись
        updateImageAndCaption();

        btnPrev.setOnClickListener(view -> {
            currentIndex = (currentIndex - 1 + images.length) % images.length;
            updateImageAndCaption();
        });

        btnNext.setOnClickListener(view -> {
            currentIndex = (currentIndex + 1) % images.length;
            updateImageAndCaption();
        });

        btnRandom.setOnClickListener(view -> {
            int newIndex;
            do {
                newIndex = random.nextInt(images.length);
            } while (newIndex == currentIndex && images.length > 1);
            currentIndex = newIndex;
            updateImageAndCaption();
        });

        return v;
    }

    private void updateImageAndCaption() {
        imageView.setImageResource(images[currentIndex]);
        textCaption.setText(captions[currentIndex]);
        textImageInfo.setText("Изображение " + (currentIndex + 1) + " из " + images.length);
    }
}

