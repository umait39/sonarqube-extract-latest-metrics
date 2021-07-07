package com.hcldex.cqmetrics;


import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcldex.cqmetrics.conf.AppProperties;
import com.hcldex.cqmetrics.domain.SonarMetrics;
import com.hcldex.cqmetrics.exceptions.SonarMetricsException;
import com.hcldex.cqmetrics.utils.Constants;
import com.hcldex.cqmetrics.utils.Util;


public class RestAPIMetricsExtractor {

	private static final Logger logger = LoggerFactory.getLogger(RestAPIMetricsExtractor.class);
	
	private AppProperties appProperties = null;

	public RestAPIMetricsExtractor(AppProperties appProps) {
		this.appProperties = appProps;

	}

	public void getMetricsFromRestAPIForLatestDate() throws SonarMetricsException {
		logger.info(Constants.INSIDE_FUNCTION + Constants.COLON_SEPARATOR + "getMetricsFromRestAPIForLatestDate ");
		try {
			boolean lastCounterReached = false;
			
			String[] projectKeyList = appProperties.getProjectKeyList().split( Constants.COMMA_SEPARATOR);
			
			for (int i = 0; i < projectKeyList.length; i++) {
					
			SonarMetrics sonarMetrics =  consumerSonarWebAPI(projectKeyList[i]);		
			Util.populateMetricsMap(sonarMetrics);
		    
		    if (projectKeyList.length == i + 1)  
		    	lastCounterReached = true;
		    
			if (Boolean.TRUE.equals(appProperties.getjDependFlag())) {
				Util.writeIntoExcel(
						appProperties.getMetrics() + Constants.COMMA_SEPARATOR + appProperties.getjdependMetrics(),lastCounterReached,i);
			} else {
				Util.writeIntoExcel(appProperties.getMetrics(),lastCounterReached,i);
			}
			
				
			}
		
		} 
		catch(NotFoundException e) {
			throw new SonarMetricsException("Please check if correct Project Key is given", e);
		}
		catch(BadRequestException e) {
			throw new SonarMetricsException("URL is not correct - Please check if all required parameters are given correctly in property file ", e);
		}
		catch(ProcessingException e) {
			throw new SonarMetricsException("Check if Sonarserver is Up", e);
		}
		catch (Exception e) {
			logger.error("Exception in getMetricsFromRestAPIForLatestcDate : ", e);
			
		}

	}

	private SonarMetrics consumerSonarWebAPI(String projectKey)   {
		
		
		Client client = ClientBuilder.newClient();
		String urlString = getSonarWebServiceUrl(projectKey);
		logger.info("URL:  {}",urlString);
		WebTarget target = client.target(urlString);	
	    return target.request(MediaType.APPLICATION_JSON).get(SonarMetrics.class);
		
	}

	private String getSonarWebServiceUrl(String projectKey ) {
		logger.info(Constants.INSIDE_FUNCTION+Constants.COLON_SEPARATOR+ "getSonarWebServiceUrl");

		if (Boolean.TRUE.equals(appProperties.getjDependFlag())) {
			logger.info("{} {}",Constants.CALLING_API_JDEPEND_METRICS,projectKey);
			return new StringBuilder().append(appProperties.getBaseuri().trim())
					.append(appProperties.getMetrics().trim())
					.append(Constants.COMMA_SEPARATOR)
					.append(appProperties.getjdependMetrics().trim())
					.append(appProperties.getCompArgument().trim())
					.append(projectKey.trim())								
					.toString();
		} else {
			logger.info("{} {}",Constants.CALLING_API_WITHOUT_JDEPEND_METRICS,projectKey);
			return new StringBuilder().append(appProperties.getBaseuri().trim())
					.append(appProperties.getMetrics().trim())
					.append(appProperties.getCompArgument().trim())
					.append(projectKey.trim())								
					.toString();
		}

		
	}
	
	
	

	

}
