package com.example.employee.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class Employee {
  //  ID , name , age , position , onLeave, employmentYear and annualLeave.
    @NotNull(message = " ID cannot be null.") //Cannot be null.
    @Size(min = 3,message = "- ID length must be more than 2 characters.")//- Length must be more than 2 characters.
    private String id;

    @NotNull(message = "Name cannot be null.")
    @Size(min = 5, message = "Name of length must be more than 4 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters(no number)")
    private String name;

    @Email(message = "Email must be a valid email format")
    private String email;

    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with 05 and have 10 digits")
    private String phoneNumber;
    //Must consists of exactly 10 digits

   @NotNull(message = "Age cannot be null")
   @Min(value = 26, message = "Age must be more than 25")
   //Must be a number.
    private Integer age;

    @NotNull(message = " Position cannot be null")
   //Must be either "supervisor" or "coordinator" only
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "Position must be either 'supervisor' or 'coordinator' only.")
    private String position;

    private boolean onLeave = false;//Must be initially set to false.

    @NotNull(message = "Hire date cannot be null")
    @PastOrPresent(message = "Hire date must be in the past or present")//should be a date in the past or the present.
    //Must be a valid year (e.g., 1900 or later).
    //@Min(value = 1900, message = "Must be a valid year (e.g., 1900 or later)")
    //@Min(1900)
    //@Validated
    // @IsAfter(current = "1900-01-01")
    //@Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    //@Past(message = "Date must be in the past")
    //@Past(year = 1900, message = "Date must be after 1900")
     @Past(message = "Date must be in the past and after 1900")
    //@Pattern(regexp = "((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])")
    //@Pattern(regexp = "# (dd)/(mm)/(yyyy)(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((?:19|20)[0-9][0-9])")
     private LocalDate hireDate;

    @NotNull(message = " AnnualLeave cannot be null.")
    @Positive(message = "AnnualLeave must be a positive number.")
    private Integer annualLeave;



}
