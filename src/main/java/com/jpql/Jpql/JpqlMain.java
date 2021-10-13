package com.jpql.Jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.jpql.Jpql.domain.Member;
import com.jpql.Jpql.domain.MemberDto;
import com.jpql.Jpql.domain.Team;

public class JpqlMain {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			
			Member member = new Member();
			member.setUsername("member1");
			member.setAge(10);
			em.persist(member);
			
			List<MemberDto> result = em.createQuery("select new com.jpql.Jpql.domain.MemberDto(m.username, m.age) from Member m", MemberDto.class)
					.getResultList();
			
			MemberDto memberDto = result.get(0);
			System.out.println("memberDto = " + memberDto.getUsername());
			System.out.println("memberDto = " + memberDto.getAge());
			
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		
		emf.close();
	}

}
