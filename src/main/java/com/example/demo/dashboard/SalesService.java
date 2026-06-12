package com.example.demo.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesService {
	
	 @Autowired
	    private SalesRepository repo;

	    public List<MonthlySales> getMonthlySales() {
	        List<Object[]> rows = repo.getMonthlySalesRaw();
	        List<MonthlySales> result = new ArrayList<>();

	        for (Object[] row : rows) {
	            String month = (String) row[0];
	            Long total = ((Number) row[1]).longValue();
	            result.add(new MonthlySales(month, total));
	        }

	        return result;
	    }

}
