package dev.carolliie.BlogServer.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    private String name;

    @Column(length = 150000)
    private String content;

    @Column(length = 5000)
    private String img;

    @Column(length = 5000)
    private String slug;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private int likeCount;

    private int viewCount;


    @ElementCollection
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tags")
    private List<String> tags;
}
