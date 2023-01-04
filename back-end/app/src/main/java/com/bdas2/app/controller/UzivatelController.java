package com.bdas2.app.controller;
import com.bdas2.app.model.Uzivatel;
import com.bdas2.app.repository.modelRepositories.UzivatelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;


@CrossOrigin
@RestController
@RequestMapping("/uzivatel")
@Slf4j
public class UzivatelController {
    public final UzivatelRepository repo;

    public UzivatelController(UzivatelRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/detail")
    public ResponseEntity read(@RequestParam Integer id, HttpResponse idk) {
        try {
            log.info("", idk);
            return ResponseEntity.status(HttpStatus.OK).body(repo.read(id));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Chyba na controller vrstvě " + ex.getMessage() + ex.getCause());
        }
    }

    @GetMapping("/get")
    public ResponseEntity get(@RequestParam Integer offset, @RequestParam Integer limit) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repo.readList(offset, limit));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Chyba na controller vrstvě " + ex.getMessage() + ex.getCause());
        }
    }

    @GetMapping("/search")
    public ResponseEntity get(@RequestParam Integer offset, @RequestParam Integer limit, @RequestParam String query) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repo.readList(offset, limit, query));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Chyba na controller vrstvě " + ex.getMessage() + ex.getCause());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repo.delete(id));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Chyba na controller vrstvě " + ex.getMessage() + ex.getCause());
        }
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity create(@RequestBody Uzivatel body) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repo.create(body));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Chyba na controller vrstvě " + ex.getMessage()+ ex.getCause());
        }
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam Integer id, @RequestBody Uzivatel body) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repo.update(id, body));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Chyba na controller vrstvě " + ex.getMessage() + ex.getCause());
        }
    }
    @GetMapping("/count")
    public ResponseEntity count() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repo.count());
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Chyba na controller vrstvě " + ex.getMessage() + ex.getCause());
        }
    }

}
