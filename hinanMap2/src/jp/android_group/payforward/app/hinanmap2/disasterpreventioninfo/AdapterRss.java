package jp.android_group.payforward.app.hinanmap2.disasterpreventioninfo;

import java.util.List;

import jp.android_group.payforward.app.hinanmap2.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterRss extends ArrayAdapter<ItemRss> {
	private static final String TAG="AdapterRss";
	
	private static final String WARN ="発表されています";
	private LayoutInflater mInflater;

	public AdapterRss(Context context, List<ItemRss> objects) {
		super(context, 0, objects);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (convertView == null) {
			view = mInflater.inflate(R.layout.disaster_prevention_info_row, null);
		}
		//
		ItemRss item = this.getItem(position);
		if (item != null) {
			String s_desc  = item.getDescript().toString();
			String s_date  = item.getDate().toString();
			if(s_date.length() > 25){
				s_date = s_date.substring(0, 25);
			}

			TextView t_txt = (TextView) view.findViewById(R.id.txt_fm011_txt);
			
			int i_pos = s_desc.indexOf(WARN);
//Log.d(TAG, String.valueOf(i_pos) );
			String s_buff="";
			if( i_pos >=0 ){
	        	s_buff  = "<font color='#0000FF'><b>" +s_desc +"</B></font><br />";
	        	s_buff += "<font color='#C1C1C1'>"+ s_date+ "</font>";

			}else{
	        	s_buff  = "<font color='#000'><b>" +s_desc +"</B></font><br />";
	        	s_buff += "<font color='#C1C1C1'>"+ s_date+ "</font>";
	        }
			CharSequence source  = Html.fromHtml(s_buff );
			t_txt.setText(source);
		}
		return view;
	}
}