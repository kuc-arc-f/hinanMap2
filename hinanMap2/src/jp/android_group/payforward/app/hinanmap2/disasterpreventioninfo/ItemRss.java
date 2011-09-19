package jp.android_group.payforward.app.hinanmap2.disasterpreventioninfo;

import android.graphics.Bitmap;

public class ItemRss {
	private CharSequence m_ID;
	private CharSequence m_Text;
	private CharSequence m_Title;
	private CharSequence m_Link;
	private CharSequence m_Descript;
	private CharSequence m_Date;

	public ItemRss() {
		m_ID       = "";
		m_Text     = "";
		m_Title    = "";
		m_Link     = "";
		m_Descript  = "";
		m_Date      = "";
	}
	public CharSequence getId() { return m_ID; }
	public void setId(CharSequence id) { m_ID = id; }

	public CharSequence getText() { return m_Text; }
	public void setText(CharSequence src) { m_Text = src; }
	
	public CharSequence getTitle() { return m_Title; }
	public void setTitle(CharSequence src) { m_Title = src; }

	public CharSequence getLink() { return m_Link; }
	public void setLink(CharSequence src) { m_Link = src; }

	public CharSequence getDescript() { return m_Descript; }
	public void setDescript(CharSequence src) { m_Descript = src; }

	public CharSequence getDate() { return m_Date; }
	public void setDate(CharSequence src) { m_Date = src; }
}