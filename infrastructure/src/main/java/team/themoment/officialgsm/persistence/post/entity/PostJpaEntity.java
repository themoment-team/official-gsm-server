package team.themoment.officialgsm.persistence.post.entity;

import jakarta.persistence.*;
import lombok.*;
import team.themoment.officialgsm.domain.post.Category;
import team.themoment.officialgsm.persistence.file.entity.FileJpaEntity;
import team.themoment.officialgsm.persistence.user.entity.UserJpaEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_seq")
    private Long postSeq;

    @Column(name = "post_title", nullable = false, length = 61)
    private String postTitle;

    @Column(name = "post_content", nullable = false, length = 5001)
    private String postContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "post")
    private List<FileJpaEntity> files = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq", nullable = false)
    private UserJpaEntity user;
}
