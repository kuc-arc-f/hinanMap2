package jp.android_group.payforward.app.hinanmap2.disasterpreventioninfo;

import jp.android_group.payforward.app.hinanmap2.R;
import android.os.Bundle;

import java.io.StringReader;
import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;

import android.content.Intent;
import android.os.AsyncTask;

import android.util.Log;
import android.util.Xml;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.ListView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


import android.net.Uri;
import jp.android_group.payforward.app.hinanmap2.util.*;

/**
 * 防災情報アクティビティー
 * 
 * @author tonosaki
 * @change nakashima
 *
 */
public class DisasterPreventionInfoActivity extends ListActivity {
	private static final String TAG="DisasterPreventionInfoActivity";
    //
	private static final int MENU1_ID = Menu.FIRST;
	//
    private ProgressDialog      m_Progress;
    private ArrayList<ItemRss>     mItems;
    private AdapterRss             mAdapter;
    //
	private final String STR_XML_ELEM_ITEM    ="item";
	private final String STR_RSS_KEY_TITLE    = "title";
	private final String STR_RSS_KEY_LINK     = "link";
	private final String STR_RSS_KEY_DESC     = "description";
	private final String STR_RSS_KEY_DT       = "pubDate";
    //
    private static final String m_URL_WARN  = "http://tenki.jp/component/static_api/rss/warn/top.xml";
    private static final String m_URL_EQ    = "http://tenki.jp/component/static_api/rss/earthquake/recent_entries.xml";
    //
    private int                m_Mode         =1;  //1=warn,2=eq
    private static final int m_NUM_WARN     =1;
    private static final int m_NUM_EQ       =2;
	//
	//fw
	private jp.android_group.payforward.app.hinanmap2.util.HttpUtil m_Http  = new HttpUtil();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            setTitle(getString(R.string.disaster_prevention_info_title));
            setContentView(R.layout.disaster_prevention_info);
        	m_Mode = m_NUM_WARN;
            
            InitTask tsk = new InitTask();
            tsk.execute( );
       }catch(Exception e){
    	    e.printStackTrace();
        }

    }
    //
    public void onClick_warn(View v){
    	try
    	{
Log.d(TAG, "onClick_warn");
    		if(m_Mode != m_NUM_WARN){
    			m_Mode = m_NUM_WARN;
                InitTask tsk = new InitTask();
                tsk.execute( );
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    //
    public void onClick_eq(View v){
    	try
    	{
Log.d(TAG, "onClick_warn");
			if(m_Mode != m_NUM_EQ){
				m_Mode = m_NUM_EQ;
			    InitTask tsk = new InitTask();
			    tsk.execute( );
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }    
    @Override
    protected void onListItemClick(
        ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try
        {
        	ItemRss    item = mItems.get(position);
        	String s_url = item.getLink().toString();
   			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( s_url ));
			startActivity(intent);        	
        }catch(Exception e){
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	boolean result = super.onCreateOptionsMenu(menu);

    	menu.add(0, MENU1_ID, Menu.NONE, "Reload").setIcon(android.R.drawable.ic_menu_rotate);
    	return result;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	try
    	{
        	switch( item.getItemId() ){
        	case MENU1_ID :
                InitTask tsk = new InitTask();
                tsk.execute( );
        		return true;
        	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return super.onOptionsItemSelected(item);
    }
    //
    public ArrayList<ItemRss> get_Item(String s_buff) throws Exception
    {
    	ArrayList<ItemRss> ret = new ArrayList<ItemRss>();
    	try{
    		String s_name=null;
    		
    		XmlPullParser parser = Xml.newPullParser(); 
    		try{
                parser.setInput(new StringReader(new String(s_buff )));  
    		}catch (XmlPullParserException e){
    			throw e;
    		}catch(Exception e){
    			throw e;
    		}
			ItemRss item  = new ItemRss();
    		for(int e = parser.getEventType(); e != XmlPullParser.END_DOCUMENT; e = parser.next()) {
    			if(e == XmlPullParser.START_DOCUMENT) {
    			}else if( e == XmlPullParser.TEXT ) {
    			}else if(e == XmlPullParser.START_TAG) {
    				s_name =parser.getName();
    				s_name = s_name.trim();
    				String  s_title="";
					
    				if( s_name.equals( STR_XML_ELEM_ITEM )==true){
        				item = new ItemRss();
    				}else if( s_name.equals( STR_RSS_KEY_TITLE )==true){
    					s_title= parser.nextText();
    					s_title = s_title.replaceAll("'", " " );
    					item.setTitle( s_title);
    				}else if( s_name.equals( STR_RSS_KEY_LINK )==true){
    					item.setLink( parser.nextText() );
    				}else if( s_name.equals( STR_RSS_KEY_DT )==true){
    					item.setDate(parser.nextText() );
    				}else if( s_name.equals( STR_RSS_KEY_DESC )==true){
    					item.setDescript( parser.nextText());
    				}
        		}else if(e == XmlPullParser.END_TAG) {
    				s_name =parser.getName();
    				if( s_name.equals( STR_XML_ELEM_ITEM )==true){
    					if(item !=null){
    						ret.add(item);
    					}
    				}
        		}
        		if ( e == XmlPullParser.END_DOCUMENT ) {
        		}
    		}
    	}catch( XmlPullParserException e){
    		throw e;
    	}catch( Exception e){
    		throw e;
    	}
		return ret;
    }
    //
    private ArrayList<ItemRss> get_Rss() throws Exception{
    	ArrayList<ItemRss> ret = new ArrayList<ItemRss>();
    	try
    	{
    		String s_url="";
    		if(m_Mode== m_NUM_WARN ){
    			s_url = m_URL_WARN;
    		}else{
    			s_url = m_URL_EQ;
    		}
    		String s= m_Http.doGet( s_url );
    		ret = get_Item(s);
    	}catch(Exception e){
    		throw e;
    	}
    	return ret;
    }
    //
	private class InitTask extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			m_Progress = new ProgressDialog(DisasterPreventionInfoActivity.this);
			m_Progress.setMessage("Now Loading...");
			m_Progress.show();
		}
		@Override
		protected Void doInBackground(String... params) {
			try {
				mItems =  get_Rss();
				mAdapter = new AdapterRss( DisasterPreventionInfoActivity.this, mItems);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			try
			{
				m_Progress.dismiss();
				setListAdapter(mAdapter);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
    
}
