package com.example.employee.Controller;

import com.example.employee.ApiResponse.ApiResponse;
import com.example.employee.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();

    //1-Get all employees .
    @GetMapping("/get")
    public ResponseEntity getEmployee() {

        return ResponseEntity.status(200).body(employees) ;
    }

    //2-Add a new employee
    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors errors) {
        if(errors.hasErrors()){
            String massage =errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(massage);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body( new ApiResponse("Employee added"));
    }

    //3-Update an employee
    @PutMapping("/update/{id}")
    public ResponseEntity updatedEmployee(@PathVariable String id, @RequestBody @Valid Employee employee,Errors errors) {
        if(errors.hasErrors()){
            String massage= errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(massage);
        }

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(id) ) {
                employees.set(i,employee);
                return ResponseEntity.status(200).body( new ApiResponse("Employee updated"));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Employee not found with id: " + id));
    }

    //4-Delete an employee
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable String id) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(id) ) {
                employees.remove(i);
                return ResponseEntity.status(200).body(new ApiResponse("Employee deleted"));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Employee not found"));
    }

    //5-Search Employees by Position.
    @GetMapping("/searchByPosition/{position}")
    public ResponseEntity<?> searchEmployeesByPosition(@PathVariable String position) {
        ArrayList<Employee> employeesWithPosition = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getPosition().equals(position)) {
                employeesWithPosition.add(employee);
            }
        }
        if (employeesWithPosition.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No employees found with the specified position."));
        }
        return ResponseEntity.status(200).body(employeesWithPosition);
    }


    //6. Get Employees by Age Range:
    @GetMapping("/getByAgeRange/{minAge}/{maxAge}")
    public ResponseEntity getByAgeRange(@PathVariable int minAge, @PathVariable int maxAge) {
        if (minAge < 0 || maxAge < 0 || minAge > maxAge) {
            return ResponseEntity.status(400).body(new ApiResponse("Invalid age range."));
        }

        ArrayList<Employee> employeesInRange = new ArrayList<>();
        for (Employee employee : employees) {
            int employeeAge = employee.getAge();
            if (employeeAge >= minAge && employeeAge <= maxAge) {
                employeesInRange.add(employee);
            }
        }
        if (!employeesInRange.isEmpty()) {
            return ResponseEntity.status(200).body(employeesInRange);
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("No employees found in the specified age range."));
        }
    }

    // 7. Apply for annual leave
    @PutMapping("/applyForLeave/{id}")
    public ResponseEntity applyForLeave(@PathVariable String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                if (employee.isOnLeave()) {
                    return ResponseEntity.status(400).body(new ApiResponse("Employee is already on leave."));
                }

                if (employee.getAnnualLeave() < 1) {
                    return ResponseEntity.status(400).body(new ApiResponse("Employee has no annual leave remaining."));
                }
                employee.setOnLeave(true);
                employee.setAnnualLeave(employee.getAnnualLeave() - 1);

                return ResponseEntity.status(200).body(new ApiResponse("Leave applied successfully."));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Employee not found with id: " + id));
    }

    // 8. Get Employees with No Annual Leave
    @GetMapping("/noAnnualLeave")
    public ResponseEntity getEmployeesWithNoAnnualLeave() {
        ArrayList<Employee> employeesNoLeave = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAnnualLeave() <= 0) {
                employeesNoLeave.add(employee);
            }
        }
        return ResponseEntity.status(200).body(employeesNoLeave);
    }

    // 9. Promote Employee
    @PutMapping("/promote/{id}")
    public ResponseEntity promoteEmployee(@PathVariable String id) {
            for (Employee employee : employees) {
                if (employee.getId().equals(id)) {
                    if (!employee.getPosition().equals("supervisor") && employee.getAge() >= 30 && !employee.isOnLeave()) {
                        employee.setPosition("supervisor");
                        return ResponseEntity.status(200).body(new ApiResponse("Employee promoted to supervisor."));
                    }
                    return ResponseEntity.status(400).body(new ApiResponse("Employee does not meet the promotion criteria."));
                }
            }
            return ResponseEntity.status(404).body(new ApiResponse("Employee not found with id: " + id));
        }
    }
































//    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
//        Employee savedEmployee = employeeService.addEmployee(employee);
//        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
//        Employee employee = employeeService.getEmployeeById(id);
//        return ResponseEntity.ok(employee);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Employee>> getAllEmployees() {
//        List<Employee> employees = employeeService.getAllEmployees();
//        return ResponseEntity.ok(employees);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employeeDetails) {
//        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
//        return ResponseEntity.ok(updatedEmployee);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
//        employeeService.deleteEmployee(id);
//        return ResponseEntity.noContent().build();







