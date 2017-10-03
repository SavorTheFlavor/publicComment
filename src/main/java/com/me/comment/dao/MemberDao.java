package com.me.comment.dao;

import java.util.List;

import com.me.comment.bean.Member;

public interface MemberDao {

	List<Member> select(Member member);

}
