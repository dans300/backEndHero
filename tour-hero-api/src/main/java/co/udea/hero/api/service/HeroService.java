package co.udea.hero.api.service;

import co.udea.hero.api.exception.BusinessException;
import co.udea.hero.api.model.Hero;
import co.udea.hero.api.repository.HeroRepository;
import co.udea.hero.api.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HeroService {

    private final Logger log = LoggerFactory.getLogger(HeroService.class);

    private HeroRepository heroRepository;
    private Messages messages;


    public HeroService(HeroRepository heroRepository, Messages messages) {
        this.heroRepository = heroRepository;
        this.messages = messages;
    }

    public Hero getHero(Integer id) {
        Optional<Hero> optionalHero = heroRepository.findById(id);
        if (!optionalHero.isPresent()) {
            log.info("No se encuentra un heroe con ID:" + id);
            throw new BusinessException(messages.get("exception.data_not_found.hero"));
        }
        return optionalHero.get();
    }

    public List<Hero> getHeroes() {
        //TODO mejorar el control de excepciones
        List<Hero> heroesList = heroRepository.findAll();
        return heroesList;
    }

    public Hero getHerobyName(String name) {
        List<Hero> optionalHero = heroRepository.findHeroByName(name);
        System.out.println(optionalHero.toString() + " YAAYAYYYAYAY");
        if (optionalHero.isEmpty()) {
            log.info("No se encuentra un heroe llamado:" + name);
            throw new BusinessException(messages.get("exception.data_not_found.hero"));
        }
        return optionalHero.get(0);
    }

    public Hero updateHero(Hero hero) {
        Optional<Hero> existe = heroRepository.findById(hero.getId());
        Hero h = null;
        if (existe.isPresent()) {
            h = existe.get();
            h.setName(hero.getName());
            return heroRepository.save(h);

        } else {
            log.info("No se encuentra un heroe llamado:" + hero.getName());
            throw new BusinessException(messages.get("exception.data_not_found.hero"));
        }

    }

    /*public Hero saveHero(Hero hero) {

        Optional<Hero> optionalHero = heroRepository.findById(hero.getId());
        if(!optionalHero.isPresent()){
             return heroRepository.save(hero);
        }
        log.info(" heroe duplicado por PK");
        throw new BusinessException(messages.get("exception.data_duplicate.hero"));

    }*/

    //SAVE HERO II
    public Hero saveHero(String name) {
        int i = -1;
        Optional<Hero> optionalHero;
        do {
            i += 1;
            optionalHero = heroRepository.findById(i);

        } while(optionalHero.isPresent());
        log.info("Guardamos a " + name);
        return heroRepository.save(new Hero(i,name));
    }

    public Hero  deleteHero(int id) {
        Optional<Hero> optionalHero = heroRepository.findById(id);
        if(!optionalHero.isPresent()){
            log.info(" heroe NO EXISTE");
            throw new BusinessException(messages.get("exception.data_not_found.hero"));
        }else
        {
            heroRepository.deleteById(id);
        }

        return optionalHero.get();
    }
}
