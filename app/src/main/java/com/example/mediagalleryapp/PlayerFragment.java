package com.example.mediagalleryapp;

import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlayerFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private Button btnPlay;
    private TextView textTrackInfo;
    private int[] sounds;
    private int currentTrack = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player, container, false);

        btnPlay = v.findViewById(R.id.btnPlay);
        textTrackInfo = v.findViewById(R.id.textTrackInfo);
        Button btnPrevTrack = v.findViewById(R.id.btnPrevTrack);
        Button btnNextTrack = v.findViewById(R.id.btnNextTrack);

        // Загружаем массив треков
        TypedArray ta = getResources().obtainTypedArray(R.array.player_sounds);
        sounds = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            sounds[i] = ta.getResourceId(i, 0);
        }
        ta.recycle();

        // Инициализация первого трека
        updateTrackInfo();
        preparePlayer();

        btnPlay.setOnClickListener(view -> {
            if (mediaPlayer == null) return;

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlay.setText("▶ Воспроизвести");
            } else {
                mediaPlayer.start();
                btnPlay.setText("■ Пауза");
            }
        });

        btnPrevTrack.setOnClickListener(view -> {
            currentTrack = (currentTrack - 1 + sounds.length) % sounds.length;
            updateTrackInfo();
            switchTrack();
        });

        btnNextTrack.setOnClickListener(view -> {
            currentTrack = (currentTrack + 1) % sounds.length;
            updateTrackInfo();
            switchTrack();
        });

        return v;
    }

    private void preparePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(getContext(), sounds[currentTrack]);
        btnPlay.setText("▶ Воспроизвести");
    }

    private void switchTrack() {
        preparePlayer();
        mediaPlayer.start();
        btnPlay.setText("■ Пауза");
    }

    private void updateTrackInfo() {
        textTrackInfo.setText("Трек " + (currentTrack + 1) + " из " + sounds.length);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
