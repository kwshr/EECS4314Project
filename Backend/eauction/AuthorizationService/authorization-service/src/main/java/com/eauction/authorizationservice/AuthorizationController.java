package com.eauction.authorizationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.common.User;
import java.util.*;


@RestController
@RequestMapping
public class AuthorizationController {

    @Autowired
    private final AuthorizationImpl authorizationImpl;
    
    public AuthorizationController(AuthorizationImpl authorizationImpl){
        this.authorizationImpl = authorizationImpl;
    }

    @RequestMapping(value="/signUp", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> signUpUser(@RequestBody User user) {
        AuthorizationQueryResult authorizationResult = authorizationImpl.SignUp(user);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",authorizationResult.getMessage());
        response.put("queryStatus", authorizationResult.getAuthorizationStatus());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/signIn", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> signInUser(@RequestBody Map<String,String> credentials) {
       String userName = credentials.get("userName");
       String password = credentials.get("password");
        AuthorizationQueryResult authorizationResult = authorizationImpl.SignIn(userName,password);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",authorizationResult.getMessage());
        response.put("queryStatus", authorizationResult.getAuthorizationStatus());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/passwordReset", method=RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> passwordReset(@RequestBody Map<String,String> credentials) {
       String userName = credentials.get("userName");
       String newPassword = credentials.get("newPassword");
        AuthorizationQueryResult authorizationResult = authorizationImpl.SignIn(userName,newPassword);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",authorizationResult.getMessage());
        response.put("queryStatus", authorizationResult.getAuthorizationStatus());
        return HttpResponseStatus.setResponse(response);
    }
    
}
