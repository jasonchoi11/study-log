package me.minikuma.effectivejava.item05.v2;

import java.util.ArrayList;
import java.util.List;
/*
    싱글톤 구현
 */

public class SpellChecker {
    // 객체 참조
    private final Lexicon dictionary = new KoreanDictionary();

    private SpellChecker() {
        //객체 생성 방지
    }

    public static final SpellChecker INSTANCE = new SpellChecker();

    public boolean isValid(String word) {
        return true;
    }

    public List<String> suggestions(String typo) {
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        boolean isChecked = SpellChecker.INSTANCE.isValid("hello");
        System.out.println("isChecked = " + isChecked);
    }
}

interface Lexicon {}

class KoreanDictionary implements Lexicon {

}

class EnglishDictionary implements Lexicon {

}