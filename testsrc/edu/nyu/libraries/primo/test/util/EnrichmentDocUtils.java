/**
 * 
 */
package edu.nyu.libraries.primo.test.util;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.exlibris.primo.api.plugins.enrichment.IEnrichmentDocUtils;
import com.google.common.collect.Lists;

/**
 * @author Scot Dalton
 *
 */
public class EnrichmentDocUtils implements IEnrichmentDocUtils {

	/* (non-Javadoc)
	 * @see com.exlibris.primo.api.plugins.enrichment.IEnrichmentDocUtils#addTags(org.w3c.dom.Document, java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public void addTags(Document doc, String sectionName, String tagName, 
			String[] values) {
		NodeList sections = doc.getElementsByTagName(sectionName);
		for(int i = 0; i<sections.getLength(); i++)
			for(String value: values) {
				Element tag = doc.createElement(tagName);
				tag.setTextContent(value);
				sections.item(i).appendChild(tag);
			}
	}

	/* (non-Javadoc)
	 * @see com.exlibris.primo.api.plugins.enrichment.IEnrichmentDocUtils#getValuesBySectionAndTag(org.w3c.dom.Document, java.lang.String, java.lang.String)
	 */
	@Override
	public String[] getValuesBySectionAndTag(Document doc, String sectionName,
			String tagName) {
		List<String> values = Lists.newArrayList();
		NodeList sections = doc.getElementsByTagName(sectionName);
		for (int i = 0; i<sections.getLength(); i++) {
			NodeList tags = sections.item(i).getChildNodes();
			for (int j = 0; j<tags.getLength(); j++) {
				if (tags.item(j).getNodeName().equals(tagName))
					values.add(tags.item(j).getTextContent());
			}
		}
		return values.toArray(new String[0]);
	}
}