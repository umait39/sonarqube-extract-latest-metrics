package com.hcldex.cqmetrics.domain;





import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

 public class SonarMetrics {

  
	@JsonIgnoreProperties(ignoreUnknown = true)
    private Component component;

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

  

}
