

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
    private TextField mostrarDatosTextfield;

    public void initialize() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservicio apiService = retrofit.create(APIservicio.class);
        Call<Tiempo> call = apiService.obtenerTiempo(
                "36.7763",
                "-2.8147",
                "9693c5a9f86ef1b1aa621cd546973a28",
                "metric"
        );

        call.enqueue(new Callback<Tiempo>() {


            @Override
            public void onResponse(Call<Tiempo> call, Response<Tiempo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Tiempo tiempo = response.body();
                    mostrarDatosTextfield.setText(String.format("Temperatura en El Ejido es: %.2f°C",
                            tiempo.main.temp));
                }
            }

            @Override
            public void onFailure(Call<Tiempo> call, Throwable t) {
                System.out.println("Error de conexión: " + t.getMessage());
            }
        });
    }
}
