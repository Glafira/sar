package com.example.sar.controllers;

import com.example.sar.domain.Messages;
import com.example.sar.domain.UserLogin;
import com.example.sar.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/list")
public class MainController {

    @Autowired
    MessageRepository messageRepository;
    public MainController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }
//    Если пользователь авторизован то он будет засунут в  AuthenticationPrincipal UserLogin
    @GetMapping
    public String main(Model model, @AuthenticationPrincipal UserLogin user){
        HashMap<Object, Object> data = new HashMap<>();
        data.put("profile", user);

        List<Messages>  list = messageRepository.findAll();
        data.put("messages", list);

        model.addAttribute("frontEndData", data);
        return "messages";

    }
}
