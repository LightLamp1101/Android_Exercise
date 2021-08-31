package kr.co.company.practice13;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kr.co.company.practice16.R;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> tempList = new ArrayList<String>();
    ArrayList<String> weatherList = new ArrayList<String>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetXMLTask task = new GetXMLTask();
        task.execute("https://www.kma.go.kr/wid/queryDFS.jsp?gridx=37.341&gridy=126.7347");

        ListView listView = findViewById(R.id.resultList);
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tempList.size()+1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.network_list_item, null);
            }

            TextView temp = view.findViewById(R.id.temp_data);
            TextView weather = view.findViewById(R.id.weather_data);
            if (i==0){
                temp.setText("온도");
                weather.setText("날씨");
            } else {
                temp.setText(tempList.get(i-1));
                weather.setText(weatherList.get(i-1));
            }

            return view;
        }
    }

    private class GetXMLTask extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... urls) {
            Document doc = null;
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
                Element weatherElmnt = (Element)nodeList.item(i);

                NodeList nameList = weatherElmnt.getElementsByTagName("temp");
                Element windElement = (Element) nameList.item(0);
                NodeList contentList = windElement.getChildNodes();
                tempList.add(contentList.item(0).getNodeValue());

                nameList = weatherElmnt.getElementsByTagName("wfKor");
                Element statusElement = (Element) nameList.item(0);
                contentList = statusElement.getChildNodes();
                weatherList.add(contentList.item(0).getNodeValue());
            }
            myAdapter.notifyDataSetChanged();
        }
    }
}
