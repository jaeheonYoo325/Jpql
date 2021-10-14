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
			
			Team team = new Team();
			team.setName("teamA");
			em.persist(team);
			
			Member member = new Member();
			member.setUsername("teamA");
			member.setAge(10);
			member.setTeam(team);
			
			em.persist(member);
			
			
			em.flush();
			em.clear();
			
			String query = "select m from Member m left join Team t on m.username = t.name";
			List<Member> result = em.createQuery(query, Member.class)
					.getResultList();
			
			System.out.println("result = " + result.size());
			
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
