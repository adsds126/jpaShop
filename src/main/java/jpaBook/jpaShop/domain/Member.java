package jpaBook.jpaShop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Member extends BaseEntity{
    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue
    private Long id;
    private String name;
    @Embedded
    private Address homeaddress;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD",
            joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

    @OrderColumn(name = "address_history_order")
    @ElementCollection
    @CollectionTable(name = "ADDRESS",
            joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressHistory = new ArrayList<>();



    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
