package com.workintech.s17d2.rest;

import com.workintech.s17d2.dto.DeveloperResponse;
import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.DeveloperFactory;
import com.workintech.s17d2.model.JuniorDeveloper;
import com.workintech.s17d2.model.SeniorDeveloper;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/developers") // localhost:8585/workintech/developers
public class DeveloperController {

    public Map<Integer, Developer> developers;
    private Taxable taxable;

    //Injection
    @Autowired
    public DeveloperController(/*@Qualifier("developerTax") => 1 den fazla implementasyon varsa*/ Taxable taxable){
        this.taxable = taxable;
    }

    @PostConstruct
    public void init(){
        this.developers = new HashMap<>();
        this.developers.put(1, new SeniorDeveloper(1,"Bilun",4000d/* Experience.SENIOR*/));
        this.developers.put(2, new JuniorDeveloper(2,"Hilal",6000d/* Experience.SENIOR*/));
        this.developers.put(3, new JuniorDeveloper(3,"Berkay",6000d/* Experience.SENIOR*/));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeveloperResponse save(@RequestBody Developer developer) {
        Developer createdDeveloper = DeveloperFactory.createDeveloper(developer, taxable);
        if (Objects.nonNull(createdDeveloper)) {
            developers.put(createdDeveloper.getId(), createdDeveloper);
        }
        return new DeveloperResponse(createdDeveloper,"Create başarılı", HttpStatus.CREATED.value());
    }

    @GetMapping
    public List<Developer> getAll(){
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public DeveloperResponse getById(@PathVariable("id") int id){
        Developer found = this.developers.get(id);
        if(found == null){
            return new DeveloperResponse(null, id + "değerinde kayıt bulunamadı.", HttpStatus.NOT_FOUND.value());
        }
        return new DeveloperResponse(found,"id ile search başarılı", HttpStatus.OK.value());
    }

    @PutMapping("/{id}")
    public DeveloperResponse update(@PathVariable("id") int id, Developer developer){
        developer.setId(id);
        Developer newDeveloper = DeveloperFactory.createDeveloper(developer,taxable);
        this.developers.put(id, newDeveloper);
        return new DeveloperResponse(newDeveloper,"Başarılı",HttpStatus.OK.value());
    }

    @DeleteMapping("/{id}")
    public DeveloperResponse delete(@PathVariable int id){
        Developer deleteDeveloper = this.developers.get(id);
        this.developers.remove(id);
        return new DeveloperResponse(deleteDeveloper,"Başarıyla silindi",HttpStatus.NO_CONTENT.value());
    }
}
