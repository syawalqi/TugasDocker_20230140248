package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import com.tugas.deploy.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "20230140248";

    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/home";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        if (ADMIN_USER.equals(username) && ADMIN_PASS.equals(password)) {
            session.setAttribute("user", username);
            return "redirect:/home";
        }
        model.addAttribute("error", "Username atau Password salah!");
        return "login";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        // Fetch all students from the database
        model.addAttribute("students", userRepository.findAll());
        model.addAttribute("nim", ADMIN_PASS);
        return "home";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "form";
    }

    @PostMapping("/form")
    public String submitForm(@RequestParam String nama, @RequestParam String nim, @RequestParam String jenisKelamin, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        // Save the new student to the database
        User newUser = new User(nim, nama, jenisKelamin);
        userRepository.save(newUser);
        
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
