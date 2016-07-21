package br.com.leomarvic;


public class Item {
	
	public String m_name;
	public String m_description;
	public String m_price;
	public Image m_image;
	
	public Item() { /*Empty Constructor*/}
	
	public Item(Item it)
	{
		m_name = new String(it.getName());
		m_description = new String(it.getDesc());
		m_price = new String(it.getPrice());
		m_image = new Image(it.getImage());
	}
	
	public Item(String name, String price, String description)
	{
		m_name = new String(name);
		m_description = new String(description);
		m_price = new String(price);
	}
	
	public void setName(String name)
	{
		m_name = new String(name);
	}
	
	public void setDesc(String desc)
	{
		m_description = new String(desc);
	}
	
	public void setPrice(String price)
	{
		m_price = new String(price);
	}
	
	public void setImage(Image image)
	{
		m_image = new Image(image);
	}

	public String toString()
	{
		return m_name;
	}
	
	public String getName()
	{
		return m_name;
	}
	
	public String getDesc()
	{
		return m_description;
	}
	
	public String getPrice()
	{
		 return m_price;
	}
	
	public Image getImage()
	{
		return m_image;
	}
	
}
