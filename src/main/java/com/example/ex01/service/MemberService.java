package com.example.ex01.service;

import com.example.ex01.domain.MemberEntity;
import com.example.ex01.dto.MemberDTO;
import com.example.ex01.repo.MemberDataSet;
import com.example.ex01.repo.MemberRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    MemberDataSet ds;
    private final MemberRepo repo;
    private final HttpSession session;

    public int insert(MemberDTO dto){
        int result = 0;
        //result = ds.insert(dto);
        try {
            repo.save( new MemberEntity( dto ) ) ;
            result = 1;
        } catch (Exception e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return result;
    }
    public Map<String, Object> getList(int start ){
        start = start > 0? start-1: start;
        int size = 3; //한페이지 3개 글
        Pageable pageable = PageRequest.of(start, size,
                Sort.by( Sort.Order.desc("id")) );
        Page<MemberEntity> page = repo.findAll( pageable );
        List<MemberEntity> listE = page.getContent();
        Map<String,Object> map = new HashMap<>();
        map.put("list", listE.stream().map(entity
                                -> new MemberDTO(entity)).toList() );
        map.put("totalPages", page.getTotalPages() );
        map.put("currentPage", page.getNumber()+1 );
        return map;
        //return ds.getList();
        /*
        return repo.findAll().stream()
                .map( entity -> new MemberDTO(entity) )
                .toList();
         */
    }
    public int update(MemberDTO dto,String id){
        if(dto.getUsername() == null || dto.getPassword() == null ||
                        dto.getRole() == null )
            return -1;
        //return ds.update(dto, id);
        MemberEntity entity = repo.findByUsername( dto.getUsername() );
        if( entity != null ){
            //entity.setUsername();
            entity.setPassword( dto.getPassword() );
            entity.setRole( dto.getRole() );
            repo.save( entity );
            return 1;
        }
        return 0;
    }
    public int mDelete(String id){
        //return ds.mDelete(id);
        MemberEntity entity = repo.findByUsername( id );
        if(entity != null){
            repo.delete( entity );
            return 1;
        }
        return 0;
    }

    public int login( String username, String password ){
        int result = -1;
        //result = ds.login(username, password);
        MemberEntity entity = repo.findByUsername(username);
        if( entity != null ){
            result = 1;
            if(entity.getPassword().equals(password) ){
                session.setAttribute("username", username);
                result = 0;
            }
        }
        return result;
    }
    public MemberDTO getOne( String username ){
        //MemberDTO dto = null;
        //dto = ds.getOne( username );
        //return dto;
        return new MemberDTO( repo.findByUsername(username) );
    }
}







