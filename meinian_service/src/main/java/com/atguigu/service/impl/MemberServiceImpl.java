package com.atguigu.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;


    @Override
    public List<Integer> getMemberCountByMonth(List<String> months) {
        List<Integer> list=new ArrayList<>();
        if (months!=null && months.size()>0){
            for (String month : months) {
             int count= memberDao.getMemberCountByMonth(month);
             list.add(count);
            }
        }
        return list;
    }
}
