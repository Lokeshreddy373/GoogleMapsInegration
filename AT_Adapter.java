package lokesh.com.assignment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import static android.R.attr.filter;
import static android.R.attr.resource;

/**
 * Created by lokeshreddy on 22-04-2017.
 */


public class AT_Adapter extends RecyclerView.Adapter<AT_Adapter.PredictionHolder> implements Filterable {

    private ArrayList<AT_Place>myResultList;
    private GoogleApiClient mgoogleapiclient;
    private LatLngBounds myBounds;
    String AT_Place;
    private AutocompleteFilter myACFilter;
    private Context myContext;
    private int layout;
    public AT_Adapter(MainActivity mainActivity, int search_row, GoogleApiClient mygoogleapiclient, LatLngBounds mybounds, Object o) {
        myContext = context;
        layout = resource;
        mgoogleapiclient = GoogleApiClient;
        mybounds = bounds;
        myACFilter = filter;
    }
    protected void setMyBounds(LatLngBounds){myBounds=bounds;}

    @Override
    public Filter getFilter() {
        android.widget.Filter.filter=new android.widget.Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results=new FilterResults();
                if(constraint!=null){
                    myResultList=getAutoComplete(constraint);
                    if(myResultList!=null){
                        results.values=myResultList;
                        results.count=myResultList.size();
                    }

                }
                return results;
            }

            private ArrayList<PredictionHolder.AT_Place> getAutoComplete(CharSequence constraint) {
                if(myApiClient.isConnected()){
                    PendingResult<AutocompletePredictionBuffer> results= Places.GeoDataApi.getAutoCompletePredictions(myApiClient,constraint.toString(),myBounds,myACFilter);
                AutocompletePredictionBuffer     autocompletepredictions=results.await(60, TimeUnit.SECONDS);
                    final Status status=autocompletepredictions.getStatus();
                    if(!status.isSucess()){
                        Toast.makeText(myContext,"ERROR CONTACTING API"+status.toSring(),Toast.LENGTH_LONG).show();
                        (Log.e("", "Error getting autocomplete prediction api call:" + status.toString());
                        autocompletepredictions.release();
                        return null;
                    }
                    Log.i(""+"Query completed.Received"+autocompletepredictions.getCount()+"predictions");
                    Iterator<AutocompletePrediction> iterator=autocompletepredictions.iterator();
                    ArrayList resultList=new ArrayList<>(autocompletepredictions.getCount());
                    while (iterator.hasNext()) {
                        AutocompletePrediction prediction = iterator.next();
                        resultList.add(new AT_Place(prediction.getPlaceId(), prediction.getDescription()));
                    }
                    autocompletepredictions.release();
                    return resultList;

                    }
                Log.e("","Google API client is  not connected for autocomplete query");
                return null;




                }
            };

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results!=null&& results.count>0) {
                    notifyDataSetChanged();

                }else{

            }

        return filter;
    };

    @Override
    public AT_Adapter.PredictionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mPredictionHolder.myPrediction.setText(myResultList.get(i).description);

    }

    @Override
    public int getItemCount() {
        if(myResultList!=null)
            return myResultList.size();
        else
        return 0;

    }
    public AT_Place getItem(int position){return myResultList.get(position);}

    @Override
    public PredictionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PredictionHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PredictionHolder extends RecyclerView.viewHolder{
        private TextView myPrediction;
        private RelativeLayout myRow;
        public PredictionHolder(view itemView){
            super(itemView);
            myPrediction=(TextView)itemView.findViewById(R.id.address);
            myRow=(RelativeLayout)itemView.findViewById(R.id.autocomplete_row);
        }
        public class AT_Place{
            public CharSequence placeId;

            public CharSequence description;
            AT_Place(CharSequence placeId,CharSequence description){
                this.placeId=placeId;
                this.description=description;
            }

            @Override
            public String toString() {
                return description.toString();
            }
        }

    }
}
