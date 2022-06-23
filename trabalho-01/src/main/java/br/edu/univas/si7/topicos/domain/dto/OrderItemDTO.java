package br.edu.univas.si7.topicos.domain.dto;

import java.text.NumberFormat;
import java.util.Locale;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.univas.si7.topicos.domain.OrderItem;

public class OrderItemDTO {

	@NotNull(message = "ProductId can not be null.")
	@Size(min = 0, message = "ProductId must not be negative.")
	private Integer productId;

	private String productName; //só para usar no toString

	@NotNull(message = "Amount can not be null.")
	@Size(min = 0, message = "Amunt must must not be negative.")
	private Integer amount;

	private Double price;

	public OrderItemDTO() {
	}

	public OrderItemDTO(OrderItem item) {
		this.productId = item.getProduct().getId();
		this.productName = item.getProduct().getName();
		this.amount = item.getAmount();
		this.price = item.getProduct().getPrice();
	}

	public OrderItemDTO(
			@NotNull(message = "ProductId can not be null.") @Size(min = 0, message = "ProductId must not be negative.") Integer productId,
			@NotNull(message = "Amount can not be null.") @Size(min = 0, message = "Amunt must must not be negative.") Integer amount,
			Double price) {
		super();
		this.productId = productId;
		this.amount = amount;
		this.price = price;
	}

	public Double getSubTotal() {
		return amount * price;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	private NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

	@Override
	public String toString() {
		return productName + ": amount=" + amount + ", price=" + nf.format(price) + ", subTotal="
				+ nf.format(getSubTotal()) + "\n";
	}
}