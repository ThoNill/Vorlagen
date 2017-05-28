package org.nill.ST.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import nu.xom.Element;
import nu.xom.Elements;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

public class XOMAdapter extends ObjectModelAdaptor {
    static Logger logger = Logger.getLogger(XOMAdapter.class.getSimpleName());
    protected String packageName;
    protected String defaultClass;

    public XOMAdapter(String packageName,String defaultClass) {
        super();
        this.packageName = packageName;
        this.defaultClass = defaultClass;
    }

    @Override
    public Object getProperty(Interpreter interpreter, ST self, Object o,
            Object property, String propertyName)
            throws STNoSuchPropertyException {
        Element elem = (Element) o;

        if (propertyName.startsWith("listOf")) {
            return getElements(elem,propertyName.substring(6));
        }
        
        switch (propertyName) {
        case "Class":
            return o.getClass();
        case "Childs":
            return ((Element) o).getChildElements();
        case "Parent":
            return ((Element) o).getParent();
        case "ElementTypeName":
            return ((Element) o).getLocalName();
        default: {
            try {
                if (propertyName != null) {
                    WrapElement wrap = getWrap(elem);
                    if (wrap != null) {
                        return super.getProperty(interpreter, self, wrap,
                                property,

                                propertyName);
                    }
                }
            } catch (STNoSuchPropertyException ex) {
            }
        }
        }
        if (elem.getAttribute(propertyName) == null) {
            propertyName = propertyName.toLowerCase();
        }
        return elem.getAttributeValue(propertyName);
    }

    private Object getElements(Element elem, String name) {
        List<Element> liste = new ArrayList<>();
        getElements(liste,elem,name);
        return liste;
    }     
        
   private void getElements(List<Element> liste,Element elem,String name) {
           
        Elements elements = elem.getChildElements();
        for(int i=0;i< elements.size();i++) {
            Element child = elements.get(i);
            if (name.equals(child.getLocalName())) {
                liste.add(child);
            } else {
                getElements(liste,child,name);
            }
        }
    }

    private WrapElement getWrap(Element elem) {
        WrapElement wrap = (WrapElement) createInstance(elem.getLocalName(),defaultClass == null);
        if (wrap == null) {
            wrap = (WrapElement) createInstance(defaultClass,true);
            if (wrap == null) {
                return null;
            }
        }
        wrap.setElem(elem);
        return wrap;
    }

    protected Object createInstance(String name,boolean pr�fen) {
        if (packageName != null) {
            String className = packageName + "." + name;
            try {
                if (pr�fen && logger.isLoggable(Level.INFO)) {
                    logger.info("Create object of class [" + className + "]");
                }
                Class cl = XOMAdapter.class.getClassLoader().loadClass(
                        className);
                Object obj = cl.newInstance();
                return obj;

            } catch (ClassNotFoundException e) {
                if (pr�fen) {
                    logger.severe("class [" + className + "] not found");
                }
            } catch (InstantiationException e) {
                if (pr�fen) {
                logger.severe("class [" + className
                        + "] can not be instantated");
                }
            } catch (IllegalAccessException e) {
                if (pr�fen) {
                logger.severe("class [" + className
                        + "] Illegal Access at instantiation");
                }
            }
        }
        return null;
    }

}
