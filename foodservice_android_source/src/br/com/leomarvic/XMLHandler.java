package br.com.leomarvic;


import java.io.* ;
import java.util.* ;

import javax.xml.parsers.* ;

import org.xml.sax.* ;
import org.w3c.dom.* ;

public class XMLHandler {
	//Class that solves any needed XML processing, following a standard formatting
	
	String m_XMLPath;
	Document m_XMLDocument;
	
	public XMLHandler(String path)
	{
		//Initializes the class with a path to a XML file, and creates a DOM object from it
		setXMLPath(path);
	}
	
	private void createDOMFromXML(String path)
	{
		//Creates new DOM object from the XML file
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    
	try {
	    DocumentBuilder builder = builderFactory.newDocumentBuilder();
	    m_XMLDocument = builder.parse( new FileInputStream(path));
		} 
	
	catch (SAXException e) {e.printStackTrace();}
	catch (IOException e) {e.printStackTrace();}
	catch (ParserConfigurationException e) {e.printStackTrace();}
	}
	
	public void setXMLPath(String path)
	{
		//Sets the path of the XML file
		m_XMLPath = path;
		
		//Creates new DOM object from new XML file
		createDOMFromXML(path);
	}
	
	public ArrayList<String> getRestaurantNames()
	{
		//Get NodeList of tags Restaurant
	    NodeList restaurantNodes = m_XMLDocument.getElementsByTagName("Restaurant");
	    
		//Prepare ArrayList with the number of elements
	    int size = restaurantNodes.getLength();
	    ArrayList<String> nameList = new ArrayList<String>();

		
	    for(int i=0; i<size; i++){
	      Node restaurant = restaurantNodes.item(i);
	      Node innerRestNode = restaurant.getFirstChild();
    
	      //Traverse the Restaurant node to find for name
	      while(innerRestNode != null)
	      {
	    	  //Search for tag 'name'
	    	  if(innerRestNode.getNodeName().equals("Name"))
	    	  {
	    		  //If name, add to the list
	    		  nameList.add(new String(innerRestNode.getFirstChild().getNodeValue())); 
	    		  
	    		  //Found name, go for next restaurant
	    		  innerRestNode = innerRestNode.getNextSibling();
	    	  }
	    	  else
	    	  {
	    		  //If child node not 'name', then get the next subnode
	    		  innerRestNode = innerRestNode.getNextSibling();
	    	  }
	      }
	    }
	    
	    //Return array of string
	    return nameList;

	    }

	private Node getRestaurantSubnode(String rest_name, String rest_subnode)
	{
		//Retrieve a list with all Restaurant sub elements
		NodeList nodeList = m_XMLDocument.getElementsByTagName("Restaurant");
		Node attr = null;
		
		for(int i=0;i<nodeList.getLength();i++)
		{
			Node restaurantNode = nodeList.item(i);
			
			//Get first child to iterate through document
			Node innerNode = restaurantNode.getFirstChild();
			Node innerNode2;
			
			//Iterate through every child
			while(innerNode != null)
			{
				if(innerNode.getNodeName().equals("Name"))
				{
					//System.out.println("AQUIII 1");
					
					if(innerNode.getFirstChild().getNodeValue().equals(rest_name))
					{
						//System.out.println("AQUIII 2");
						//Now that we've found the right node, lets find the attribute
						innerNode2 = restaurantNode.getFirstChild();
						
						while(innerNode2 != null)
						{
							if(innerNode2.getNodeName().equals(rest_subnode))
							{
								//Return Logo node
								attr = innerNode2;
								return attr;
							}
							//Go to next node child of restaurant
							innerNode2 = innerNode2.getNextSibling();
						}
					}
					else
					{
						//Not the wanted restaurant, break the loop for next one
						break;
					}
				}
				
				//Go to next child to look for Name or Attribute
				innerNode = innerNode.getNextSibling();
	
			}	
		}
		
		//If not found, return node with null
		return attr;
	}
	
	public String getRestaurantLogo(String rest_name)
	{
		//Get attribute Logo
		Node logo = getRestaurantSubnode(rest_name, "Logo");
		
		if(logo == null) return null;
		
		return logo.getFirstChild().getNodeValue();
	}

	public String getRestaurantAddr(String rest_name)
	{
		//Get attribute Address
		Node addr = getRestaurantSubnode(rest_name, "Address");
		
		if(addr == null) return null;
		
		return addr.getFirstChild().getNodeValue();
	}
	
	public String getRestaurantDesc(String rest_name)
	{
		//Get attribute Description
		Node desc = getRestaurantSubnode(rest_name, "Description");
		
		if(desc == null) return null;
		
		return desc.getFirstChild().getNodeValue();
	}
	
	public int getNumberRestaurants()
	{
		NodeList restList = m_XMLDocument.getElementsByTagName("Restaurant");
		return restList.getLength();
	}

