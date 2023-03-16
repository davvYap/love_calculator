package sg.edu.nus.iss.love_calculator.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.love_calculator.model.Calculator;
import sg.edu.nus.iss.love_calculator.repository.CalculatorRepository;

@Service
public class CalculatorService {

    @Autowired
    private CalculatorRepository calRepo;

    @Value("${love.calculator.api.url}")
    private String loveCalculatorApiUrl;

    @Value("${love.calculator.api.key}")
    private String loveCalculatorApiKey;

    @Value("${love.calculator.api.host}")
    private String loveCalculatorApiHost;

    public Optional<Calculator> getResult(String sname, String fname) throws IOException {
        String calculatorUrl = UriComponentsBuilder.fromUriString(loveCalculatorApiUrl)
                .queryParam("fname", fname)
                .queryParam("sname", sname)
                .toUriString();
        // HttpHeaders headers = new HttpHeaders();
        // headers.set("X-RapidAPI-Key", loveCalculatorApiKey);
        // headers.set("X-RapidAPI-Host", loveCalculatorApiUrl);
        // HttpEntity entity = new HttpEntity(headers);

        RequestEntity req = RequestEntity.get(calculatorUrl)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-RapidAPI-Key", loveCalculatorApiKey)
                .header("X-RapidAPI-Host", loveCalculatorApiHost)
                .build();
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> response = template.exchange(req, String.class);

        Calculator c = Calculator.createFromJSON(response.getBody());

        if (c != null) {
            return Optional.of(c);
        }
        return Optional.empty();
    }

    public void saveResult(Optional<Calculator> cal) throws IOException {
        calRepo.saveResult(cal);
    }

    public Optional<Calculator> getResult(String id) throws IOException {
        return calRepo.getResult(id);
    }

    public List<Calculator> getAllResults() throws IOException {
        return calRepo.getAllResults();
    }
}
