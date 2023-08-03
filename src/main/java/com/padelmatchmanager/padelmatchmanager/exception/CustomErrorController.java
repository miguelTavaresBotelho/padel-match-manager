package com.padelmatchmanager.padelmatchmanager.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(WebRequest webRequest) {
        // Get the status code from the request attributes, or default to 500 (Internal Server Error)
        Integer statusCode = (Integer) webRequest.getAttribute("javax.servlet.error.status_code", WebRequest.SCOPE_REQUEST);
        if (statusCode == null) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        // Log the error details (optional - you can customize this part based on your logging framework)
        System.err.println("Error occurred. Status Code: " + statusCode);

        // Get the throwable (exception) from the request attributes, if available
        Throwable throwable = (Throwable) webRequest.getAttribute("javax.servlet.error.exception", WebRequest.SCOPE_REQUEST);

        if (throwable != null) {
            throwable.printStackTrace();
        }

        // Custom error response to send to the browser
        String errorResponse = "An unexpected error occurred. Status Code: " + statusCode;
        if (throwable != null) {
            errorResponse += "\nError Details: " + throwable.getMessage();
        }

        // You can customize the error response format or return it as JSON, etc.
        webRequest.setAttribute("errorResponse", errorResponse, WebRequest.SCOPE_REQUEST);

        return "error";
    }

    //@Override
    public String getErrorPath() {
        return "/error";
    }
}
