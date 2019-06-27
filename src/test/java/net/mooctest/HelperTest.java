package net.mooctest;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class HelperTest {
    private Class<?> List;

    @org.junit.Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Class clazz = Class.forName("net.mooctest.Helper");

        Helper o = new Helper();
        Helper oo = o;

        boolean equivalent1 = Helper.isEquivalent(new char[]{}, new char[]{});
        boolean equivalent2 = Helper.isEquivalent(new char[]{'a'}, new char[]{'b'});
        boolean equivalent3 = Helper.isEquivalent(new char[]{'a','b'}, new char[]{'b'});
        boolean equivalent4 = Helper.isEquivalent(new char[]{'a','b'}, new char[]{'a','b'});


        char[] chars1 = Helper.setDifference(new char[]{'a', 'b'}, new char[]{'a', 'b'});
        Set<String> set = new HashSet<String>();
        Helper.setMultiplication();
        Helper.setMultiplication(null);
        set.add("233");
        Helper.setMultiplication(set);

        Method toCharArray = Helper.class.getDeclaredMethod("toCharArray", java.util.List.class);
        toCharArray.setAccessible(true);
        List<Character> list = null;
        toCharArray.invoke(Helper.class,list);

        toCharArray.invoke(Helper.class,new ArrayList<Character>());
        list = new ArrayList<Character>();
        list.add('1');
        toCharArray.invoke(Helper.class,list);

        Method toList = Helper.class.getDeclaredMethod("toList", char[].class);
        toList.setAccessible(true);
        try {
            char[] c = null;
            ArrayList<Character> list1 = (ArrayList<Character>)toList.invoke(new Helper(),c);
            fail("no Error");
        } catch (InvocationTargetException e){
            assertTrue(e.getTargetException() instanceof AssertionError);
        }

        Helper.sortWord("2312");
        Helper.sortWord("");
        Helper.isSubset("237".toCharArray(),"2634".toCharArray());
        Helper.isSubset("2634".toCharArray(),"237".toCharArray());
        Helper.isSubset("26".toCharArray(),"2634".toCharArray());


    }
}