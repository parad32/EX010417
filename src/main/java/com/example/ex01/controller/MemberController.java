package com.example.ex01.controller;

import com.example.ex01.dto.MemberDTO;
import com.example.ex01.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService ms;

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
    @PutMapping("/members/{id}")
    public ResponseEntity memberUpdate(@PathVariable String id,
                                       @RequestBody MemberDTO dto){
        log.info("modify id : {}",id);
        log.info("modify dto : {}",dto);
        //return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/members/{id}")
    public ResponseEntity memberDelete(@PathVariable("id") String id){
        log.debug("삭제 : {}",id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/mem")
    public ResponseEntity insert(@RequestBody MemberDTO dto){
        log.debug("insert : {}", dto);
        int result = ms.insert( dto );
        if( result == 1 )
            return ResponseEntity.status(HttpStatus.CREATED).body("추가 성공");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("존재하는 id 임");
    }
    @GetMapping("/mem")
    public ResponseEntity getList(@RequestParam(defaultValue = "0") int start ){
        return ResponseEntity.status(HttpStatus.OK).body( ms.getList(start) );
    }
    @PutMapping("/mem/{id}")
    public ResponseEntity update1(@PathVariable("id") String id,
                                  @RequestBody MemberDTO dto){
        log.debug("id {}",id);
        log.debug("dto {}",dto);
        int result = ms.update(dto, id);
        if(result == 1 )
            return ResponseEntity.status(HttpStatus.OK).body("성공");
        else if(result == 0 )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("없음");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("필수");
    }
    @DeleteMapping("/mem/{id}")
    public ResponseEntity mDelete(@PathVariable("id") String id){
        int result = ms.mDelete(id);
        if(result == 1)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id없음");
    }
    @PostMapping("/mem/login2")
    public ResponseEntity login2(@RequestBody Map<String, String> map){//@RequestBody MemberDTO dto){
        log.debug("login map : {}", map);
        int result = ms.login( map.get("username"), map.get("password") );
        if( result == 0 )
            return ResponseEntity.status(HttpStatus.OK).body("성공");
        else if( result == 1)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비번틀림");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id없음");
    }
    @PostMapping("/mem/login")
    //@RequestParam String username, password
    public ResponseEntity login( @ModelAttribute MemberDTO dto ){
        log.debug("login dto : {}", dto);
        int result = ms.login( dto.getUsername(), dto.getPassword() );
        if( result == 0 )
            return ResponseEntity.status(HttpStatus.OK).body("성공");
        else if( result == 1)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비번틀림");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id없음");
    }
    @GetMapping("/mem/{id}")
    public ResponseEntity memOne(@PathVariable("id") String username){
        log.debug("받은 id {} ", username);
        MemberDTO dto = ms.getOne( username );
        if( dto != null )
            return ResponseEntity.ok( dto );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}













