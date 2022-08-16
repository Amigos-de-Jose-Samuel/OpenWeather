package br.ufpr.tads.openweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText cityInput, countryInput;
    TextView output;
    private final String units = "metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityInput = findViewById(R.id.cityInput);
        countryInput = findViewById(R.id.countryInput);
        output = findViewById(R.id.output);
    }

    public void doRequest(View view) {
        if (this.cityInput.getText().toString().length() == 0 || this.countryInput.getText().toString().length() == 0) {
            Toast.makeText(this, "Informe a cidade e o código do país!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Consultando temperatura...");
        progressDialog.show();

        Call<Weather> call = new RetrofitConfig("https://api.openweathermap.org/data/2.5/").getWeatherService().getWeather(this.cityInput.getText().toString() + "," + this.countryInput.getText().toString(), units, "de76910fa1d0a22616799e55199b9704");
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.isSuccessful()) {
                    Weather weather = response.body();
                    progressDialog.dismiss();
                    output.setText(String.format("Temperatura atual: %s °C\nTemperatura mínimia: %s °C\nTemperatura máxima: %s °C\nUmidade: %s %%", weather.getMain().getTemp(), weather.getMain().getTemp_min(), weather.getMain().getTemp_max(), weather.getMain().getHumidity()));
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });
    }
}