package com.example.sar.controllers;

import com.example.sar.domain.Messages;
import com.example.sar.repo.MessageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.RequestWrapper;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping( "/messages")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    public  MessageController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }
    @GetMapping("")
    public List<Messages> getAllMessage(){
        return messageRepository.findAll();
    }

    @GetMapping("{id}")
    public Messages getIdMessage(@PathVariable("id") Messages mess){
        //Spring сам найдет по id  нужный обьект и запехнеи его в mess
        return  mess;
    }

    @PostMapping("")
    public Messages Create(@RequestBody  Messages mes){
        mes.setDateCreate(LocalDateTime.now()); //установка даты создания
        return messageRepository.save(mes);
    }

    @PutMapping("{id}")
    public Messages updateMessages(@PathVariable("id") Messages mes, @RequestBody Messages newmes ){
        BeanUtils.copyProperties(newmes,mes,"id", "dateCreate"); //с сновую скопируем неизменяемые поя
        return messageRepository.save(newmes);
    }

    @DeleteMapping("{id}")
    public void deleteMessage(@PathVariable("id") Messages mes){
        messageRepository.delete(mes);
    }
}
