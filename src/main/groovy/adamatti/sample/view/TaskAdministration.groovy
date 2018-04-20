package adamatti.sample.view

import adamatti.sample.model.Task
import groovy.util.logging.Slf4j
import org.lightadmin.api.config.AdministrationConfiguration
import org.lightadmin.api.config.builder.FiltersConfigurationUnitBuilder
import org.lightadmin.api.config.builder.ScopesConfigurationUnitBuilder
import org.lightadmin.api.config.unit.FiltersConfigurationUnit
import org.lightadmin.api.config.unit.ScopesConfigurationUnit
import org.lightadmin.api.config.utils.DomainTypeSpecification

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

import static org.lightadmin.api.config.utils.ScopeMetadataUtils.all
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.specification

@Slf4j
class TaskAdministration extends AdministrationConfiguration<Task> {
    TaskAdministration(){
        log.info "Class created"
    }

    ScopesConfigurationUnit scopes(final ScopesConfigurationUnitBuilder scopeBuilder ) {
        log.info "Scopes"

        scopeBuilder
            .scope( "All", all() )
            .scope( "Active", specification( activeSpec() ) ).defaultScope()
            .build()

    }

    FiltersConfigurationUnit filters(final FiltersConfigurationUnitBuilder filterBuilder ) {
        log.info "Filters"

        filterBuilder
            .filter( "active", "active" )
            .filter( "user", "user" )
            .build()
    }

    DomainTypeSpecification<Task> activeSpec() {
        new DomainTypeSpecification<Task>() {
            Predicate toPredicate(final Root<Task> root, final CriteriaQuery<?> query, final CriteriaBuilder cb ) {
                cb.equal( root.get( "active" ), true)
            }
        };
    }
}
