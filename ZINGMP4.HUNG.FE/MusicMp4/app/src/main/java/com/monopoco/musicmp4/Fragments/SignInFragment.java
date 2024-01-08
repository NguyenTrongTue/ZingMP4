package com.monopoco.musicmp4.Fragments;

import android.content.Context;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.monopoco.musicmp4.Activities.MainActivity;
import com.monopoco.musicmp4.Models.LoginModel;
import com.monopoco.musicmp4.Models.UserModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInFragment extends Fragment {

    private TextView dontHaveAnAccount;

    private FrameLayout frameLayout;

    private EditText email;

    private EditText password;

    private Button loginButton;

    private LinearLayout loading;

    private FirebaseAuth mAuth;

    private Drawable errorIcon;

    private TextView txtToForget;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        errorIcon = getResources().getDrawable(R.drawable.ic_error);

        dontHaveAnAccount = view.findViewById(R.id.text_to_register);

        frameLayout = getActivity().findViewById(R.id.sign_in_frame_layout);

        email = view.findViewById(R.id.login_email_field);

        password = view.findViewById(R.id.login_password_field);

        loginButton = view.findViewById(R.id.login_btn);

        loading = view.findViewById(R.id.loading);

        txtToForget = view.findViewById(R.id.text_to_forget);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new RegisterFragment());
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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                handleLogin();
                loginButton.setEnabled(false);

            }
        });
    }

    private void handleLogin() {
        if (email.getText().toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            LoginModel loginBody = new LoginModel(
                    email.getText().toString(),
                    password.getText().toString()
            );
            APIService.getService().Login(loginBody).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.code() == 200) {
                        SharedPreferences sp= getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor Ed=sp.edit();
                        Ed.putString("isLogin", "true" );
                        Ed.putString("userId", response.body().getUserId());
                        Ed.commit();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();

                    } else if (response.code() == 409) {
                        Toast.makeText(getContext(), "Tài khoản mật khẩu không chính xác",
                                Toast.LENGTH_LONG).show();
                        loginButton.setEnabled(true);
                    }
                    loading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    loading.setVisibility(View.GONE);
                    loginButton.setEnabled(true);
                }
            });
        } else {
            email.setError("Email invalidate.", errorIcon);
            loading.setVisibility(View.GONE);
            loginButton.setEnabled(true);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.out_from_left);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInPuts() {
        if (!email.getText().toString().isEmpty()) {
            if (!password.getText().toString().isEmpty()) {
                loginButton.setEnabled(true);
            } else {
                loginButton.setEnabled(false);
            }
        } else {
            loginButton.setEnabled(false);
//            signUpButton.setTextColor(getResources().getColor(R.color.));
        }
    }
}