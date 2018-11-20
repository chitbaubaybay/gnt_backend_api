package controllers;

import models.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import play.mvc.Controller;
import play.mvc.Result;

@Singleton
public class UserController extends Controller {

    private List<User> userList;

    @Inject
    public UserController() {
        this.userList = new ArrayList<>();
    }

    public Result create()
    {
        User user = play.libs.Json.fromJson(ctx().request().body().asJson(), User.class);
        String name = user.getName();
        boolean flag = true;

        if (  userList.size() > 0  ) {
            for (User savedUser : userList) {
                if (savedUser.getName().equals(name)) {
                    flag = false;
                    break;
                }
            }
        }

        if (!flag)
            return badRequest("User Existed");
        else {
            userList.add(user);
            return ok("Add User Success");
        }
    }

    public Result getOne()
    {
        String name = ctx().request().body().toString();
        User one = null;
        boolean flag = false;
        if (  userList.size() > 0  ) {
            for (User savedUser : userList) {
                if (savedUser.getName().equals(name)) {
                    one = savedUser;
                    flag = true;
                    break;
                }
            }
        }

        if (flag)
            return ok(play.libs.Json.toJson(one));
        else
            return badRequest("User Not Existed");
    }

    public Result putNew()
    {
        String name = ctx().request().body().toString();

        boolean flag = false;

        if (  userList.size() > 0  ) {
            for (User savedUser : userList) {
                if (savedUser.getName().equals(name)) {
                    User newUser = play.libs.Json.fromJson(ctx().request().body().asJson(), User.class);
                    savedUser.setHighestScore(newUser.getHighestScore());
                    savedUser.setLevel(newUser.getLevel());
                    flag = true;
                    break;
                }
            }
        }

        if (!flag)
            return badRequest("User Not Existed");
        else
            return ok();
    }

    public Result listAll()
    {
        return ok(play.libs.Json.toJson(userList));
    }

}
