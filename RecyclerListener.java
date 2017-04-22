package lokesh.com.assignment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by lokeshreddy on 22-04-2017.
 */

public class RecyclerListener implements RecyclerView.OnItemTouchListener {
    View view;
    private AdapterView.OnItemClickListener myListener;
    public interface OnItemClickListener{
        public void onItemClick(View view,int position);

    }
    GestureDetector mygesturedetector;
    public RecyclerListener(Context context,OnItemClickListener listener){
        myListener= (AdapterView.OnItemClickListener) listener;
        mygesturedetector  =new GestureDetector(context, GestureDetector.SimpleOnGestureListener))

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView=view.findChildViewUnder(e.getX(),e.getY());
        if (childView!=null&& myListener!=null&&mygesturedetector.onTouchEvent(e)){
            myListener.onItemClick(childView,view.getChildLayoutPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {


    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
