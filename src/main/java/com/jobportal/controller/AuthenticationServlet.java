package com.jobportal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * Servlet implementation class AuthenticationServlet
 */
@WebServlet("/AuthenticationServlet")
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action= request.getParameter("action");
		System.out.println("=============>>  "+action);
		switch (action) {
		case "signup": {
			signup(request,response);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
		
		
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
private void signup(HttpServletRequest request, HttpServletResponse response) {
		System.err.println("---------------->>  "+request.getParameter("user_name"));
		 String[] skillsArray=request.getParameterValues("skills");
		 for (String string : skillsArray) {
			System.out.println(string);
		}
		try {
			Part part=request.getPart("resume_path");
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
	}
}
