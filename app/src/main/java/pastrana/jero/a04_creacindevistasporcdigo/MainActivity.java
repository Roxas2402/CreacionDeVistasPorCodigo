package pastrana.jero.a04_creacindevistasporcdigo;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import pastrana.jero.a04_creacindevistasporcdigo.databinding.ActivityMainBinding;
import pastrana.jero.a04_creacindevistasporcdigo.modelos.AlumnoModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> addAlumnoLauncher;

    //Necesitamos 3 cosas
    //1: Un elemento para mostrar información
    //2: Los datos (ArrayList<AlumnosModel>)
    //3: Un contenedor donde meter los datos (LinearLayout(Vertical) -> ScrollView)
    //4: Lógica para printar los elementos
    private ArrayList<AlumnoModel> alumnoModelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        alumnoModelArrayList = new ArrayList<>();
        inicializaLauncher();


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAlumnoLauncher.launch(new Intent(MainActivity.this, AddAlumnoActivity.class));
            }
        });
    }

    private void inicializaLauncher() {
        addAlumnoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null && result.getData().getExtras() != null) {
                        AlumnoModel alumnoModel = (AlumnoModel) result.getData().getExtras().getSerializable("ALUMNO");
                        if (alumnoModel != null) {
                            alumnoModelArrayList.add(alumnoModel);
                            mostrarAlumnos();
                        }
                    }
                }
            }
        });
    }

    private void mostrarAlumnos() {
        binding.contentMain.contenedorMain.removeAllViews();

        for (AlumnoModel alumno : alumnoModelArrayList) {
            LayoutInflater li = LayoutInflater.from(this);
            View alumnoView = li.inflate(R.layout.alumno_model_view, null);
            TextView txtNombre = alumnoView.findViewById(R.id.lblNombreAlumnoView);
            TextView txtApellidos = alumnoView.findViewById(R.id.lblApellidosAlumnoView);
            TextView txtCiclo = alumnoView.findViewById(R.id.lblCiclosAlumnoView);
            TextView txtGrupo = alumnoView.findViewById(R.id.lblGrupoAlumnoView);

            txtNombre.setText(alumno.getNombre());
            txtApellidos.setText(alumno.getApellidos());
            txtCiclo.setText(alumno.getCiclo());
            txtGrupo.setText(String.valueOf(alumno.getGrupo()));

            binding.contentMain.contenedorMain.addView(alumnoView);
        }
    }

}