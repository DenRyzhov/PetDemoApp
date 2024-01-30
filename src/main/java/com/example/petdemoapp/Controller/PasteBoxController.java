package com.example.petdemoapp.Controller;



import Service.PasteBoxService;
import api.request.PasteBoxRequest;
import api.request.response.PasteBoxResponse;
import api.request.response.PasteBoxUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;


@RestController
@RequiredArgsConstructor
public class PasteBoxController {
    public PasteBoxService pasteBoxService;

    @GetMapping("/")
    public Collection<PasteBoxResponse> getPublicPasteList() {
        return pasteBoxService.getFirstPublicPasteBoxes();
    }

    @GetMapping("/{hash}")
    public PasteBoxResponse getByHash(@PathVariable String hash) {
        return pasteBoxService.getByHash(hash);
    }

    @PostMapping("/")
    public PasteBoxUrlResponse add(@RequestBody PasteBoxRequest request){
        return pasteBoxService.create(request);
    }
}


