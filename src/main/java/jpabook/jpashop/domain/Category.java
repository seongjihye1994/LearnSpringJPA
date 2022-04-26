package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    /**
     * 실무에서는 N : M 사용 금지!!
     * N : M 테이블은 반드시 중간에 서로를 이어주는 프록시 테이블을 별도로 구축해야 한다.
     *
     * @JoinTable 로 프록시 테이블 구축하기.
     * 여러개의 카테고리(N)와 여러개의 아이템(M)은 서로 다대다 테이블
     * -> 프록시 테이블 설정하기
     *      name : 조인 테이블명
     *      joinColumns : Category 엔티티 FK
     *      inverseJoinColumns : Items 엔티티 FK
     *
     */
    @ManyToMany
    @JoinTable(name = "category_item", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // == 연관관계 메서드 == //
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this); // 자식 카테고리 설정 시 부모 카테고리 양방향 연관관계 설정
    }
}
