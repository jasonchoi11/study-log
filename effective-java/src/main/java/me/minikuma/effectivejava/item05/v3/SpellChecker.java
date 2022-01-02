package me.minikuma.effectivejava.item05.v3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
/*
    의존성 주입
 */
public class SpellChecker {

    private final Lexicon dictionary;

//    public SpellChecker(Lexicon dictionary) {
//        this.dictionary = Objects.requireNonNull(dictionary);
//    }
    public SpellChecker(Supplier<Lexicon> dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary.get());
    }

    public boolean isValid(String word) {
        return true;
    }

    public List<String> suggestions(String typo) {
        return new ArrayList<>();
    }

    public static void main(String[] args) {
//        SpellChecker spellChecker = new SpellChecker(new EnglishDictionary());
        Supplier<Lexicon> dictionary = new Supplier<Lexicon>() {
            @Override
            public Lexicon get() {
                return new KoreanDictionary();
            }
        }; // lambda 변경 가능
        SpellChecker spellChecker = new SpellChecker(dictionary);
        boolean isChecked = spellChecker.isValid("hello");
        System.out.println("isChecked = " + isChecked);
    }
}

interface Lexicon {}

class KoreanDictionary implements Lexicon {

}

class EnglishDictionary implements Lexicon {

}


