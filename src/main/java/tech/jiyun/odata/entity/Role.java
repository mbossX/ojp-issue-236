package tech.jiyun.odata.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Comment("角色名称")
    @Column(length = 32, nullable = false)
    private String name;

    @Comment("备注")
    @Column(length = 128)
    private String comment;

    @Comment("权限列表")
    @Column(columnDefinition = "text")
    private String permissions;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}
