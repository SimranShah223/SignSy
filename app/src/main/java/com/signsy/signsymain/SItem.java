package com.signsy.signsymain;

public class SItem {
    String word,link;

    public SItem() {}

    public SItem(String word, String link) {
        this.word = word;
        this.link = link;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
