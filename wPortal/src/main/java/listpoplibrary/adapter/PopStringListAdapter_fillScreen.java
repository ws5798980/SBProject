package listpoplibrary.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.listpoplibrary.ListPopWindowManager;

import java.util.List;

/**
 * Created by radio on 2017/10/31.
 */

public class PopStringListAdapter_fillScreen extends BaseAdapter {
    private List<String> list;
    private Context context;
    public void setNotify(Context context){
        this.context=context;
        notifyDataSetChanged();
    }
    public PopStringListAdapter_fillScreen(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(com.android.listpoplibrary.R.layout.popwindow_listview_string_item_library, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvPopwindowListview=(TextView) view.findViewById(com.android.listpoplibrary.R.id.tv_popwindow_listview);
            viewHolder.tvPopwindowListview.setGravity(Gravity.CENTER);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvPopwindowListview.setText(list.get(i));
        if (ListPopWindowManager.getInstance().getTextColor()!=0){
            viewHolder.tvPopwindowListview.setTextColor(context.getResources().getColor(ListPopWindowManager.getInstance().getTextColor()));
        }
        return view;
    }

    static class ViewHolder {
        TextView tvPopwindowListview;

    }
}
