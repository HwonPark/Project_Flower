package project.flower.domain.favorite;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.flower.domain.member.Member;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    // FK
    @OneToMany (mappedBy = "favoriteId")
    private List<FavoriteStore> favorites;
}
