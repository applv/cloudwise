package com.applv.cloudwise.entity;

import static com.applv.cloudwise.entity.Constants.ID;
import static com.applv.cloudwise.entity.Constants.NAME;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = ID, nullable = false)
  private Integer id;

  @Column(name = NAME, nullable = false, length = 200)
  private String name;

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClazz(this) != getClazz(o)) {
      return false;
    }
    return id != null && Objects.equals(id, getClass().cast(o).getId());
  }

  @Override
  public final int hashCode() {
    return Objects.isNull(id) ? System.identityHashCode(this) : id.hashCode();
  }

  private Class<?> getClazz(Object o) {
    return o instanceof HibernateProxy
        ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
        : o.getClass();
  }
}
