package tech.jiyun.odata.entity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.Comment;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Comment("姓名")
    @Column(length = 32)
    private String name;

    @Comment("邮箱")
    private String email;

    @Comment("用户名 - 手机号")
    @Column(length = 11, unique = true, nullable = false)
    private String username;

    @Column(length = 16, nullable = false)
    private String password;
    
    @Comment("状态")
    @Column(nullable = false, columnDefinition = "tinyint DEFAULT 0")
    private Integer status;

    @Comment("角色")
    @Column(name = "role_id")
    private Integer roleId;
    // @EdmIgnore
    // @Column(name = "role_id", precision = 10, insertable = false, updatable = false)
    // private Integer roleId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Role role;
}
