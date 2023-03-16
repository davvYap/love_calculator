package sg.edu.nus.iss.love_calculator.repository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.love_calculator.model.Calculator;

@Repository
public class CalculatorRepository {
    @Autowired
    @Qualifier("calculator")
    private RedisTemplate<String, String> redisTemplate;

    public void saveResult(Optional<Calculator> cal) throws IOException {
        Calculator calculator = cal.get();
        redisTemplate.opsForValue().set(calculator.getId(), calculator.toJSON().toString());
    }

    public List<Calculator> getAllResults() throws IOException {
        Set<String> redisKeys = redisTemplate.keys("*");
        List<Calculator> results = new LinkedList<>();
        for (String key : redisKeys) {
            String json = redisTemplate.opsForValue().get(key);
            Calculator calculator = Calculator.createFromJSON(json);
            calculator.setId(key);
            results.add(calculator);
        }
        return results;

    }
}
