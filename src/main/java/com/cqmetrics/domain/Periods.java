
package com.cqmetrics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



public class Periods {

	@JsonIgnoreProperties(ignoreUnknown = true)
    private Integer index;
    private String value; 
    private Boolean bestValue;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getBestValue() {
        return bestValue;
    }

    public void setBestValue(Boolean bestValue) {
        this.bestValue = bestValue;
    }

  
}
