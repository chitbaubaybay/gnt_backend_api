package controllers;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import models.Hero;
import play.mvc.Controller;
import play.mvc.Result;
import services.Counter;

/**
 * This controller demonstrates how to use dependency injection to
 * bind a component into a controller class. The class contains an
 * action that shows an incrementing count to users. The {@link Counter}
 * object is injected by the Guice dependency injection system.
 */
@Singleton
public class HeroController extends Controller {

    private List<Hero> heroes;

    @Inject
    public HeroController() {
        this.heroes = new ArrayList<>();
    }

    public Result create() {
        Hero hero = play.libs.Json.fromJson(ctx().request().body().asJson(), Hero.class);
        if(hero.getLevel() < 1) {
            return badRequest();
        } else {
            heroes.add(hero);
            return created();
        }
    }

    public Result get() {
        return ok(play.libs.Json.toJson(heroes));
    }

    public Result list() {
        return ok(views.html.heroList.render(heroes));
    }


}
