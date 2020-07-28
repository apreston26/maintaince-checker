package edu.cnm.deepdive.maintaincechecker.controller;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import edu.cnm.deepdive.maintaincechecker.R;
import edu.cnm.deepdive.maintaincechecker.service.GoogleSignInService;
import edu.cnm.deepdive.maintaincechecker.service.PermissionsService;
import edu.cnm.deepdive.maintaincechecker.viewmodel.MainViewModel;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

  private FloatingActionButton fab;
  private FloatingActionButton addMechanic;
  private FloatingActionButton addMaintenance;
  private TextView addMechanicText;
  private TextView addMaintenanceText;
  private boolean isOpen;
  private GoogleSignInService signInService;
  private static final int PERMISSIONS_REQUEST_CODE = 999;
  private final PermissionsService permissionsService = PermissionsService.getInstance();


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
    checkPermissionsOnce();
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

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == PERMISSIONS_REQUEST_CODE) {
      permissionsService.updatePermissions(permissions, grantResults);
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  private void checkPermissionsOnce() {
    MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    // Observe a flag indicating whether permissions have been checked previously.
    viewModel.getPermissionsChecked().observe(this, (checked) -> {
      // If permissions have not yet been checked, do so; then set the flag accordingly.
      if (!checked) {
        viewModel.setPermissionsChecked(true);
        permissionsService.checkPermissions(this, PERMISSIONS_REQUEST_CODE);
      }
    });
  }

}
