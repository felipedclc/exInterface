package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private Double pricePerDay;
	private Double pricePerHour;
	
	private BrazilTaxService taxService;
	

	public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) { // TRANSFORMANDO A DATA EM MILESSEGUNDOS PARA CALCULAR (ADICIONANDO .getTime) 
		long t1 = carRental.getStart().getTime();
		long t2 = carRental.getFinish().getTime();
		double hours = ((double)t2 - t1) / 1000 / 3600; // TRANSFORMANDO MILESSEGUNDOS EM SEGUNDOS(X 1000) , DEPOIS EM HORAS (X 3600)
		
		double basicPayment;
		if(hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour; // MATH.CEIL ARREDONDA PARA CIMA AS HORAS
		}
		else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay; // ACHANDO A QUANTIDADE EM DIAS  
		}
		double tax = taxService.tax(basicPayment); // CALCULA O VALOR DO IMPOSTO A PARTIR DO "basicPayment"
		
		carRental.setInvoice(new Invoice(basicPayment, tax)); // INSTANCIANDO A CLASSE INVOICE ONDE ESTA O BASICPAYMENT 
	}
	
}
