
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lenovo
 */
public class Trie {

    private TrieNode root;
    ArrayList<String> words;
    TrieNode prefixRoot;
    String curPrefix;

    public Trie() {
        root = new TrieNode();
        words = new ArrayList<String>();
    }

    public void insert(String word) {
        HashMap<Character, TrieNode> children = root.children;

        TrieNode crntparent;

        crntparent = root;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            TrieNode t;
            if (children.containsKey(c)) {
                t = children.get(c);
            } else {
                t = new TrieNode(c);
                t.parent = crntparent;
                children.put(c, t);
            }

            children = t.children;
            crntparent = t;

            //set leaf node
            if (i == word.length() - 1) {
                t.isLeaf = true;
            }
        }
    }

    public boolean search(String word) {
        TrieNode t = searchNode(word);
        if (t != null && t.isLeaf) {
            return true;
        } else {
            return false;
        }
    }

    public boolean startsWith(String prefix) {
        if (searchNode(prefix) == null) {
            return false;
        } else {
            return true;
        }
    }

    public TrieNode searchNode(String str) {
        Map<Character, TrieNode> children = root.children;
        TrieNode t = null;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                children = t.children;
            } else {
                return null;
            }
        }

        prefixRoot = t;
        curPrefix = str;
        words.clear();
        return t;
    }

    void wordsFinderTraversal(TrieNode node, int offset) {

        if (node.isLeaf == true) {

            TrieNode altair;
            altair = node;

            Stack<String> hstack = new Stack<String>();

            while (altair != prefixRoot) {
                hstack.push(Character.toString(altair.c));
                altair = altair.parent;
            }

            String wrd = curPrefix;

            while (hstack.empty() == false) {
                wrd = wrd + hstack.pop();
            }

            words.add(wrd);

        }

        Set<Character> kset = node.children.keySet();
        Iterator itr = kset.iterator();
        ArrayList<Character> aloc = new ArrayList<Character>();

        while (itr.hasNext()) {
            Character ch = (Character) itr.next();
            aloc.add(ch);
        }

        for (int i = 0; i < aloc.size(); i++) {
            wordsFinderTraversal(node.children.get(aloc.get(i)), offset + 2);
        }

    }

    void displayFoundWords(int count) {
        for (int i = 0; i <count; i++) {
            System.out.println(words.get(i));
        }
    }

    
}
