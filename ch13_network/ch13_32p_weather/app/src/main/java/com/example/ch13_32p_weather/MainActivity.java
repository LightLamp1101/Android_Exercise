package com.example.ch13_32p_weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    TextView textview;
    Document doc = null;
    LinearLayout layout = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = (TextView) findViewById(R.id.textView1);
    }
    public void onClick(View view) {
        GetXMLTask task = new GetXMLTask();
        task.execute("https://www.kma.go.kr/wid/queryDFS.jsp?gridx=37.341&gridy=126.7347");
    }
    // private inner class extending AsyncTask
    private class GetXMLTask extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Parsing Error",
                        Toast.LENGTH_SHORT).show();
            }
            return doc;
        }
        @Override
        protected void onPostExecute(Document doc) {
            String s = "";
            NodeList nodeList = doc.getElementsByTagName("data");
            for (int i = 0; i < nodeList.getLength(); i++) {
                s += "" + i + ": 날씨 정보: ";
                Element dataElement = (Element) nodeList.item(i);
                NodeList tempList = dataElement.getElementsByTagName("temp");
                Element tempElement = (Element) tempList.item(0);
                NodeList textList = tempElement.getChildNodes();
                s += "온도 = " + ((Node) textList.item(0)).getNodeValue() + " ,";
                NodeList weatherList = dataElement.getElementsByTagName("wfKor");
                Element weatherElement = (Element) weatherList.item(0);
                textList = weatherElement.getChildNodes();
                s += "날씨 = " + ((Node) textList.item(0)).getNodeValue() + "\n";
            }
            textview.setText(s);
        }
    }
}