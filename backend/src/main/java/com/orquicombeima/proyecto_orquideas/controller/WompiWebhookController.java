package com.orquicombeima.proyecto_orquideas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
public class WompiWebhookController {

    private final WompiWebhookController wompiWebhookController;
}
