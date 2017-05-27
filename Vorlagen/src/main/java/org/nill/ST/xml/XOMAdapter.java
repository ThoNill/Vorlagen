package org.nill.ST.xml;

import java.util.logging.Level;
import java.util.logging.Logger;

import nu.xom.Element;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

public class XOMAdapter extends ObjectModelAdaptor {
    static Logger logger = Logger.getLogger(XOMAdapter.class.getSimpleName());
    protected String packageName;

    public XOMAdapter(String packageName) {
        super();
        this.packageName = packageName;
    }

    @Override
    public Object getProperty(Interpreter interpreter, ST self, Object o,
            Object property, String propertyName)
            throws STNoSuchPropertyException {
        Element elem = (Element) o;

        switch (propertyName) {
        case "Class":
            return o.getClass();
        case "Childs":
            return ((Element) o).getChildElements();
        case "Parent":
            return ((Element) o).getParent();
        case "Name":
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

    private WrapElement getWrap(Element elem) {
        WrapElement wrap = (WrapElement) createInstance(elem.getLocalName());
        if (wrap == null) {
            return null;
        }
        wrap.setElem(elem);
        return wrap;
    }

    protected Object createInstance(String name) {
        if (packageName != null) {
            String className = packageName + "." + name;
            try {
                if (logger.isLoggable(Level.INFO)) {
                    logger.info("Create object of class [" + className + "]");
                }
                Class cl = XOMAdapter.class.getClassLoader().loadClass(
                        className);
                Object obj = cl.newInstance();
                return obj;

            } catch (ClassNotFoundException e) {
                logger.severe("class [" + className + "] not found");
            } catch (InstantiationException e) {
                logger.severe("class [" + className
                        + "] can not be instantated");
            } catch (IllegalAccessException e) {
                logger.severe("class [" + className
                        + "] Illegal Access at instantiation");
            }
        }
        return null;
    }

}
