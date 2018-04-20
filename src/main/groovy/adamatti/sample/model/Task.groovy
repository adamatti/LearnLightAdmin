package adamatti.sample.model

import javax.persistence.*

@Entity
class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id

    String name

    Boolean active

    @ManyToOne
    User user
}
