package dev.weiwang.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiController {

    @PostMapping("/api/submit")
    public ResponseEntity<String> handleSubmit(@Valid @RequestBody FormGroup form, BindingResult result) {
        System.out.println("Result: " + result);
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body("\"" + String.join(", ", errors) + "\"");
        }
        System.out.println("Form Data: " + form.toString());
        return ResponseEntity.ok("\"Form submitted successfully\"");
    }

    @GetMapping("/api/supervisors")
    public List<String> getSupervisors() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Supervisor[]> responseEntity = restTemplate.getForEntity("https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/managers", Supervisor[].class);

        List<String> sortedSupervisors = Arrays.asList(responseEntity.getBody()).stream()
//                Filter out numeric jurisdictions
                .filter(s -> !s.getJurisdiction().matches("[0-9]+"))
//                Sort supervisors by jurisdiction, lastname, firstname
                .sorted(getSortedSupervisor())
//                Convert list to “<jurisdiction> - <lastName>, <firstName>” format
                .map(Supervisor::toString)
                .collect(Collectors.toList());
        return sortedSupervisors;
    }

    private Comparator<Supervisor> getSortedSupervisor() {
        return Comparator.comparing(Supervisor::getJurisdiction)
                .thenComparing(Supervisor::getLastName)
                .thenComparing(Supervisor::getFirstName);
    }
}