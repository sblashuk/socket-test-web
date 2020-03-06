package com.sockettestweb.controller;

import com.sockettestweb.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class SocketViewController {

    private ClientService clientService;

    @GetMapping("/clients")
    public String clients(final Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "clients";
    }
}
