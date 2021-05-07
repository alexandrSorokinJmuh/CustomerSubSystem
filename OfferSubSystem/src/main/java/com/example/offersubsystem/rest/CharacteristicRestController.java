package com.example.offersubsystem.rest;

import com.example.offersubsystem.dto.CharacteristicDto;
import com.example.offersubsystem.entities.Characteristic;
import com.example.offersubsystem.services.CharacteristicService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/characteristic", produces = MediaType.APPLICATION_JSON_VALUE)
public class CharacteristicRestController {
    CharacteristicService characteristicService;

    public CharacteristicRestController(CharacteristicService characteristicService) {
        this.characteristicService = characteristicService;
    }

    @GetMapping("/characteristics")
    public List<Characteristic> indexRest() {
        return characteristicService.getAll();
    }

    @GetMapping("/characteristics/{characteristics_id}")
    public Characteristic showRest(@PathVariable("characteristics_id") int id) {
        return characteristicService.getById(id);
    }


    @PostMapping("")
    public Characteristic createRest(Characteristic characteristics) {
        characteristicService.create(characteristics);
        return characteristics;
    }

    @PutMapping("/{characteristics_id}")
    public Characteristic updateRest(@PathVariable("characteristics_id") int id,
                               CharacteristicDto characteristicDto
    ) {

        Characteristic characteristic = characteristicService.updateWithDto(characteristicDto);

        return characteristic;
    }
    @DeleteMapping("/{characteristic_id}")
    public Characteristic deleteRest(@PathVariable("characteristic_id") int id) {
        Characteristic characteristic = characteristicService.getById(id);
        characteristicService.delete(id);
        return characteristic;
    }
}
