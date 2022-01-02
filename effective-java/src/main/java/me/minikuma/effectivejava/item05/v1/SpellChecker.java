package me.minikuma.effectivejava.item05.v1;

import java.util.ArrayList;
import java.util.List;

/**
 * static utility 구현
 */

public class SpellChecker {
    // dictionary 객체가 고정됨 -> 변경이 어렵다.
    private static final Lexicon dictionary = new KoreanDictionary();

    private SpellChecker() {
        // 객체 생성 방지
    }

    public static boolean isValid(String word) {
        return true;
    }

    public static List<String> suggestions(String typo) {
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        boolean isChecked = SpellChecker.isValid("hello");
        System.out.println("isChecked = " + isChecked);
    }
}

interface Lexicon {}

class KoreanDictionary implements Lexicon {

}

class EnglishDictionary implements Lexicon {

}
