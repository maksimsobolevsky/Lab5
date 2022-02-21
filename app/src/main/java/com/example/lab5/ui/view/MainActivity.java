package com.example.lab5.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5.App;
import com.example.lab5.R;
import com.example.lab5.data.database.UserDao;
import com.example.lab5.data.entity.User;
import com.example.lab5.ui.adapter.UserAdapter;
import com.example.lab5.ui.viewmodel.FormViewModel;
import com.example.lab5.ui.viewmodel.FormViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    private TextInputEditText searchEditText;
    private TextView totalRecords;
    private TextView foundRecords;
    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private final UserAdapter adapter = new UserAdapter(new ArrayList<>());
    private FormViewModel viewModel;
    private final ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            Bundle extras = result.getData().getExtras();
            User user = new User(
                    extras.getString(FormActivity.NAME),
                    extras.getString(FormActivity.SURNAME),
                    extras.getString(FormActivity.COMMENT)
            );

            viewModel.saveUser(user);
            viewModel.filter.postValue(viewModel.filter.getValue());
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        App app = (App) getApplication();
        UserDao userDao = app.database.userDao();
        viewModel = new ViewModelProvider(this, new FormViewModelFactory(userDao)).get(FormViewModel.class);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.allUsersCount.observe(this, integer -> {
            totalRecords.setText(integer.toString());
        });

        viewModel.filteredUsers.observe(this, adapter::setUsers);
        viewModel.foundRecords.observe(this, integer -> {
            foundRecords.setText(integer.toString());
        });

        searchEditText.addTextChangedListener(this);

        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, FormActivity.class);
            launcher.launch(intent);
        });
    }

    private void setupViews() {
        totalRecords = findViewById(R.id.totalRecordsTextView);
        foundRecords = findViewById(R.id.foundRecordsTextView);
        searchEditText = findViewById(R.id.searchEditText);
        recyclerView = findViewById(R.id.recycler);
        addButton = findViewById(R.id.addButton);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        viewModel.filter.postValue(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}