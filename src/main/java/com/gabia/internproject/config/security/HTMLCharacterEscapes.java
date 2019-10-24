package com.gabia.internproject.config.security;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;


import org.apache.commons.lang3.text.translate.AggregateTranslator;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.EntityArrays;
import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.apache.commons.text.StringEscapeUtils;

public class HTMLCharacterEscapes extends CharacterEscapes {

    private final int[] asciiEscapes;


     private final CharSequenceTranslator translator;

    public HTMLCharacterEscapes() {

        //  XSS 방지 처리할 특수 문자 지정
        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
        asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\"'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;

        //특정 문자열을 다른문자열로 치환할떄 커스터마이징 하는부분(처음에 json to object롤 할떄 바꾼느법은 다른가?)
        //하지만 common lang 새버전에서는 translator가 depreceated되서 새버전에서 바꾸는 법 고민필요
        // XSS 방지 처리 특수 문자 인코딩 값 지정
        translator = new AggregateTranslator(
                new LookupTranslator(EntityArrays.BASIC_ESCAPE()),  // <, >, &, " 는 여기에 포함됨
                new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE()),
                new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE()),
                // 여기에서 커스터마이징 가능
                new LookupTranslator(
                        new String[][]{
                                {"(",  "&#40;"},
                                {")",  "&#41;"},
                                {"#",  "&#35;"},
                                {"\'", "&#39;"}
                        }
                )
        );
    }

    @Override
    public int[] getEscapeCodesForAscii() {
        return asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {
        new SerializedString(translator.translate(Character.toString((char) ch)));

        return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char) ch)));
    }
}