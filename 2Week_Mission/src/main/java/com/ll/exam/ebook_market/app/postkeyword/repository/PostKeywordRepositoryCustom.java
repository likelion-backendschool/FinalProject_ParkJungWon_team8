package com.ll.exam.ebook_market.app.postkeyword.repository;

import com.ll.exam.ebook_market.app.postkeyword.entity.PostKeyword;

import java.util.List;

public interface PostKeywordRepositoryCustom {
    List<PostKeyword> getQslAllByAuthorId(Long authorId);
}
