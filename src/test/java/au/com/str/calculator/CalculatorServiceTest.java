package au.com.str.calculator;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class CalculatorServiceTest {

	@Test
	public void testCompute() {
		CalculatorService service = new CalculatorService();
		
		String result = service.compute(Arrays.asList(new Integer[] {5,4,6,1}));
		System.out.println(result);
		assertEquals("8.77", result);
	}

}
