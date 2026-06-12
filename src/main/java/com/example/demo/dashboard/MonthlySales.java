package com.example.demo.dashboard;

public class MonthlySales {
	
	private String month;
	private Long total;
	
	public MonthlySales(String month, Long total) {
        this.month = month;
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public Long getTotal() {
        return total;
    }

}
