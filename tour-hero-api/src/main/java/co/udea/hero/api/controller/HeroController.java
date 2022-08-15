package co.udea.hero.api.controller;
import co.udea.hero.api.model.Hero;
import co.udea.hero.api.service.HeroService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/heroes")
public class HeroController {

    private final Logger log = LoggerFactory.getLogger(HeroController.class);

    private HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Busca un hero por su id",  response = Hero.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Hero encontrado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Hero> getHero(@PathVariable Integer id){
        log.info("Rest request buscar heroe por id: "+ id);
        return ResponseEntity.ok(heroService.getHero(id));
    }

    @GetMapping("")
    @ApiOperation(value = "Buscar todos los heroes",  response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Heroes encontrado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<List<Hero>> getHeroes(){
        log.info("Rest request buscar heroes");
        ResponseEntity<List<Hero>> lista=ResponseEntity.ok(heroService.getHeroes());
        return lista;
    }

    @GetMapping("name/{name}")
    @ApiOperation(value = "Busca un hero por su nombre",  response = Hero.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Hero encontrado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Hero> searchHeroes(@PathVariable String name){
        log.info("Rest request buscar heroe por nombre: "+ name);
        return ResponseEntity.ok(heroService.getHerobyName(name));
        //tal vez diferenciando si le llega string o int en el metodo byid orginial
    }

    @PutMapping("")
    @ApiOperation(value = "Actualiza  un hero , su nombre",  response = Hero.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Hero encontrado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Hero> updateHero(@RequestBody Hero hero)
    {
        log.info("Rest request buscar heroe y actualizar nombre");
        return ResponseEntity.ok(heroService.updateHero(hero));
    }


    @PostMapping("crear")
    @ApiOperation(value = "crea un hero ",  response = Hero.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Hero anadido existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Hero> addHero(@RequestBody Hero hero) {
        log.info("adicionar heroe"+hero.getName());
        return ResponseEntity.ok(heroService.saveHero(hero.getName()));
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "borra un hero ",  response = Hero.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Hero borrado existosamente"),
            @ApiResponse(code = 400, message = "La petición es invalida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<Hero> deleteHero(@PathVariable int id){
        return ResponseEntity.ok(heroService.deleteHero(id));
    }
}
