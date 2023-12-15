package com.monopoco.musicmp4.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.monopoco.musicmp4.Activities.MainActivity;
import com.monopoco.musicmp4.Models.RegisterModel;
import com.monopoco.musicmp4.Models.UserModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {

    TextView toSignInText;

    FrameLayout frameLayout;

    private EditText email;
    private EditText password;
    private EditText username;
    private Button signUpButton;

    private FirebaseAuth mAuth;

    private LinearLayout loading;

    private Drawable errorIcon;

    private TextView txtToForget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        errorIcon = getResources().getDrawable(R.drawable.ic_error);

        toSignInText = view.findViewById(R.id.text_to_login);
        frameLayout = getActivity().findViewById(R.id.sign_in_frame_layout);

        //Get Email field
        email = view.findViewById(R.id.register_email_field);

        // Get Password field
        password = view.findViewById(R.id.register_password_field);

        // Get ConfirmPassword field
        username = view.findViewById(R.id.register_username_field);

        // Get Sign Up button
        signUpButton = view.findViewById(R.id.sign_up_button);

        // Get Loading
        loading = view.findViewById(R.id.loading);

        txtToForget = view.findViewById(R.id.text_to_forget);



        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toSignInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        txtToForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ForgotPasswordFragment());
            }
        });

        errorIcon.setBounds(0, 0, errorIcon.getIntrinsicWidth(), errorIcon.getIntrinsicHeight());

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

        password.addTextChangedListener(new TextWatcher() {
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

        username.addTextChangedListener(new TextWatcher() {
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

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                signUpHandle();
                signUpButton.setEnabled(false);
            }
        });
    }

    private void signUpHandle() {
        if (email.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {

            RegisterModel registerModel = new RegisterModel(
                    username.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString()
            );

            APIService.getService().Register(
                    RequestBody.create(MediaType.parse("text/plain"), registerModel.getUsername()),
                    RequestBody.create(MediaType.parse("text/plain"), registerModel.getPassword()),
                    RequestBody.create(MediaType.parse("text/plain"), registerModel.getEmail()))
                    .enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    loading.setVisibility(View.GONE);
                    signUpButton.setEnabled(true);
                    if (response.code() == 200) {
                        Toast.makeText(getContext(), "Created account successfully",
                                Toast.LENGTH_LONG).show();
                        new AlertDialog.Builder(getContext())
                                .setTitle("Created account successfully")
                                .setPositiveButton("Login now", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        setFragment(new SignInFragment());
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .setIcon(R.drawable.ic_check_circle)
                                .show();
                    } else if (response.code() == 400) {
                        Toast.makeText(getContext(), "Something wrong try again",
                                Toast.LENGTH_LONG).show();
                    }

                }
                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Toast.makeText(getContext(), "Error",
                            Toast.LENGTH_LONG).show();
                    loading.setVisibility(View.GONE);
                    signUpButton.setEnabled(true);

                }
            });
        } else {
            email.setError("Email invalidate.", errorIcon);
            signUpButton.setEnabled(true);
            loading.setVisibility(View.GONE);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.out_from_right);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInPuts() {
        if (!email.getText().toString().isEmpty() && !username.getText().toString().isEmpty()) {
            if (!password.getText().toString().isEmpty() && password.getText().toString().length() >= 8) {
                signUpButton.setEnabled(true);
            } else {
                signUpButton.setEnabled(false);
            }
        } else {
            signUpButton.setEnabled(false);
//            signUpButton.setTextColor(getResources().getColor(R.color.));
        }
    }
}