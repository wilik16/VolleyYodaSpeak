package id.wilik.android.volleyyodaspeak;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editText_sentence;
    Button button_yoda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_sentence = (EditText) findViewById(R.id.editText_sentence);
        button_yoda = (Button) findViewById(R.id.button_yoda);
        button_yoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sentence = editText_sentence.getText().toString().trim();
                yodaSpeaks(sentence);
            }
        });
    }

    private void yodaSpeaks(String sentence) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String uri = Uri.parse("https://yoda.p.mashape.com/yoda").buildUpon()
                .appendQueryParameter("sentence", sentence)
                .build().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("X-Mashape-Key", "<API Key>");
                params.put("Accept", "text/plain");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
