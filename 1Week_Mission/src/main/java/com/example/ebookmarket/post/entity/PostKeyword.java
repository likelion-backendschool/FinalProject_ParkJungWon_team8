package com.example.ebookmarket.post.entity;

import com.example.ebookmarket.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class PostKeyword extends BaseEntity {

    private String content;

}
