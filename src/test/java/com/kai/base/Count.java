package com.kai.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;


public class Count {

	public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("Generate JavaBean");
        Map properties = new HashMap();
        properties.put("id", Class.forName("java.lang.Integer"));
        properties.put("name", Class.forName("java.lang.String"));
        properties.put("address", Class.forName("java.lang.String"));
        Object stu = generateObject(properties);

        System.out.println("Set values");
        setValue(stu, "id", 123);
        setValue(stu, "name", "454");
        setValue(stu, "address", "789");

        System.out.println("Get values");
        System.out.println(">> " + getValue(stu, "id"));
        System.out.println(">> " + getValue(stu, "name"));
        System.out.println(">> " + getValue(stu, "address"));

        System.out.println("Show all methods");
        Method[] methods = stu.getClass().getDeclaredMethods();
        for(Method method : methods) {
            System.out.println(">> " + method.getName());
        }

        System.out.println("Show all properties");
        Field[] fields = stu.getClass().getDeclaredFields();
        for(Field field : fields) {
            System.out.println(">> " + field.getName());
        }
        System.out.println(stu.toString());
    }

	public static Object generateObject(Map properties) {
        BeanGenerator generator = new BeanGenerator();
        Set keySet = properties.keySet();
        for(Iterator i = keySet.iterator(); i.hasNext();) {
            String key = (String)i.next();
            generator.addProperty(key, (Class)properties.get(key));
        }
        return generator.create();
    }

    public static Object getValue(Object obj, String property) {
        BeanMap beanMap = BeanMap.create(obj);
        return beanMap.get(property);
    }

    public static void setValue(Object obj, String property, Object value) {
        BeanMap beanMap = BeanMap.create(obj);
        beanMap.put(property, value);
    }
}
