
package com.cqmetrics.domain;

import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class Measures {

	@JsonIgnoreProperties(ignoreUnknown = true)
    private String metric;
   
    private String value;
  
    private Boolean bestValue;
   
    private List<Periods> periods = null;
   
    private Period period;

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
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

    public List<Periods> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Periods> periods) {
        this.periods = periods;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    

}
