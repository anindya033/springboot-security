package com.crafter.security.redisplayground.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crafter.security.redisplayground.redisservice.RedisService;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    // --------------------
    // STRING
    // --------------------

    @PostMapping("/testApi/get")
    public String testAPI() {
        System.out.println("Test Resids 1");
        return "Hello";
    }

    @PostMapping("/string/save/{key}")
    public String saveString(@PathVariable String key, @RequestBody HashMap<String, Object> map) {
        System.out.println("Saving String : " + map);
        redisService.save(key, map, 60);
        return "Saved (String) key=" + key + ", value=" + map;
    }

    @GetMapping("/string/get/{key}")
    public Object getString(@PathVariable String key) {
        return redisService.get(key);
    }

    @DeleteMapping("/string/delete/{key}")
    public String deleteString(@PathVariable String key) {
        boolean deleted = redisService.delete(key);
        return deleted ? "Deleted key=" + key : "Key not found: " + key;
    }

    // --------------------
    // LIST
    // --------------------

    @PostMapping("/list/push/{key}")
    public String pushToList(@PathVariable String key, @RequestBody Object value) {
        redisService.pushToList(key, value);
        return "Pushed to list=" + key + ", value=" + value;
    }

    @GetMapping("/list/get/{key}")
    public List<Object> getList(@PathVariable String key) {
        return redisService.getList(key);
    }

    @GetMapping("/list/pop/{key}")
    public Object popFromList(@PathVariable String key) {
        return redisService.popFromList(key);
    }

    // --------------------
    // SET
    // --------------------

    @PostMapping("/set/add/{key}")
    public String addToSet(@PathVariable String key, @RequestBody Set<Object> values) {
        redisService.addToSet(key, values.toArray());
        return "Added to set=" + key + ", values=" + values;
    }

    @GetMapping("/set/get/{key}")
    public Set<Object> getSet(@PathVariable String key) {
        return redisService.getSet(key);
    }

    @GetMapping("/set/ismember/{key}/{value}")
    public boolean isMemberOfSet(@PathVariable String key, @PathVariable String value) {
        return redisService.isMemberOfSet(key, value);
    }

    // --------------------
    // HASH (Map)
    // --------------------

    @PostMapping("/hash/put/{key}/{hashKey}")
    public String putToHash(@PathVariable String key, @PathVariable String hashKey, @RequestBody Object value) {
        redisService.putToHash(key, hashKey, value);
        return "Added to hash=" + key + ", field=" + hashKey + ", value=" + value;
    }

    @GetMapping("/hash/get/{key}/{hashKey}")
    public Object getFromHash(@PathVariable String key, @PathVariable String hashKey) {
        return redisService.getFromHash(key, hashKey);
    }

    @DeleteMapping("/hash/delete/{key}/{hashKey}")
    public String deleteFromHash(@PathVariable String key, @PathVariable String hashKey) {
        redisService.deleteFromHash(key, hashKey);
        return "Deleted field=" + hashKey + " from hash=" + key;
    }

    // --------------------
    // SORTED SET (ZSET)
    // --------------------

    @PostMapping("/zset/add/{key}")
    public String addToSortedSet(@PathVariable String key, @RequestParam Object value, @RequestParam double score) {
        redisService.addToSortedSet(key, value, score);
        return "Added to sorted set=" + key + ", value=" + value + ", score=" + score;
    }

    @GetMapping("/zset/get/{key}")
    public Set<Object> getSortedSet(@PathVariable String key) {
        return redisService.getSortedSet(key);
    }
}
