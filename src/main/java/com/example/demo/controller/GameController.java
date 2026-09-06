package com.example.demo.controller;

import com.example.demo.model.Enemy;
import com.example.demo.model.Goblin;
import com.example.demo.model.Hero;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    private void initGame(HttpSession session) {
        if (session.getAttribute("hero") == null) {
            session.setAttribute("hero", new Hero("Воин"));
            session.setAttribute("enemy", new Goblin());
            session.setAttribute("logs", new ArrayList<String>());
        }
    }

    @GetMapping("/")
    public String showMainPage(HttpSession session, Model model) {
        initGame(session);

        model.addAttribute("hero", session.getAttribute("hero"));
        model.addAttribute("enemy", session.getAttribute("enemy"));
        model.addAttribute("logs", session.getAttribute("logs"));

        return "index";
    }

    @PostMapping("/attack")
    public String attack(HttpSession session) {
        Hero hero = (Hero) session.getAttribute("hero");
        Enemy enemy = (Enemy) session.getAttribute("enemy");
        List<String> logs = (List<String>) session.getAttribute("logs");

        if (hero.isAlive() && enemy.isAlive()) {
            hero.setDefending(false);

            // Атака Героя
            int rawHeroDamage = hero.attack();
            int actualHeroDamage = enemy.takeDamage(rawHeroDamage);
            logs.add(0, "⚔️ Вы атаковали " + enemy.getName() + " на " + actualHeroDamage + " урона (заблокировано броней: " + (rawHeroDamage - actualHeroDamage) + ")");

            // Ответный ход Врага
            processEnemyTurn(hero, enemy, logs);
        }

        return "redirect:/";
    }

    @PostMapping("/defend")
    public String defend(HttpSession session) {
        Hero hero = (Hero) session.getAttribute("hero");
        Enemy enemy = (Enemy) session.getAttribute("enemy");
        List<String> logs = (List<String>) session.getAttribute("logs");

        if (hero.isAlive() && enemy.isAlive()) {
            hero.setDefending(true);
            logs.add(0, "🛡️ Вы встали в защитную стойку! Ваша броня временно увеличена.");

            // Враг всё равно атакует, но нанесет меньше урона
            processEnemyTurn(hero, enemy, logs);
        }

        return "redirect:/";
    }

    @PostMapping("/heal")
    public String heal(HttpSession session) {
        Hero hero = (Hero) session.getAttribute("hero");
        Enemy enemy = (Enemy) session.getAttribute("enemy");
        List<String> logs = (List<String>) session.getAttribute("logs");

        if (hero.isAlive() && enemy.isAlive()) {
            hero.setDefending(false);

            int healed = hero.heal();
            logs.add(0, "🧪 Вы выпили зелье и восстановили " + healed + " HP");

            // Ответный ход Врага
            processEnemyTurn(hero, enemy, logs);
        }

        return "redirect:/";
    }

    @PostMapping("/restart")
    public String restart(HttpSession session) {
        Hero hero = (Hero) session.getAttribute("hero");
        Enemy enemy = (Enemy) session.getAttribute("enemy");
        List<String> logs = (List<String>) session.getAttribute("logs");

        if (hero != null) hero.reset();
        if (enemy != null) enemy.reset();
        if (logs != null) {
            logs.clear();
            logs.add("Бой начался заново!");
        }

        return "redirect:/";
    }

    private void processEnemyTurn(Hero hero, Enemy enemy, List<String> logs) {
        if (enemy.isAlive()) {
            int rawEnemyDamage = enemy.attack();
            int actualEnemyDamage = hero.takeDamage(rawEnemyDamage);
            logs.add(0, "💥 " + enemy.getName() + " нанес вам " + actualEnemyDamage + " урона (заблокировано броней: " + (rawEnemyDamage - actualEnemyDamage) + ")");
        } else {
            logs.add(0, "🏆 Вы одержали победу над " + enemy.getName() + "!");
        }

        if (!hero.isAlive()) {
            logs.add(0, "💀 Вы пали в бою...");
        }
    }
}