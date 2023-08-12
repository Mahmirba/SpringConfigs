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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "privilege")
public class PrivilegeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "uri")
    private String uri;
    @ManyToMany(mappedBy = "privileges")
    @JsonIgnore
    private Collection<RoleEntity> roles;


    public Collection<RoleEntity> getRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        return roles;
    }
}
