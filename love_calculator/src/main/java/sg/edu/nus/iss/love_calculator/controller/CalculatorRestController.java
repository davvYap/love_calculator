package sg.edu.nus.iss.love_calculator.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.love_calculator.model.Calculator;
import sg.edu.nus.iss.love_calculator.service.CalculatorService;

@RestController
@RequestMapping(path = "api")
public class CalculatorRestController {
    @Autowired
    private CalculatorService calSvc;

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getResult(@PathVariable String id) throws IOException {
        Optional<Calculator> cal = calSvc.getResult(id);
        if (cal.isEmpty()) {
            JsonObject response = Json.createObjectBuilder()
                    .add("message", "Record with id %s is not found.".formatted(id)).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response.toString());
        }
        return ResponseEntity.ok(cal.get().toJSON().toString());
    }
}
