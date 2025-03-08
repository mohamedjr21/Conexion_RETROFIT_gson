package com.example.conexion_retrofit_gson.Controladores;

import com.example.conexion_retrofit_gson.Api.APIservicio;
import com.example.conexion_retrofit_gson.Controladores.Tiempo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HelloController {
    @FXML private TextField campoCiudad;
    @FXML private TextField campoTemperatura;
    @FXML private TextField campoHumedad;
    @FXML private TextField campoViento;
    @FXML private TextField campoPresion;
    @FXML private TextField campoDescripcion;
    @FXML private Label etiquetaSensacionTermica;
    @FXML private Label etiquetaDireccionViento;
    @FXML private Label etiquetaTendenciaPresion;
    @FXML private Label etiquetaUltimaActualizacion;
    @FXML private Label etiquetaEstado;
    @FXML private ProgressBar barraHumedad;

    private final String claveAPI = "9693c5a9f86ef1b1aa621cd546973a28";
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

    @FXML
    public void obtenerTiempo() {
        String ciudad = campoCiudad.getText().trim();

        if (ciudad.isEmpty()) {
            mostrarError("Por favor, introduce una ciudad");
            return;
        }

        resetearEstilos();
        etiquetaEstado.setText("Buscando datos del tiempo...");

        Thread hiloAPI = new Thread(() -> {
            Retrofit retrofit = new Retrofit.Builder()
                  .baseUrl("https://api.openweathermap.org/data/2.5/")
                  .addConverterFactory(GsonConverterFactory.create())
                  .build();

            APIservicio servicioAPI = retrofit.create(APIservicio.class);
            Call<Tiempo> llamada = servicioAPI.obtenerTiempo(ciudad, claveAPI, "metric");

            llamada.enqueue(new Callback<Tiempo>() {
                @Override
                public void onResponse(Call<Tiempo> call, Response<Tiempo> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        actualizarDatosTiempo(response.body());
                    } else {
                        Platform.runLater(() ->
                              mostrarError("Ciudad no encontrada. Verifica el nombre e inténtalo de nuevo.")
                        );
                    }
                }

                @Override
                public void onFailure(Call<Tiempo> call, Throwable t) {
                    Platform.runLater(() ->
                          mostrarError("Error de conexión: " + t.getMessage())
                    );
                }
            });
        });
        hiloAPI.start();
    }

    private void actualizarDatosTiempo(Tiempo tiempo) {
        Platform.runLater(() -> {
            campoTemperatura.setText(String.format("%.1f°C", tiempo.main.temp));
            campoHumedad.setText(tiempo.main.humidity + "%");
            campoViento.setText(String.format("%.1f km/h", tiempo.wind.speed));
            campoPresion.setText(tiempo.main.pressure + " hPa");
            campoDescripcion.setText(tiempo.weather.get(0).description);

            etiquetaSensacionTermica.setText(String.format("Sensación térmica: %.1f°C", tiempo.main.feels_like));
            barraHumedad.setProgress(tiempo.main.humidity / 100.0);
            etiquetaDireccionViento.setText(String.format("Dirección del viento: %d°", tiempo.wind.deg));

            actualizarEstadoExito();
        });
    }

    private void mostrarError(String mensaje) {
        campoCiudad.setStyle("-fx-border-color: #ff6b6b;");
        etiquetaEstado.setText(mensaje);
        etiquetaEstado.setStyle("-fx-text-fill: #ff6b6b;");
    }

    private void resetearEstilos() {
        campoCiudad.setStyle("");
        etiquetaEstado.setStyle("");
    }

    private void actualizarEstadoExito() {
        String horaActual = LocalTime.now().format(formatoHora);
        etiquetaUltimaActualizacion.setText("Última actualización: " + horaActual);
        etiquetaEstado.setText("Datos actualizados correctamente");
        etiquetaEstado.setStyle("-fx-text-fill: #4CAF50;");
    }
}
