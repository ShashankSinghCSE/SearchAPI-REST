package com.sedosearch.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Domain {

	@Id
	@GeneratedValue
	private Long Id;
	private String domain;
	private String price;
	private int length;
	private String numbers;
	private int letters;
	private int hyphens;

	@Override
	public String toString() {
		return "Domain [Id=" + Id + ", domain=" + domain + ", price=" + price + ", length=" + length + ", numbers="
				+ numbers + ", letters=" + letters + ", hyphens=" + hyphens + "]";
	}

	public Domain() {
	}

	public Domain(Long id, String domain, String price, int length, String numbers, int letters, int hyphens) {
		Id = id;
		this.domain = domain;
		this.price = price;
		this.length = length;
		this.numbers = numbers;
		this.letters = letters;
		this.hyphens = hyphens;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public int getLetters() {
		return letters;
	}

	public void setLetters(int letters) {
		this.letters = letters;
	}

	public int getHyphens() {
		return hyphens;
	}

	public void setHyphens(int hyphens) {
		this.hyphens = hyphens;
	}

}
