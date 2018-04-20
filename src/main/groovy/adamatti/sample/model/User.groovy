package adamatti.sample.model

import groovy.transform.ToString

import javax.persistence.*

@Entity
@ToString(includeNames = true,ignoreNulls = true)
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id

    String firstname
    String lastname

    @OneToMany
    List<Task> tasks
}
