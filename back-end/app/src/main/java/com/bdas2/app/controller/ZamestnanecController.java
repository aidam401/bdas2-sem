package com.bdas2.app.controller;

import com.bdas2.app.model.Zamestnanec;
import com.bdas2.app.repository.modelRepositories.ZamestnanecRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Zamestnanec")
public class ZamestnanecController {
    public final ZamestnanecRepository repo;

    public ZamestnanecController(ZamestnanecRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/detail")
    public ResponseEntity read(@RequestParam Integer id) {
        try {
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
    public ResponseEntity create(@RequestBody Zamestnanec body) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repo.create(body));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Chyba na controller vrstvě " + ex.getMessage()+ ex.getCause());
        }
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestParam Integer id, @RequestBody Zamestnanec body) {
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
