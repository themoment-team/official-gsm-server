package team.themoment.officialgsm.persistence.file.entity;

import jakarta.persistence.*;
import lombok.*;
import team.themoment.officialgsm.domain.file.FileExtension;
import team.themoment.officialgsm.persistence.post.entity.PostJpaEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_seq")
    private Long fileSeq;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_extension", nullable = false)
    private FileExtension fileExtension;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_seq", nullable = false)
    private PostJpaEntity post;
}
