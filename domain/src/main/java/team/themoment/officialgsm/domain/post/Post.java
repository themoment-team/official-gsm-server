package team.themoment.officialgsm.domain.post;

import team.themoment.officialgsm.domain.file.File;
import team.themoment.officialgsm.domain.user.User;

import java.util.ArrayList;
import java.util.List;

public record Post(

        Long postSeq,
        String postTitle,
        String postContent,
        Category category,
        List<File> files,
        User user
) {
}
