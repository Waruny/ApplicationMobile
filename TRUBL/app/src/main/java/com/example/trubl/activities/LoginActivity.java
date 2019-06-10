package com.example.trubl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import com.example.trubl.R;
import com.example.trubl.helpers.InputValidation;
import com.example.trubl.sql.DatabaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout username_text_input;
    private TextInputLayout password_text_input;

    private TextInputEditText username_edit_text;
    private TextInputEditText password_edit_text;

    private MaterialButton login_button;
    private MaterialButton mdpoublie_button;

    private TextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView);

        username_text_input = findViewById(R.id.username_text_input);
        password_text_input = findViewById(R.id.password_text_input);

        username_edit_text = findViewById(R.id.username_edit_text);
        password_edit_text = findViewById(R.id.password_edit_text);

        mdpoublie_button = findViewById(R.id.mdpoublie_button);
        login_button = findViewById(R.id.login_button);
        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        login_button.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(username_edit_text, username_text_input, getString(R.string.error_message_username))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(password_edit_text, password_text_input, getString(R.string.error_message_username))) {
            return;
        }

        if (databaseHelper.checkUser(username_edit_text.getText().toString().trim()
                , password_edit_text.getText().toString().trim())) {


            Intent accountsIntent = new Intent(activity, AccueilActivity.class);
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_username_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        username_edit_text.setText(null);
        password_edit_text.setText(null);
    }
}
