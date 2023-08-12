package org.example.repo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_ROLE_PRIVILEGE_1")),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_PRIVILEGE_ROLE_1")),
            uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "privilege_id"})
    )
    @JsonIgnore
    private Collection<PrivilegeEntity> privileges;

    public Collection<PrivilegeEntity> getPrivileges() {
        if (privileges == null)
            privileges = new ArrayList<>();
        return privileges;
    }
}

