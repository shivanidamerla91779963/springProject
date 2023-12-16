package com.inn.cafe.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
//import lombok.Data;


@NamedQuery(name="Product.getAllProduct",query="select new com.inn.cafe.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) from Product p ")

@NamedQuery(name="Product.updateProductStatus",query="update Product p set p.status=:status where p.id=:id")

@NamedQuery(name="Product.getProductByCategory",query="select new com.inn.cafe.wrapper.ProductWrapper(p.id,p.name) from Product p where p.category.id=:id and p.status='true' ")

@NamedQuery(name="Product.getProductById",query="select new com.inn.cafe.wrapper.ProductWrapper(p.id,p.name,p.description,p.price) from Product p where p.id=:id")

//@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="product")
public class Product 
{
	public static final long serialVersionUID=123456L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;//before equal in named query(same)
	
	@Column(name="name")
	private String name;
	
	//multiple product can refer to one category
	//one category can have many products
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_fk",nullable=false)
	private Category category;
	
	@Column(name="description")
	private String description;
	
	@Column(name="price")
	private Integer price;
	
	@Column(name="status")
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Product(Integer id, String name, Category category, String description, Integer price, String status) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.status = status;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
