package com.monopoco.musicmp4.Fragments;

import android.content.Intent;
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
import com.monopoco.musicmp4.Activities.HomeActivity;
import com.monopoco.musicmp4.R;


public class RegisterFragment extends Fragment {

    TextView toSignInText;

    FrameLayout frameLayout;

    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button signUpButton;

    private FirebaseAuth mAuth;

    private LinearLayout loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        toSignInText = view.findViewById(R.id.text_to_login);
        frameLayout = getActivity().findViewById(R.id.sign_in_frame_layout);

        //Get Email field
        email = view.findViewById(R.id.register_email_field);

        // Get Password field
        password = view.findViewById(R.id.register_password_field);

        // Get ConfirmPassword field
        confirmPassword = view.findViewById(R.id.confirm_password_field);

        // Get Sign Up button
        signUpButton = view.findViewById(R.id.sign_up_button);

        // Get Loading
        loading = view.findViewById(R.id.loading);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();



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

        confirmPassword.addTextChangedListener(new TextWatcher() {
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
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                mAuth.createUserWithEmailAndPassword(
                        email.getText().toString(),
                        password.getText().toString()
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Todo: handle if create successful
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        } else {
                            // Todo: fail to create account
                            Toast.makeText(getContext(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            signUpButton.setEnabled(true);
                            loading.setVisibility(View.GONE);
                        }
                    }
                });
            } else {
                confirmPassword.setError("Password doesn't match.");
                signUpButton.setEnabled(true);
                loading.setVisibility(View.GONE);
            }
        } else {
            email.setError("Email invalidate.");
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
        if (!email.getText().toString().isEmpty()) {
            if (!password.getText().toString().isEmpty() && password.getText().toString().length() >= 8) {
                if (!confirmPassword.getText().toString().isEmpty()) {
                    signUpButton.setEnabled(true);
                } else {
                    signUpButton.setEnabled(false);
                }
            } else {
                signUpButton.setEnabled(false);
            }
        } else {
            signUpButton.setEnabled(false);
//            signUpButton.setTextColor(getResources().getColor(R.color.));
        }
    }
}