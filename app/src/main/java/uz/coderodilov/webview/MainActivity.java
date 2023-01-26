package uz.coderodilov.webview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import uz.coderodilov.webview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        loadWebView();
        binding.reloadWebView.setOnClickListener(v -> loadWebView());

    }

     public boolean isInternetConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
     }

     public void loadWebView(){
        binding.webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (isInternetConnected()){
                    super.onPageStarted(view, url, favicon);
                    binding.webView.setVisibility(View.INVISIBLE);
                    binding.progressBarWebLoading.setVisibility(View.VISIBLE);
                    binding.noInternetConnection.setVisibility(View.INVISIBLE);
                } else {
                    binding.webView.setVisibility(View.INVISIBLE);
                    binding.progressBarWebLoading.setVisibility(View.GONE);
                    binding.noInternetLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (isInternetConnected()){
                    super.onPageFinished(view, url);
                    binding.progressBarWebLoading.setVisibility(View.GONE);
                    binding.webView.setVisibility(View.VISIBLE);
                    binding.noInternetLayout.setVisibility(View.INVISIBLE);
                } else {
                    binding.webView.setVisibility(View.INVISIBLE);
                    binding.progressBarWebLoading.setVisibility(View.GONE);
                    binding.noInternetConnection.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
                }
            }

        });

        binding.webView.loadUrl("https://translate.google.com/");
     }

}