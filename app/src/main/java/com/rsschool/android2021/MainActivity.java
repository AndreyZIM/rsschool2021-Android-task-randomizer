package com.rsschool.android2021;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements FragmentChanger {

    private int value = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        openFirstFragment(0);
    }

    public void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    public void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, secondFragment, "s_fr");
        transaction.commit();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int previousNumber) {
        value = previousNumber;
    }

    @Override
    public void onBackPressed() {
        if (Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("s_fr")).isAdded())
            openFirstFragment(value);
        else super.onBackPressed();
    }
}
