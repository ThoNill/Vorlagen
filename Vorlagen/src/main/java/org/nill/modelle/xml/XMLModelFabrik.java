package org.nill.modelle.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Vector;

import org.nill.vorlagen.ModellFabrik;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParentNode;

public class XMLModelFabrik implements ModellFabrik<Document, String> {

    @Override
    public Document erzeugeModell(String dateiName) {
        InputStream file;
        try {
            file = holeStream(dateiName);
            return ladeDasDokument(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected InputStream holeStream(String dateiName)
            throws FileNotFoundException {
        InputStream fileStream;
        File file = new File(dateiName);
        if (file.exists()) {
            fileStream = new FileInputStream(dateiName);
        } else {
            fileStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(dateiName);
        }
        return fileStream;
    }

    public static Element ladeDasRootElement(InputStream in) {
        try {
            return ladeDasDokument(in).getRootElement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static Document ladeDasDokument(InputStream in) {
        try {
            XMLElementFabrik factory = new XMLElementFabrik();
            factory.setDir("templates/");
            Builder builder = new Builder(false, factory);

            Document document = builder.build(in);
            Element root = document.getRootElement();
            erstzeIncludes(root);
            return document;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void ersetzeElementDurchTemplate(Element source,
            Element template, String sternValue) {
        ersetzePlacesDurchIntos(source, template);
        int count = source.getAttributeCount();
        for (int i = 0; i < count; i++) {
            Attribute a = source.getAttribute(i);
            ersetzeAlleAttribute(template, a.getLocalName(), a.getValue(),
                    sternValue);
        }
        ersetzeZielDurchDieKinderDerQuelle(source, template);
    }

    private static HashMap<String, Element> extractHashOfElementsWithName(
            Element source, String name) {
        HashMap<String, Element> childs = new HashMap<String, Element>();
        Nodes nodes = source.query("descendant::*");
        for (int i = 0; i < nodes.size(); i++) {
            Node c = nodes.get(i);
            if (c instanceof Element
                    && name.equals(((Element) c).getLocalName())) {
                Element e = (Element) c;
                childs.put(e.getAttributeValue("name"), e);
            }
        }
        return childs;
    }

    private static Vector<Element> extractVectorOfElementsWithName(
            Element source, String name) {
        Vector<Element> childs = new Vector<Element>();
        Elements elements = source.getChildElements();
        for (int i = 0; i < elements.size(); i++) {
            childs.add(elements.get(i));
        }
        return childs;
    }

    private static void ersetzePlacesDurchIntos(Element source, Element template) {
        Vector<Element> intos = extractVectorOfElementsWithName(source, "INTO");
        HashMap<String, Element> places = extractHashOfElementsWithName(
                template, "PLACE");
        for (Element into : intos) {
            Element target = places.get(into.getAttributeValue("name"));
            if (target != null) {
                ersetzeZielDurchDieKinderDerQuelle(target, into);
            } else {
                throw new RuntimeException(source.getLocalName()
                        + " hat keinen Platz " + into.getAttributeValue("name"));
            }
        }
    }

    private static void ersetzeAttribut(Attribute attrib, String oldValue,
            String newValue) {
        if (oldValue.equals(attrib.getValue())) {
            attrib.setValue(newValue);
        }
    }

    private static void ersetzeSternInAttribut(Attribute attrib, String value) {
        String oldValue = attrib.getValue();
        if (oldValue.indexOf('*') >= 0) {
            attrib.setValue(oldValue.replaceAll("\\*", value));
        }
    }

    private static void ersetzeAttributeEinesElements(Element elem,
            String oldValue, String newValue, String sternValue) {
        int count = elem.getAttributeCount();
        for (int i = 0; i < count; i++) {
            Attribute a = elem.getAttribute(i);
            ersetzeAttribut(a, oldValue, newValue);
            ersetzeSternInAttribut(a, sternValue);
        }
    }

    private static void ersetzeAlleAttribute(Element elem, String oldValue,
            String newValue, String sternValue) {
        ersetzeAttributeEinesElements(elem, oldValue, newValue, sternValue);
        Nodes nodes = elem.query("descendant::*");
        for (int i = 0; i < nodes.size(); i++) {
            Node c = nodes.get(i);
            if (c instanceof Element) {
                ersetzeAttributeEinesElements((Element) c, oldValue, newValue,
                        sternValue);
            }
        }
    }

    private static void erstzeIncludes(Element elem) {
        Vector<IncludeElement> includes = new Vector<IncludeElement>();
        Nodes nodes = elem.query("descendant::*");
        for (int i = 0; i < nodes.size(); i++) {
            Node c = nodes.get(i);
            if (c instanceof IncludeElement) {
                includes.add((IncludeElement) c);
            }
        }
        for (IncludeElement c : includes) {
            c.replace();
        }

    }

    private static void ersetzeZielDurchDieKinderDerQuelle(Element target,
            Element source) {
        ParentNode p = target.getParent();
        int index = p.indexOf(target);
        target.detach();

        Vector<Element> childs = new Vector<Element>();
        Elements elements = source.getChildElements();
        for (int i = 0; i < elements.size(); i++) {
            childs.add(elements.get(i));
        }

        for (Node c : childs) {
            c.detach();
            p.insertChild(c, index);
            index++;
        }
    }

}
