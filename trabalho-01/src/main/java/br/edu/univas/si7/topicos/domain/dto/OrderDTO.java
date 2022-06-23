package br.edu.univas.si7.topicos.domain.dto;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.edu.univas.si7.topicos.domain.Order;
import br.edu.univas.si7.topicos.domain.enums.PaymentStatus;

public class OrderDTO {

    //@JsonManagedReference
    //@JsonBackReference // deserialization works fine
    //@JsonIgnore //deserialization does not work, the reference will be null   @NotNull(message = "CustomerId can not be null.")
    @NotEmpty(message = "CustomerId can not be empty.")
    private String customerId;

    @NotNull(message = "ShipAddress can not be null.")
    @Min(0) // , message = "ShipAddress must not be negative.")
    private Integer shipAddressId;

    @NotNull(message = "Payment can not be null.")
    private PaymentDTO payment;

    @NotEmpty(message = "Items can not be empty.")
    private List<OrderItemDTO> items;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateRequest;

    @JsonIgnore
    private Integer orderId;

    @JsonIgnore
    private String customerName;

    @JsonIgnore
    private String customerEmail;

    public OrderDTO() {
	}
    
    public OrderDTO(Order order) {
        this.orderId = order.getId();
        this.customerId = order.getCustomer().getId();
        this.customerName = order.getCustomer().getName();
        this.customerEmail = order.getCustomer().getEmail();
        this.shipAddressId = order.getShippingAddress().getId();
        this.dateRequest = order.getDateRequest();
        this.payment = new PaymentDTO(order.getPayment());
        this.items = order.getItems().stream().map(i -> new OrderItemDTO(i)).collect(Collectors.toList());
    }

    public Double getTotalValue() {
        return items.stream().mapToDouble(i -> i.getSubTotal()).sum();
    }

    public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Integer getShipAddressId() {
		return shipAddressId;
	}

	public void setShipAddressId(Integer shipAddressId) {
		this.shipAddressId = shipAddressId;
	}

	public PaymentDTO getPayment() {
		return payment;
	}

	public void setPayment(PaymentDTO payment) {
		this.payment = payment;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}

	public LocalDateTime getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(LocalDateTime dateRequest) {
		this.dateRequest = dateRequest;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	//utilities for handling with date
	private NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public String toString() {
        StringBuffer itemsStr = new StringBuffer();
        for (OrderItemDTO i : items) {
            itemsStr.append(i);
        }
        return "Order: " + orderId 
            + "\nDate Request=" + formatter.format(dateRequest) 
            + "\nCustomer=" + customerName
            + "\nPayment Status=" + PaymentStatus.toEnum(payment.getStatus()).getDescription() 
            + "\nDetails:\n" + itemsStr
            + "TotalValue=" + nf.format(getTotalValue());
    }
}