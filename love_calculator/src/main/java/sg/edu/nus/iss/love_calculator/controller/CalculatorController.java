package sg.edu.nus.iss.love_calculator.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.love_calculator.model.Calculator;
import sg.edu.nus.iss.love_calculator.service.CalculatorService;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {
    @Autowired
    private CalculatorService calSvc;

    @GetMapping
    public String getResult(
            @RequestParam(required = true) String sname,
            @RequestParam(required = true) String fname,
            Model model)
            throws IOException {
        Optional<Calculator> c = calSvc.getResult(sname, fname);
        calSvc.saveResult(c);
        model.addAttribute("result", c.get());
        model.addAttribute("sname", sname);
        model.addAttribute("fname", fname);
        String message = "%s and %s percentage is %s !".formatted(fname, sname, c.get().getPercentage());
        model.addAttribute("message", message);

        // trying
        List<Calculator> results = calSvc.getAllResults();
        model.addAttribute("results", results);
        return "indexSec";
    }

    @GetMapping(path = "{id}")
    public String getResult(Model model, @PathVariable String id) throws IOException {
        Optional<Calculator> cal = calSvc.getResult(id);
        Calculator result = cal.get();
        model.addAttribute("result", result);
        model.addAttribute("sname", result.getSname());
        model.addAttribute("fname", result.getFname());
        return "result";
    }

    @GetMapping(path = "/list")
    public String getAllResults(Model model) throws IOException {
        List<Calculator> results = calSvc.getAllResults();
        model.addAttribute("results", results);
        return "allResults";
    }
}
