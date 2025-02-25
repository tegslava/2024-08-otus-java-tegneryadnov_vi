package ru.otus.java.pro.controllers;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.java.pro.entities.Message;
import ru.otus.java.pro.services.MessageService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    @GetMapping({"/", "/list"})
    public String messageListView(Model model) {
        var messagesDto = messageService.findAll();
        model.addAttribute("messages", messagesDto);
        return "messageList";
    }

    @GetMapping("/create")
    public String messageCreateView(Model model) {
        model.addAttribute("textMessage", "");
        return "messageCreate";
    }

    @PostMapping("/send")
    public RedirectView messageSend(@RequestParam(value = "textMessage") String textMessage) throws InterruptedException {
        Message message = messageService.create(textMessage);
        messageService.send(message);
        TimeUnit.SECONDS.sleep(1);
        return new RedirectView("/api/v1/messages/", true);
    }
}
