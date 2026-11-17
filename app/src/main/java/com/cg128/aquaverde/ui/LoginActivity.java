
package com.cg128.aquaverde.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cg128.aquaverde.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout layoutCorreo, layoutPassword;
    private TextInputEditText etCorreo, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DynamicColors.applyToActivityIfAvailable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layoutCorreo = findViewById(R.id.layoutCorreo);
        layoutPassword = findViewById(R.id.layoutPassword);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);

        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegistro = findViewById(R.id.tvRegistro);
        TextView tvRecuperar = findViewById(R.id.tvRecuperar);

        btnLogin.setOnClickListener(v -> validarLogin());

        tvRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistroActivity.class);
            startActivity(intent);
        });

        tvRecuperar.setOnClickListener(v -> recuperarPassword());
    }

    private void validarLogin() {
        String correo = etCorreo.getText() == null
                ? "" : etCorreo.getText().toString().trim();

        String password = etPassword.getText() == null
                ? "" : etPassword.getText().toString();

        layoutCorreo.setError(null);
        layoutPassword.setError(null);

        boolean valido = true;

        if (correo.isEmpty()) {
            layoutCorreo.setError("Ingresa tu correo");
            valido = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            layoutCorreo.setError("Correo electrónico inválido");
            valido = false;
        }

        if (password.isEmpty()) {
            layoutPassword.setError("Ingresa tu contraseña");
            valido = false;
        } else if (password.length() < 6) {
            layoutPassword.setError("Mínimo 6 caracteres");
            valido = false;
        }

        if (valido) {
            Toast.makeText(
                    this,
                    "¡Bienvenido a AquaVerde!",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void recuperarPassword() {
        TextInputLayout inputLayout =
                new TextInputLayout(this);

        inputLayout.setHint("Correo electrónico");

        TextInputEditText input =
                new TextInputEditText(inputLayout.getContext());

        input.setInputType(
                android.text.InputType.TYPE_CLASS_TEXT |
                        android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        );

        inputLayout.addView(input);

        android.widget.LinearLayout contenedor =
                new android.widget.LinearLayout(this);

        contenedor.setOrientation(
                android.widget.LinearLayout.VERTICAL
        );

        int margen = (int) (24 * getResources()
                .getDisplayMetrics().density);

        contenedor.setPadding(margen, 0, margen, 0);
        contenedor.addView(inputLayout);

        androidx.appcompat.app.AlertDialog dialog =
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Recuperar contraseña")
                        .setMessage(
                                "Ingresa tu correo para solicitar " +
                                        "la recuperación de tu contraseña."
                        )
                        .setView(contenedor)
                        .setNegativeButton("Cancelar", (d, w) -> d.dismiss())
                        .setPositiveButton("Enviar", null)
                        .create();

        dialog.setOnShowListener(d -> {
            dialog.getButton(
                    androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE
            ).setOnClickListener(v -> {

                String correo = input.getText() == null
                        ? "" : input.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS
                        .matcher(correo).matches()) {
                    inputLayout.setError(
                            "Ingresa un correo válido"
                    );
                    return;
                }

                Toast.makeText(
                        this,
                        "Solicitud simulada enviada",
                        Toast.LENGTH_LONG
                ).show();

                dialog.dismiss();
            });
        });

        dialog.show();
    }
}
