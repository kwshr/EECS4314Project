package com.eauction.authorizationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.common.User;
import java.util.*;

@CrossOrigin(origins = {"https://kwshr.github.io/*","http://localhost:3000/*" })
@RestController
@RequestMapping
public class AuthorizationController {

    @Autowired
    private final AuthorizationImpl authorizationImpl;
    
    public AuthorizationController(AuthorizationImpl authorizationImpl){
        this.authorizationImpl = authorizationImpl;
    }

    @RequestMapping(value="/signUp/{accountType}", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> signUpUser(@RequestBody User user, @PathVariable("accountType") String accountType) {
        AuthorizationQueryResult authorizationResult = authorizationImpl.signUp(user, accountType);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",authorizationResult.getMessage());
        response.put("queryStatus", authorizationResult.getAuthorizationStatus());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/signIn/{accountType}", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> signInUser(@RequestBody Map<String,String> credentials,@PathVariable("accountType") String accountType) {
       String userName = credentials.get("userName");
       String password = credentials.get("password");
        AuthorizationQueryResult authorizationResult = authorizationImpl.signIn(userName,password,accountType);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",authorizationResult.getMessage());
        response.put("queryStatus", authorizationResult.getAuthorizationStatus());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/passwordReset", method=RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> passwordReset(@RequestBody Map<String,String> credentials) {
       String userName = credentials.get("userName");
       String newPassword = credentials.get("newPassword");
        AuthorizationQueryResult authorizationResult = authorizationImpl.passwordReset(userName,newPassword);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",authorizationResult.getMessage());
        response.put("queryStatus", authorizationResult.getAuthorizationStatus());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/getDetails/{userId}", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getUserDetails(@PathVariable("userId") int userId) {
        AuthorizationQueryResult authorizationResult = authorizationImpl.getUserDetails(userId);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",authorizationResult.getMessage());
        response.put("queryStatus", authorizationResult.getAuthorizationStatus());
        response.put("data",authorizationResult.getData());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/getUserId/{userName}", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getUserId(@PathVariable("userName") String userName) {
        AuthorizationQueryResult authorizationResult = authorizationImpl.getUserId(userName);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",authorizationResult.getMessage());
        response.put("queryStatus", authorizationResult.getAuthorizationStatus());
        response.put("data",authorizationResult.getData());
        return HttpResponseStatus.setResponse(response);
    }

    @RequestMapping(value="/getSellerId/{userName}", method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getSellerId(@PathVariable("userName") String userName) {
        AuthorizationQueryResult authorizationResult = authorizationImpl.getSellerId(userName);
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("message",authorizationResult.getMessage());
        response.put("queryStatus", authorizationResult.getAuthorizationStatus());
        response.put("data",authorizationResult.getData());
        return HttpResponseStatus.setResponse(response);
    }
    
}
