package com.monopoco.musicmp4.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.monopoco.musicmp4.Activities.MainActivity;
import com.monopoco.musicmp4.Models.LoginModel;
import com.monopoco.musicmp4.Models.UserModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends Fragment {

    private EditText email;

    private Button sendBtn;

    private FrameLayout frameLayout;

    private LinearLayout loading;

    private TextView txtBack;

    private Drawable errorIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        initValue(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void initValue(View view) {
        errorIcon = getResources().getDrawable(R.drawable.ic_error);
        errorIcon.setBounds(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight());
        email = view.findViewById(R.id.fp_email_field);
        sendBtn = view.findViewById(R.id.send_email_button);
        frameLayout = getActivity().findViewById(R.id.sign_in_frame_layout);
        txtBack = view.findViewById(R.id.text_back);
        loading = view.findViewById(R.id.loading);

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInPuts();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                sendBtn.setEnabled(false);
                handleGetNewPassword();

            }
        });
    }

    private void handleGetNewPassword() {
        if (email.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            APIService.getService().GetNewPassword(email.getText().toString()).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    loading.setVisibility(View.GONE);
                    sendBtn.setEnabled(true);
                    if (response.code() == 200) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Send new password successfully")
                                .setMessage("Check your email")
                                .setPositiveButton("Login now", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        setFragment(new SignInFragment());
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .setIcon(R.drawable.ic_check_circle)
                                .show();
                    } else {
                        Toast.makeText(getContext(), "Something wrong try again",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Toast.makeText(getContext(), "Error",
                            Toast.LENGTH_LONG).show();
                    loading.setVisibility(View.GONE);
                    sendBtn.setEnabled(true);
                }
            });

        } else {
            email.setError("Email invalidate.", errorIcon);
            loading.setVisibility(View.GONE);
            sendBtn.setEnabled(true);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.out_from_right);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInPuts() {
        if (!email.getText().toString().isEmpty()) {
            sendBtn.setEnabled(true);
        } else {
            sendBtn.setEnabled(false);
        }
    }
}