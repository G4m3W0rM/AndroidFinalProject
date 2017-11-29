package com.akhil.schooldiaries;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class Fragment_Login extends Fragment  implements View.OnClickListener{

    private EditText userid;
    private EditText password;
    private Button Loginme;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    public Fragment_Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        progressDialog = new ProgressDialog(getContext());
        firebaseAuth = FirebaseAuth.getInstance();

        userid = (EditText) view.findViewById(R.id.UsernameField);
        password = (EditText) view.findViewById(R.id.PasswordField);

        Loginme = (Button) view.findViewById(R.id.Button_Submit);
        Loginme.setOnClickListener(this);

        return view;
    }


    public void LoginPressed(View view) {
        final String username = userid.getText().toString();
        String pass = password.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(getContext(), "User Id cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.isEmpty()) {
            Toast.makeText(getContext(), "Password cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setTitle("Authenticating");
        progressDialog.setMessage("Loggin in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(username, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Welcome " + username , Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(),ParentMain.class);
                    i.putExtra("username", username);
                    startActivity(i);
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(getContext(), "Loggin failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Button_Submit:
                LoginPressed(v);
        }
    }
}

