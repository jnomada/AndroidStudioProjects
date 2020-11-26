package myapp.jsealey.webviewsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign the view to a variable
        WebView webView = findViewById(R.id.webView);

        // Enable Javascript within the webview.
        webView.getSettings().setJavaScriptEnabled(true);

        // This allows the sites to be displayed in the WebView instead of launching the default browser on the phone
        webView.setWebViewClient(new WebViewClient());

        // Enter the website that we want to load within the WebView
        //webView.loadUrl("http://www.google.com");

        // We can also display HTML within the webview. First param is html code, second is the type, and third is the encoding
        webView.loadData("<html><head></head><body><h1>This is working!!</h1><p>Hello world this is my cool website!</p></body></html>", "text/html", "UTF-8");
    }
}