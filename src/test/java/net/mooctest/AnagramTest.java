package net.mooctest;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class AnagramTest {
    private Class<?> Set;

    @Test
    public void test() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //test for Anagram constructor
        //1.dictionaryFilePath.empty() = false
        try {
        Anagram anagram2 = new Anagram("");
            fail("no Error");
        }catch (Error error){
            assertTrue(error instanceof AssertionError);
        }
        //2.dictionaryFilePath.empty = true
        Anagram anagram3 = new Anagram(2,this.getClass().getResource("/net/mooctest/demo.txt").getPath());

        //test for findAllAnagrams(String wordString)
        java.util.Set<Set<String >> test_sets =  new HashSet<Set<String>>();
        //1.wordString = ""; should return []
        assertEquals(test_sets,anagram3.findAllAnagrams(""));
        //2.wordString = "233323acd23d"; should return[[233323acd23d]]
        java.util.Set<String> test_set =  new HashSet<String>();
        test_set.add("233323acd23d");
        test_sets.add(test_set);
        assertEquals(test_sets,anagram3.findAllAnagrams("233323acd23d"));
        test_set.remove("233323acd23d");
        //3.wordString = "china"; should return[[ch],[ina]]
        test_set.add("ch");
        test_set.add("ina");
        assertArrayEquals(test_sets.toArray(),anagram3.findAllAnagrams("china").toArray());

        //test for usage
        Method usage = Anagram.class.getDeclaredMethod("usage");
        usage.setAccessible(true);
        usage.invoke(Anagram.class);

        //test for mergeAnagramKeyWords(Set<String> anagramKeySet)
        Method mergeAnagramKeyWords = Anagram.class.getDeclaredMethod("mergeAnagramKeyWords", java.util.Set.class);
        mergeAnagramKeyWords.setAccessible(true);
        //1.anagramKeySet = null
        try {
            java.util.Set<String> set = null;
            mergeAnagramKeyWords.invoke(anagram3,set);
            fail("no Exception");
        }catch (InvocationTargetException e){
            assertEquals("anagram keys set cannot be null", e.getTargetException().getMessage());
        }

        //test for mergeWordToSets(String word, Set<Set<String>> sets)
        Method mergeWordToSets = Anagram.class.getDeclaredMethod("mergeWordToSets", String.class,java.util.Set.class);
        mergeWordToSets.setAccessible(true);
        //1.word.isEmpty() = true, assertionError
        try {
            mergeWordToSets.invoke(anagram3,"",new HashSet<>());
            fail("no Exception");
        }catch (InvocationTargetException e){
            assertTrue(e.getTargetException() instanceof AssertionError);
        }
        //2.sets = null, return null
        assertNull(mergeWordToSets.invoke(anagram3,"32",null));
        //3.exist set in sets = null, throws an exception
        try {
            java.util.Set<Set<String >> sets =  new HashSet<Set<String>>();
            java.util.Set<String> set1 = new HashSet<String>();
            java.util.Set<String> set2 = null;
            sets.add(set2);
            set1.add("1");
            sets.add(set1);
            mergeWordToSets.invoke(anagram3,"32",sets);
            fail("no Exception");
        }catch (InvocationTargetException e){
            assertEquals(e.getTargetException().getMessage(),("anagram keys set cannot be null"));
        }
        //4.return normally; word = 32, sets=[[1]], return [[1,32]]
        java.util.Set<Set<String >> test_sets1 =  new HashSet<Set<String>>();
        java.util.Set<String> test_set1 = new HashSet<String>();
        test_set1.add("1");
        test_sets1.add(test_set1);
        mergeWordToSets.invoke(anagram3,"32",test_sets1);
        java.util.Set<Set<String >> exp_sets1 =  new HashSet<Set<String>>();
        java.util.Set<String> exp_set1 = new HashSet<String>();
        exp_set1.add("1");
        exp_set1.add("32");
        exp_sets1.add(exp_set1);
        assertArrayEquals(test_sets1.toArray(),exp_sets1.toArray());

        //test for findAnagrams(int dictionaryIndex, char[] charInventory, List<String> keyList)
        Method findAnagrams = Anagram.class.getDeclaredMethod("findAnagrams", int.class,char[].class, List.class);
        findAnagrams.setAccessible(true);
        char[] d = new char[7];
        //dictionaryIndex >= keyList.size(),charInventory.length > minWordSize
        assertNull(findAnagrams.invoke(anagram3,10,d,new ArrayList<String>()));

    }
}
