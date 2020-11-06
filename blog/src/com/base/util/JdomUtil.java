package com.base.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.bean.XmlBean;
import com.base.filter.AccessFilter;

public class JdomUtil {
	
	private static Logger logger = Logger.getLogger(AccessFilter.class);
	String path;
	
	public JdomUtil(String path) {
		this.path=path;
	}
	
	public List<XmlBean> read(String tagName) {
		List<XmlBean> rtnList=new ArrayList<XmlBean>();
		try {
			File inputFile = new File(path);
	        DocumentBuilderFactory dbFactory 
	           = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			NodeList listenerList=doc.getElementsByTagName(tagName);
			for (int i=0;i<listenerList.getLength();i++){
				XmlBean xml=new XmlBean();
				  Node node=listenerList.item(i);
		            //再把节点中的所有属性拿出来
		            NamedNodeMap nodeMap= node.getAttributes();
		            HashMap parameterMap=new HashMap();
		            for(int j=0;j<nodeMap.getLength();j++){
		            	Node node1=nodeMap.item(j);
		            	parameterMap.put(node1.getNodeName(), node1.getNodeValue());
		            }
		            xml.setParameterMap(parameterMap);
		            xml.setContent(node.getTextContent());
		            rtnList.add(xml);
			}
		} catch (Exception ex) {
			logger.error("读取xml文件出错！"+ex);
		}
		return rtnList;
	}
}
