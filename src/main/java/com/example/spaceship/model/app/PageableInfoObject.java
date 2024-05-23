package com.example.spaceship.model.app;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.example.spaceship.utils.Utils;

public class PageableInfoObject<T> {
	private T object;	
	private String direction;
	private String sortBy;
	private int page;


	public T getObject() {
		return object;
	}


	public void setObject(T object) {
		this.object = object;
	}

	public String getDirection() {
		return direction;
	}


	public void setDirection(String direction) {
		this.direction = direction;
	}


	public String getSortBy() {
		return sortBy;
	}


	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}
	
	//Creates the PageRequest from the object's fields. Direction: ASC or DESC and propperties as object attribute name.
	public Pageable getPageRequest( ) {

		Order orderStyle = new Order(((direction.equals("ASC")) ? Direction.ASC : Direction.DESC), this.sortBy);

		return PageRequest.of(page, Utils.NUM_ITEMS_PER_PAGE, Sort.by(orderStyle));

	}

	
}
