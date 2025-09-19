package com.acciojob.Library_Management_System.Controllers;

import com.acciojob.Library_Management_System.Services.LibraryCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class LibraryCardController {
    @Autowired
    private LibraryCardService libraryCardService;

    @PostMapping("/generatecard")
    public ResponseEntity add(@RequestParam(value = "studentId",required = false)Integer studentId){
        try{
            String result= libraryCardService.add(studentId);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/associateStudentAndCard")
    public ResponseEntity associateStudentAndCard(@RequestParam("rollNo")Integer rollNo,
                                                  @RequestParam("cardNo")Integer cardNo){
        try {
            String res= libraryCardService.associateStudentAndCArd(rollNo,cardNo);
            return new ResponseEntity(res,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
