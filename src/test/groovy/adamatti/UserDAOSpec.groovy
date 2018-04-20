package adamatti

import adamatti.sample.model.User
import groovy.util.logging.Slf4j
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

@Slf4j
class UserDAOSpec extends Specification {
    private static final def context = buildContext()

    def cleanupSpec(){
        context.close()
    }

    def "crud test"(){
        when:
            listBeans(context)
            //def emf = context.getBean(EntityManagerFactory)
            def em = context.getBean(EntityManager)
            doWithTransaction(em) {
                // List
                list(em, User)

                // Count
                println "Count: ${count(em)}"

                // Insert
                def user = new User(firstname: "Marcelo")
                em.persist(user)
                em.flush()
                println "User Created: ${user.id}"

                // update
                user.lastname = "Adamatti"
                em.persist(user)
                em.flush()

                // Find
                user = em.find(User, user.id)

                // List
                list(em, User)

                // Delete
                em.remove(user)
                em.flush()
            }
            em.close()
        then:
            noExceptionThrown()
    }

    private <T> void list(EntityManager em, Class<T> entity){
        CriteriaBuilder cb = em.getCriteriaBuilder()
        CriteriaQuery<T> cq = cb.createQuery(entity)
        Root<T> rootEntry = cq.from(entity)
        CriteriaQuery<T> all = cq.select(rootEntry)
        TypedQuery<T> allQuery = em.createQuery(all)
        List result = allQuery.getResultList()

        result.each { T row ->
            println row
        }
    }

    private Long count(EntityManager em){
        CriteriaBuilder qb = em.getCriteriaBuilder()
        CriteriaQuery<Long> cq = qb.createQuery(Long.class)
        cq.select(qb.count(cq.from(User.class)))
        em.createQuery(cq).getSingleResult()
    }

    private void doWithTransaction (EntityManager em, Closure func){
        def tx = em.transaction
        tx.begin()
        func.call()
        tx.commit()
    }

    private void listBeans(ApplicationContext context){
        context.getBeansOfType(Object).each {k, v ->
            log.info k
        }
    }

    private static ConfigurableApplicationContext buildContext(){
        def context = new ClassPathXmlApplicationContext("classpath:/spring/main.xml")
        context.registerShutdownHook()
        context
    }
}

