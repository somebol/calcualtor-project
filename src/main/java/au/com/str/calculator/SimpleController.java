package au.com.str.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@RestController
public class SimpleController {
	
	@Autowired
	private CalculatorService calculator;
	
	@ResponseBody
	@PostMapping(value = "/calculate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sumOfSquares(@RequestBody JsonNode inputData) {
		if (inputData.isEmpty(null)) {
			return ResponseEntity.badRequest().body(buildError("empty input"));
		}
		
		if (!inputData.isArray()) {
			return ResponseEntity.badRequest().body(buildError("expected array as input"));
		}
		
		if (inputData.size() < 3) {
			return ResponseEntity.badRequest().body(buildError("expected atleast 3 elements in request"));
		}
		
		try {
			ArrayNode arrayInput = (ArrayNode) inputData;
			List<Integer> numbers = new ArrayList<>();
			arrayInput.forEach(node -> numbers.add(Integer.parseInt(node.asText())));
			
			String result = calculator.compute(numbers);
			
			HashMap<String, String> response = new HashMap<>();
			response.put("result", result);
			
			return ResponseEntity.ok(response);
		} catch(NumberFormatException e) {
			return ResponseEntity.badRequest().body(buildError("expected array of integers as input"));
		} catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildError("expected array of integers as input"));
		}
	}

	private HashMap<String, String> buildError(String msg) {
		HashMap<String, String> response = new HashMap<>();
		response.put("error", msg);
		return response;
	}
}
