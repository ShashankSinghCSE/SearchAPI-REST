package com.sedosearch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sedosearch.entity.Domain;
import com.sedosearch.repository.DomainRepository;
import com.sun.javafx.fxml.builder.URLBuilder;

@Service
public class DomainService {

	private DomainRepository domainRepository;

	@Autowired
	public void setDomRepository(DomainRepository domRepository) {
		this.domainRepository = domRepository;
	}

	public List<Domain> findAllByKeyword(String keyword) throws JsonMappingException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		List<Domain> domainObjects = new ArrayList<>();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		//past your rest endpoint here
		String url = "https://domain.com/service/common.php?keyword=" + keyword
				+ "&page=1&rel=6&orderdirection=2&domainIds=&v=0.1&o=json&m=search&f=requestSearch&pagesize=50";
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		
		//
		//create this Apache URIBuilder   :URIBuilder uri = new URIBuilder()
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(result.getBody());
		JsonNode resultListItem = root.path("resultList");
		
		//
		
		JSONObject object = new JSONObject(result.getBody().toString());
		JSONArray resultList = object.getJSONObject("b").getJSONObject("general").getJSONObject("searchRequest").getJSONArray("resultList");
		
		for(int i=0; i<resultList.length(); i++) {
			JSONObject domainItem = resultList.getJSONObject(i);
			String domain = domainItem.getString("0");
			String price = domainItem.getString("4");
			if(price.isEmpty()) {
				continue;
			}
			int length = 0, hyphenCount = 0, countLetter = 0;
			String digits = null;
			//split the domain and TLD
			String[] dom = domain.split("\\.");
			length = dom[0].length();
			char[] array = dom[0].toCharArray();
			
			//find numbers
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(dom[0]);
			while (matcher.find()) {
				digits = matcher.group();
			}
			
			//count hyphens and letters
			for (char item : array) {
				if (item == '-') {
					hyphenCount++;
				}
				if (Character.isLetter(item)) {
					countLetter++;
				}
			}
			
			Domain item = new Domain();
			item.setDomain(domain);
			item.setHyphens(hyphenCount);
			item.setLength(length);
			item.setNumbers(digits);
			item.setLetters(countLetter);
			item.setPrice(price);
			domainObjects.add(item);
			domainRepository.save(item);
		}
	return domainObjects;	
	}
}
