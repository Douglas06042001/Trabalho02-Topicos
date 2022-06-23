package br.edu.univas.si7.topicos.domain.dto;

import br.edu.univas.si7.topicos.domain.Payment;
import br.edu.univas.si7.topicos.domain.enums.PaymentStatus;

public class PaymentDTO {

	private Integer status;
	private Integer numberOfInstallments;
	private Integer installmentsPaid;

	public PaymentDTO() {
	}

	public PaymentDTO(Payment payment) {
		this.installmentsPaid = payment.getInstallmentsPaid();
		this.numberOfInstallments = payment.getNumberOfInstallments();
		this.status = payment.getStatus().getCode();
	}
	
	public PaymentDTO(PaymentStatus status, Integer numberOfInstallments, Integer installmentsPaid) {
		this.status = status.getCode();
		this.installmentsPaid = installmentsPaid;
		this.numberOfInstallments = numberOfInstallments;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	@Override
	public String toString() {
		return "PaymentDTO [status=" + status + ", numberOfInstallments=" + numberOfInstallments + ", installmentsPaid="
				+ installmentsPaid + "]";
	}

}
