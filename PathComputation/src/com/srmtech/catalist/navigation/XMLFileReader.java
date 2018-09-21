package com.srmtech.catalist.navigation;

import com.srmtech.catalist.navigation.WallCoordinates;

import com.srmtech.catalist.navigation.PathCoordinates;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLFileReader {

	
/*static final String WALL_ID =  "id";
static final String WALL_NAME = "name";
*/
//static final String WALL_SHAPE = "shape";
/*static final String SPATH_ID = "id";
static final String SPATH_NAME = "name";
*/
//static final String SPATH_SHAPE = "shape";
/*static final String MAP_ID = "id";
static final String MAP_NAME = "name";
*/
//static final String MAP_SHAPE = "shape";
/*static final String STAIRCASE_ID = "id";
static final String STAIRCASE_NAME = "name";
*/
//static final String STAIRCASE_SHAPE = "shape";
	
static final String WALL = "Wall";
static final String WALL_COORDS = "coords";
static final String PATH = "Spath";
static final String SPATH_COORDS = "edges";
//static final String MAP = "mapSize";
//static final String MAP_COORDS = "coords";
static final String SNODE = "Snode";
static final String SNODE_VRIMAGE = "vrimage";
static final String  SHOP_COORDS = "coords";
static final String SNODE_COORDS = "edges";
static final String ESCALATOR_NODE = "EscalatorNode";
static final String ESCALATOR_NODE_COORDS = "edges";

@SuppressWarnings({ "unchecked", "null" })
public List<WallCoordinates> readWalls(String configFile) {
	List<WallCoordinates> items = new ArrayList<WallCoordinates>();
	try {
		// First create a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		// Setup a new eventReader
		InputStream in = new FileInputStream(configFile);
		
		XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
		// Read the XML document
		WallCoordinates item = null;
		
		while(eventReader.hasNext()){
			XMLEvent event = eventReader.nextEvent();
			
			
			if(event.isStartElement())
			{
				StartElement start = event.asStartElement();
				if(start.getName().getLocalPart() == (WALL))
				{
					item = new WallCoordinates();
					Iterator<Attribute> attrib = start.getAttributes();
					while(attrib.hasNext())
					{
						Attribute attribute = attrib.next();
						/*if(attribute.getName().toString().equals(WALL_NAME))
						{
							item.setWallName(attribute.getValue());
						}
						*/
						if(attribute.getName().toString().equals(WALL_COORDS))
						{
							item.setCoords(attribute.getValue());
						}
						
						/*if(attribute.getName().toString().equals(WALL_ID))
						{
							item.setId(attribute.getValue());
						}
						
						if(attribute.getName().toString().equals(WALL_SHAPE))
						{
							item.setShape(attribute.getValue());
						}*/
						
					}
					
					
				}
			}
			// If we reach the end of an item element we add it to the list
			if (event.isEndElement()) {
				EndElement endElement = event.asEndElement();
				if (endElement.getName().getLocalPart() == (WALL)) {
					items.add(item);
				}
			}
		}
		
		
	}
catch (FileNotFoundException e) {
	e.printStackTrace();
} catch (XMLStreamException e) {
	e.printStackTrace();
}
return items;
}
	
@SuppressWarnings({ "unchecked", "null" })
public List<PathCoordinates> readPath(String config) {
	List<PathCoordinates> data = new ArrayList<PathCoordinates>();
	try {
		// First create a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		// Setup a new eventReader
		InputStream input = new FileInputStream(config);
		XMLEventReader Reader = inputFactory.createXMLEventReader(input);
		// Read the XML document
		PathCoordinates spath = null;
		
		while(Reader.hasNext()){
			XMLEvent event = Reader.nextEvent();
			
			
			if(event.isStartElement())
			{
				StartElement start = event.asStartElement();
				if(start.getName().getLocalPart() == (PATH))
				{
					spath = new PathCoordinates();
					Iterator<Attribute> attrib = start.getAttributes();
					while(attrib.hasNext())
					{
						Attribute attribute = attrib.next();
						/*if(attribute.getName().toString().equals(SPATH_NAME))
						{
							spath.setPathName(attribute.getValue());
						}*/
						
						if(attribute.getName().toString().equals(SPATH_COORDS))
						{
							spath.setEdges(attribute.getValue());
						}
						
						/*if(attribute.getName().toString().equals(SPATH_ID))
						{
							spath.setId(attribute.getValue());
						}
						
						if(attribute.getName().toString().equals(SPATH_SHAPE))
						{
							spath.setShape(attribute.getValue());
						}*/
						
					}
					
					
				}
			}
			// If we reach the end of an item element we add it to the list
			if (event.isEndElement()) {
				EndElement endElement = event.asEndElement();
				if (endElement.getName().getLocalPart() == (PATH)) {
					data.add(spath);
				}
			}
		}
		
		
	}
catch (FileNotFoundException e) {
	e.printStackTrace();
} catch (XMLStreamException e) {
	e.printStackTrace();
}
return data;
}
	

@SuppressWarnings({ "unchecked", "null" })
public List<EscalatorNode> readMapSize(String extract) {
	List<EscalatorNode> map = new ArrayList<EscalatorNode>();
	try {
		// First create a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		// Setup a new eventReader
		InputStream input = new FileInputStream(extract);
		XMLEventReader Reader = inputFactory.createXMLEventReader(input);
		// Read the XML document
		EscalatorNode maps = null;
		
		while(Reader.hasNext()){
			XMLEvent event = Reader.nextEvent();
				
			if(event.isStartElement())
			{
				StartElement start = event.asStartElement();
				if(start.getName().getLocalPart() == (ESCALATOR_NODE))
				{
					maps = new EscalatorNode();
					Iterator<Attribute> attrib = start.getAttributes();
					while(attrib.hasNext())
					{
						Attribute attribute = attrib.next();
					
						
						if(attribute.getName().toString().equals(ESCALATOR_NODE_COORDS))
						{
							maps.setEdges(attribute.getValue());
						}
						
						
						
					}
					
					
				}
			}
			// If we reach the end of an item element we add it to the list
			if (event.isEndElement()) {
				EndElement endElement = event.asEndElement();
				if (endElement.getName().getLocalPart() == (ESCALATOR_NODE)) {
					map.add(maps);
				}
			}
		}
		
		
	}
catch (FileNotFoundException e) {
	e.printStackTrace();
} catch (XMLStreamException e) {
	e.printStackTrace();
}
return map;
}

public List<NodeCoordinates> readstairCase(String getdata) {
	List<NodeCoordinates> map = new ArrayList<NodeCoordinates>();
	try {
		// First create a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		// Setup a new eventReader
		InputStream input = new FileInputStream(getdata);
		XMLEventReader Reader = inputFactory.createXMLEventReader(input);
		// Read the XML document
		NodeCoordinates maps = null;
		
		while(Reader.hasNext()){
			XMLEvent event = Reader.nextEvent();
		  if(event.isStartElement())
			{
				StartElement start = event.asStartElement();
				if(start.getName().getLocalPart() == (SNODE))
				{
					maps = new NodeCoordinates();
					@SuppressWarnings("unchecked")
					Iterator<Attribute> attrib = start.getAttributes();
					while(attrib.hasNext())
					{
						Attribute attribute = attrib.next();
						
						if(attribute.getName().toString().equals(SHOP_COORDS))
						{
							maps.setCoords(attribute.getValue());
						}
						
						if(attribute.getName().toString().equals(SNODE_COORDS))
						{
							maps.setEdges(attribute.getValue());
						}
						
						if(attribute.getName().toString().equals(SNODE_VRIMAGE))
						{
							maps.setVrimage(attribute.getValue());
						}
						
						
						
						
					}
					
					
				}
			}
			// If we reach the end of an item element we add it to the list
			if (event.isEndElement()) {
				EndElement endElement = event.asEndElement();
				if (endElement.getName().getLocalPart() == (SNODE)) {
					map.add(maps);
				}
			}
		}
		
	}
catch (FileNotFoundException e) {
	e.printStackTrace();
} catch (XMLStreamException e) {
	e.printStackTrace();
}
return map;
}

}
