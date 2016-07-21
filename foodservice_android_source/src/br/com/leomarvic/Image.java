package br.com.leomarvic;


public class Image {
	
	private String m_ID;
	private String m_Description;
	
	public Image() { /*Empty Constructor*/};
	
	public Image(Image img)
	{
		m_ID = new String(img.getID());
		m_Description = new String(img.getDesc());
	}
	
	public Image(String ID, String Description)
	{
		m_ID = new String(ID);
		m_Description = new String(Description);
	}
	
	public String getID()
	{
		return m_ID;
	}

	public String getDesc()
	{
		return m_Description;
	}
	
	public void setID(String ID)
	{
		m_ID = new String(ID);
	}
	
	public void setDesc(String Desc)
	{
		m_Description = new String(Desc);
	}
}
