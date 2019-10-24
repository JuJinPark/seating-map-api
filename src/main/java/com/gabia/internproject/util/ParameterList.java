package com.gabia.internproject.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParameterList {

    private static final char QUERY_STRING_SEPARATOR = '?';
    private static final String PARAM_SEPARATOR = "&";
    private static final String PAIR_SEPARATOR = "=";
    private static final String EMPTY_STRING = "";


    private final List<Parameter> params;

    public ParameterList() {
        params = new ArrayList<>();
    }

    public ParameterList(Map<String, String> map) {
        this();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.add(new Parameter(entry.getKey(), entry.getValue()));
            }
        }
    }

    public void add(String key, String value) {
        params.add(new Parameter(key , value));
    }

    public String appendTo(String url) throws UnsupportedEncodingException {
       // StringValidationChecker.checkNotNull(url, "Cannot append to null URL");
        final String queryString = asFormUrlEncodedString();
        if (queryString.equals(EMPTY_STRING)) {
            return url;
        } else {
            return url
                    + (url.indexOf(QUERY_STRING_SEPARATOR) == -1 ? QUERY_STRING_SEPARATOR : PARAM_SEPARATOR)
                    + queryString;
        }
    }
    public String appendToWithoutEncoding(String url) throws UnsupportedEncodingException {
        //StringValidationChecker.checkNotNull(url, "Cannot append to null URL");
        final String queryString = asFormUrlNormalString();
        if (queryString.equals(EMPTY_STRING)) {
            return url;
        } else {
            return url
                    + (url.indexOf(QUERY_STRING_SEPARATOR) == -1 ? QUERY_STRING_SEPARATOR : PARAM_SEPARATOR)
                    + queryString;
        }
    }

    public String asFormUrlEncodedString() throws UnsupportedEncodingException {
        if (params.isEmpty()) {
            return EMPTY_STRING;
        }

        final StringBuilder builder = new StringBuilder();
        for (Parameter p : params) {
            builder.append(PARAM_SEPARATOR).append(p.asUrlEncodedPair());
        }

        return builder.substring(1);
    }

    public String asFormUrlNormalString() throws UnsupportedEncodingException {
        if (params.isEmpty()) {
            return EMPTY_STRING;
        }

        final StringBuilder builder = new StringBuilder();
        for (Parameter p : params) {
            builder.append(PARAM_SEPARATOR).append(p.asUrlPair());
        }

        return builder.substring(1);
    }


}
