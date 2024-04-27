package org.hogent.olympisch_spelen_24.controller;

import org.hogent.olympisch_spelen_24.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sport")
public class SportController {
    @Autowired
    private SportRepository sportRepository;
}
