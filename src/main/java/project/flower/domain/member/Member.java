package project.flower.domain.member;

import jakarta.persistence.*;
import lombok.*;
import project.flower.domain.Role;
import project.flower.domain.admin.Business;
import project.flower.domain.cart.Cart;
import project.flower.domain.favorite.Favorite;
import project.flower.domain.order.FlowerOrder;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String sex;

    @Enumerated(EnumType.STRING)
    private Role role;

    // FK
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Favorite favorite;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<FlowerOrder> flowerOrderList = new ArrayList<>();

    @OneToMany(mappedBy = "member",fetch = FetchType.EAGER)
    List<Business> businessList= new ArrayList<Business>();
}
