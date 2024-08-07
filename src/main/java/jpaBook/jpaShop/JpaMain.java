package jpaBook.jpaShop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpaBook.jpaShop.domain.*;

import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member member = new Member();
            member.setName("member1");
            member.setHomeaddress(new Address("homeCity", "street", "12345"));
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            member.getAddressHistory().add(new Address("old1", "street1","10000"));
            member.getAddressHistory().add(new Address("old2", "Street3","12345"));

            em.persist(member);

            em.flush();
            em.clear();
            System.out.println("==========START=========");
            Member findMember = em.find(Member.class, member.getId());

            //findMember.getHomeaddress().setCity("newCity");
            Address a = findMember.getHomeaddress();
            findMember.setHomeaddress(new Address("newCity", a.getStreet(), a.getZipcode()));
            //치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new Address("old1", "street1","10000"));
            findMember.getAddressHistory().add(new Address("newCity1", "flindersSt", "10000"));

//            값 컬렉션 조회
//            List<Address> addresseHisory = findMember.getAddressHistory();
//            for(Address address : addresseHisory){
//                System.out.println("address = " + address.getCity());
//            }
//            Set<String> favoriteFoods = findMember.getFavoriteFoods();
//            for(String favoriteFood : favoriteFoods){
//                System.out.println("address = " + favoriteFood);
//            }
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
