package br.com.exemplo.exemplo;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExemploApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExemploApplication.class, args);
	
		
		String num = "628,22";
		String n = num.replace(",", ".");
		float m = Float.parseFloat(n);
		System.out.println("Número:" + m);
				
//		
//		
//		s.setDecimalSeparator(',');
//        s.setGroupingSeparator('.');
//        formatter.setDecimalFormatSymbols(s);
//        var mf = formatter.format(num);

//        float m = Float.parseFloat(num);
        
        //var mf = formatter.format(m);
        //Float mediaFinal = Float.parseFloat(mf);
        
        //System.out.println("Número formatado: " + mediaFinal);
	}

}
