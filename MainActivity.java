package lokesh.com.assignment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{
    protected GoogleApiClient mygoogleapiclient;
    private static final LatLngBounds mybounds=new LatLngBounds(
        new LatLng(-0,0), new LatLng(0,0));
    private EditText myATView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private AT_Adapter mATAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myATView=(EditText)findViewById(R.id.edittext);
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        mATAdapter=new AT_Adapter(this,R.layout.search_row,mygoogleapiclient,mybounds,null);
        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mATAdapter);
        myATView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!s.toString().equals("")&& mygoogleapiclient.isConnected()) {

                }
                else if (!mygoogleapiclient.isConnected()){
                    Toast.makeText(getApplicationContext(),"Google API Client is not Conneted",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRecyclerView.addOnItemTouchListener(
                new RecyclerListener(this,new RecyclerListener.OnItemClickListener(){
                    public void OnItemClick(View view,int position) {
                        final AT_Adapter.AT_Place_item=mATAdapter.getItemId(position);
                        final String placeID = String.valueOf(item.placeID);
                        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mygoogleapiclient, placeID);
                        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                            @Override
                            public void onResult(@NonNull PlaceBuffer places) {
                                if (places.getCount() == 1) {
                                    Toast.makeText(getApplicationContext(), String.valueOf(places.get(0).getLatLng()), Toast.LENGTH_SHORT).show();

                                } else {

                                }

                            }
                        });

                    }

    }));


    }
    protected synchronized void buildgoogleapiclient() {
        mygoogleapiclient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
    }


            @Override
            public void dump(String s, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strings) {

            }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
