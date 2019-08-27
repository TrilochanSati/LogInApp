package www.windapp.com.loginapp.ui.login;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import www.windapp.com.loginapp.R;
import www.windapp.com.loginapp.data.LoginDataSource;
import www.windapp.com.loginapp.data.LoginRepository;
import www.windapp.com.loginapp.data.Result;
import www.windapp.com.loginapp.data.model.LoggedInUser;

import static www.windapp.com.loginapp.ui.login.LoginActivity.loginViewModel;

public class LogInSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_success);

     final Dialog   passChange=new Dialog(this);
        passChange.requestWindowFeature(Window.FEATURE_NO_TITLE);

        passChange.setContentView(R.layout.password_change);

        passChange.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        final TextView userName=(TextView)findViewById(R.id.username);


        final EditText passChangeEditText=passChange.findViewById(R.id.passChangeEditText);
        final        Button submit=passChange.findViewById(R.id.newPassSubmit);

        final       Button changePass=findViewById(R.id.changePass);
        final    Button logout=findViewById(R.id.logout); // Pressing out bring back to Login form.




        userName.setText( LoginViewModel.data.getDisplayName());



        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                submit.setEnabled(loginFormState.isDataValid());



                if (loginFormState.getPasswordError() != null) {
                    passChangeEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Loged Out",Toast.LENGTH_LONG);
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passChange.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = LoginActivity.getSharedpreferences().edit();
                editor.putString(LoginViewModel.data.getDisplayName(), passChangeEditText.getText().toString());
                editor.commit();
                passChange.dismiss();
            }
        });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(LoginViewModel.data.getDisplayName(),
                        passChangeEditText.getText().toString());
            }
        };

        passChangeEditText.addTextChangedListener(afterTextChangedListener);
        passChangeEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(LoginViewModel.data.getDisplayName(),
                            passChangeEditText.getText().toString());
                }
                return false;
            }
        });

    }



}
