package seoulnightmarket.seoulnightmarket.fragment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import seoulnightmarket.seoulnightmarket.R;
import seoulnightmarket.seoulnightmarket.adapter.MenuAdapter;
import seoulnightmarket.seoulnightmarket.etc.HttpTask;
import seoulnightmarket.seoulnightmarket.etc.Singleton;

public class FragmentMenu extends Fragment {
    private String uri;
    ListView listView;
    MenuAdapter adapter;

    public FragmentMenu() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    { // onCreate 후에 화면을 구성할때 호출
        View view = inflater.inflate(R.layout.activity_fragment_menu, null);
        listView = view.findViewById(R.id.listView);
        ImageView imageView = Singleton.getInstance().getStoreImageView();
        imageView.setImageBitmap(HttpTask.getInstance().translateBitmap(Singleton.getInstance().getNowStoreImage()));

        TextView textView = Singleton.getInstance().getStoreTextView();
        textView.setText(Singleton.getInstance().getNowStore());

        uri = Uri.parse("http://ec2-13-59-247-200.us-east-2.compute.amazonaws.com:3000/food")
                .buildUpon()
                .appendQueryParameter("store", HttpTask.getInstance().getURLEncode(Singleton.getInstance().getNowStore()))
                .build().toString();

        Log.e("URL", uri);
        HttpAsyncTask httpAsyncTask = new HttpAsyncTask("음식");
        httpAsyncTask.execute(uri);

        uri = Uri.parse("http://ec2-13-59-247-200.us-east-2.compute.amazonaws.com:3000/ticket")
                .buildUpon()
                .appendQueryParameter("store", HttpTask.getInstance().getURLEncode(Singleton.getInstance().getNowStore()))
                .build().toString();

        TicketAsyncTask ticketAsyncTask = new TicketAsyncTask("번호표 보기");
        ticketAsyncTask.execute(uri);

        return view;
    }

    public class HttpAsyncTask extends AsyncTask<String, Void, String> {
        String type;

        HttpAsyncTask(String type) {
            this.type = type;
        }

        @Override
        protected String doInBackground(String... urls) {
            //urls[0] 은 URL 주소
            return HttpTask.getInstance().GET(urls[0], type);
        }
        // onPostExecute displays the results of the AsyncTask.

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            adapter = new MenuAdapter();

            for (int i = 0; i < Singleton.getInstance().getProductImageList().size(); i++)
            {
                adapter.addItem(HttpTask.getInstance().translateBitmap(Singleton.getInstance().getProductImageList().get(i)), Singleton.getInstance().getProductNameList().get(i), Singleton.getInstance().getProductPriceList().get(i));
            }

            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            listView.invalidateViews();
        }
    }

    public class TicketAsyncTask extends AsyncTask<String, Void, String>
    {
        String type;

        TicketAsyncTask(String type) {
            this.type = type;
        }

        @Override
        protected String doInBackground(String... urls) {
            //urls[0] 은 URL 주소
            return HttpTask.getInstance().GET(urls[0], type);
        }
        // onPostExecute displays the results of the AsyncTask.

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            TextView textView = Singleton.getInstance().getWaitTextView();
            textView.setText(Singleton.getInstance().getWaitCount()+"");
        }
    }
}