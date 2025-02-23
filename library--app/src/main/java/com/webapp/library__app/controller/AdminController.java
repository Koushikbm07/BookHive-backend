package com.webapp.library__app.controller;

import com.webapp.library__app.requestmodels.AddBookRequest;
import com.webapp.library__app.service.AdminService;
import com.webapp.library__app.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5174")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService=adminService;
    }

    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader(value="Authorization") String token,
                         @RequestBody AddBookRequest addBookRequest) throws Exception{
        String admin= ExtractJWT.payloadJWTExtraction(token,"\"userType\"");
        if(admin==null || !admin.equals("admin")){
            throw new Exception("Not a Administration Page");
        }
        adminService.postBook(addBookRequest);
    }
}
