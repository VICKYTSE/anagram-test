package net.mooctest;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DictionaryTest {

    public static Field getFields(Class cuClass,String fieldName) throws NoSuchFieldException {
        Field[] fields=cuClass.getDeclaredFields();
        for(Field field:fields){
            if(field.getName().equals(fieldName)){
                field.setAccessible(true);
                return field;
            }
        }
        throw new NoSuchFieldException("No such Field:"+fieldName);
    }


    @org.junit.Test(timeout = 4000)
    public void test() throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        Dictionary dictionary = new Dictionary();
        Class clazz = Class.forName("net.mooctest.Dictionary");
        Field field = getFields(clazz,"sortedStringMap");
        field.set(dictionary,null);
        try {
            Method method = clazz.getDeclaredMethod("getDictionaryKeyList");
            dictionary.getDictionaryKeyList();
            fail("no Error");
        } catch (Error e){
            assertTrue(e instanceof AssertionError);
        }
        field.set(dictionary,new TreeMap<String, Set<String>>());
        dictionary.getDictionaryKeyList();

        String pString = this.getClass().getResource("/net/mooctest/demo.txt").getPath();
        //System.out.println(pString);
        //dictionary.loadDictionary(ClassLoader.GetResource.class.getClassLoader().getResource("net/mooctest/demo.txt").getPath());
        dictionary.loadDictionary(pString);
        dictionary.loadDictionaryWithSubsets(pString,"23",2);
        dictionary.loadDictionaryWithSubsets(pString,"23",4);
        dictionary.loadDictionaryWithSubsets(pString,"23acd3",4);
        dictionary.loadDictionaryWithSubsets(pString,"23acd3",24);
        dictionary.loadDictionaryWithSubsets(pString,"6",24);
        dictionary.loadDictionaryWithSubsets(pString,"ghf",24);
        dictionary.loadDictionaryWithSubsets(pString,"",0);
        dictionary.loadDictionaryWithSubsets(pString,null,0);


        java.util.List<String> list = dictionary.getDictionaryKeyList();
        boolean b3 = dictionary.isDictionaryLoaded();
        String ssString = dictionary.toString();
        Set<String>ss = dictionary.findSingleWordAnagrams("233");

        boolean b1 = dictionary.addWord("122");
        boolean b2 = dictionary.addWord("");
        boolean b4 = dictionary.addWord("233");


        String pString2 = this.getClass().getResource("/net/mooctest/demo1.txt").getPath();
        dictionary.loadDictionary(pString2);
        dictionary.loadDictionaryWithSubsets(pString2,"23",2);
        dictionary.loadDictionaryWithSubsets(pString2,"23",4);
        dictionary.loadDictionaryWithSubsets(pString2,"",2);
        dictionary.loadDictionaryWithSubsets(pString2,null,2);
        boolean b21 = dictionary.addWord("122");
        boolean b22 = dictionary.addWord("");


    }


    @org.junit.Test
    public void testEmptyFile()  {
        Dictionary dictionary = new Dictionary();


        //loadDictionary加载空路径
        try {
            String pString = "";
            dictionary.loadDictionary(pString);
            fail("No exception thrown.");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertTrue(e.getMessage().contains("file path invalid"));
        }

        //loadDictionary加载错误路径
        try {
            String pString = "1";
            dictionary.loadDictionary(pString);

        } catch (Exception e) {
            assertTrue(e instanceof IOException);
        }

        //loadDictionary加载null
        try {
            dictionary.loadDictionary(null);
            fail("No exception thrown.");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertTrue(e.getMessage().contains("file path invalid"));
        }



    }
    @org.junit.Test()
    public void testNullFileLoad() throws IOException {
        Dictionary dictionary = new Dictionary();

        //findSingleWordAnagrams还未加载文件
        try {
            String pString = "";
            dictionary.findSingleWordAnagrams(pString);
            fail("No exception thrown.");
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains("dictionary not loaded"));
        }

        //findSingleWordAnagrams传入空字符串
        try {
            String pString = this.getClass().getResource("/net/mooctest/demo.txt").getPath();
            dictionary.loadDictionary(pString);
            dictionary.findSingleWordAnagrams("");
            fail("No exception thrown.");
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains("word string invalid"));
        }

        //findSingleWordAnagrams传入null
        try {
            String pString = this.getClass().getResource("/net/mooctest/demo.txt").getPath();
            dictionary.loadDictionary(pString);
            dictionary.findSingleWordAnagrams(null);
            fail("No exception thrown.");
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains("word string invalid"));
        }
    }

}
