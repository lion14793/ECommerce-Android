package com.appdynamics.pmdemoapps.android.ECommerceAndroid.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.appdynamics.eumagent.runtime.InfoPoint;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.model.Book;
import com.appdynamics.pmdemoapps.android.ECommerceAndroid.model.Item;

public class ItemParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Unfortunately doesn't run as a java application. Need to figure out
		ItemParser parser = new ItemParser();
		try {
			List<Item> list = parser.parse("<product> <id>1</id> <title>A Clockwork Orange</title> " +
         		"<imagePath>images/A_Clockwork_Orange-Anthony_Burgess.jpg</imagePath> </product>");
			
			for (Item book : list){
				System.out.println((Book)book);
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@InfoPoint
	public List<Item> parse(String xml) throws XmlPullParserException, IOException{
         if(xml == null) return null;
         
		
		 XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
         List<Item> itemList = new ArrayList<Item>();

         parser.setInput( new StringReader(xml) );
         int eventType = parser.nextTag();
         
         while (eventType != XmlPullParser.END_DOCUMENT) {
        	 if(parser.getName().equalsIgnoreCase("items")){
        		//Ignore parent item tag for multiple items case
        	 }
        	 
        	 else if(eventType == XmlPullParser.START_TAG) {	
        		 if(parser.getName().equalsIgnoreCase("product")){
        			 Item item = parseProduct(parser);
        			 if (item!=null){
        				 itemList.add(item);
        			 }
        		 }
	           else {
	        	  skip(parser);
	           }
        	 }
          eventType = parser.next();
         }
         
         
         return itemList;
	}
	
	public Item parseProduct(XmlPullParser parser) throws XmlPullParserException, IOException{
		Item item = new Item();
		
		parser.require(XmlPullParser.START_TAG, null, "product");
		int eventType = parser.next();
		//If event type is END_TAG and the end end tag corresponds to product then stop
		while (!(eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("product"))){
			String name = parser.getName();
			if(name.equalsIgnoreCase("id")){
				item.setId(parseLong(parser, "id"));
			}
			else if(name.equalsIgnoreCase("title")){
				item.setTitle(parseString(parser, "title"));
			}
			else if(name.equalsIgnoreCase("imagePath")){
				item.setImagePath(parseString(parser, "imagePath"));
			} else if (name.equalsIgnoreCase("price")) {
				item.setPrice(parseDoubel(parser,"price"));
			}
			eventType = parser.next();
		}
		parser.require(XmlPullParser.END_TAG, null, "product");
		
		return item.getId()==null?null:item;//Accounting for improper data
		
	}
	
	private Long parseLong(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, tagName);
		String result = getText(parser);
		Long value = null;
		if(result!=null){
			value = Long.parseLong(result);
		}
		parser.require(XmlPullParser.END_TAG, null, tagName);
		return value;
	}
	
	private String parseString(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, tagName);
		String result = getText(parser);
		parser.require(XmlPullParser.END_TAG, null, tagName);
		return result;
	}
	
	private String getText(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = null;
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}
	
	private Double parseDoubel(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, tagName);
		String result = getText(parser);
		Double value = null;
		if(result!=null){
			value = Double.parseDouble(result);
		}
		parser.require(XmlPullParser.END_TAG, null, tagName);
		return value;
	}
	
	/**
	 * Avoid any tags we are not interested in
	 * @param parser
	 */
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException{
		String startTagName = parser.getName();
		String endTagName = "";
		while (!endTagName.equalsIgnoreCase(startTagName)){
			int eventType = parser.next();
			if (eventType==XmlPullParser.END_TAG){
				endTagName = parser.getName();
			}
			
			
		}
		
	}

}
