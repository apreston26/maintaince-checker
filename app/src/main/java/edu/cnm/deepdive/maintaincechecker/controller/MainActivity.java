package edu.cnm.deepdive.maintaincechecker.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import edu.cnm.deepdive.maintaincechecker.R;
import edu.cnm.deepdive.maintaincechecker.service.GoogleSignInService;
import edu.cnm.deepdive.maintaincechecker.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

  private FloatingActionButton fab;
  private FloatingActionButton addMechanic;
  private FloatingActionButton addMaintenance;
  private TextView addMechanicText;
  private TextView addMaintenanceText;
  private boolean isOpen;
  
  private GoogleSignInService signInService;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    //noinspection SwitchStatementWithTooFewBranches
    switch (item.getItemId()) {
      case R.id.sign_out:
        signInService.signOut().addOnCompleteListener((ignored) -> switchToLogin());
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupObservers();

    fab = findViewById(R.id.fab);
    addMechanic = findViewById(R.id.add_mechanic);
    addMaintenance = findViewById(R.id.add_maintenance);
    addMechanicText = findViewById(R.id.add_mechanic_text);
    addMaintenanceText = findViewById(R.id.add_maintenance_text);
    isOpen = false;

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(isOpen) {
          addMaintenance.setVisibility(View.INVISIBLE);
          addMechanic.setVisibility(View.INVISIBLE);
          addMaintenanceText.setVisibility(View.INVISIBLE);
          addMechanicText.setVisibility(View.INVISIBLE);
          isOpen = false;
        } else {
          addMaintenance.setVisibility(View.VISIBLE);
          addMechanic.setVisibility(View.VISIBLE);
          addMaintenanceText.setVisibility(View.VISIBLE);
          addMechanicText.setVisibility(View.VISIBLE);
          isOpen = true;
        }
      }
    });
  }



  private void setupObservers() {
    MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    getLifecycle().addObserver(viewModel);
    viewModel.getThrowable().observe(this, (throwable) -> {
      if (throwable != null) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
      }
    });
    signInService = GoogleSignInService.getInstance();
  }

  private void switchToLogin() {
    Intent intent = new Intent(this, LoginActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

}
