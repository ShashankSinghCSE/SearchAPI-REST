package com.sedosearch.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sedosearch.entity.Domain;
import com.sedosearch.service.DomainService;

@RestController
public class DomainController {
	@Autowired
	private DomainService domainService;
	
	@GetMapping(value = "/search/{keyword}")
	public String getKeywordResults(@PathVariable String keyword) throws IOException {
		List<Domain> domainObjects = domainService.findAllByKeyword(keyword);
		return domainObjects.toString();
		
	}

}
