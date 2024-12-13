package com.example.conexion_retrofit_gson;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HelloController {

    @FXML
    private TextField ciudadTextField;
    @FXML
    private TextField mostrarDatosTextfield;

    private final String apiKey = "9693c5a9f86ef1b1aa621cd546973a28";

    @FXML
    public void obtenerTiempo() {
        String ciudad = ciudadTextField.getText();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        APIservicio apiService = retrofit.create(APIservicio.class);
        Call<Tiempo> call = apiService.obtenerTiempo(ciudad, apiKey, "metric");

        call.enqueue(new Callback<Tiempo>() {
            @Override
            public void onResponse(Call<Tiempo> call, Response<Tiempo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Tiempo tiempo = response.body();

                    mostrarDatosTextfield.setText(String.format("Temperatura en %s es: %.2f°C", ciudad, tiempo.main.temp));

                } else {
                    mostrarDatosTextfield.setText("Error al obtener el clima de dicha. Ciudad no encontrada. Vuelva a intentarlo por favor");
                }
            }

            @Override
            public void onFailure(Call<Tiempo> call, Throwable ilo) {
                mostrarDatosTextfield.setText("Ha habido un error de conexión intentalo otra vez por favor: " + ilo.getMessage());
            }
        });
    }
}