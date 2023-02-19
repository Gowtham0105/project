package com.onesoft.useproduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UseProductController {
	@Autowired
	RestTemplate rest;
	@GetMapping(value="/productwithtax")
	public List<Product> gstwithtax(){
		String url1="http://localhost:8082/getproductlist";
		String url2="http://localhost:8081/getpercentage/";
		
		ResponseEntity<List<Product>> response1=rest.exchange(url1, HttpMethod.GET, null,new ParameterizedTypeReference<List<Product>>() {});
		List<Product> products=response1.getBody();
		
		for(Product p:products) {
			int hsn=p.getHsn();
			ResponseEntity<Integer> response2=rest.exchange(url2+hsn,HttpMethod.GET,null,Integer.class);
		    int percentage=response2.getBody();
		    p.setPrice(p.getPrice()+(p.getPrice()*percentage/100));
		    
		}
		return products;
		
		
	}
	

}
