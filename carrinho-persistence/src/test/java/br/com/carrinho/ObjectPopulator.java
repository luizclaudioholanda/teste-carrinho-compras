package br.com.carrinho;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjectPopulator {

	public static Object populate(final Object instance)
            throws Exception {

        List<Field> fields = getAllFields(instance);
        
        for (Field eachField : fields) {
            
        	eachField.setAccessible(true);
        
            if (eachField.getType().equals(Integer.class)) {
                eachField.set(instance, getRandomInteger());
            } else if (eachField.getType().equals(Long.class)) {
                eachField.set(instance, getRandomLong());
            } else if (eachField.getType().equals(String.class)) {
                eachField.set(instance, getRandomString());
            } else if (eachField.getType().equals(Float.class)) {
                eachField.set(instance, getRandomFloat());
            } else if (eachField.getType().equals(Date.class)) {
                eachField.set(instance, getRandomDate());
            } 
        }
        
        return instance;

    }

    private static Integer getRandomInteger() {
        return new Integer((int) (Math.random() * 1000));
    }
    private static Long getRandomLong() {
        return new Long((int) (Math.random() * 1000));
    }

    private static Float getRandomFloat() {
        return new Float( Math.random() * 1000+Math.random());
    }

    private static Date getRandomDate() {
        return new Date(  new Date().getTime() - (int)(Math.random() * 1000*60*60*24*100) );
    }

    private static String getRandomString() {
        StringBuffer result = new StringBuffer();
        String[] letters = new String[] { "A", "B", "C", "D", "E", "F", "G" };
        int length = (int) (Math.random() * 15) + 5;
        for (int i = 0; i < length; i++) {
            int pos = (int) (Math.random() * letters.length);
            result.append(letters[pos]);
        }
        return result.toString();
    }

    private static List<Field> getAllFields(final Object instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        List<Field> result = new ArrayList<Field>();
        for (int i = 0; i < fields.length; i++) {
            if (!java.lang.reflect.Modifier.isFinal(fields[i].getModifiers())
                    && !java.lang.reflect.Modifier.isStatic(fields[i]
                    .getModifiers())) {
                result.add(fields[i]);
            }
        }
        return result;
    }
	
}
