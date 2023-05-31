package hello.entity;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    //@Column(nullable = false)
    //private String region;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(nullable = false)
    private String imageUrl;
    
    @Column(nullable = false)
    private int points;
    
    
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }*/

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void setImageUrl(String url) {
    	this.imageUrl = url;
    }
    public String getImageUrl() {
    	return this.imageUrl;
    }
    
    public void setPoints(int p) {
    	this.points = p;
    }
    public int getPoints() {
    	return this.points;
    }
    
    // Autres getters et setters
    
}