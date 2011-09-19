package jp.android_group.payforward.app.hinanmap2.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	
	/**
	 * urlアクセスの結果を返す
	 * 
	 * @param strUrl URL
	 * @return 結果
	 * @throws IllegalStateException ネットワークエラー発生時
	 */
    public static String getResultFromURL(String strUrl) throws IllegalStateException {
        HttpURLConnection con = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(strUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            // 以下を実行しないと、連続でアクセスするときに
            // androidがHTTPアクセスしないときがある
            con.getResponseMessage();

            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            		
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new IllegalStateException();
		} finally {
            try {
                if (con != null) {
                    con.disconnect();
                    con = null;
                }
                if (br != null)
                	br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    //
    public String doGet( String url ) throws Exception
    {
        try
        {
            HttpGet method = new HttpGet( url );

            DefaultHttpClient client = new DefaultHttpClient();

            method.setHeader( "Connection", "Keep-Alive" );
            HttpResponse response = client.execute( method );
            int status = response.getStatusLine().getStatusCode();
            if ( status != HttpStatus.SC_OK )
                throw new Exception( "" );
            
            return EntityUtils.toString( response.getEntity(), "UTF-8" );
        }
        catch ( Exception e )
        {
        	throw e;
        }
    }

}