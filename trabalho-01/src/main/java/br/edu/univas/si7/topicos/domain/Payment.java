package br.edu.univas.si7.topicos.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import br.edu.univas.si7.topicos.domain.enums.PaymentStatus;

@Entity
public abstract class Payment implements Serializable {

	@Id
	private Integer id;
	private PaymentStatus status;
	private Integer numberOfInstallments;
	private Integer installmentsPaid;

	@OneToOne
	@JoinColumn(name = "ORDER_FK")
	@MapsId
	private Order order;

	public Payment() {
	}

	public Payment(Integer id, PaymentStatus status, Integer numberOfInstallments, Integer installmentsPaid) {
		super();
		this.id = id;
		this.status = status;
		this.numberOfInstallments = numberOfInstallments;
		this.installmentsPaid = installmentsPaid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}

	public Integer getInstallmentsPaid() {
		return installmentsPaid;
	}

	public void setInstallmentsPaid(Integer installmentsPaid) {
		this.installmentsPaid = installmentsPaid;
	}

	public Order getOrder() {
		return order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
