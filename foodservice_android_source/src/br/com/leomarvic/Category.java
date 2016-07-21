package br.com.leomarvic;


import java.util.*;

public class Category {
	
	public String m_name;
	public ArrayList<Item> m_itens;
	
	public Category(){
		m_itens = new ArrayList<Item>();
	}
	
	public Category(Category cat)
	{
		m_name = new String(cat.getName());
		m_itens = new ArrayList<Item>(cat.getItems());
	}
	
	public void addItem(Item item)
	{
		m_itens.add(item);		
	}
	
	public ArrayList<Item> getItems()
	{
		return m_itens;
	}
	
	public void setName(String name)
	{
		m_name = new String(name);
	}
	
	public String toString()
	{
		return m_name;
	}
	
	public String getName()
	{
		return m_name;
	}

}
