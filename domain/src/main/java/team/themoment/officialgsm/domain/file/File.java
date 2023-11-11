package team.themoment.officialgsm.domain.file;

import team.themoment.officialgsm.domain.post.Post;

public record File(

        Long fileSeq,
        String fileUrl,
        String fileName,
        FileExtension fileExtension,
        Post post
) {
}
