package com.vet24.web.controllers.media;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.reflections.Reflections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@Tag(name = "Enums Controller", description = "Return lists")
public class EnumsController {


    @GetMapping("/api/enums")
    public ResponseEntity<List> findAllEnums() {
        List<String> result = new ArrayList<>();
        Reflections reflections = new Reflections("com.vet24.models.enums");
        Set<Class<? extends Enum>> classez = reflections.getSubTypesOf(Enum.class);
        for (Class<? extends Enum> cl : classez) {
            result.add(cl.getName().replaceFirst("com.vet24.models.enums.", ""));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/api/enums/{enumName}")
    public ResponseEntity<List> getEnumNameList(@PathVariable String enumName) {
        String pkg = "com.vet24.models.enums";
        String found = "com.vet24.models.enums." + enumName;
        List<String> result = new ArrayList<>();
        Reflections reflections = new Reflections(pkg);
        Set<Class<? extends Enum>> classez = reflections.getSubTypesOf(Enum.class);

        for (Class<? extends Enum> cl : classez) {
            if (found.contains(cl.getName())) {
                Enum[] o = cl.getEnumConstants();
                for (Enum k : o) {
                    result.add(k.name());
                }
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
