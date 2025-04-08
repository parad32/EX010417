package com.example.ex01.controller;

import com.example.ex01.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Slf4j
public class MemberController {
    @GetMapping("/")
    public void testLog(){
        log.debug("debug execute");
        log.info("info execute");
        log.warn("warn execute");
        log.error("error execute");
    }
    @GetMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok("test");
    }
    @GetMapping("/members")
    public ResponseEntity memberAll(){
        ArrayList<MemberDTO> list = new ArrayList<>();
        for(int i=0 ; i<3 ; i++){
            MemberDTO dto =
                    new MemberDTO("aaa"+i, "aaa"+i, "role"+i);
            list.add(dto);
        }
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/members/{id}")
    public ResponseEntity memberOne(@PathVariable String id){
        log.debug("받은 id {} ", id);
        return ResponseEntity.ok(new MemberDTO("test","test","role"));
    }
    @PostMapping("/members")
    public ResponseEntity memberInsert(@RequestBody MemberDTO dto){
        log.debug("dto 확인 {}",dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("저장 성공");
    }
}