	public ArrayList<Category> getRestCategories(String rest_name)
	{
		//Retrieve Categories Node, containing all categories inside Restaurant Node
		Node categories = getRestaurantSubnode(rest_name,"Categories");
		
		//Prepare list
		ArrayList<Category> categoryList = new ArrayList<Category>();
		
		//If restaurant has no categories
		if(categories == null) return categoryList;
		
		//Prepare NodeList to traverse child nodes
		Node categoryNode = categories.getFirstChild();
		
		while(categoryNode != null)
		{
			if(categoryNode.getNodeName().equals("Category"))
			{
				//Prepare spare category
				Category cat = new Category();
				
				//For each category node, search its subnodes for info: name, etc
				Node nodesInCat = categoryNode.getFirstChild();
				
				while(nodesInCat != null)
				{
					if(nodesInCat.getNodeName().equals("Name"))
					{
						//Set category name
						cat.setName(nodesInCat.getFirstChild().getNodeValue());
					}
					else if(nodesInCat.getNodeName().equals("Itens"))
					{
						//Get list of itens
						Node item = nodesInCat.getFirstChild();
						
						while(item != null)
						{
							if(item.getNodeName().equals("Item"))
							{
								//Prepare spare item
								Item it = new Item();
								
								//Get each subnode of item for info
								Node nodesInItem = item.getFirstChild();
								
								//Iterate through info of item, like name, etc
								while(nodesInItem != null)
								{
									//Get item information and put in the spare objects
									if(nodesInItem.getNodeName().equals("Name"))
									{
										//Add name to result item
										it.setName(nodesInItem.getFirstChild().getNodeValue());
									}
									else if(nodesInItem.getNodeName().equals("Description"))
									{
										//Add description to result item
										it.setDesc(nodesInItem.getFirstChild().getNodeValue());
									}
									else if(nodesInItem.getNodeName().equals("Price"))
									{
										//Add price to result item
										it.setPrice(nodesInItem.getFirstChild().getNodeValue());
									}
									else if(nodesInItem.getNodeName().equals("Image"))
									{
										//Prepare spare image
										Image img = new Image();
										
										//Get info on image, like ID, etc
										Node nodesInImage = nodesInItem.getFirstChild();
										
										while(nodesInImage != null)
										{
											if(nodesInImage.getNodeName().equals("Description"))
											{
												//Add description to resulting image
												img.setDesc(nodesInImage.getFirstChild().getNodeValue());
											}
											else if(nodesInImage.getNodeName().equals("ID"))
											{
												//Add ID to resulting image
												img.setID(nodesInImage.getFirstChild().getNodeValue());
											}
											
											nodesInImage = nodesInImage.getNextSibling();
										}
										//Set item image as img
										it.setImage(img);

									}
									
									//Get next node inside Item
									nodesInItem = nodesInItem.getNextSibling();
								}
								//Add the item to Category
								cat.addItem(it);
							}

							//Get next Item
							item = item.getNextSibling();
						}
					}
					//Get next node in category
					nodesInCat = nodesInCat.getNextSibling();
				}
				
				//Add category to the list of categories
				categoryList.add(cat);
				
			}
			//Get next Category node inside categories
			categoryNode = categoryNode.getNextSibling();
		}
		
		return categoryList;
	}

	
	public ArrayList<Image> getRestImages(String rest_name)
	{
		//Retrieve Images Node, containing all images inside Restaurant Node
		Node imagesNode = getRestaurantSubnode(rest_name,"Images");
		
		//Prepare list
		ArrayList<Image> imageList = new ArrayList<Image>();		
		//Prepare if restaurant has no images
		if(imagesNode == null) return imageList;
		
		//Prepare Node to traverse image nodes
		Node image = imagesNode.getFirstChild();
		
		while(image != null)
		{ 			
			//Test if node is image
			if(image.getNodeName().equals("Image"))
			{
				Image img = new Image();
				
				//Prepare Node to traverse image node attributes
				Node imageAttr = image.getFirstChild();	
				
				//Run across all images
				while(imageAttr != null)
				{
					if(imageAttr.getNodeName().equals("Description"))
					{
						img.setDesc(imageAttr.getFirstChild().getNodeValue());
					}
					else if(imageAttr.getNodeName().equals("ID"))
					{
						img.setID(imageAttr.getFirstChild().getNodeValue());
					}
					//Go to next subnode
					imageAttr = imageAttr.getNextSibling();
				}
				
				//Add img to the image list
				imageList.add(img);
			}
				//Go to other image
				image = image.getNextSibling();
		}
		
		//Get item images
		ArrayList<Category> rest_categories = getRestCategories(rest_name);
		for(int i=0;i<rest_categories.size();i++)
		{
			Category current_cat = rest_categories.get(i);
			ArrayList<Item> item_list = current_cat.getItems();
			
			for(int j=0;j<item_list.size();j++)
				{
					Image img = new Image();
					img.setID(item_list.get(j).getImage().getID());
					imageList.add(img);
				}
		}
		
		return imageList;
	}

}


