package com.atguigu.dao;

import com.atguigu.pojo.Member;

public interface MemberDao {


    void add(Member member);

    Member getMember(String telephone);

    int getMemberCountByMonth(String month);

    int getTodayNewMember(String today);

    int getTotalMember();

    int getThisWeekAndMonthNewMember(String weekMonday);
}
