package com.monopoco.musicmp4.Classes;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.monopoco.musicmp4.Fragments.ProfileFragment;
import com.monopoco.musicmp4.Models.UserModel;
import com.monopoco.musicmp4.R;
import com.monopoco.musicmp4.Requests.APIService;
import com.monopoco.musicmp4.Utils.ImageUtils;
import com.monopoco.musicmp4.Utils.RealPathUtil;

import org.w3c.dom.Text;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditBottomSheet extends BottomSheetDialogFragment {

    BottomSheetBehavior bBehavior;

    TextView btnClose;

    TextView btnSave;

    ImageView profileImage;

    EditText usernameField;

    EditText emailField;

    FloatingActionButton fabPick;

    Uri mUri;

    private ProgressDialog progressDialog;


    private UserModel userModel;

    private ProfileFragment parent;

    public ProfileEditBottomSheet(UserModel userModel, ProfileFragment profileFragment) {
        this.userModel = userModel;
        this.parent = profileFragment;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init progress
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait ...");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_edit_profile, null);
        btnClose = view.findViewById(R.id.txt_close);
        btnSave = view.findViewById(R.id.txt_save);

        profileImage = view.findViewById(R.id.profile_user_image);
        usernameField = view.findViewById(R.id.edit_username_field);
        emailField = view.findViewById(R.id.edit_email_field);
        fabPick = view.findViewById(R.id.fab_pick);
        initValue();
        dialog.setContentView(view);
        bBehavior = BottomSheetBehavior.from((View) view.getParent());
        bBehavior.setSkipCollapsed(true);
        return dialog;
    }

    private void initValue() {
        if (userModel.getAvatar() != null && !userModel.getAvatar().isEmpty()) {
            ImageUtils.setImageUrl(userModel.getAvatar(), profileImage, getContext());
        } else {
            profileImage.setImageResource(R.drawable.defaul_image_profile);
        }

        usernameField.setText(userModel.getUsername());
        emailField.setText(userModel.getEmail());
        btnSave.setClickable(false);

        usernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    btnSave.setClickable(true);
                    btnSave.setTextColor(getResources().getColor(R.color.white));
                } else {
                    btnSave.setClickable(false);
                    btnSave.setTextColor(getResources().getColor(R.color.disabletxt));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    btnSave.setClickable(true);
                    btnSave.setTextColor(getResources().getColor(R.color.white));
                } else {
                    btnSave.setClickable(false);
                    btnSave.setTextColor(getResources().getColor(R.color.disabletxt));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fabPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSave();
            }
        });
    }

    public void handleSave() {
        if (mUri != null) {
            progressDialog.show();

            RequestBody rbUsername = RequestBody.create(MediaType.parse("multipart/form-data"), usernameField.getText().toString().trim());
            RequestBody rbEmail = RequestBody.create(MediaType.parse("multipart/form-data"), userModel.getEmail().trim());
            String strPath = RealPathUtil.getRealPath(getContext(), mUri);
            File file = new File(strPath);

            RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part partAvt = MultipartBody.Part.createFormData("avatar", file.getName(), requestBodyAvt);

            APIService.getService().EditUser(rbUsername, rbEmail, partAvt).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.code() == 200) {
                        parent.updateUser(response.body());
                        dismiss();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Toast.makeText(getContext(), "Server error", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });

        }
    }

    public void imagePicker() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri uri = data.getData();
            mUri = uri;
            profileImage.setImageURI(uri);

            btnSave.setClickable(true);
            btnSave.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        bBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getDialog().isShowing()) {
                    dismiss();
                }
            }
        });
    }

}
