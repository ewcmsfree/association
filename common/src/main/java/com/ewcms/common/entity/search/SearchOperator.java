package com.ewcms.common.entity.search;

import com.ewcms.common.entity.search.exception.SearchException;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 查询操作符
 * 
 * @author wu_zhijun
 */
public enum SearchOperator {
    EQ("等于", "eq"), 
    NE("不等于", "ne"),
    GT("大于", "gt"), 
    GTE("大于等于", "gte"), 
    LT("小于", "lt"), 
    LTE("小于等于", "lte"),
    PREFIXLIKE("前缀模糊匹配", "prefixLike"), 
    PREFIXNOTLIKE("前缀模糊不匹配", "prefixNotLike"),
    SUFFIXLIKE("后缀模糊匹配", "suffixLike"), 
    SUFFIXNOTLIKE("后缀模糊不匹配", "suffixNotLike"),
    LIKE("模糊匹配", "like"), 
    INLIKE("已含匹配符的模糊匹配", "like"), 
    NOTLIKE("不匹配", "notLike"),
    ISNULL("空", "isNull"), 
    ISNOTNULL("非空", "isNotNull"),
    IN("包含", "in"), 
    NOTIN("不包含", "notIn"), 
    NULL("空值", "null"),
    CUSTOM("不参与","");

    private final String info;
    private final String symbol;

    SearchOperator(final String info, String symbol) {
        this.info = info;
        this.symbol = symbol;
    }

    public String getInfo() {
        return info;
    }

    public String getSymbol() {
        return symbol;
    }

    public static String toStringAllOperator() {
        return Arrays.toString(SearchOperator.values());
    }

    /**
     * 操作符是否允许为空
     *
     * @param operator
     * @return
     */
    public static boolean isAllowBlankValue(final SearchOperator operator) {
        return operator == SearchOperator.ISNOTNULL || operator == SearchOperator.ISNULL;
    }


    public static SearchOperator valueBySymbol(String symbol) throws SearchException {
        symbol = formatSymbol(symbol);
        for (SearchOperator operator : values()) {
            if (operator.getSymbol().equals(symbol)) {
                return operator;
            }
        }

        throw new SearchException("SearchOperator not method search operator symbol : " + symbol);
    }

    private static String formatSymbol(String symbol) {
        if (StringUtils.isBlank(symbol)) {
            return symbol;
        }
        return symbol.trim().toLowerCase().replace("  ", " ");
    }
}
